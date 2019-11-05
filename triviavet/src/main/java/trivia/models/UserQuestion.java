package trivia.models;

import org.javalite.activejdbc.Model;
/**
 * UserQuestion model.
 * @author agustin
 * Schema info:
 * id int primary key
 * user_id int not null foreign key to user
 * question_id int not null foreign key to question
 */
public class UserQuestion extends Model {
	/*Validators*/
	static {
		validatePresenceOf("user_id").message("Please, provide user_id");
		validatePresenceOf("question_id").message("Please, provide question_id");
	}
	/**
	 * Get the user id.
	 * @return the user id
	 */
	public Object getUserId() {
		return this.get("user_id");
	}
	/**
	 * Get the question id.
	 * @return the question id
	 */
	public Object getQuestionId() {
		return this.get("question_id");
	}
	/**
	 * Set the user id.
	 * @param id the user id
	 */
	public void setUserId(Object id) {
		this.set("user_id",id);
	}
	/**
	 * Set the question id.
	 * @param id the question id
	 */
	public void setQuestionId(Object id) {
		this.set("question_id",id);
	}
	
}
