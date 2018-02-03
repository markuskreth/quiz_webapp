/**
 * 
 */

function nextQuestion() {
	$("#answers").empty();
	$.ajax({
		type : "GET",
		url : "quiz?next",
		error : function(xhr, statusText) {
			alert("Error: " + statusText);
		},
		success : function(question) {
			if(question.finished) {
				$("#questionText").text("Beendet!");
			} else {
				$("#questionText").text(question.question);
				setupAnswerRows(question.answers, question.id);
				updateStatistics();
			}
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
		$("#anzahlQuestions").text(quiz.anzahlQuestions);
		$("#anzahlAntworten").text(quiz.anzahlAntworten);
		$("#anzahlRichtig").text(quiz.anzahlRichtig);
	} else {
		updateStatistics();
	}
}

$(document).ready(function() {

	$.ajax({
		type: "PUT",
		url : "quiz?restart",
  		contentType: "application/json",
		error : function(xhr, statusText) {
			alert("Error: " + statusText);
		},
		success: updateStatistics
	});
});
