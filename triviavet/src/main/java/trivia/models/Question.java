package trivia.models;

import trivia.structures.Pair;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DBException;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.validation.UniquenessValidator;
import org.json.JSONObject;

import controllers.OptionController;
import controllers.QuestionStatisticController;
import controllers.UserQuestionController;
import controllers.UserStatisticsCategoryController;
import trivia.structures.QuestionParam;
/**
 * Question Model.
 * @author agustin
 *	Schema info:
 *	id int primary key
 *	description varchar unique not null
 *	category varchar foreign key to category
 */
public class Question extends Model {
	/*Valiadtors*/
	static {
		validatePresenceOf("description").message("Please, provide description");
		validateWith(new UniquenessValidator("description")).message("This description is already used.");
		validatePresenceOf("category").message("A question must have a category");
	}
	
	/**
	 * Get the description of a question
	 * @return the description
	 */
	public String getDescription() {
		return this.getString("description");
	}
	
	/**
	 * Get the category of a question.
	 * @return the category
	 */
	public String getCategory() {
		return this.getString("category");
	}
	
	/**
	 * Set a description for a question.
	 * @param desc the description
	 */
	public void setDescription(String desc) {
		this.setString("description", desc);
	}
	
	/**
	 * Set a category for a question.
	 * @param cat the category
	 */
	public void setCategory(String cat) {
		this.setString("category", cat);
	}

}
