package controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DBException;
import org.json.JSONObject;

import trivia.models.Option;
import trivia.models.Question;
import trivia.models.QuestionStatistic;
import trivia.models.UserStatisticsCategory;
import trivia.structures.Pair;
import trivia.structures.QuestionParam;
/**
 * Class that provides a controller for the Question model.
 */
public class QuestionController {

	/**
	 * Set the attributes of a question.
	 * @param bodyParams, the attributes
	 * @param question, the question
	 */
	public static void setQuestion(QuestionParam bodyParams,Question question) {
		question.setDescription(bodyParams.description);
		question.setCategory(bodyParams.category);
		question.saveIt();
		OptionController.setOptions(bodyParams.options, question);
	}
	
	/**
	 * Recovery a question from the DB.
	 * @param bodyParams, the params of the question
	 * @param userId, the user that requested a question
	 * @return a Pair with the question, the options, and the id of the question
	 */
	public static Pair<JSONObject,Pair<String,List<Option>>> getQuestion(Map<String,Object> bodyParams,String userId) {
		Random r = new Random();
		JSONObject resp = new JSONObject();
		List<Question> questions;
		try {
			if(bodyParams.get("category") == null) {
				throw new NullPointerException();
			}
			else {
				questions = Question.findBySQL(
						"SELECT * FROM questions WHERE id "
								+ "NOT IN (SELECT id FROM questions "
								+ "INNER JOIN ((SELECT * FROM user_questions WHERE user_id = ?) as contestadas) "
								+ "ON questions.id = contestadas.question_id) AND category = ?",
								userId, bodyParams.get("category"));
			}
		}
		catch(NullPointerException e) {
			questions = Question.findBySQL(
					"SELECT * FROM questions WHERE id "
							+ "NOT IN (SELECT id FROM questions "
							+ "INNER JOIN ((SELECT * FROM user_questions WHERE user_id = ?) as contestadas) "
							+ "ON questions.id = contestadas.question_id)",
							userId);
		}
		Question question = questions.get(r.nextInt(questions.size()));
		List<Option> options = Option.where("question_id = ?", question.get("id"));
		Collections.shuffle(options);
		resp.put("description", question.get("description"));
		int i = 1;
		for (Option o : options) {
			resp.put("answer" + i, o.get("description"));
			i++;
		}
		Pair<JSONObject,Pair<String,List<Option>>>answer = new Pair<JSONObject,Pair<String,List<Option>>>(resp,
				new Pair<String,List<Option>>(question.get("id").toString(),options));
		return answer;


	}
	/**
	 * Verifies if the provided answer for a question is correct.
	 * @param answer, the answer
	 * @param username, the user that provided the answer
	 * @param options, all the options for the question
	 * @param question, the question
	 * @return a Json that contains a message depending if the answer was correct or not
	 */
	public static JSONObject answerQuestion(String answer, String username, List<Option> options, Question question) {
		JSONObject resp;
		int i = Integer.parseInt(answer);
		Option option = options.get(i - 1);
		if (option.getCorrect()) {
			resp = QuestionController.updateCorrectAnswer(username, question);
		} else {
			resp = QuestionController.updateWrongAnswer(username, question);
		}
		return resp;
	}

	public static Question getQuestionById(String id) {
		return Question.findFirst("id = ?", id);
	}
	/**
	 * Updates the statistic of the question
	 * @param username, the user that provided the answer
	 * @param question, the answered question
	 * @return a Json that contains a answer
	 */
	public static JSONObject updateCorrectAnswer(String username, Question question) {
		JSONObject resp = new JSONObject();;
		try {
			QuestionStatistic questionStat = QuestionStatistic.findFirst("question = ?", question.getDescription());
			QuestionStatisticController.updateCorrectAnswer(questionStat);
			UserStatisticsCategory stat = UserStatisticsCategory.findFirst("user = ? AND nombre = ?",
					username, question.getCategory());
			UserQuestionController.createUserQuestion(username, question.getId().toString());
			UserStatisticsCategoryController.updateCorrectAnswer(stat);
			resp.put("answer", "Correcto!");
		}
		catch(DBException e) {
			resp.put("answer", "Ocurrio un error inesperado");
		}
		return resp;
	}

	/**
	 * Updates the statistic of the question
	 * @param username, the user that provided the answer
	 * @param question, the answered question
	 * @return a Json that contains a answer
	 */
	public static JSONObject updateWrongAnswer(String username, Question question) {
		JSONObject resp = new JSONObject();
		try {
			QuestionStatistic questionStat = QuestionStatistic.findFirst("question = ?", question.getDescription());
			QuestionStatisticController.updateIncorrectAnswer(questionStat);			
			UserStatisticsCategory stat = UserStatisticsCategory.findFirst("user = ? AND nombre = ?",
					username, question.getCategory());
			UserStatisticsCategoryController.updateIncorrecrAnswer(stat);
			resp.put("answer", "Incorrecto!");
		}
		catch(DBException e) {
			resp.put("answer", "Ocurrio un error inesperado");
		}
		return resp;
	}
}
