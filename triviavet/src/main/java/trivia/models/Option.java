package trivia.models;

import org.javalite.activejdbc.Model;
import trivia.structures.OptionParam;
import java.util.ArrayList;
/**
 * Option model.
 * @author agustin
 *
 *Schema info:
 *id int primary key
 *description varchar unique
 *boolean correct not null
 *question id Not null foreign key to questions
 */
public class Option extends Model{
	/*Validators*/
	static {
		validatePresenceOf("description").message("Please, provide your description");
		validatePresenceOf("correct").message("Please, provide if the option is correct or not");
		validatePresenceOf("question_id").message("The option must have associed a question");
	}

	/**
	 * Get the description of the option.
	 * @return the description
	 */
	public String getDescription() {
		return this.getString("description");
	}

	/**
	 * Get if the option is correct.
	 * @return if the option is correct
	 */
	public boolean getCorrect() {
		return this.getBoolean("correct");
	}

	/**
	 * Get the id of the question.
	 * @return the id of the question
	 */
	public Object getQuestionId() {
		return this.get("question_id");
	}

	/**
	 * Set the description of the option
	 * @param desc the new description
	 */
	public void setDescription(String desc) {
		this.setString("description", desc);
	}
	/**
	 * Set if the option is correct
	 * @param correct
	 */
	public void setCorrect(boolean correct) {
		this.setBoolean("correct", correct);
	}
	/**
	 * Set the question id
	 * @param id
	 */
	public void setQuestionId(Object id) {
		this.setInteger("question_id",id);
	}
}
