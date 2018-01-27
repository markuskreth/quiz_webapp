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
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script>

function nextQuestion() {
	$.get("quiz?next", function(responseText) {
		$("#content").append(responseText).append("<br>");
	});
	
	updateStatistics();
}

function updateStatistics() {
	$.get("quiz?anzahlQuestions", function(responseText) {
		$("#anzahlQuestions").text(responseText);
	});
	
	$.get("quiz?anzahlAntworten", function(responseText) {
		$("#anzahlAntworten").text(responseText);
	});
	
	$.get("quiz?anzahlRichtig", function(responseText) {
		$("#anzahlRichtig").text(responseText);
	});
	
}

$(document).ready(function(){
	$.get("quiz?title", function(responseText) {
		$("title").text(responseText);
	});
	
	updateStatistics();
	}); 
</script>

</head>
<body>
	<h3>
		Challenge start at 
	</h3>

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
	 <button type="button" onclick="nextQuestion()">Click Me!</button> 
	</div>
	<div><%= request.getRequestURL() %></div>
	<div><%= request.getLocale() %></div>
</body>
</html>