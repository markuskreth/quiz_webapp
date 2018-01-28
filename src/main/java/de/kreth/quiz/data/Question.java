package de.kreth.quiz.data;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.ralleytn.simple.json.JSONArray;
import de.ralleytn.simple.json.JSONObject;

public class Question implements Serializable, Data {

	private static final long serialVersionUID = 5385618773122684243L;
	private final String question;
	private final List<Answer> answers;
	private int choosen;

	private Question(Build build) {
		this.question = build.question;
		this.answers = Collections.unmodifiableList(build.answers);
		this.choosen = -1;
	}

	public boolean isAnsweredCorrectly() {
		if(!isAnswered()) {
			return false;
		}
		return answers.get(choosen).getCorrect();
	}

	@Override
	public JSONObject toJson() {

		Map<String, Object> values= new HashMap<>();
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

		private String question;
		private final List<Answer> answers = new LinkedList<>();

		private Build() {
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

}