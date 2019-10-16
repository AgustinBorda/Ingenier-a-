package trivia.models;

import java.util.Map;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.validation.UniquenessValidator;

public class User extends Model {

	static {
		validatePresenceOf("username").message("Please, provide your username");
		validateWith(new UniquenessValidator("username")).message("This username is already taken.");
		validatePresenceOf("email").message("Please, provide your email");
		validateWith(new UniquenessValidator("email")).message("This email is already taken.");
		validatePresenceOf("password").message("Please, provide your password");
		validatePresenceOf("admin").message("Please, provide admin flag");
	}
	
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
	
	public void giveAdminPermissions() {
		this.set("admin", true);
		this.saveIt();
	}

}
