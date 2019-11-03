package trivia.models;

import java.util.Map;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.validation.UniquenessValidator;

import spark.Request;

public class User extends Model {

	static {
		validatePresenceOf("username").message("Please, provide your username");
		validateWith(new UniquenessValidator("username")).message("This username is already taken.");
		validatePresenceOf("email").message("Please, provide your email");
		validateWith(new UniquenessValidator("email")).message("This email is already taken.");
		validatePresenceOf("password").message("Please, provide your password");
		validatePresenceOf("admin").message("Please, provide admin flag");
	}
	
	public String getUsername() {
		return this.getString("username");
	}
	
	public String getEmail() {
		return this.getString("email");
	}
	
	public String getPassword() {
		return this.getString("password");
	}
	
	public boolean getAdmin() {
		return this.getBoolean("admin");
	}
	
	public void setUsername(String user) {
		this.setString("username", user);
	}
	
	public void setEmail(String email) {
		this.setString("email", email);
	}
	
	public void setPassword(String password) {
		this.setString("password", password);
	}
	
	public void setAdmin(boolean admin) {
		this.setBoolean("admin", admin);
	}

}
