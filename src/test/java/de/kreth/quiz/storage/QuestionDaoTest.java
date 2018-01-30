package de.kreth.quiz.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kreth.quiz.data.Answer;
import de.kreth.quiz.data.Question;
import de.ralleytn.simple.json.JSONObject;
import de.ralleytn.simple.json.JSONParseException;

class QuestionDaoTest implements ReadWriteSource {

	private final static String SERIALIZED_FILE_NAME= "testdata.xml";
	private static Question testQuestion;
	/**
	 * Do not use, for reset to original 
	 */
	private static ReadWriteSource old;

	private StringWriter writer;
	private StringReader reader;
	
	@BeforeAll
	public static void initTestDataObj() throws IOException {

		testQuestion = Question.build()
				.setId(1L)
				.setQuestion("What is the Question string?")
				.add(Answer.build().setId(1L).setText("Antwort 1").setCorrect(false).build())
				.add(Answer.build().setId(2L).setText("Antwort 2").setCorrect(true).build())
				.add(Answer.build().setId(3L).setText("Antwort 3").setCorrect(false).build())
				.build();
		JSONObject json = testQuestion.toJson();
		BufferedWriter out = new BufferedWriter(new FileWriter(SERIALIZED_FILE_NAME));
		out.write(json.toJSONString());
		out.newLine();
		out.close();
		old = DatabaseConnection.INSTANCE.question;
	}

	@AfterAll
	public static void resetDatabaseConnection() {
		DatabaseConnection.INSTANCE.question = old;
	}
	
	@BeforeEach
	public void initWriter() {
		DatabaseConnection.INSTANCE.question = this;
		this.writer = new StringWriter() {
			@Override
			public void close() throws IOException {
				super.close();
				reader = new StringReader(this.toString());
			}
		};
		this.reader = new StringReader("");
	}
	
	@Test
	public void testFactory() throws IOException {
		Dao<Question> dao = DatabaseConnection.INSTANCE.getDao(Question.class);
		assertNotNull(dao);
	}
	
	@Test
	void testInsert() throws IOException {
		Dao<Question> dao = DatabaseConnection.INSTANCE.getDao(Question.class);
		assertNotNull(dao);
		Question obj = Question.build().setQuestion("One Test Question").build();
		dao.insert(obj);
		List<Question> all = dao.getAll();
		assertNotNull(all);
		assertEquals(1, all.size());
		dao.flush();
		String json = writer.toString();
		assertTrue(json.length()>10);
		assertEquals("{\"question\":\"One Test Question\",\"id\":1}\n", json);
		
	}
	
	@Test
	void testGetAll() throws IOException {
		reader = new StringReader("{\"question\":\"One Test Question\",\"id\":3}\n");
		Dao<Question> dao = DatabaseConnection.INSTANCE.getDao(Question.class);
		List<Question> all = dao.getAll();
		assertNotNull(all);
		assertEquals(1, all.size());
		Question q = all.get(0);
		assertEquals("One Test Question", q.getQuestion());
		assertEquals(3L, q.getId().longValue());
	}

	@Test 
	void sequenceIsIncrementedToMax() throws IOException, JSONParseException {
		QuestionDao dao = new QuestionDao(this);
		assertEquals(0, dao.sequence.get());
		reader = new StringReader("{\"question\":\"One Test Question\",\"id\":3}\n");
		dao = new QuestionDao(this);
		assertEquals(3, dao.sequence.get());
	}
	
	@Override
	public Reader getReader() {
		return reader;
	}

	@Override
	public Writer getWriter() {
		return writer;
	}
}
