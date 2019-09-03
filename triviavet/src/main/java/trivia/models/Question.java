package trivia.models;

import org.javalite.activejdbc.Model;

public class Question extends Model {

	static{
		validatePresenceOf("description").message("Please, provide description");
		validateWith(new UniquenessValidator("description")).message("This description is already used.");
	}
}
