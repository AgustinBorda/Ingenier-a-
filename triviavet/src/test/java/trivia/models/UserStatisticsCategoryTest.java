package trivia.models;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserStatisticsCategoryTest {

	@Before
	public void before() {
		if(!Base.hasConnection())
			Base.open();
		System.out.println("UserStatisticsCategory test setup");
		Base.openTransaction();
	}

	@After
	public void after() {
		System.out.println("UserStatisticsCategory test tearDown");
		Base.rollbackTransaction();
		Base.close();
	}

	@Test
	public void validatePrecenseOfPoints() {
		UserStatisticsCategory UserStatisticsCategoryTest = new UserStatisticsCategory();
		UserStatisticsCategoryTest.set("correct_answer", 3, "incorrect_answer", 5);
		assertEquals(UserStatisticsCategoryTest.isValid(), false);
	}
	
	@Test
	public void validatePrecenseOfCorrect_answer() {
		UserStatisticsCategory UserStatisticsCategoryTest = new UserStatisticsCategory();
		UserStatisticsCategoryTest.set("points", 5, "incorrect_answer", 5);
		assertEquals(UserStatisticsCategoryTest.isValid(), false);
	}
	
	@Test
	public void validatePrecenseOfIncorrect_answer() {
		UserStatisticsCategory UserStatisticsCategoryTest = new UserStatisticsCategory();
		UserStatisticsCategoryTest.set("points", 5,"correct_answer", 3);
		assertEquals(UserStatisticsCategoryTest.isValid(), false);
	}

	@Test
	public void validatePrecenseOfSomethingIn() {
		UserStatisticsCategory UserStatisticsCategoryTest = new UserStatisticsCategory();
		UserStatisticsCategoryTest.set("points", 5,"correct_answer", 3, "incorrect_answer", 5);
		assertEquals(UserStatisticsCategoryTest.isValid(), true);
	}
}