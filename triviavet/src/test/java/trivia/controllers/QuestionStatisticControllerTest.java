package trivia.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import controllers.CategoryController;
import controllers.QuestionStatisticController;
import trivia.models.Question;
import trivia.models.QuestionStatistic;
import trivia.testUtils.DBSpecAlternative;

public class QuestionStatisticControllerTest extends DBSpecAlternative {
	
	@Before
	public void setUp() {
		CategoryController.createCategory("cat");
		Question question = new Question();
		question.setDescription("test");
		question.setCategory("cat");
		question.saveIt();
		Question question2 = new Question();
		question2.setDescription("test1");
		question2.setCategory("cat");
		question2.saveIt();
		QuestionStatisticController.generateQuestionStatistic("test");
	}
	
	@Test
	public void generateQuestionStatisticsTest() {
		QuestionStatisticController.generateQuestionStatistic("test1");
		QuestionStatistic stat = QuestionStatistic.findFirst("question = ?", "test1");
		assertEquals("test1", stat.getQuestion());
		assertEquals(0, stat.getRightAttempts());
		assertEquals(0, stat.getWrongAttempts());
		assertEquals(0, stat.getTotalAttempts());
	}
	
	@Test
	public void updateCorrectAnswerTest() {
		QuestionStatistic stat = QuestionStatistic.findFirst("question = ?", "test");
		QuestionStatisticController.updateCorrectAnswer(stat);
		assertEquals(1, stat.getRightAttempts());
		assertEquals(0, stat.getWrongAttempts());
		assertEquals(1, stat.getTotalAttempts());
	}
	
	@Test
	public void updateIncorrectAnswerTest() {
		QuestionStatistic stat = QuestionStatistic.findFirst("question = ?", "test");
		QuestionStatisticController.updateIncorrectAnswer(stat);;
		assertEquals(0, stat.getRightAttempts());
		assertEquals(1, stat.getWrongAttempts());
		assertEquals(1, stat.getTotalAttempts());
	}
	
}
