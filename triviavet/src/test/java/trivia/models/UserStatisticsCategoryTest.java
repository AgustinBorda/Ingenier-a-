package trivia.models;

import org.junit.Before;
import org.junit.Test;

import controllers.CategoryController;
import controllers.UserStatisticsCategoryController;
import trivia.testUtils.DBSpecAlternative;

import static org.junit.Assert.*;

public class UserStatisticsCategoryTest extends DBSpecAlternative{

	@Before
	public void before() {
		User u2 = new User();
		u2.set("username", "Hackerman", "password", "jak1ad0r" , "admin", true, "email", "hackingsa@gmail.com");
		u2.saveIt();
		CategoryController.createCategory("cat"); 
		Category cat = Category.findFirst("nombre =?", "cat");
		UserStatisticsCategoryController.createUserStatistic(u2, cat);
	}

	@Test
	public void validatePrecenseOfPoints() {
		UserStatisticsCategory userStatisticsCategoryTest = new UserStatisticsCategory();
		userStatisticsCategoryTest.set("correct_answer", 3);
		userStatisticsCategoryTest.set("incorrect_answer", 5);
		userStatisticsCategoryTest.set("nombre","nombreCat");
		userStatisticsCategoryTest.set("user","Hackerman");
		assertFalse(userStatisticsCategoryTest.isValid());
	}
	
	@Test
	public void validatePrecenseOfCorrect_answer() {
		UserStatisticsCategory userStatisticsCategoryTest = new UserStatisticsCategory();
		userStatisticsCategoryTest.set("points", 3);
		userStatisticsCategoryTest.set("incorrect_answer", 5);
		userStatisticsCategoryTest.set("nombre","nombreCat");
		userStatisticsCategoryTest.set("user","Hackerman");
		assertFalse(userStatisticsCategoryTest.isValid());
	}
	
	@Test
	public void validatePrecenseOfIncorrect_answer() {
		UserStatisticsCategory userStatisticsCategoryTest = new UserStatisticsCategory();
		userStatisticsCategoryTest.set("correct_answer", 3);
		userStatisticsCategoryTest.set("points", 5);
		userStatisticsCategoryTest.set("nombre","nombreCat");
		userStatisticsCategoryTest.set("user","Hackerman");
		assertFalse(userStatisticsCategoryTest.isValid());
	}
	
	@Test
	public void validatePrecenseOfNombre() {
		UserStatisticsCategory userStatisticsCategoryTest = new UserStatisticsCategory();
		userStatisticsCategoryTest.set("correct_answer", 3);
		userStatisticsCategoryTest.set("points", 5);
		userStatisticsCategoryTest.set("incorrect_answer",342);
		userStatisticsCategoryTest.set("user","Hackerman");
		assertFalse(userStatisticsCategoryTest.isValid());
	}
	
	@Test
	public void validatePrecenseOfUser() {
		UserStatisticsCategory userStatisticsCategoryTest = new UserStatisticsCategory();
		userStatisticsCategoryTest.set("correct_answer", 3);
		userStatisticsCategoryTest.set("points", 5);
		userStatisticsCategoryTest.set("incorrect_answer",342);
		userStatisticsCategoryTest.set("nombre","cat");
		assertFalse(userStatisticsCategoryTest.isValid());
	}
	
	@Test
	public void getUsernameTest() {
		UserStatisticsCategory u = UserStatisticsCategory.findFirst("user = ?", "Hackerman");
		assertEquals(u.getString("user"), u.getUsername());
	}
	
	@Test
	public void getCategoryTest() {
		UserStatisticsCategory u = UserStatisticsCategory.findFirst("user = ?", "Hackerman");
		assertEquals(u.getString("nombre"), u.getCategory());
	}
	
	@Test
	public void getPointsTest() {
		UserStatisticsCategory u = UserStatisticsCategory.findFirst("user = ?", "Hackerman");
		assertEquals((Integer)u.getInteger("points"), (Integer)u.getPoints());
	}
	
	@Test
	public void getCorrectAnswerTest() {
		UserStatisticsCategory u = UserStatisticsCategory.findFirst("user = ?", "Hackerman");
		assertEquals((Integer)u.getInteger("correct_answer"), (Integer)u.getCorrectAnswers());
	}
	
	@Test
	public void getIncorrectAnswerTest() {
		UserStatisticsCategory u = UserStatisticsCategory.findFirst("user = ?", "Hackerman");
		assertEquals((Integer)u.getInteger("incorrect_answer"), (Integer)u.getIncorrectAnswers());
	}
	
	@Test
	public void setUsernameTest() {
		UserStatisticsCategory u = UserStatisticsCategory.findFirst("user = ?", "Hackerman");
		u.setUsername("No-Hackerman");
		assertEquals("No-Hackerman", u.getUsername());
	}
	
	@Test
	public void setCategoryTest() {
		UserStatisticsCategory u = UserStatisticsCategory.findFirst("user = ?", "Hackerman");
		u.setCategory("cat2");
		assertEquals("cat2", u.getCategory());
	}
	
	@Test
	public void setPointsTest() {
		UserStatisticsCategory u = UserStatisticsCategory.findFirst("user = ?", "Hackerman");
		u.setPoints(41225507);
		assertEquals(41225507, u.getPoints());
	}
	
	@Test
	public void setCorrectAnswersTest() {
		UserStatisticsCategory u = UserStatisticsCategory.findFirst("user = ?", "Hackerman");
		u.setCorrectAnswers(41225507);
		assertEquals(41225507, u.getCorrectAnswers());
	}
	
	@Test
	public void setIncorrectAnswersTest() {
		UserStatisticsCategory u = UserStatisticsCategory.findFirst("user = ?", "Hackerman");
		u.setIncorrectAnswers(41225507);
		assertEquals(41225507, u.getIncorrectAnswers());
	}

}
