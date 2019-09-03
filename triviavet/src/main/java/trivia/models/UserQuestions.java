package trivia.models;

import org.javalite.activejdbc.Model;

public class UserQuestions extends Model {

	static {
		validatePresenceOf("user_id").message("Please, provide user_id");
		validatePresenceOf("question_id").message("Please, provide question_id");
	}
}
