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
		a.setChoice(1);
		assertTrue(a.isAnswered());
		assertTrue(a.isAnsweredCorrectly());
		a.setChoice(0);
		assertTrue(a.isAnswered());
		assertFalse(a.isAnsweredCorrectly());
	}
}
