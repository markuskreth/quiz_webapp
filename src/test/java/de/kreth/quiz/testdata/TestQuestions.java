package de.kreth.quiz.testdata;

import de.kreth.quiz.data.Answer;
import de.kreth.quiz.data.Question;

public class TestQuestions {

	public static Question q1() {
		return Question
				.build()
				.setId(1L)
				.setQuestion("Die Frage")
				.add(Answer.build().setId(1L).setText("Antwort 1").setCorrect(false).build())
				.add(Answer.build().setId(2L).setText("Antwort 2").setCorrect(true).build())
				.add(Answer.build().setId(3L).setText("Antwort 3").setCorrect(false).build())
				.build();
	}
}
