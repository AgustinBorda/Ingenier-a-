package trivia.models;

import org.javalite.activejdbc.Model;
import trivia.structures.OptionParam;
import java.util.ArrayList;

public class Option extends Model{

	static {
		validatePresenceOf("description").message("Please, provide your description");
		validatePresenceOf("correct").message("Please, provide if the option is correct or not");
		validatePresenceOf("question_id").message("The option must have associed a question");
	}
	
	public String getDescription() {
		return this.getString("description");
	}
	
	public boolean getCorrect() {
		return this.getBoolean("correct");
	}
	
	public Object getQuestionId() {
		return this.get("question_id");
	}
	
	public void setDescription(String desc) {
		this.setString("description", desc);
	}
	
	public void setCorrect(boolean correct) {
		this.setBoolean("correct", correct);
	}
	
	public void setQuestionId(Object id) {
		this.setInteger("question_id",id);
	}
}
