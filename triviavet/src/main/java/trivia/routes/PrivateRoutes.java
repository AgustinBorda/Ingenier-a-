package trivia.routes;

import static spark.Spark.*;

import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.json.JSONObject;

import com.google.gson.Gson;

import controllers.QuestionController;
import controllers.UserStatisticsCategoryController;
import spark.*;
import trivia.BasicAuth;
import trivia.models.*;
import trivia.structures.*;

import org.javalite.activejdbc.DBException;

public class PrivateRoutes {

	public static final Filter CheckSession = (request, response) -> {
		String headerToken = (String) request.headers("Authorization");
		if (request.requestMethod() != "OPTIONS"){ 
			if (headerToken == null || headerToken.isEmpty() || !BasicAuth.authorize(headerToken)) {
				halt(401,"Token invalido \n");
			}
		}
	};

	public static final Route PostQuestion = (req, res) -> {// its a get
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		Base.openTransaction();
		try {
			Pair<JSONObject,Pair<String,List<Option>>> answer = QuestionController.getQuestion(bodyParams, req.session().attribute("id").toString());
			req.session().attribute("preg_id", answer.getSecond().getFirst());
			req.session().attribute("options", answer.getSecond().getSecond());
			Base.commitTransaction();
			res.status(200);
			return answer.getFirst();
		}
		catch(DBException e) {
			Base.rollbackTransaction();
			res.status(401);
			JSONObject resp = new JSONObject();
			resp.put("description", "Error interno del servidor");
			return resp;
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
		res.status(200);
		return resp;
	};

	public static final Route PostAnswer = (req, res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		Question question = QuestionController.getQuestionById(req.session().attribute("preg_id").toString());
		req.session().removeAttribute("preg_id");
		JSONObject resp;
		Base.openTransaction();
		try {
			resp = QuestionController.answerQuestion(bodyParams.get("answer").toString(),
					req.session().attribute("username"),req.session().attribute("options"), question);
			res.status(200);
			Base.commitTransaction();
			return resp;		
		}
		catch(DBException e) {
			resp = new JSONObject();
			res.status(401);
			resp.put("description", "Server Error");
			Base.rollbackTransaction();
			return resp;
		}
	};

	public static final Route GetStatistics = (req, res) -> {
		JSONObject resp;
		try {
			resp = UserStatisticsCategoryController.getStatistics(req.session().attribute("username").toString());
			res.status(200);
		}
		catch(DBException e) {
			resp = new JSONObject();
			res.status(401);
			resp.put("description", "Server Error");
		}

		return resp;
	};

	public static final Route GetCategory = (req, res) -> {
		JSONObject resp = new JSONObject();
		try {
			resp.put("categories", Category.findAll().collect("nombre").toArray());
			res.status(200);
		}
		catch(DBException e) {
			res.status(401);
			resp.put("description", "Server Error");
		}
		return resp;
	};
	
}
