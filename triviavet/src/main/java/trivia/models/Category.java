package trivia.models;

/**
 * Category Model
 * Schema info;
 * id int(11) Primary key
 * nombre varchar unique
 * 
 */
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.validation.UniquenessValidator;

public class Category extends Model {
	/*Validators*/
	static {
		validatePresenceOf("nombre").message("Please, provide nombre");
		validateWith(new UniquenessValidator("nombre")).message("This nombre is already load.");
	}
	/**
	 * Get the name of the category
	 * @return the name of the category
	 */
	public String getNombre() {
		return this.getString("nombre");
	}
	
	/**
	 * Set the name of the category.
	 * @param n the new name of the category
	 */
	public void setNombre(String n) {
		this.set("nombre",n);
	}

}
