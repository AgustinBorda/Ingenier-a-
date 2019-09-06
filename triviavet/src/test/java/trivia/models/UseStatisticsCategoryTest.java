package trivia.models;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UseStatisticsCategoryTest {

	@Before
	public void before() {
		if(!Base.hasConnection())
			Base.open();
		System.out.println("UseStatisticsCategory test setup");
		Base.openTransaction();
	}

	@After
	public void after() {
		System.out.println("UseStatisticsCategory test tearDown");
		Base.rollbackTransaction();
		Base.close();
	}

	@Test
	public void validatePrecenseOfPoints() {
		UseStatisticsCategory useStatisticsCategoryTest = new UseStatisticsCategory();
		useStatisticsCategoryTest.set("correct_answer", 3, "incorrect_answer", 5);
		assertEquals(useStatisticsCategoryTest.isValid(), false);
	}
	
	@Test
	public void validatePrecenseOfCorrect_answer() {
		UseStatisticsCategory useStatisticsCategoryTest = new UseStatisticsCategory();
		useStatisticsCategoryTest.set("points", 5, "incorrect_answer", 5);
		assertEquals(useStatisticsCategoryTest.isValid(), false);
	}
	
	@Test
	public void validatePrecenseOfIncorrect_answer() {
		UseStatisticsCategory UseStatisticsCategoryTest = new UseStatisticsCategory();
		UseStatisticsCategoryTest.set("points", 5,"correct_answer", 3);
		assertEquals(UseStatisticsCategoryTest.isValid(), false);
	}

	@Test
	public void validatePrecenseOfSomethingIn() {
		UseStatisticsCategory useStatisticsCategoryTest = new UseStatisticsCategory();
		useStatisticsCategoryTest.set("points", 5,"correct_answer", 3, "incorrect_answer", 5);
		assertEquals(useStatisticsCategoryTest.isValid(), true);
	}
}