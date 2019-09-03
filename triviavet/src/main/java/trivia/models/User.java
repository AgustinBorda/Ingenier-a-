package trivia.models;

import org.javalite.activejdbc.Model;

public class User extends Model {

	static {
		validatePresenceOf("username").message("Please, provide your username");
		validateWith(new UniquenessValidator("username")).message("This username is already taken.");
		validatePresenceOf("password").message("Please, provide your password");
		validatePresenceOf("admin").message("Please, provide admin flag");
	}
}
