package trivia.models;


import org.junit.Before;
import org.junit.Test;

import controllers.CategoryController;
import trivia.testUtils.DBSpecAlternative;

import static org.junit.Assert.*;

public class OptionTest extends DBSpecAlternative{

	@Before
	public void before(){
		CategoryController.createCategory("cat"); 
		Question questionTest = new Question();
		questionTest.setDescription("algo");
		questionTest.setCategory("cat");
		questionTest.saveIt();
		Question questionTest2 = new Question();
		questionTest2.setDescription("algo2");
		questionTest2.setCategory("cat");
		questionTest2.saveIt();
		Option o = new Option();
		o.setString("description", "test");
		o.setBoolean("correct",false);
		o.set("question_id",questionTest.getId());
		o.saveIt();
	}

	@Test
	public void validatePrecenseOfDescription(){
		Question question = Question.findFirst("description = ?", "algo");
		Option optionTest = new Option();
		optionTest.set("description", "");
		optionTest.setCorrect(false);
		optionTest.setQuestionId(question.getId());
		assertFalse(optionTest.isValid());
	}
	
	@Test
	public void validatePrecenseOfCorrect() {
		Question question = Question.findFirst("description = ?", "algo");
		Option optionTest = new Option();
		optionTest.set("description", "algo");
		optionTest.setQuestionId(question.getId());
		assertFalse(optionTest.isValid());
	}
	
	@Test
	public void ValidatePrecenseOfQuestionId() {
		Option optionTest = new Option();
		optionTest.set("description", "algo");
		optionTest.setCorrect(false);
		assertFalse(optionTest.isValid());
	}

	@Test
	public void validatePrecenseOfSomethingInDescription(){
		Question question = Question.findFirst("description = ?", "algo");
		Option optionTest = new Option();
		optionTest.set("description", "perro");
		optionTest.setCorrect(false);
		optionTest.setQuestionId(question.getId());
		assertTrue(optionTest.isValid());
	}
	
	@Test
	public void getDescriptionTest() {
		Option option = Option.findFirst("description = ?", "test");
		assertEquals(option.getString("description"), option.getDescription());
	}
	
	@Test
	public void getCorrectnTest() {
		Option option = Option.findFirst("description = ?", "test");
		assertEquals(option.getBoolean("correct"), option.getCorrect());
	}
	
	@Test
	public void getQuestionIdTest() {
		Option option = Option.findFirst("description = ?", "test");
		assertEquals(option.get("question_id"), option.getQuestionId());
	}
	
	@Test
	public void setDescriptionTest() {
		Option option = Option.findFirst("description = ?", "test");
		option.setDescription("nuevo");
		assertEquals("nuevo", option.getDescription());
	}
	
	@Test
	public void setCorrectTest() {
		Option option = Option.findFirst("description = ?", "test");
		assertFalse(option.getCorrect());
		option.setCorrect(true);
		assertTrue(option.getCorrect());
	}
	
	@Test
	public void setQuestionIdTest() {
		Option option = Option.findFirst("description = ?", "test");
		Question question = Question.findFirst("description = ?", "algo");
		assertEquals(option.getQuestionId(),question.getId());
		Question question2 = Question.findFirst("description = ?", "algo2");
		option.setQuestionId(question2.getId());
		assertEquals(option.getQuestionId(),question2.getId());		
	}

}
