package de.kreth.quiz.storage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import de.kreth.quiz.data.Question;
import de.ralleytn.simple.json.JSONArray;
import de.ralleytn.simple.json.JSONObject;
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
		Reader reader = source.getReader();
		JSONArray arr = new JSONArray(reader);
		arr.forEach(e -> {
			Question q = Question.fromJSON((JSONObject)e);
			cache.add(q);
			if(sequence.get()<q.getId()) {
				sequence.set(q.getId());
			}
		});
		reader.close();
	}

	@Override
	public void flush() throws IOException {
		JSONArray list = new JSONArray();
		for(Question q: cache) {
			list.add(q.toJson());
		}

		Writer writer = source.getWriter();
		BufferedWriter out = new BufferedWriter(writer);
		out.write(list.toJSONString());
		out.close();
	}
	
	@Override
	public List<Question> getAll() {
		return new ArrayList<>(cache);
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
