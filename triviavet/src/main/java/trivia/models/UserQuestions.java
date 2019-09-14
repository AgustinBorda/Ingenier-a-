package trivia.models;

import org.javalite.activejdbc.Model;

public class UserQuestions extends Model {

	static {
		validatePresenceOf("user_id").message("Please, provide user_id");
		validatePresenceOf("question_id").message("Please, provide question_id");
	}
	
	public static void createUserQuestion(String username,String preg_id) {
		UserQuestions preg = new UserQuestions();
		User u = User.findFirst("username = ?", username);
		preg.set("user_id", u.get("id"));
		preg.set("question_id", preg_id);
		preg.saveIt();
	}
}
