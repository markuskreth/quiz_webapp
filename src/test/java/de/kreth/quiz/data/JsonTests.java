package de.kreth.quiz.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JsonTests {

	@Test
	void testAnswer() {
		Answer a = Answer.build().setText("The answer text").setCorrect(true).build();
		String json = a.toJson().toJSONString();
		assertEquals("{\"correct\":true,\"text\":\"The answer text\"}", json);
	}

	@Test
	void testQuestionWithoutAnswers() {
		Question q = Question.build().setQuestion("The question text").build();
		q.setChoice(1);
		String json = q.toJson().toJSONString();
		assertEquals("{\"question\":\"The question text\",\"choosen\":1}", json);
	}
	
	@Test
	void testQuestionWithAnswers() {
		Question q = Question.build()
				.setQuestion("The question text")
				.add(Answer.build().setText("A1").setCorrect(false).build())
				.add(Answer.build().setText("A2").setCorrect(true).build())
				.add(Answer.build().setText("A3").setCorrect(false).build())
				.build();
		q.setChoice(1);
		String json = q.toJson().toJSONString();
		assertEquals("{\"question\":\"The question text\",\"choosen\":1,\"answers\":[{\"correct\":false,\"text\":\"A1\"},{\"correct\":true,\"text\":\"A2\"},{\"correct\":false,\"text\":\"A3\"}]}", json);
	}
}
