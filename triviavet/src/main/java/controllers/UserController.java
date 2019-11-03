package controllers;

import java.util.Map;

import org.javalite.activejdbc.Model;

import spark.Request;
import trivia.models.Category;
import trivia.models.User;
import trivia.models.UserStatisticsCategory;

public class UserController {
	
	public static User createUser(Map<String,Object> bodyParams) {
		User u = new User();
		u.set("username", bodyParams.get("username"),
				 "password", bodyParams.get("password"),
				 "admin", false,
				 "email", bodyParams.get("email")).saveIt();
		for (Model c : Category.findAll()) {
			UserStatisticsCategory.createUserStatistic(u, (Category) c);
		}
		return u;
	}
	
	public static void giveAdminPermissions(User u) {
		u.set("admin", true);
		u.saveIt();
	}
	
	public static void loadSession(Request req, User u) {
		req.session().attribute("username", u.getUsername());
		req.session().attribute("id", u.getId());
		req.session().attribute("admin", u.getAdmin());
	}
}
