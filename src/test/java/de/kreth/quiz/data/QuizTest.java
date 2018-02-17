package de.kreth.quiz.data;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.kreth.quiz.testdata.TestQuestions;

class QuizTest {

	@Test
	void testQuiz() {
		Question e = TestQuestions.q1();
		Quiz quiz = Quiz.build()
				.add(e)
				.build();
		
		assertNotNull(quiz);
		Question a = quiz.next();
		assertNotNull(a);
		assertNull(quiz.next());
	}

	@Test
	public void testQuestion() {
		Question a = TestQuestions.q1();
		int answerId = -1;
		
		for(int i=0; i<a.getAnswers().size(); i++) {
			if(a.getAnswers().get(i).getCorrect()) {
				answerId = i;
			}
		}
		a.setChoice(answerId);
		assertTrue(a.isAnswered());
		assertTrue(a.isAnsweredCorrectly());
		if(answerId>0) {
			a.setChoice(0);
		}else {
			a.setChoice(1);
		}
		assertTrue(a.isAnswered());
		assertFalse(a.isAnsweredCorrectly());
	}
}
