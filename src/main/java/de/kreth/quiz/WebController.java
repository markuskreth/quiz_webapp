package de.kreth.quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.kreth.quiz.data.Answer;
import de.kreth.quiz.data.Question;
import de.kreth.quiz.data.Question.Build;
import de.kreth.quiz.data.Quiz;

@WebServlet(
		urlPatterns = "/quiz",
		loadOnStartup = 1,
		asyncSupported = true
)
public class WebController extends HttpServlet {

	static AtomicInteger count = new AtomicInteger();
	private static final long serialVersionUID = -4764036064264922682L;

	private static final DateFormat df = DateFormat.getDateTimeInstance();

	private final Quiz quiz;

	public static WebController createTest() {
		Build bld = Question.build()
				.add(Answer.build().setText("Antwort 1").setCorrect(false).build())
				.add(Answer.build().setText("Antwort 2").setCorrect(true).build())
				.add(Answer.build().setText("Antwort 3").setCorrect(false).build());
		WebController con = new WebController(Quiz.build()
				.setTitle("Test Quiz java")
				.add(bld.setQuestion("Die Frage 1").build())
				.add(bld.setQuestion("Die Frage 2").build())
				.build());
		return con;
	}

	public WebController() {
		Build bld = Question.build()
				.add(Answer.build().setText("Antwort 1").setCorrect(false).build())
				.add(Answer.build().setText("Antwort 2").setCorrect(true).build())
				.add(Answer.build().setText("Antwort 3").setCorrect(false).build());
		this.quiz = Quiz.build()
				.setTitle("Test Quiz java")
				.add(bld.setQuestion("Die Frage 1").build())
				.add(bld.setQuestion("Die Frage 2").build())
				.build();
	}
	
	private WebController(Quiz quiz) {
		this.quiz = quiz;
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("init " + getClass().getName() + " #" + count.incrementAndGet());
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");

	    PrintWriter writer = response.getWriter();
	    switch (request.getQueryString()) {
		case "anzahlRichtig":
			writer.write(quiz.correctlyAnswered());
			break;
		case "anzahlQuestions":
			writer.write(quiz.size());
			break;
		case "anzahlAntworten":
			writer.write(quiz.totalAnswered());
			break;
		case "content":
			writer.write(quiz.next().getQuestion());
			break;

		case "title":
			writer.write(quiz.getTitle());
			break;

		case "today":
			writer.write(df.format(new Date()));
			break;

		default:
			break;
		}
	}
	
}