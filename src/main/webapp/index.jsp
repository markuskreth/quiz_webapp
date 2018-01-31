<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="de.kreth.quiz.WebController"%>
<%@ page import="de.kreth.quiz.data.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tit</title>
<style type="text/css">
.tg {
	border-collapse: collapse;
	border-spacing: 0;
	border-color: #ccc;
	margin: 0px auto;
	width: 50%;
}

.tg td {
	font-family: Arial, sans-serif;
	font-size: 14px;
	padding: 10px 5px;
	border-style: solid;
	border-width: 1px;
	overflow: hidden;
	word-break: normal;
	border-color: #ccc;
	color: #333;
	background-color: #fff;
}

.tg th {
	font-family: Arial, sans-serif;
	font-size: 14px;
	font-weight: normal;
	padding: 10px 5px;
	border-style: solid;
	border-width: 1px;
	overflow: hidden;
	word-break: normal;
	border-color: #ccc;
	color: #333;
	background-color: #f0f0f0;
}

.tg .tg-yw4l {
	vertical-align: top
}

.tg .tg-b7b8 {
	background-color: #f9f9f9;
	vertical-align: top
}

#content {
	height: 60ex;
}

#templates {
	display: none;
}

.answerresult {
	width: 2em;
	height: 2em;
}

.answerresult.correct {
	background-color: green;
}

.answerresult.incorrect {
	background-color: red;
}
</style>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="js/js.cookie.js"></script>

<script>
	function nextQuestion() {
		$("#answers").empty();
		$.ajax({
			type : "GET",
			url : "quiz?next",
			error : function(xhr, statusText) {
				alert("Error: " + statusText);
			},
			success : function(question) {
				$("#questionText").text(question.question);
				setupAnswerRows(question.answers, question.id);
				updateStatistics();
			}
		});
	}

	function setupAnswerRows(answers, questionId) {
		var table = $("#answers");
		var index = 0;
		answers.forEach(function(el) {
			var row = $("#templates #answerTemplate").clone();
			row.prop("id", "answer" + index);
			row.prop("index", index);
			row.prop("answer", el);
			row.prop("questionId", questionId);
	
			var box = row.find("#checkboxcell [name='answerSelection']");
			box.prop('id', "answer_box" + index);
			box.prop("index", index);
			box.change(answerCheckboxChange);
			box.prop('answer', el);
			box.prop('checked', false);
			row.find("#text").text(el.text);
			var resultBox = row.find("#answerresult");
			resultBox.prop('id', "answer_resultBox" + index);
			resultBox.prop("index", index);
			resultBox.removeClass("correct");
			resultBox.removeClass("incorrect");
			table.append(row);
		});
	}
	
	function answerCheckboxChange() {
		var answer = $(this).prop("answer");
		var theRow = $(this).parent().parent();
		$("#answers :input").prop('disabled', true);
		var answerBox = theRow.find(".answerresult");
		if (answer.correct) {
			answerBox.addClass("correct");
		} else {
			answerBox.addClass("incorrect");
			theRow.parent().find("tr").each(function (index, row) {
				var box = $(this).find("[name='answerSelection']");
				var answer = box.prop("answer");
				if(answer.correct){
					$(this).find(".answerresult").addClass("correct");
				}
			});
		}
		
		var msg =  {};
		msg.question = theRow.prop("questionId");
		msg.answer = answer.id;
		
		$.ajax({
			type: "PUT",
			url : "quiz",
      		contentType: "application/json",
			data: msg,
			error : function(xhr, statusText) {
				alert("Error: " + statusText);
			},
			success: updateQuizData
		});
	}

	function updateStatistics() {
		$.get("quiz?quiz", function(quiz) {
			updateQuizData(quiz);
		});
	}

	function updateQuizData(quiz) {
		if(quiz) {
			$("title").text(quiz.title);
			$("#content").append(quiz.today).append("<br>");
			$("#anzahlQuestions").text(quiz.anzahlQuestions);
			$("#anzahlAntworten").text(quiz.anzahlAntworten);
			$("#anzahlRichtig").text(quiz.anzahlRichtig);
		} else {
		updateStatistics();
		}
	}

	$(document).ready(function() {
		var sessId = Cookies.get("JSESSIONID", { path: "/quiz-webapp" });
		Cookies.remove("JSESSIONID", { path: "/quiz-webapp" });
		document.cookie = 'JSESSIONID=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
		updateStatistics();
	});
</script>

</head>
<body>
	<table class="tg">
		<tr>
			<th class="tg-yw4l">Anzahl</th>
			<th class="tg-yw4l">Beantwortet</th>
			<th class="tg-yw4l">Korrekt</th>
		</tr>
		<tr>
			<td class="tg-b7b8" id="anzahlQuestions"></td>
			<td class="tg-b7b8" id="anzahlAntworten"></td>
			<td class="tg-b7b8" id="anzahlRichtig"></td>
		</tr>
	</table>
	<div id="content">
		<div id="questionText"></div>
		<table class="tg" id="answers">
		</table>
		<button type="button" onclick="nextQuestion()">Ende</button>
		<button type="button" onclick="nextQuestion()">Nächste</button>
	</div>
	<div><%=request.getRequestURL()%></div>
	<div><%=request.getLocale()%></div>
	<div id="templates">
		<table>
			<tr id="answerTemplate">
				<td class="tg-b7b8" id="checkboxcell">
					<input type="radio" name="answerSelection" value="" />
				</td>
				<td class="tg-b7b8" id="text"></td>
				<td class="tg-b7b8 answerresult" id="answerresult"></td>
			</tr>
		</table>
	</div>
</body>
</html>