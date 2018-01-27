package de.kreth.quiz.data;

public class Answer {

	private final String text;
	private final boolean correct;

	private Answer(Build bld) {
		this.text = bld.text;
		this.correct = bld.correct;
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

		private String text;
		private Boolean correct;
		
		private Build() {
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
			return new Answer(this);
		}

	}
}