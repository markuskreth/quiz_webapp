package de.kreth.quiz.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonTests {

	@BeforeEach
	public void resetAnswerSequence() {
		Answer.resetSequence();
	}
	
	@Test
	void testAnswer() {
		Answer a = Answer.build().setId(1L).setText("The answer text").setCorrect(true).build();
		String json = a.toJson().toJSONString();
		assertEquals("{\"correct\":\"true\",\"id\":1,\"text\":\"The answer text\"}", json);
	}

	@Test
	void testQuestionWithoutAnswers() {
		Question q = Question.build().setId(1L).setQuestion("The question text").build();
		q.setChoice(1);
		String json = q.toJson().toJSONString();
		assertEquals("{\"question\":\"The question text\",\"choosen\":1,\"id\":1}", json);
	}
	
	@Test
	void testQuestionWithAnswers() {
		Question q = Question.build()
				.setId(1L)
				.setQuestion("The question text")
				.add(Answer.build().setId(1L).setText("A1").setCorrect(false).build())
				.add(Answer.build().setId(2L).setText("A2").setCorrect(true).build())
				.add(Answer.build().setId(3L).setText("A3").setCorrect(false).build())
				.build();
		q.setChoice(1);
		String json = q.toJson().toJSONString();
		assertEquals("{\"question\":\"The question text\",\"choosen\":1,\"answers\":[{\"correct\":\"false\",\"id\":1,\"text\":\"A1\"},{\"correct\":\"true\",\"id\":2,\"text\":\"A2\"},{\"correct\":\"false\",\"id\":3,\"text\":\"A3\"}],\"id\":1}", json);
	}
}
