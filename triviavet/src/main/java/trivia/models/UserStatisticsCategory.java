package trivia.models;

import java.util.ArrayList;
import java.util.List;
import org.javalite.activejdbc.Model;
import org.json.JSONObject;

import trivia.structures.StatisticContainer;

public class UserStatisticsCategory extends Model {

	static {
		validatePresenceOf("user").message("The stat must reference a user");
		validatePresenceOf("nombre").message("The stat must reference a category");
		validatePresenceOf("points").message("Please, provide points");
		validatePresenceOf("correct_answer").message("Please, provide correct_answer");
		validatePresenceOf("incorrect_answer").message("Please, provide incorrect_answer");
	}
	
	public String getUsername() {
		return this.getString("user");
	}
	
	public String getCategory() {
		return this.getString("nombre");
	}
	
	public int getPoints() {
		return this.getInteger("points");
	}
	
	public int getCorrectAnswers() {
		return this.getInteger("correct_answer");
	}
	
	public int getIncorrectAnswers() {
		return this.getInteger("incorrect_answer");
	}
	
	public void setUsername(String username) {
		this.setString("user", username);
	}
	
	public void setCategory(String cat) {
		this.setString("nombre", cat);
	}
	
	public void setPoints(int points) {
		this.setInteger("points", points);
	}
	
	public void setCorrectAnswers(int correctAnswers) {
		this.setInteger("correct_answer", correctAnswers);
	}
	
	public void setIncorrectAnswers(int incorrectAnswers) {
		this.setInteger("incorrect_answer", incorrectAnswers);
	}

}