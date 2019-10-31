package trivia.routes;

import spark.*;
import static spark.Spark.halt;
import org.javalite.activejdbc.Base;
import org.json.JSONObject;

import java.util.Map;
import com.google.gson.Gson;

import trivia.models.User;
import trivia.utils.Email;

public class PublicRoutes {

	public static final Filter BaseOpen = (request, response) -> {
		if (!Base.hasConnection()) {
			Base.open();
		}
	};
	

	public static final Filter BaseClose = (request, response) -> {
		if (Base.hasConnection()) {
			Base.close();
		}
		response.header("Access-Control-Allow-Origin", "*");
		response.header("Access-Control-Allow-Methods", "*");
		response.header("Access-Control-Allow-Headers", "*");
		response.header("Access-Control-Allow-Body", "*");
		response.header("Content-Type","application/json");
	};
	
	public static final Route SetHeaders = (request, response) -> {	
        return "OK";
	};

	public static final Route PostLogin = (req, res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		User user = User.findFirst("username = ? and password = ?", bodyParams.get("username"),
				bodyParams.get("password"));
		if (user != null) {
			user.loadSession(req);
			JSONObject resp = new JSONObject();
			resp.put("isAdmin", user.get("admin"));
			return resp;
		}
		res.status(401);
		return false;
	};

	public static final Route PostUsers = (req, res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		if (!bodyParams.containsKey("username") || !bodyParams.containsKey("password")
				|| !bodyParams.containsKey("email")) {
			res.status(403);
			return false;
		}
		if (((String) bodyParams.get("username")).length() == 0 || ((String) bodyParams.get("password")).length() == 0
				|| ((String) bodyParams.get("email")).length() == 0) {
			res.status(403);
			return false;
		}
		if (User.findFirst("Username = ?", bodyParams.get("username")) != null) {
			res.status(401);
			return false;
		}
		if (User.findFirst("email = ?", bodyParams.get("email")) != null) {
			res.status(401);
			return false;
		}
		User user = User.createUser(bodyParams);
		System.out.println("Registred: " + user.get("username"));
		user.loadSession(req);
		res.status(200);
		JSONObject resp = new JSONObject();
		resp.put("isAdmin", user.get("admin"));
		return resp;
	};


	public static final Route PostReset = (req, res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		User user = User.findFirst("username = ?", bodyParams.get("username"));
		if (user == null) {
			System.out.println("Try reset: " + bodyParams.get("username"));
			res.status(401);
			return false;
		}
		else {
			System.out.println("Reset: " + user.get("username"));
			Email.getSingletonInstance().sendMail((String) user.get("email"), (String) user.get("username"));
			res.status(200);
			return true;
		}
	};
	
	public static final Route PostNewPass= (req, res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		String username = Email.getSingletonInstance().checkCode((String) bodyParams.get("code"));
		if(username == null) {
			res.status(401);
			return false;
		}
		else {
			System.out.println("New pass to: " + username);
			User.update("password = ?", "username = ?", bodyParams.get("newPass"), username);
			res.status(200);
			return true;
		}
	};

}
