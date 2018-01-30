package de.kreth.quiz.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import de.kreth.quiz.data.Question;
import de.ralleytn.simple.json.JSONParseException;

public class QuestionDao implements Dao<Question> {

	AtomicLong sequence = new AtomicLong();
	private final ArrayList<Question> cache = new ArrayList<>();	
	private ReadWriteSource source;
	
	public QuestionDao(ReadWriteSource source) throws IOException, JSONParseException {
		this.source = source;
		fillCache();
	}
	
	private void fillCache() throws IOException, JSONParseException {
		cache.clear();
		BufferedReader in = new BufferedReader(source.getReader());
		String line;
		while ((line = in.readLine()) != null) {
			Question fromJSON = Question.fromJSON(line);
			cache.add(fromJSON);
			if(fromJSON.getId()>sequence.get()) {
				sequence.set(fromJSON.getId());
			}
		}
	}

	@Override
	public void flush() throws IOException {
		Writer writer = source.getWriter();
		BufferedWriter out = new BufferedWriter(writer);
		for(Question q: cache) {
			out.write(q.toJson().toJSONString());
			out.newLine();
		}
		out.close();
	}
	
	@Override
	public List<Question> getAll() {
		return Collections.unmodifiableList(cache);
	}

	@Override
	public Question getById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Question obj) {
		obj.setId(sequence.incrementAndGet());
		cache.add(obj);
	}

	@Override
	public void delete(Question obj) {
		// TODO Auto-generated method stub
		
	}

}
