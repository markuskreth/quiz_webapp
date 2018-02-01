package de.kreth.quiz;

import java.io.File;
import java.io.IOException;

import de.kreth.quiz.data.Answer;
import de.kreth.quiz.data.Question;
import de.kreth.quiz.storage.Dao;
import de.kreth.quiz.storage.DatabaseConnection;

public class InsertNewQuestion {

	public static void main(String[] args) throws IOException {
		DatabaseConnection instance = DatabaseConnection.INSTANCE;
		
		instance.setSourceDir(new File("/home/markus/programming/workspace_ee/quiz-webapp/src/main/webapp/"));
		Dao<Question> dao = instance.getDao(Question.class);

		dao.insert(Question.build()
				.setQuestion("Welche Person/Politiker starb am 30. Juli 1945?")
				.add(Answer.build().setText("Helmut Schmidt").setCorrect(false).build())
				.add(Answer.build().setText("Adolf Hitler").setCorrect(true).build())
				.add(Answer.build().setText("George W. Bush").setCorrect(false).build())
				.add(Answer.build().setText("Stalin").setCorrect(false).build())
				.add(Answer.build().setText("Barack Obama").setCorrect(false).build())
				.build());
		dao.insert(Question.build()
				.setQuestion("Ra ist der Gott...")
				.add(Answer.build().setText("... der Krieger.").setCorrect(false).build())
				.add(Answer.build().setText("...der Sonne.").setCorrect(true).build())
				.add(Answer.build().setText("... des Blitzes.").setCorrect(false).build())
				.add(Answer.build().setText("... des Wassers.").setCorrect(false).build())
				.add(Answer.build().setText("... der Ernte und der Bauern.").setCorrect(false).build())
				.build());
		dao.insert(Question.build()
				.setQuestion("Asterix' bester Freund heißt")
				.add(Answer.build().setText("Miraculix").setCorrect(false).build())
				.add(Answer.build().setText("Verleihnix").setCorrect(false).build())
				.add(Answer.build().setText("Rührfix").setCorrect(false).build())
				.add(Answer.build().setText("Obelix").setCorrect(true).build())
				.add(Answer.build().setText("Idefix").setCorrect(false).build())
				.build());
		dao.insert(Question.build()
				.setQuestion("Die Tomate ist")
				.add(Answer.build().setText("Eine Steinfrucht.").setCorrect(false).build())
				.add(Answer.build().setText("Ein Nachtschattengewächs.").setCorrect(false).build())
				.add(Answer.build().setText("Ein Gemüse.").setCorrect(true).build())
				.add(Answer.build().setText("Ein Pilz.").setCorrect(false).build())
				.add(Answer.build().setText("Ein Obst.").setCorrect(false).build())
				.build());
		dao.insert(Question.build()
				.setQuestion("'Philosophie' heißt übersetzt so viel wie:")
				.add(Answer.build().setText("Freude am Leben").setCorrect(false).build())
				.add(Answer.build().setText("Liebe zur Weisheit").setCorrect(false).build())
				.add(Answer.build().setText("Geisteswissenschaften").setCorrect(false).build())
				.add(Answer.build().setText("Weisheit ist das höchste Gebot").setCorrect(false).build())
				.add(Answer.build().setText("Die Schöne").setCorrect(false).build())
				.build());
		dao.insert(Question.build()
				.setQuestion("Kokain war früher ein Zahnmedizinisches Mittel.")
				.add(Answer.build().setText("Wahr").setCorrect(true).build())
				.add(Answer.build().setText("Falsch").setCorrect(false).build())
				.build());
		dao.insert(Question.build()
				.setQuestion("Ibiza ist eine kanarische Insel.")
				.add(Answer.build().setText("Wahr").setCorrect(true).build())
				.add(Answer.build().setText("Falsch").setCorrect(false).build())
				.build());
		dao.insert(Question.build()
				.setQuestion("Es gibt einen Tag der schlechten Wortspiele.")
				.add(Answer.build().setText("Wahr").setCorrect(true).build())
				.add(Answer.build().setText("Falsch").setCorrect(false).build())
				.build());
		dao.insert(Question.build()
				.setQuestion("Eskimo heißt wörtlich übersetzt\n" + 
						"\"Fleischfresser\".")
				.add(Answer.build().setText("Wahr").setCorrect(false).build())
				.add(Answer.build().setText("Falsch").setCorrect(true).build())
				.build());
		dao.insert(Question.build()
				.setQuestion("Eine Oktillion hat 46 Nullen.")
				.add(Answer.build().setText("Wahr").setCorrect(true).build())
				.add(Answer.build().setText("Falsch").setCorrect(false).build())
				.build());
		dao.insert(Question.build()
				.setQuestion("Es gibt laut Forscher keine objektive Schönheit.")
				.add(Answer.build().setText("Wahr").setCorrect(true).build())
				.add(Answer.build().setText("Falsch").setCorrect(false).build())
				.build());
		dao.insert(Question.build()
				.setQuestion("Eisbären haben weiße Haut.")
				.add(Answer.build().setText("Wahr").setCorrect(true).build())
				.add(Answer.build().setText("Falsch").setCorrect(false).build())
				.build());
		dao.insert(Question.build()
				.setQuestion("Der römische Kaiser Caesar regierte zur Zeit Jesu Geburt.")
				.add(Answer.build().setText("Wahr").setCorrect(false).build())
				.add(Answer.build().setText("Falsch").setCorrect(true).build())
				.build());
//		dao.flush();
	}

}
