package trivia.routes;

import static spark.Spark.halt;

import java.util.Map;

import org.javalite.activejdbc.Base;

import com.google.gson.Gson;

import spark.*;
import trivia.models.User;

public class PublicRoutes {

	public static final Filter BaseOpen = (request, response) -> {
		if (!Base.hasConnection())
			Base.open();
	};

	public static final Filter BaseClose = (request, response) -> {
		if (Base.hasConnection())
			Base.close();

		response.header("Access-Control-Allow-Origin", "*");
		response.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
		response.header("Access-Control-Allow-Headers",
				"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
	};

	public static final Route PostLogin = (req, res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		User user = User.findFirst("username = ? and password = ?", bodyParams.get("username"),
				bodyParams.get("password"));
		if (user != null) {
			loadSession(req, user);
			System.out.println("Loged: " + user.get("username"));
			return true;
		}
		res.status(401);
		return true;
	};

	public static final Route PostUsers = (req, res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		if (User.findFirst("Username = ?", bodyParams.get("username")) != null) {
			halt(401, "");
			return "";
		}
		if (((String) bodyParams.get("username")).length() == 0
				|| ((String) bodyParams.get("password")).length() == 0) {
			halt(403, "");
			return "";
		}
		User user = User.createUser(bodyParams);
		System.out.println("Registred: " + user.get("username"));
		
		loadSession(req, user);
		return user.toJson(true);
	};
	
	private static void loadSession(Request req, User user) {
		req.session().attribute("username", user.get("username"));
		req.session().attribute("id", user.get("id"));
		req.session().attribute("admin", user.get("admin"));
	}
}
