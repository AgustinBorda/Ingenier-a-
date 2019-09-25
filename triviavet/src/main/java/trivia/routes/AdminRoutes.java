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

public class AdminRoutes {
	public static final Filter CheckAdmin = (request,response) -> {
		String headerToken = (String) request.headers("Authorization");
		if (request.session().attributes().isEmpty() || headerToken == null || headerToken.isEmpty()
				|| !BasicAuth.authorize(headerToken) || (boolean)request.session().attribute("admin") == false)
			halt(401, "Usuario o clave invalidos \n");
	};
	
	public static final Route PostQuestion = (req, res) -> {// its a get
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		Pair<JSONObject, String> answer = Question.getQuestion(bodyParams, req.session().attribute("id").toString());
		req.session().attribute("preg_id", answer.getSecond());
		return answer.getFirst();
	};
	
	public static final Route PostAdmin = (req, res) -> {
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
	};
}
