package trivia.models;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.validation.UniquenessValidator;

public class Category extends Model {
	
	static {
		validatePresenceOf("nombre").message("Please, provide nombre");
		validateWith(new UniquenessValidator("nombre")).message("This nombre is already load.");
	}
	
	public static void createCategory(String name) {
		Category category = new Category();
		category.set("nombre",name);
		category.saveIt();
		LazyList<User> users = User.findAll();
		for(User u : users) {
			UserStatisticsCategory.createUserStatistic(u,category);
		}
	}
}
