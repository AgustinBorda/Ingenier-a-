package controllers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import trivia.models.Category;
import trivia.models.User;
import trivia.models.UserStatisticsCategory;
import trivia.structures.StatisticContainer;
/**
 * Class that provides a controller for QuestionStatisticCategory.
 * @author agustin
 *
 */
public class UserStatisticsCategoryController {
	/**
	 * Create a USerStatisticCategory
	 * @param user the user who belongs the stat.
	 * @param c the category who belongs the stat.
	 */
	public static void createUserStatistic (User user, Category c) {
		UserStatisticsCategory usc = new UserStatisticsCategory();
		usc.set("user", user.getUsername());
		usc.set("nombre", c.getNombre());
		usc.set("points", 0);
		usc.set("correct_answer", 0);
		usc.set("incorrect_answer", 0);
		usc.saveIt();
	}
	/**
	 * Get all the statistics of a user.
	 * @param username the username of the user  we want statistics.
	 * @return a Json with a array of the statistics.
	 */
	public static JSONObject getStatistics(String username) {
		List<UserStatisticsCategory> estadisticas = UserStatisticsCategory.where("user = ?",
				username);
		JSONObject resp = new JSONObject();
		resp.put("User", username);
		List<JSONObject> stats = new ArrayList<JSONObject>();
		for (UserStatisticsCategory e : estadisticas) {
			stats.add(new StatisticContainer(e).toJSON());			
		}
		resp.put("statistics", stats.toArray());
		return resp;		
	}
	/**
	 * Calculate the level of a stat.
	 * @param stat
	 * @return the level
	 */
	public static int calculateLevel(UserStatisticsCategory stat) {
		int i = 0;
		int j = 0;
		int num = stat.getCorrectAnswers();
		while(num >= i) {
			i+=2;
			num -= i;
			j++;
		}
		return j;
	}
	/**
	 * Update a given stat
	 * @param stat the stat
	 */
	public static void updateCorrectAnswer(UserStatisticsCategory stat) {
		int j = (int) stat.getPoints() + 1;
		stat.setPoints(j);
		j = stat.getCorrectAnswers() + 1;
		stat.setCorrectAnswers(j);
		stat.saveIt();
	}
	/**
	 * Update a given stat
	 * @param stat the stat
	 */
	public static void updateIncorrecrAnswer(UserStatisticsCategory stat) {
		stat.setIncorrectAnswers(stat.getIncorrectAnswers()+1);
		stat.saveIt();
	}
}
