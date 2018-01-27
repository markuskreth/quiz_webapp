package de.kreth.quiz;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

	public Quiz nextQuiz() {
		Build bld = Question.build()
				.add(Answer.build().setText("Antwort 1").setCorrect(false).build())
				.add(Answer.build().setText("Antwort 2").setCorrect(true).build())
				.add(Answer.build().setText("Antwort 3").setCorrect(false).build());
		return Quiz.build()
				.setTitle("Test Quiz java")
				.add(bld.setQuestion("Die Frage 1").build())
				.add(bld.setQuestion("Die Frage 2").build())
				.build();
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("init " + getClass().getName() + " #" + count.incrementAndGet());
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    Quiz q = (Quiz) session.getAttribute("quiz");
	    if(q == null) {
	    	q = nextQuiz();
	    	session.setAttribute("quiz", q);
	    }
	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");

		Question current = (Question) session.getAttribute("question");
		
	    switch (request.getQueryString()) {
		case "anzahlRichtig":
			anzahlRichtig(response, q);
			break;
		case "anzahlQuestions":
			anzahlQuestions(response, q);
			break;
		case "anzahlAntworten":
			anzahlAntworten(response, q);
			break;
		case "content":
			content(response, current);
			break;

		case "title":
			title(response, q);
			break;

		case "next":
			current = q.next();
			session.setAttribute("question", current);
			content(response, current);
			break;

		case "today":
			today(response);
			break;

		default:
			response.getWriter().println("unrecognized function!");
			break;
		}
	}

	private void today(HttpServletResponse response) throws IOException {
		response.getWriter().write(df.format(new Date()));
	}

	private void title(HttpServletResponse response, Quiz quiz) throws IOException {
		response.getWriter().write(quiz.getTitle());
	}

	private void content(HttpServletResponse response, Question current) throws IOException {
		if(current != null) {
			response.getWriter().write(current.getQuestion());
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "No question available");
		}
	}

	private void anzahlAntworten(HttpServletResponse response, Quiz quiz) throws IOException {
		response.getWriter().write(String.valueOf(quiz.totalAnswered()));
	}

	private void anzahlQuestions(HttpServletResponse response, Quiz quiz) throws IOException {
		response.getWriter().write(String.valueOf(quiz.size()));
	}

	private void anzahlRichtig(HttpServletResponse response, Quiz quiz) throws IOException {
		response.getWriter().write(String.valueOf(quiz.correctlyAnswered()));
	}
	
}