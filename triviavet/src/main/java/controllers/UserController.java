package controllers;

import java.util.Map;

import org.javalite.activejdbc.Model;

import spark.Request;
import trivia.models.Category;
import trivia.models.User;
/**
 * Class that provides a controller for the User model.
 */
public class UserController {
	/**
	 * Create a new user.
	 * @param bodyParams the params of the usert
	 * @return the user
	 */
	public static User createUser(Map<String,Object> bodyParams) {
		User u = new User();
		u.set("username", bodyParams.get("username"),
				 "password", bodyParams.get("password"),
				 "admin", false,
				 "email", bodyParams.get("email")).saveIt();
		for (Model c : Category.findAll()) {
			UserStatisticsCategoryController.createUserStatistic(u, (Category) c);
		}
		return u;
	}
	/**
	 * Give a given user admin permissions.
	 * @param u a user
	 */
	public static void giveAdminPermissions(User u) {
		u.set("admin", true);
		u.saveIt();
	}
	/**
	 * Loads a session.
	 * @param req a request
	 * @param u a user
	 */
	public static void loadSession(Request req, User u) {
		req.session().attribute("username", u.getUsername());
		req.session().attribute("id", u.getId());
		req.session().attribute("admin", u.getAdmin());
	}
}
