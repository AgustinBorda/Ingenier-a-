package trivia.models;

import java.util.ArrayList;
import java.util.List;
import org.javalite.activejdbc.Model;
import org.json.JSONObject;

import trivia.structures.StatisticContainer;
/**
 * UserStatisticCategory model.
 * @author agustin
 * Schema info:
 * id int primary key
 * user varchar not null
 * nombre varchar not null
 * points int not null
 * correct_answer int not null
 * incorrect_answer int not null
 */
public class UserStatisticsCategory extends Model {
	/*Validators*/
	static {
		validatePresenceOf("user").message("The stat must reference a user");
		validatePresenceOf("nombre").message("The stat must reference a category");
		validatePresenceOf("points").message("Please, provide points");
		validatePresenceOf("correct_answer").message("Please, provide correct_answer");
		validatePresenceOf("incorrect_answer").message("Please, provide incorrect_answer");
	}
	/**
	 * Get the username.
	 * @return the username
	 */
	public String getUsername() {
		return this.getString("user");
	}
	/**
	 * Get the category.
	 * @return the category 
	 */
	public String getCategory() {
		return this.getString("nombre");
	}
	/**
	 * Get the points of the stat.
	 * @return the points
	 */
	public int getPoints() {
		return this.getInteger("points");
	}
	/**
	 * Get the number of correct answers.
	 * @return the number of correct answers
	 */
	public int getCorrectAnswers() {
		return this.getInteger("correct_answer");
	}
	/**
	 * Get the number of incorrect answers.
	 * @return the number of incorrect answers
	 */
	public int getIncorrectAnswers() {
		return this.getInteger("incorrect_answer");
	}
	/**
	 * Set the username.
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.setString("user", username);
	}
	/**
	 * Set the category
	 * @param cat the category
	 */
	public void setCategory(String cat) {
		this.setString("nombre", cat);
	}
	/**
	 * Set the points of the stat.
	 * @param points the points
	 */
	public void setPoints(int points) {
		this.setInteger("points", points);
	}
	/**
	 * Set the number of correct answers.
	 * @param correctAnswers the number of correct answers
	 */
	public void setCorrectAnswers(int correctAnswers) {
		this.setInteger("correct_answer", correctAnswers);
	}
	/**
	 * Set the number of incorrect answers.
	 * @param incorrectAnswers the number of incorrect answers
	 */
	public void setIncorrectAnswers(int incorrectAnswers) {
		this.setInteger("incorrect_answer", incorrectAnswers);
	}

}