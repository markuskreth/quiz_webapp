package de.kreth.quiz.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import de.ralleytn.simple.json.JSONObject;

public class Answer implements Serializable,Data {

	private static final long serialVersionUID = -3262618312403569574L;

	private Long id;
	private String text;
	private Boolean correct;

	private Answer() {
	}
	
	private Answer(Build bld) {
		this.id = bld.id;
		this.text = bld.text;
		this.correct = bld.correct;
	}

	public Long getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}

	public Boolean getCorrect() {
		return correct;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correct == null) ? 0 : correct.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Answer other = (Answer) obj;
		if (correct == null) {
			if (other.correct != null)
				return false;
		} else if (!correct.equals(other.correct))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Answer [id=").append(id).append(", text=").append(text).append(", correct=").append(correct)
				.append("]");
		return builder.toString();
	}

	@Override
	public JSONObject toJson() {

		if(id == null) {
			throw new IllegalStateException("id must be set for serialization");
		}
		
		Map<String, Object> values= new HashMap<>();

		values.put("id", id);
		
		if(text != null) {
			values.put("text", text);
		}
		if( correct != null) {
			values.put("correct", correct);
		}
		JSONObject json = new JSONObject(values);
		return json;
	}
	
	public static Answer create(JSONObject object) {
		Build bld = build();
		bld.setText(object.getString("text")).setCorrect(object.getBoolean("correct"));
		return bld.build();
	}

	public static Build build() {
		return new Build();
	}
	
	public static class Build implements Builder<Answer> {
		private static final AtomicLong SEQUENCE = new AtomicLong();
		
		private Long id;
		private String text;
		private Boolean correct;
		
		private Build() {
		}
		
		public Build setId(Long id) {
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
			if(id == null || id.longValue()<SEQUENCE.get()) {
				id = SEQUENCE.incrementAndGet();
			} else if(id.longValue() > SEQUENCE.get()) {
				SEQUENCE.set(id);
			}
			return new Answer(this);
		}

	}

}