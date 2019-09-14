package trivia;

import static spark.Spark.*;
import org.javalite.activejdbc.Base;

import trivia.models.*;
import trivia.routes.*;
import trivia.structures.*;
import trivia.BasicAuth;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;

public class App {


	public static void main(String[] args) {

		before("*",Public.BaseOpen);

		after("*", Public.BaseClose);

		before("/logged/*",(request, response) -> {
			String headerToken = (String) request.headers("Authorization");
			if (headerToken == null || headerToken.isEmpty() || !BasicAuth.authorize(headerToken))
				halt(401, "Usuario o clave invalidos \n");
		});

		post("/logged/question", (req, res) -> {// its a get
			Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
			Pair<JSONObject,String> answer = Question.getQuestion(bodyParams, req.session().attribute("id").toString());
			req.session().attribute("preg_id",answer.getSecond());
			return answer.getFirst();				
		});

		get("/logged/statistics", (req, res) -> {
			System.out.println("/logged/statistics");
			return UseStatisticsCategory.getStatistics(req.session().attribute("username").toString());
		});

		post("/logged/admin", (req, res) -> {
			User user;
			JSONObject resp = new JSONObject();
			if ((boolean) req.session().attribute("admin")) {
				Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
				user = User.findFirst("username = ?", bodyParams.get("username"));
				user.giveAdminPermissions();
				resp.put("answer", "OK");
			} else {
				resp.put("answer", "permission denied");
			}
			return resp;
		});

		post("/logged/userdelete", (req, res) -> {
			Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
			User usuario = User.findFirst("username = ?", bodyParams.get("username"));
			JSONObject resp = new JSONObject();
			if (usuario != null) {
				usuario.delete();
				resp.put("answer", "Usuario Borrado");
			} else {
				resp.put("answer", "El usuario no existe");
			}
			return resp;
		});

		post("/logged/answer", (req, res) -> {
			Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
			JSONObject resp;
			Question question = Question.getQuestion(req.session().attribute("preg_id").toString());
			req.session().removeAttribute("preg_id");
			return question.answerQuestion(bodyParams.get("answer").toString(), req.session().attribute("username"));
		});

		post("/logged/questions", (req, res) -> {
			if (!(boolean) req.session().attribute("admin"))
				return "No tenes permiso para crear preguntas";
			QuestionParam bodyParams = new Gson().fromJson(req.body(), QuestionParam.class);
			Question question = new Question();
			question.createQuestion(bodyParams);
			return question.toJson(true);
		});

		post("/login", (req, res) -> {
			Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class); // 0 seguridad
			User u = User.findFirst("username = ? and password = ?", bodyParams.get("username"), bodyParams.get("password"));
			if (u != null) {
				req.session().attribute("username", u.get("username"));
				req.session().attribute("id",u.get("id"));
				req.session().attribute("admin", u.get("admin"));
				System.out.println("logged: "+u.get("username"));
				return true;
			}
			return null;
		});

		post("/users", (req, res) -> {
			Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
			if (User.findFirst("Username = ?", bodyParams.get("username")) != null) {
				halt(401, "");
				return null;
			}
			if (((String) bodyParams.get("username")).length() == 0	|| ((String) bodyParams.get("password")).length() == 0) {
				halt(403, "");
				return null;
			}
			User user = new User();
			user.createUser(bodyParams);
			List<Category> cat = Category.findAll();
			for (Category c : cat) {
				UseStatisticsCategory stats = new UseStatisticsCategory();
				stats.createUserStatistic(user, c);
			}
			req.session().attribute("username", user.get("username"));
			req.session().attribute("id",user.get("id"));
			req.session().attribute("admin", user.get("admin"));
			return user.toJson(true);
		});

		get("/category", (req, res) -> {
			JSONObject resp = new JSONObject();
			resp.put("categories", Category.findAll().collect("nombre").toArray());
			return resp;
		});
	}
}
