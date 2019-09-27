package trivia.routes;

import static spark.Spark.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.javalite.activejdbc.Base;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import spark.*;
import trivia.BasicAuth;
import trivia.models.*;
import trivia.structures.*;

public class PrivateRoutes {

	public static final Filter CheckSession = (request, response) -> {
		String headerToken = (String) request.headers("Authorization");
		if (request.session().attributes().isEmpty() || headerToken == null || headerToken.isEmpty()
				|| !BasicAuth.authorize(headerToken))
			halt(401, "Usuario o clave invalidos \n");
	};

	public static final Route PostQuestion = (req, res) -> {// its a get
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		Pair<JSONObject, String> answer = Question.getQuestion(bodyParams, req.session().attribute("id").toString());
		req.session().attribute("preg_id", answer.getSecond());
		return answer.getFirst();
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
		Question question = Question.getQuestion(req.session().attribute("preg_id").toString());
		req.session().removeAttribute("preg_id");
		return question.answerQuestion(bodyParams.get("answer").toString(), req.session().attribute("username"));
	};

	public static final Route GetStatistics = (req, res) -> {
		System.out.println("/loged/statistics");
		return UseStatisticsCategory.getStatistics(req.session().attribute("username").toString());
	};

	public static final Route GetCategory = (req, res) -> {
		JSONObject resp = new JSONObject();
		resp.put("categories", Category.findAll().collect("nombre").toArray());
		return resp;
	};

}
