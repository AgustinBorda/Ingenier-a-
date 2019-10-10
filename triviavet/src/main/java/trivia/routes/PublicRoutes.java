package trivia.routes;

import spark.*;
import static spark.Spark.halt;
import org.javalite.activejdbc.Base;

import java.util.Map;
import com.google.gson.Gson;

import trivia.models.User;
import trivia.utils.Email;

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

	public static final Route PostReset = (req, res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		User user = User.findFirst("email = ?", bodyParams.get("email"));
		if (user == null) {
			halt(401, "");
			return "";
		}
		System.out.println("Reset: " + user.get("username"));
		Email.getSingletonInstance().sendMail((String) bodyParams.get("email"), (String) user.get("username"));
	
		return true;
	};
	
	private static void loadSession(Request req, User user) {
		req.session().attribute("username", user.get("username"));
		req.session().attribute("id", user.get("id"));
		req.session().attribute("admin", user.get("admin"));
	}
}
