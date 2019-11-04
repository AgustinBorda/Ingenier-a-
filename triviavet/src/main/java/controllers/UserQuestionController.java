package controllers;

import trivia.models.User;
import trivia.models.UserQuestion;
/**
 * Class that provides a controller for the UserQuestion model.
 */
public class UserQuestionController {
	/**
	 * Create a user question.
	 * @param username the username of a user.
	 * @param preg_id the id of a question.
	 */
	public static void createUserQuestion(String username,Object preg_id) {
		UserQuestion preg = new UserQuestion();
		User u = User.findFirst("username = ?", username);
		preg.setUserId(u.getId());
		preg.setQuestionId(preg_id);
		preg.saveIt();
	}

}
