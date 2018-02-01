package de.kreth.quiz;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.kreth.quiz.data.Question;
import de.kreth.quiz.data.Quiz;
import de.kreth.quiz.data.Quiz.Build;
import de.kreth.quiz.storage.DatabaseConnection;
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
		
		Build builder = Quiz.build()
				.setTitle("Test Quiz java");
		try {
			List<Question> questions = DatabaseConnection.INSTANCE.getDao(Question.class).getAll();
			for (Question q: questions) {
				builder.add(q);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.build();
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String path = config.getServletContext().getRealPath("/");
		DatabaseConnection.INSTANCE.setSourceDir(new File(path));
		System.out.println("init " + getClass().getName() + " #" + count.incrementAndGet() + " with path: " + path);
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
		
	    String queryString = request.getQueryString();
	    if (queryString == null) {
			quiz(response, q);
	    } else {
			switch (queryString) {

			case "current":
				current(response, current);
				break;

			case "quiz":
				quiz(response, q);
				break;

			case "next":
				current = q.next();
				session.setAttribute("question", current);
				if(current != null) {
					current(response, current);
				} else {
					quiz(response, q);
				}
				break;
				
			default:
				response.getWriter().println("unrecognized function!");
				break;
			}
	    }
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	    HttpSession session = req.getSession();
	    Quiz q = (Quiz) session.getAttribute("quiz");
		
		BufferedReader reader = req.getReader();
		String line;
		
		while ((line = reader.readLine()) != null) {
			long questionId = -1;
			long answerId = -1;
			for (String ele : line.split("&")) {
				String[] pair = ele.split("=");
				switch (pair[0]) {
				case "question":
					questionId = Long.parseLong(pair[1]);
					break;
				case "answer":
					answerId = Long.parseLong(pair[1]);
					break;
				}
			}
			q.setAnswer(questionId, answerId);
		}

		session.setAttribute("quiz", q);

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter writer = resp.getWriter();
		writer.write(q.toJson().toJSONString());
		writer.close();
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
		}
	}

}