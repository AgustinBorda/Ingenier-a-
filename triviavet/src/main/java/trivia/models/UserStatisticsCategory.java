package trivia.models;

import java.util.List;

import org.javalite.activejdbc.Model;
import org.json.JSONObject;

public class UserStatisticsCategory extends Model {

	static {
		validatePresenceOf("points").message("Please, provide points");
		validatePresenceOf("correct_answer").message("Please, provide correct_answer");
		validatePresenceOf("incorrect_answer").message("Please, provide incorrect_answer");
	}
	
	public static void createUserStatistic (User user, Category c) {
		UserStatisticsCategory usc = new UserStatisticsCategory();
		usc.set("user", user.get("username"));
		usc.set("nombre", c.get("nombre"));
		usc.set("points", 0);
		usc.set("correct_answer", 0);
		usc.set("incorrect_answer", 0);
		usc.saveIt();
	}
	
	public static JSONObject getStatistics(String username) {
		List<UserStatisticsCategory> estadisticas = UserStatisticsCategory.where("user = ?",
				username);
		JSONObject resp = new JSONObject();
		resp.put("User", username);
		int i = 0;
		for (UserStatisticsCategory e : estadisticas) {
			resp.put("cat" + i, e.get("nombre"));
			resp.put("points" + i, e.get("points"));
			resp.put("correct_answer" + i, e.get("correct_answer"));
			resp.put("incorrect_answer" + i, e.get("incorrect_answer"));
			resp.put("level" + i, e.calculateLevel());
			i++;
		}
		return resp;		
	}
	
	public int calculateLevel() {
		int i = 0;
		int j = 0;
		int num = this.getInteger("correct_answer");
		while(num >= i) {
			i+=2;
			num -= i;
			j++;
		}
		return j;
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