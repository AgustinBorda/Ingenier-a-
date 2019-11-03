package trivia.models;

import org.javalite.activejdbc.Model;

public class UserQuestion extends Model {

	static {
		validatePresenceOf("user_id").message("Please, provide user_id");
		validatePresenceOf("question_id").message("Please, provide question_id");
	}
	
	public Object getUserId() {
		return this.get("user_id");
	}
	
	public Object getQuestionId() {
		return this.get("question_id");
	}
	
	public void setUserId(Object id) {
		this.set("user_id",id);
	}
	
	public void setQuestionId(Object id) {
		this.set("question_id",id);
	}
	
}
