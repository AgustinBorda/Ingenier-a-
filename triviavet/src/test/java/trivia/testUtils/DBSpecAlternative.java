package trivia.testUtils;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
/**
 * Class that opens the database before the tests,
 * open a transaction before each test,
 * rollback the transaction after each test
 * and close the database after all the tests
 * @author agustin
 *
 */

public class DBSpecAlternative {
	/**
	 * Opens the database
	 * before all the tests
	 */
	@BeforeClass
	public static void classSetUpDBSpecAlternative() {
		Base.open();
	}
	/**
	 * Opens a transaction
	 * before each test
	 */
	@Before
	public void setUpDBSpecAlternative() {
		Base.openTransaction();
	}
	
	/**
	 * Rollback a transaction
	 * after each test
	 */
	@After
	public void tearDownDBSpecAlternative() {
		Base.rollbackTransaction();
	}
	
	/**
	 * Closes the database
	 * after all the tests
	 */
	@AfterClass
	public static void classTearDownDBSpecAlternative() {
		Base.close();
	}
}
