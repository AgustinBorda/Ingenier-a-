package trivia.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.validation.UniquenessValidator;

public class QuestionStatistic extends Model {
	
	static {
		validatePresenceOf("question").message("The stat must reference a question");
		validatePresenceOf("wrong_attempts").message("The stat must have a number of wrong attempts");
		validatePresenceOf("right_attempts").message("The stat must have a number of correct attempts");
		validatePresenceOf("total_attempts").message("The stat must have a number of wrong attempts");
	}
	
	public String getQuestion() {
		return this.getString("question");
	}
	
	public int getWrongAttempts() {
		return this.getInteger("wrong_attempts");
	}
	
	public int getRightAttempts() {
		return this.getInteger("right_attempts");
	}
	
	public int getTotalAttempts() {
		return this.getInteger("total_attempts");
	}
	
	public void setQuestion(String question) {
		this.setString("question",question);
	}
	
	public void setWrongAttempts(int wrongAttempts) {
		this.setInteger("wrong_attempts",wrongAttempts);
	}
	
	public void setRightAttempts(int rightAttempts) {
		this.setInteger("right_attempts",rightAttempts);
	}
	
	public void setTotalAttempts(int attempts) {
		this.setInteger("total_attempts",attempts);
	}
}
