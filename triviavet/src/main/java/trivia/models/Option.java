package trivia.models;

import org.javalite.activejdbc.Model;

public class Option extends Model{

	static {
		validatePresenceOf("description").message("Please, provide your description");
	}
}