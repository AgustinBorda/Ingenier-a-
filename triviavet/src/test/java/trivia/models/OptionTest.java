package trivia.models;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.CategoryController;

import static org.junit.Assert.*;

public class OptionTest {

	@Before
	public void before(){
		Base.open();
		System.out.println("Option test setup");
		Base.openTransaction();
	}

	@After
	public void after(){
		System.out.println("Option test tearDown");
		Base.rollbackTransaction();
		Base.close();
	}

	@Test
	public void validatePrecenseOfDescription(){
		CategoryController.createCategory("cat"); 
		Question questionTest = new Question();
		questionTest.set("description", "algo","category","cat");
		questionTest.saveIt();
		Option optionTest = new Option();
		optionTest.set("description", "");
		optionTest.setCorrect(false);
		optionTest.setQuestionId(questionTest.getId());
		assertEquals(optionTest.isValid(), false);
	}

	@Test
	public void validatePrecenseOfSomethingInDescription(){
		CategoryController.createCategory("cat"); 
		Question questionTest = new Question();
		questionTest.set("description", "algo cool","category","cat");
		questionTest.saveIt();
		Option optionTest = new Option();
		optionTest.set("description", "perro");
		optionTest.setCorrect(false);
		optionTest.setQuestionId(questionTest.getId());
		assertEquals(optionTest.isValid(), true);
	}
}