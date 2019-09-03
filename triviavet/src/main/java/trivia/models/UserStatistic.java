package trivia.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.validation.UniquenessValidator;

public class UserStatistic extends Model {

	static {
		validatePresenceOf("user").message("Please, provide the user");
		validateWith(new UniquenessValidator("user")).message("This user is already loaded.");
		validatePresenceOf("points").message("Please, provide points");
		validatePresenceOf("correct_answer").message("Please, provide correct_answer");
	}
}
