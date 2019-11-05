package trivia.models;

import java.util.Map;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.validation.UniquenessValidator;

import spark.Request;
/**
 * User model.
 * @author agustin
 * Schema info
 * id int primary key
 * username varchar unique not null
 * email varchar unique not null
 * password varchar not null
 * admin boolean not null
 */
public class User extends Model {
	/*Validator*/
	static {
		validatePresenceOf("username").message("Please, provide your username");
		validatePresenceOf("email").message("Please, provide your email");
		validatePresenceOf("password").message("Please, provide your password");
		validatePresenceOf("admin").message("Please, provide admin flag");
	}
	/**
	 * Get the username.
	 * @return the username
	 */
	public String getUsername() {
		return this.getString("username");
	}
	/**
	 * Get the email.
	 * @return the email
	 */
	public String getEmail() {
		return this.getString("email");
	}
	/**
	 * Get the password.
	 * @return the password
	 */
	public String getPassword() {
		return this.getString("password");
	}
	/**
	 * Get if the user is admin.
	 * @return true iff the user is admin
	 */
	public boolean getAdmin() {
		return this.getBoolean("admin");
	}
	/**
	 * Set the username.
	 * @param user the new username
	 */
	public void setUsername(String user) {
		this.setString("username", user);
	}
	/**
	 * Set the email.
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.setString("email", email);
	}
	/**
	 * Set the password.
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.setString("password", password);
	}
	/**
	 * Set if the user is admin.
	 * @param admin if the user is admin or not
	 */
	public void setAdmin(boolean admin) {
		this.setBoolean("admin", admin);
	}

}
