package trivia.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.CategoryController;
import trivia.testUtils.DBSpecAlternative;

import static org.junit.Assert.*;

public class QuestionTest extends DBSpecAlternative{

	@Before
	public void before(){
		System.out.println("Question test setup");
		CategoryController.createCategory("cat"); 
		assertTrue(new Question().set("description", "algo cool","category","cat").saveIt());
	}

	@After
	public void after(){
		System.out.println("Question test tearDown");
	}

	@Test
	public void validatePrecenseOfDescription(){
		Question questionTest = new Question();
		questionTest.set("description", "","category","cat");
		assertFalse(questionTest.isValid());
	}

	@Test
	public void validatePrecenseOfCategory(){
		Question questionTest = new Question();
		questionTest.set("description", "algo no cool","category","");
		assertFalse(questionTest.isValid());
	}

	@Test
	public void validateUniqueLeague(){ 
		Question questionTest = new Question();
		questionTest.set("description", "algo cool","category","cat");
		assertFalse(questionTest.isValid());
	}

	@Test
	public void getDescriptionTest() {
		Question question = Question.findFirst("description =?", "algo cool");
		assertEquals(question.getString("description"), question.getDescription());
	}
	
	@Test
	public void getCategoryTest() {
		Question question = Question.findFirst("description =?", "algo cool");
		assertEquals(question.getString("category"), question.getCategory());
	}
	
	@Test
	public void setDescriptionTest() {
		Question question = Question.findFirst("description =?", "algo cool");
		question.setDescription("modificado");
		assertEquals("modificado", question.getDescription());	
	}
	
	@Test
	public void setCategoryTest() {
		Question question = Question.findFirst("description =?", "algo cool");
		question.setCategory("newCat");
		assertEquals("newCat", question.getCategory());
	}
	
}
