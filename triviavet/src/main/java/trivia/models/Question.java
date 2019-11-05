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

public class Question extends Model {

	static {
		validatePresenceOf("description").message("Please, provide description");
		validateWith(new UniquenessValidator("description")).message("This description is already used.");
		validatePresenceOf("category").message("A question must have a category");
	}
	
	public String getDescription() {
		return this.getString("description");
	}
	
	public String getCategory() {
		return this.getString("category");
	}
	
	public void setDescription(String desc) {
		this.setString("description", desc);
	}
	
	public void setCategory(String cat) {
		this.setString("category", cat);
	}

}
