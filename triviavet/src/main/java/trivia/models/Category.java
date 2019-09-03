package trivia.models;

import org.javalite.activejdbc.Model;

public class Category extends Model {
	
	static {
		validatePresenceOf("nombre").message("Please, provide nombre");
		validateWith(new UniquenessValidator("nombre")).message("This nombre is already load.");
	}
}
