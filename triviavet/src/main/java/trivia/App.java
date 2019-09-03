package trivia;

import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.options;

import static spark.Spark.before;
import static spark.Spark.after;
import static spark.Spark.halt;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DBException;

import trivia.models.*;
import trivia.BasicAuth;

import com.google.gson.Gson;
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
	static User currentUser;
	static Object preg_id;

	public static void main(String[] args) {

		before((request, response) -> {
			try {
				Base.open();
			} catch (DBException e) {
				Base.close();
				Base.open();
			}

			String headerToken = (String) request.headers("Authorization");

			if (headerToken == null || headerToken.isEmpty() || !BasicAuth.authorize(headerToken)) {
				halt(401, "Usuario o clave invalidos \n");
			}

			currentUser = BasicAuth.getUser(headerToken);
		});

		after((request, response) -> {
			try {
				Base.close();
			} catch (DBException e) {
				Base.open();
				Base.close();
			}
			response.header("Access-Control-Allow-Origin", "*");
			response.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
			response.header("Access-Control-Allow-Headers",
					"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
		});

		options("/*", (request, response) -> {
			return "OK";
		});

		get("/hello/:name", (req, res) -> {
			return "hello" + req.params(":name");
		});

		post("/categoryquestion", (req, res) -> {
			Random r = new Random();
			JSONObject resp = new JSONObject();
			Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
			List<Question> questions;
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
					currentUser.get("username"));
			JSONObject resp = new JSONObject();
			resp.put("User", currentUser.get("Username"));
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
			if ((boolean) currentUser.get("admin")) {
				Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
				User user = User.findFirst("username = ?", bodyParams.get("username"));
				user.set("admin", true);
				user.saveIt();
				res.type("application/json");
				return user.toJson(true);
			} else {
				res.type("application/json");
				return currentUser.toJson(true);
			}
		});

		post("/usersdelete", (req, res) -> {
			List<User> users = User.findAll();
			for (User u : users) {
				u.delete();
			}
			return "Todos los usuario eliminados";
		}); 

		post("/userdelete", (req, res) -> {
			Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
			User usuario = User.findFirst("username = ?", bodyParams.get("username"));
			if (usuario != null) {
				usuario.delete();
				res.type("application/json");
				return "se ha borrado";
			} else {
				return "No se encontro usuario para borrar";
			}
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
					currentUser.get("username"), question.get("category"));
			UseStatisticsCategory stat = stats.get(0);
			if ((boolean) option.get("correct")) {
				UserQuestions preg = new UserQuestions();
				preg.set("user_id", currentUser.get("id"));
				preg.set("question id", preg_id);
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
			if ((boolean) currentUser.get("admin")) {
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
			} else {
				return "No tenes permiso para crear preguntas";
			}
		});

		post("/login", (req, res) -> {
			return currentUser.toJson(true);
		});

		post("/users", (req, res) -> {
			Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
			List<User> aux = User.where("Username = ?", bodyParams.get("username"));
			if (aux.size() == 0) {
				if (((String) bodyParams.get("username")).length() >= 1
						&& ((String) bodyParams.get("password")).length() >= 1) {
					User user = new User();
					user.set("username", bodyParams.get("username"));
					user.set("password", bodyParams.get("password"));
					user.set("admin", false);
					user.saveIt();
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
				} else {
					halt(403, "Usuario o clave invalidos");
					return "";
				}
			} else {
				halt(401, "Usuario o clave invalidos \n");
				return "Usuario ya existente";
			}
		});

	}
}
