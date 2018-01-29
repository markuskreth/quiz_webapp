package de.kreth.quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
import de.ralleytn.simple.json.JSONObject;

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
				.setId(1)
				.add(Answer.build().setId(1).setText("Antwort 1").setCorrect(false).build())
				.add(Answer.build().setId(2).setText("Antwort 2").setCorrect(true).build())
				.add(Answer.build().setId(3).setText("Antwort 3").setCorrect(false).build());
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
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

		Question current = (Question) session.getAttribute("question");
		
	    switch (request.getQueryString()) {

		case "current":
			current(response, current);
			break;

		case "quiz":
			quiz(response, q);
			break;

		case "next":
			current = q.next();
			session.setAttribute("question", current);
			current(response, current);
			break;
			
		default:
			response.getWriter().println("unrecognized function!");
			break;
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader reader = req.getReader();
		
	}
	
	private void quiz(HttpServletResponse response, Quiz quiz) throws IOException {
		Map<String, Object> values = new HashMap<>();
		values.put("today", df.format(new Date()));
		JSONObject json = quiz.toJson();
		json.putAll(values);
		response.getWriter().write(json.toJSONString());
	}

	private void current(HttpServletResponse response, Question current) throws IOException {
		if(current != null) {
			response.getWriter().write(current.toJson().toJSONString());
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "No question available");
		}
	}

}