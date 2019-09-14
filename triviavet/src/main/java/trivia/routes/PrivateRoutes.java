package trivia.routes;

import static spark.Spark.halt;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.javalite.activejdbc.Base;
import org.json.JSONObject;

import com.google.gson.Gson;

import spark.*;
import trivia.BasicAuth;
import trivia.models.Category;
import trivia.models.Option;
import trivia.models.Question;
import trivia.models.UseStatisticsCategory;
import trivia.models.User;
import trivia.models.UserQuestions;
import trivia.structures.OptionParam;
import trivia.structures.QuestionParam;

public class PrivateRoutes {

	public static final Filter CheckSession = (request, response) -> {
		String headerToken = (String) request.headers("Authorization");
		if (request.session().attributes().isEmpty() || headerToken == null || headerToken.isEmpty()
				|| !BasicAuth.authorize(headerToken))
			halt(401, "Usuario o clave invalidos \n");
	};

	public static final Filter BaseClose = (request, response) -> {
		if (Base.hasConnection())
			Base.close();

		response.header("Access-Control-Allow-Origin", "*");
		response.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
		response.header("Access-Control-Allow-Headers",
				"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
	};

	public static final Route PostCategoryQuestion = (req, res) -> {// its a get
		Random r = new Random();
		JSONObject resp = new JSONObject();
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		List<Question> questions;
		questions = Question.findBySQL(
				"SELECT * FROM questions WHERE id NOT IN (SELECT id FROM questions "
						+ "INNER JOIN ((SELECT * FROM user_questions WHERE user_id = ?) as contestadas) "
						+ "ON questions.id = contestadas.question_id) AND category = ?",
				req.session().attribute("id").toString(), bodyParams.get("category"));
		Question question = questions.get(r.nextInt(questions.size()));
		List<Option> options = Option.where("question_id = ?", question.get("id"));
		req.session().attribute("preg_id", question.get("id"));
		resp.put("description", question.get("description"));

		int i = 1;
		for (Option o : options) {
			resp.put("answer" + i, o.get("description"));
			i++;
		}
		return resp;
	};

	public static final Route GetQuestion = (req, res) -> {
		Random r = new Random();
		JSONObject resp = new JSONObject();
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		List<Question> questions;
		questions = Question
				.findBySQL(
						"SELECT * FROM questions WHERE id NOT IN " + "(SELECT id FROM questions INNER JOIN "
								+ "((SELECT * FROM user_questions WHERE user_id = ?) as contestadas) "
								+ "ON questions.id = contestadas.question_id)",
						req.session().attribute("id").toString());
		Question question = questions.get(r.nextInt(questions.size()));
		List<Option> options = Option.where("question_id = ?", question.get("id"));
		req.session().attribute("preg_id", question.get("id"));
		resp.put("description", question.get("description"));

		int i = 1;
		for (Option o : options) {
			resp.put("answer" + i, o.get("description"));
			i++;
		}
		return resp;
	};

	public static final Route GetStatistics = (req, res) -> {
		System.out.println("/loged/statistics");
		List<UseStatisticsCategory> estadisticas = UseStatisticsCategory.where("user = ?",
				req.session().attribute("username").toString());
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
		System.out.println(resp);
		return resp;
	};

	public static final Route PostAdmin = (req, res) -> {
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
		}
	};

	public static final Route PostUserDelete = (req, res) -> {
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
	};

	public static final Route PostAnswer = (req, res) -> {
			Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
			JSONObject resp = new JSONObject();
			int preg_id = req.session().attribute("preg_id");
			List<Option> options = Option.where("question_id = ?", preg_id);
			List<Question> questions = Question.where("id = ?", preg_id);
			Question question = questions.get(0);
			int i = Integer.parseInt((String) bodyParams.get("answer"));
			Option option = options.get(i - 1);
			List<UseStatisticsCategory> stats = UseStatisticsCategory.where("user = ? AND nombre = ?",
					req.session().attribute("username").toString(), question.get("category"));
			UseStatisticsCategory stat = stats.get(0);
			if ((boolean) option.get("correct")) {
				UserQuestions preg = new UserQuestions();

				User u = User.findFirst("username = ?", req.session().attribute("username").toString());
				preg.set("user_id", u.get("id"));
				preg.set("question_id", preg_id);
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
		};

	public static final Route PostQuestions = (req, res) -> {
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
		};

	public static final Route GetCategory =  (req, res) -> {
			JSONObject resp = new JSONObject();
			resp.put("categories", Category.findAll().collect("nombre").toArray());
			return resp;
		};
	
}
