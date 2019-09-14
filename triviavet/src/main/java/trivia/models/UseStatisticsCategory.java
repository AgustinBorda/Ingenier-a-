package trivia.models;

import java.util.List;

import org.javalite.activejdbc.Model;
import org.json.JSONObject;

public class UseStatisticsCategory extends Model {

	static {
		validatePresenceOf("points").message("Please, provide points");
		validatePresenceOf("correct_answer").message("Please, provide correct_answer");
		validatePresenceOf("incorrect_answer").message("Please, provide incorrect_answer");
	}
	
	public void createUserStatistic (User user, Category c) {
		this.set("user", user.get("username"));
		this.set("nombre", c.get("nombre"));
		this.set("points", 0);
		this.set("correct_answer", 0);
		this.set("incorrect_answer", 0);
		this.saveIt();
	}
	
	public static JSONObject getStatistics(String username) {
		List<UseStatisticsCategory> estadisticas = UseStatisticsCategory.where("user = ?",
				username);
		JSONObject resp = new JSONObject();
		resp.put("User", username);
		int i = 0;
		for (UseStatisticsCategory e : estadisticas) {
			resp.put("cat" + i, e.get("nombre"));
			resp.put("points" + i, e.get("points"));
			resp.put("correct_answer" + i, e.get("correct_answer"));
			resp.put("incorrect_answer" + i, e.get("incorrect_answer"));
			i++;
		}
		return resp;		
	}
	
	public void updateCorrectAnswer() {
		int j = (int) this.get("points") + 1;
		this.set("points", j);
		j = (int) this.get("correct_answer") + 1;
		this.set("correct_answer", j);
		this.saveIt();
	}
	
	public void updateIncorrecrAnswer() {
		this.set("incorrect_answer", (int) this.get("incorrect_answer") + 1);
		this.saveIt();
	}
}