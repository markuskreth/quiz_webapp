package de.kreth.quiz.data;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableInt;

import de.ralleytn.simple.json.JSONObject;

public class Quiz implements Serializable, Data {

	private static final long serialVersionUID = -8938149899710233257L;
	private final String title;
	private final List<Question> questions;
	private int current = -1;
	private boolean finished;
	
	public Question next() {
		current++;
		if(current >= questions.size()) {
			return null;
		}
		return questions.get(current);
	}

	public int correctlyAnswered() {
		MutableInt answers = new MutableInt();
		questions.forEach(a -> {
			if(a.isAnsweredCorrectly()) {
				answers.increment();
			}
		});
		return answers.intValue();
	}

	public int totalAnswered() {
		MutableInt answers = new MutableInt();
		questions.forEach(a -> {
			if(a.isAnswered()) {
				answers.increment();
			}
		});
		return answers.intValue();
	}
	
	public String getTitle() {
		return title;
	}
	
	public int size () {
		return questions.size();
	}
	
	@Override
	public JSONObject toJson() {
		Map<String, Object> values = new HashMap<>();
		values.put("title", getTitle());

		values.put("anzahlQuestions", size());
		values.put("anzahlAntworten", totalAnswered());
		values.put("anzahlRichtig", correctlyAnswered());
		if(finished) {
			values.put("finished", finished);
		}

		JSONObject json = new JSONObject(values);
		return json;
	}
	
	private Quiz(Build build) {
		this.questions = Collections.unmodifiableList(build.questions);
		this.title = build.title;
	}

	public static Build build() {
		return new Build();
	}
	
	public static class Build implements Builder<Quiz> {

		public String title = "";
		private final List<Question> questions = new LinkedList<>();
		
		private Build() {
		}
		
		public int size() {
			return questions.size();
		}

		public Build add(Question e) {
			questions.add(e);
			return this;
		}

		public Build setTitle(String title) {
			this.title = title;
			return this;
		}
		
		public void clear() {
			questions.clear();
		}

		@Override
		public Quiz build() {
			return new Quiz(this);
		}

	}

	public void setAnswer(long questionId, long answerId) {
		Question question = questions.get(current);
		if(question.getId().longValue() == questionId && answerId >=0) {
			List<Answer> answers = question.getAnswers();
			question.setChoice(answers.indexOf(answers.stream().filter(a -> a.getId()==answerId).findFirst().get()));
			
		}
		
	}

	public void setFinished(boolean b) {
		this.finished = b;
	}

}