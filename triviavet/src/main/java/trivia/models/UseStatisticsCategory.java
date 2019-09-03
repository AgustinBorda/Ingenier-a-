package trivia.models;

import org.javalite.activejdbc.Model;

public class UseStatisticsCategory extends Model {

	static {
		validatePresenceOf("points").message("Please, provide points");
		validatePresenceOf("correct_answer").message("Please, provide correct_answer");
		validatePresenceOf("incorrect_answer").message("Please, provide incorrect_answer");
	}
}