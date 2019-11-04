package trivia.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import controllers.CategoryController;
import controllers.QuestionStatisticController;
import trivia.testUtils.DBSpecAlternative;

public class QuestionStatisticTest extends DBSpecAlternative {
	
	@Before
	public void setUp() {
		CategoryController.createCategory("testCat");
		Question question = new Question();
		question.setDescription("test");
		question.setCategory("testCat");
		question.saveIt();
		QuestionStatisticController.generateQuestionStatistic("test");
	}
	
	@Test
	public void validatePresenceOfRightAttempts() {
		QuestionStatistic stat = new QuestionStatistic();
		stat.setString("question", "dummy");
		stat.setInteger("wrong_attempts", 0);
		stat.setInteger("total_attempts", 0);
		assertFalse(stat.isValid());
	}
	
	@Test
	public void validatePresenceOfWrongAttempts() {
		QuestionStatistic stat = new QuestionStatistic();
		stat.setInteger("right_attempts", 0);
		stat.setString("question", "dummy");
		stat.setInteger("total_attempts", 0);
		assertFalse(stat.isValid());
	}
	
	@Test
	public void validatePresenceOfTotalAttempts() {
		QuestionStatistic stat = new QuestionStatistic();
		stat.setInteger("right_attempts", 0);
		stat.setInteger("wrong_attempts", 0);
		stat.setString("question", "dummy");
		assertFalse(stat.isValid());
	}
	
	@Test
	public void validatePresenceOfQuestion() {
		QuestionStatistic stat = new QuestionStatistic();
		stat.setInteger("right_attempts", 0);
		stat.setInteger("wrong_attempts", 0);
		stat.setInteger("total_attempts", 0);
		assertFalse(stat.isValid());
	}
	
	@Test
	public void getQuestionTest() {
		QuestionStatistic stat = QuestionStatistic.findFirst("question = ?", "test");
		assertEquals(stat.getString("question"),stat.getQuestion());
	}
	
	@Test
	public void getRightAtteptsTest() {
		QuestionStatistic stat = QuestionStatistic.findFirst("question = ?", "test");
		assertEquals((Integer)stat.getInteger("right_attempts"),(Integer)stat.getRightAttempts());
	}
	
	@Test
	public void getWrongAtteptsTest() {
		QuestionStatistic stat = QuestionStatistic.findFirst("question = ?", "test");
		assertEquals((Integer)stat.getInteger("wrong_attempts"),(Integer)stat.getWrongAttempts());
	}
	
	@Test
	public void getTotalAtteptsTest() {
		QuestionStatistic stat = QuestionStatistic.findFirst("question = ?", "test");
		assertEquals((Integer)stat.getInteger("total_attempts"),(Integer)stat.getTotalAttempts());
	}
	
	@Test
	public void setQuestionTest() {
		QuestionStatistic stat = QuestionStatistic.findFirst("question = ?", "test");
		stat.setQuestion("mod");
		assertEquals("mod",stat.getQuestion());
	}
	
	@Test
	public void setWrongAttemptsTest() {
		QuestionStatistic stat = QuestionStatistic.findFirst("question = ?", "test");
		stat.setWrongAttempts(41225507);
		assertEquals(41225507,stat.getWrongAttempts());
	}
	
	@Test
	public void setRightAttemptsTest() {
		QuestionStatistic stat = QuestionStatistic.findFirst("question = ?", "test");
		stat.setRightAttempts(41225507);
		assertEquals(41225507,stat.getRightAttempts());
	}
	
	@Test
	public void setTotalAttemptsTest() {
		QuestionStatistic stat = QuestionStatistic.findFirst("question = ?", "test");
		stat.setTotalAttempts(41225507);
		assertEquals(41225507,stat.getTotalAttempts());
	}

}
