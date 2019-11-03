package controllers;

import trivia.models.User;
import trivia.models.UserQuestion;

public class UserQuestionController {

	public static void createUserQuestion(String username,String preg_id) {
		UserQuestion preg = new UserQuestion();
		User u = User.findFirst("username = ?", username);
		preg.setUserId(u.getId());
		preg.setQuestionId(preg_id);
		preg.saveIt();
	}

}
