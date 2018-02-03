<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="de.kreth.quiz.WebController"%>
<%@ page import="de.kreth.quiz.data.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tit</title>
<link rel="stylesheet" href="css/quiz.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="js/js.cookie.js"></script>
<script src="js/quiz.js"></script>

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
		<button type="button" onclick="newQuiz()">Ende</button>
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