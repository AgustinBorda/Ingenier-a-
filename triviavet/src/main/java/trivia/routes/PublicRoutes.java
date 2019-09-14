package trivia.routes;

import static spark.Spark.halt;

import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;

import com.google.gson.Gson;

import spark.*;
import trivia.models.Category;
import trivia.models.UseStatisticsCategory;
import trivia.models.User;

public class PublicRoutes {

	public static final Filter BaseOpen = (request, response) -> {
		if (!Base.hasConnection())
			Base.open();	
	};
	
	public static final Filter BaseClose = (request, response) -> {
		if(Base.hasConnection())
			Base.close();

		response.header("Access-Control-Allow-Origin", "*");
		response.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
		response.header("Access-Control-Allow-Headers",
				"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
	};
	
	public static final Route PostLogin =  (req, res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		User u = User.findFirst("username = ? and password = ?", bodyParams.get("username"), bodyParams.get("password"));
		if (u != null) {
			req.session().attribute("username", u.get("username"));
			req.session().attribute("id",u.get("id"));
			req.session().attribute("admin", u.get("admin"));
			System.out.println("Loged: "+u.get("username"));
			return true;
		}
		return null;
	};

	public static final Route PostUsers = (req, res) -> {
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
			System.out.println("Registred: "+user.get("username"));
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
			req.session().attribute("username", user.get("username"));
			req.session().attribute("id",user.get("id"));
			req.session().attribute("admin", user.get("admin"));
			return user.toJson(true);
		};
}
