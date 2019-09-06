package trivia;

import static spark.Spark.*;
import org.javalite.activejdbc.Base;

import trivia.models.*;
import trivia.BasicAuth;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONObject;

class QuestionParam {
	String description;
	String category;
	ArrayList<OptionParam> options;

}

class OptionParam {
	String description;
	Boolean correct;
}

public class App {

	static Object preg_id;

	public static void main(String[] args) {

		before((request, response) -> {
			if (!Base.hasConnection())
				Base.open();				
			String headerToken = (String) request.headers("Authorization");
			
			if (headerToken == null || headerToken.isEmpty() || !BasicAuth.authorize(headerToken))
				halt(401, "Usuario o clave invalidos \n");
		});

		after((request, response) -> {
			if(Base.hasConnection())
				Base.close();

			response.header("Access-Control-Allow-Origin", "*");
			response.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
			response.header("Access-Control-Allow-Headers",
					"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
		});

		options("/*", (request, response) -> {
			return "OK";
		});

		post("/categoryquestion", (req, res) -> {// its a get
			Random r = new Random();
			JSONObject resp = new JSONObject();
			Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
			List<Question> questions;
			User currentUser = User.findFirst("username = ?", bodyParams.get("username"));
			questions = Question.findBySQL(
					"SELECT * FROM questions WHERE id NOT IN (SELECT id FROM questions NATURAL JOIN user_questions WHERE user_id = ?) AND category = ?",
					currentUser.get("id"), bodyParams.get("category"));
			Question question = questions.get(r.nextInt(questions.size()));
			List<Option> options = Option.where("question_id = ?", question.get("id"));
			preg_id = question.get("id");
			resp.put("description", question.get("description"));

			int i = 1;
			for (Option o : options) {
				resp.put("answer" + i, o.get("description"));
				i++;
			}
			return resp;
		});

		get("/question", (req, res) -> {
			Random r = new Random();
			JSONObject resp = new JSONObject();
			Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
			List<Question> questions;
			User currentUser = User.findFirst("username = ?", bodyParams.get("username"));
			questions = Question.findBySQL(
					"SELECT * FROM questions WHERE id NOT IN (SELECT id FROM questions NATURAL JOIN user_questions WHERE user_id = ?)",
					currentUser.get("id"));
			Question question = questions.get(r.nextInt(questions.size()));
			List<Option> options = Option.where("question_id = ?", question.get("id"));
			preg_id = question.get("id");
			resp.put("description", question.get("description"));
			
			int i = 1;
			for (Option o : options) {
				resp.put("answer" + i, o.get("description"));
				i++;
			}
			return resp;

		});

		get("/statistics", (req, res) -> {
			List<UseStatisticsCategory> estadisticas = UseStatisticsCategory.where("user = ?",
					req.session().attribute("username"));
			JSONObject resp = new JSONObject();
			resp.put("User", req.session().attribute("username").toString());
			int i = 0;
			for (UseStatisticsCategory e : estadisticas) {
				resp.put("cat" + i, e.get("nombre"));
				resp.put("points" + i, e.get("points"));
				resp.put("correct_answer" + i, e.get("correct_answer"));
				resp.put("incorrect_answer" + i, e.get("incorrect_answer"));
				i++;
			}
			return resp;
		});

		post("/admin", (req, res) -> {
			if ((boolean) req.session().attribute("admin")) {
				Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
				User user = User.findFirst("username = ?", bodyParams.get("username"));
				user.set("admin", true);
				user.saveIt();
				res.type("application/json");
				return user.toJson(true);
			} else {
				res.type("application/json");
				User u = User.findFirst("username = ?", req.session().attribute("username"));
				return u.toJson(true);
				//why?
			}
		});

		post("/userdelete", (req, res) -> {
			Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
			User usuario = User.findFirst("username = ?", bodyParams.get("username"));
			JSONObject resp = new JSONObject();
			if (usuario != null) {
				usuario.delete();
				res.type("application/json");
				resp.put("answer", "Usuario Borrado");
			} else {
				resp.put("answer", "El usuario no existe");
			}
			return resp;
		});

		post("/answer", (req, res) -> {
			Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
			JSONObject resp = new JSONObject();
			List<Option> options = Option.where("question_id = ?", preg_id);
			List<Question> questions = Question.where("id = ?", preg_id);
			Question question = questions.get(0);
			int i = Integer.parseInt((String) bodyParams.get("answer"));
			Option option = options.get(i - 1);
			List<UseStatisticsCategory> stats = UseStatisticsCategory.where("user = ? AND nombre = ?",
					req.session().attribute("username"), question.get("category"));
			UseStatisticsCategory stat = stats.get(0);
			if ((boolean) option.get("correct")) {
				UserQuestions preg = new UserQuestions();
				User u = User.findFirst("username = ?", req.session().attribute("username"));
				preg.set("user_id", u.get("id"));
				preg.set("question id", preg_id);
				preg.saveIt();
				int j = (int) stat.get("points") + 1;
				stat.set("points", j);
				j = (int) stat.get("correct_answer") + 1;
				stat.set("correct_answer", j);
				stat.saveIt();
				resp.put("answer", "Correcto!");
				return resp;
			} else {
				stat.set("incorrect_answer", (int) stat.get("incorrect_answer") + 1);
				stat.saveIt();
				resp.put("answer", "Incorrecto!");
				return resp;
			}
		});

		post("/questions", (req, res) -> {
			if (!(boolean) req.session().attribute("admin"))
				return "No tenes permiso para crear preguntas";
			QuestionParam bodyParams = new Gson().fromJson(req.body(), QuestionParam.class);
			Question question = new Question();
			question.set("description", bodyParams.description);
			question.set("category", bodyParams.category);
			question.save();
			for (OptionParam item : bodyParams.options) {
				Option option = new Option();
				option.set("description", item.description).set("correct", item.correct);
				question.add(option);
			}
			return question.toJson(true);
		});

		post("/login", (req, res) -> {
			Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class); // 0 seguridad
			User u = User.findFirst("username = ? and password = ?", bodyParams.get("username"), bodyParams.get("password"));
			if (u != null) {
				req.session().attribute("username", u.get("username"));
				req.session().attribute("admin", u.get("admin"));
				System.out.println(req.session().attribute("username").toString());
			}
			return u.toJson(true); 
		});

		post("/users", (req, res) -> {
			Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
			if (User.findFirst("Username = ?", bodyParams.get("username")) != null) {
				halt(401, "");
				return "";
			}
			if (((String) bodyParams.get("username")).length() == 0	|| ((String) bodyParams.get("password")).length() == 0) {
				halt(403, "");
				return "";
			}
			User user = new User();
			user.set("username", bodyParams.get("username"),
					 "password", bodyParams.get("password"),
					 "admin", false).saveIt();
			List<Category> cat = Category.findAll();
			for (Category c : cat) {
				UseStatisticsCategory stats = new UseStatisticsCategory();
				stats.set("user", user.get("username"));
				stats.set("nombre", c.get("nombre"));
				stats.set("points", 0);
				stats.set("correct_answer", 0);
				stats.set("incorrect_answer", 0);
				stats.saveIt();
			}
			return user.toJson(true);
		});
	}
}
