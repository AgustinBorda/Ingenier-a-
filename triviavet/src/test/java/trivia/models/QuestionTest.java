package trivia.models;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.CategoryController;

import static org.junit.Assert.*;

public class QuestionTest {

	@Before
	public void before(){
		Base.open();
		System.out.println("Question test setup");
		Base.openTransaction();
	}

  @After
  public void after(){
	  System.out.println("Question test tearDown");
	  Base.rollbackTransaction();
	  Base.close();
  }

  @Test
  public void validatePrecenseOfDescription(){
	  CategoryController.createCategory("cat"); 
	  Question questionTest = new Question();
	  questionTest.set("description", "","category","cat");
	  assertEquals(questionTest.isValid(), false);
  }

  @Test
  public void validatePrecenseOfSomethingInNombre(){
	  CategoryController.createCategory("cat"); 
	  Question questionTest = new Question();
	  questionTest.set("description", "algo cool","category","cat");
	  assertEquals(questionTest.isValid(), true);
  }

  @Test
  public void validateUniqueLeague(){
	  CategoryController.createCategory("cat"); 
	  assertTrue(new Question().set("description", "algo cool","category","cat").saveIt());
	  Question questionTest = new Question();
	  questionTest.set("description", "algo cool","category","cat");
	  assertEquals(questionTest.isValid(), false);
  }
}