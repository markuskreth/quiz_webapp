package de.kreth.quiz.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import de.ralleytn.simple.json.JSONObject;

public class Answer implements Serializable,Data {

	private static final long serialVersionUID = -3262618312403569574L;
	private final Integer id;
	private final String text;
	private final Boolean correct;

	private Answer(Build bld) {
		this.id = bld.id;
		this.text = bld.text;
		this.correct = bld.correct;
	}

	@Override
	public JSONObject toJson() {

		Map<String, Object> values= new HashMap<>();
		if(text != null) {
			values.put("text", text);
		}
		if( correct != null) {
			values.put("correct", correct);
		}
		JSONObject json = new JSONObject(values);
		return json;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}

	public Boolean getCorrect() {
		return correct;
	}

	public static Build build() {
		return new Build();
	}
	
	public static class Build implements Builder<Answer> {

		private Integer id;
		private String text;
		private Boolean correct;
		
		private Build() {
		}
		
		public Build setId(Integer id) {
			this.id = id;
			return this;
		}

		public Build setText(String text) {
			this.text = text;
			return this;
		}
		
		public Build setCorrect(Boolean correct) {
			this.correct = correct;
			return this;
		}
		
		@Override
		public Answer build() {
			if(id == null || id<0) {
				throw new IllegalStateException("Invalid id = "+ id);
			}
			return new Answer(this);
		}

	}

}