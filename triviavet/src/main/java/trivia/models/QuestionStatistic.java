package trivia.models;

import org.javalite.activejdbc.Model;
/**
 * QuestionStatistic model.
 * @author agustin
 * Schema Info:
 * id int primary key
 * question foreign key to question
 * wrong_attempts int not null
 * right_attempts int not null
 * total_attempts int not null
 */
public class QuestionStatistic extends Model {
	
	static {
		validatePresenceOf("question").message("The stat must reference a question");
		validatePresenceOf("wrong_attempts").message("The stat must have a number of wrong attempts");
		validatePresenceOf("right_attempts").message("The stat must have a number of correct attempts");
		validatePresenceOf("total_attempts").message("The stat must have a number of wrong attempts");
	}
	/**
	 * Get the question description.
	 * @return the question description.
	 */
	public String getQuestion() {
		return this.getString("question");
	}
	
	/**
	 * Get the wrong attempts to answer the question.
	 * @return the wrong attempts
	 */
	public int getWrongAttempts() {
		return this.getInteger("wrong_attempts");
	}
	/**
	 * Get the right attempts to answer the question.
	 * @return the right attempts
	 */	
	public int getRightAttempts() {
		return this.getInteger("right_attempts");
	}
	/**
	 * Get the total attempts to answer the question.
	 * @return the total attempts
	 */
	public int getTotalAttempts() {
		return this.getInteger("total_attempts");
	}
	/**
	 * Set the question for the category
	 * @param question the question name
	 */
	public void setQuestion(String question) {
		this.setString("question",question);
	}
	/**
	 * Set the wrong attempts to answer the question.
	 * @param wrongAttempts the wrong attempts
	 */
	public void setWrongAttempts(int wrongAttempts) {
		this.setInteger("wrong_attempts",wrongAttempts);
	}
	/**
	 * Set the right attempts to answer the question.
	 * @param rightAttempts the right attempts
	 */
	public void setRightAttempts(int rightAttempts) {
		this.setInteger("right_attempts",rightAttempts);
	}
	/**
	 * Set the total attempts to answer the question.
	 * @param attempts the total attempts
	 */
	public void setTotalAttempts(int attempts) {
		this.setInteger("total_attempts",attempts);
	}
}
