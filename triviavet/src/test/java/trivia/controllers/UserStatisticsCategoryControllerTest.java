package trivia.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import controllers.CategoryController;
import controllers.UserStatisticsCategoryController;
import trivia.models.Category;
import trivia.models.User;
import trivia.models.UserStatisticsCategory;
import trivia.testUtils.DBSpecAlternative;

public class UserStatisticsCategoryControllerTest extends DBSpecAlternative {
	
	
	@Before
	public void setUp() {
		User u2 = new User();
		u2.set("username", "Hackerman", "password", "jak1ad0r" , "admin", true, "email", "hackingsa@gmail.com");
		u2.saveIt();
		CategoryController.createCategory("cat");
		CategoryController.createCategory("cat2");
		Category cat = Category.findFirst("nombre = ?", "cat2");
		UserStatisticsCategoryController.createUserStatistic(u2, cat);
	}
	
	@Test
	public void createuserStatisticTest() {
		User u = User.findFirst("username = ?", "Hackerman");
		Category cat = Category.findFirst("nombre = ?", "cat");
		UserStatisticsCategoryController.createUserStatistic(u, cat);
		UserStatisticsCategory stat = UserStatisticsCategory.findFirst("user = ? AND nombre = ?", "Hackerman","cat");
		assertEquals("Hackerman", stat.getUsername());
		assertEquals("cat", stat.getCategory());
		assertEquals(0, stat.getPoints());
		assertEquals(0, stat.getCorrectAnswers());
		assertEquals(0, stat.getIncorrectAnswers());		
	}
	
	@Test
	public void calculateLevelTest() {
		UserStatisticsCategory stat = UserStatisticsCategory.findFirst("user = ? AND nombre = ?", "Hackerman","cat2");
		assertEquals(1,UserStatisticsCategoryController.calculateLevel(stat));
	}
	
	@Test
	public void updateCorrectAnswerTest() {
		UserStatisticsCategory statisticsCategory = UserStatisticsCategory.findFirst("user = ?", "Hackerman");
		UserStatisticsCategoryController.updateCorrectAnswer(statisticsCategory);
		statisticsCategory = UserStatisticsCategory.findFirst("user = ?", "Hackerman");
		assertEquals(1, statisticsCategory.getPoints());
		assertEquals(1, statisticsCategory.getCorrectAnswers());
		assertEquals(0, statisticsCategory.getIncorrectAnswers());	
	}
	
	@Test
	public void updateIncorrectAnswerTest() {
		UserStatisticsCategory statisticsCategory = UserStatisticsCategory.findFirst("user = ?", "Hackerman");
		UserStatisticsCategoryController.updateIncorrecrAnswer(statisticsCategory);
		statisticsCategory = UserStatisticsCategory.findFirst("user = ?", "Hackerman");
		assertEquals(0, statisticsCategory.getPoints());
		assertEquals(0, statisticsCategory.getCorrectAnswers());
		assertEquals(1, statisticsCategory.getIncorrectAnswers());	
	}

}
