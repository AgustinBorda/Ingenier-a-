package trivia.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import controllers.CategoryController;
import controllers.UserQuestionController;
import trivia.models.Question;
import trivia.models.User;
import trivia.models.UserQuestion;
import trivia.testUtils.DBSpecAlternative;

public class UserQUestionControllerTest extends DBSpecAlternative {
	
	@Before
	public void setUp() {
		User u2 = new User();
		u2.set("username", "Hackerman", "password", "jak1ad0r" , "admin", true, "email", "hackingsa@gmail.com");
		u2.saveIt();
		CategoryController.createCategory("cat");
		Question question = new Question();
		question.setDescription("test");
		question.setCategory("cat");
		question.saveIt();
	}
	
	@Test
	public void createUserQuestionTest() {
		Question question = Question.findFirst("description = ?", "test");
		User u = User.findFirst("username = ?", "Hackerman");
		UserQuestionController.createUserQuestion("Hackerman", question.getId());
		UserQuestion uq = UserQuestion.findFirst("question_id = ? AND user_id = ?", question.getId(),u.getId());
		assertNotNull(uq);
		assertEquals(u.getId(),uq.getUserId());
		assertEquals(question.getId(),uq.getQuestionId());
	}

}
