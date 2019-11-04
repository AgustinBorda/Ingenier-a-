
package trivia.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.CategoryController;
import controllers.UserQuestionController;
import trivia.testUtils.DBSpecAlternative;

import static org.junit.Assert.*;

public class UserQuestionsTest extends DBSpecAlternative{

	@Before
	public void before(){
		System.out.println("UserQuestions test setup");
		User u = new User();
		u.set("username", "jose98523168541", "password", "jose98523168541" , "admin", true, "email", "theemail@gmail.com");
		u.saveIt();
		User u2 = new User();
		u2.set("username", "Hackerman", "password", "jak1ad0r" , "admin", true, "email", "hackingsa@gmail.com");
		u2.saveIt();
		CategoryController.createCategory("cat"); 
		Question question = new Question(); 
		Question question2 = new Question(); 
		question.set("description", "algo cool","category","cat");
		question.saveIt();
		question2.set("description", "TeJackie","category","cat");
		question2.saveIt();
		UserQuestionController.createUserQuestion("Hackerman", question.getId());
	}

	@After
	public void after(){
		System.out.println("UserQuestions test tearDown");
	}

	@Test
	public void validatePrecenseOfuser_id(){
		UserQuestion UserQuestionsTest = new UserQuestion();
		UserQuestionsTest.set("user_id", null);
		assertFalse(UserQuestionsTest.isValid());
	}

	@Test
	public void validatePrecenseOfquestion_id(){
		UserQuestion userQuestionsTest = new UserQuestion();
		userQuestionsTest.set("user_id", 1, "question_id", null);
		assertFalse(userQuestionsTest.isValid());
	}

	@Test
	public void validatePrecenseOfSomethingIn(){
		UserQuestion userQuestionsTest = new UserQuestion();
		userQuestionsTest.set("user_id", 1,"question_id", 1);
		assertTrue(userQuestionsTest.isValid());
	}

	@Test
	public void getUserIdTest() {
		User u = User.findFirst("username = ?", "Hackerman");
		UserQuestion question = UserQuestion.findFirst("user_id = ?", u.getId());
		assertEquals(u.getId(), question.getUserId());
	}
	
	@Test
	public void getQuestionIdTest() {
		Question q = Question.findFirst("description = ?", "algo cool");
		UserQuestion question = UserQuestion.findFirst("question_id = ?", q.getId());
		assertEquals(q.getId(), question.getQuestionId());
	}
	
	@Test
	public void setUserIdTest() {
		User u = User.findFirst("username = ?", "Hackerman");
		User u2 = User.findFirst("username = ?", "jose98523168541");
		UserQuestion question = UserQuestion.findFirst("user_id = ?", u.getId());
		question.setUserId(u2.getId());
		assertEquals(u2.getId(), question.getUserId());
	}
	
	@Test
	public void setQuestionIdTest() {
		Question q = Question.findFirst("description = ?", "algo cool");
		Question q2 = Question.findFirst("description = ?", "TeJackie");
		UserQuestion question = UserQuestion.findFirst("question_id = ?", q.getId());
		question.setQuestionId(q2.getId());
		assertEquals(q2.getId(), question.getQuestionId());
	}

}