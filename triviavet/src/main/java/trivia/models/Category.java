package trivia.models;


import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.validation.UniquenessValidator;

public class Category extends Model {
	
	static {
		validatePresenceOf("nombre").message("Please, provide nombre");
		validateWith(new UniquenessValidator("nombre")).message("This nombre is already load.");
	}
	public String getNombre() {
		return this.getString("nombre");
	}
	
	public void setNombre(String n) {
		this.set("nombre",n);
	}

}
