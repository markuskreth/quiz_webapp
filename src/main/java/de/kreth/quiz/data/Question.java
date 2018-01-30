package de.kreth.quiz.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.ralleytn.simple.json.JSONArray;
import de.ralleytn.simple.json.JSONObject;
import de.ralleytn.simple.json.JSONParseException;

public class Question implements Serializable, Data {

	private static final long serialVersionUID = 5385618773122684243L;
	private Long id;
	private String question;
	private List<Answer> answers;
	private int choosen;

	public Question() {
		answers = new ArrayList<>();
		this.choosen = -1;
	}
	
	private Question(Build build) {
		this();
		this.id = build.id;
		this.question = build.question;
		this.answers = Collections.unmodifiableList(build.answers);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setChoosen(int choosen) {
		this.choosen = choosen;
	}

	public boolean isAnsweredCorrectly() {
		if(!isAnswered()) {
			return false;
		}
		return answers.get(choosen).getCorrect();
	}

	public Long getId() {
		return id;
	}
	
	public String getQuestion() {
		return question;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public boolean isAnswered() {
		return choosen>=0;
	}

	public void setChoice(int choice) {
		choosen = choice;
	}
	
	public static class Build implements Builder<Question> {

		private Long id;
		private String question;
		private final List<Answer> answers = new LinkedList<>();

		private Build() {
		}
		
		public Build setId(Long id) {
			this.id = id;
			return this;
		}
		
		public Build setQuestion(String question) {
			this.question = question;
			return this;
		}

		public Build add(Answer answer) {
			answers.add(answer);
			return this;
		}

		@Override
		public Question build() {
			return new Question(this);
		}
		
	}

	public static Build build() {
		return new Build();
	}

	@Override
	public JSONObject toJson() {

		Map<String, Object> values= new HashMap<>();
		if(id == null) {
			throw new IllegalStateException("id must be set for serialization");
		}
		
		values.put("id", id);
		
		if(question != null) {
			values.put("question", question);
		}
		if (choosen >= 0) {
			values.put("choosen", choosen);
		}
		if(answers != null && answers.size() >0) {
			JSONArray ans = new JSONArray();
			for (Answer a: answers) {
				ans.add(a.toJson());
			}
			values.put("answers", ans);
		}
		JSONObject json = new JSONObject(values);
		return json;
	}
	
	public static Question fromJSON(String line) throws JSONParseException {
		JSONObject o = new JSONObject(line);
		
		Build bld = build().setId(o.getLong("id")).setQuestion(o.getString("question"));
		JSONArray ans = o.getArray("answers");
		if(ans != null) {
			for (int i= 0; i<ans.size(); i++) {
				bld.add(Answer.create(ans.getObject(i)));
			}
		}
		return bld.build();
	}

}