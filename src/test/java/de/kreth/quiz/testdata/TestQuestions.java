package de.kreth.quiz.testdata;

import de.kreth.quiz.data.Answer;
import de.kreth.quiz.data.Question;

public class TestQuestions {

	public static Question q1() {
		return Question
				.build()
				.setId(1)
				.setQuestion("Die Frage")
				.add(Answer.build().setId(1).setText("Antwort 1").setCorrect(false).build())
				.add(Answer.build().setId(2).setText("Antwort 2").setCorrect(true).build())
				.add(Answer.build().setId(3).setText("Antwort 3").setCorrect(false).build())
				.build();
	}
}
