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

public class QuestionController {


	public static void setQuestion(QuestionParam bodyParams,Question question) {
		question.setDescription(bodyParams.description);
		question.setCategory(bodyParams.category);
		question.saveIt();
		OptionController.setOptions(bodyParams.options, question);
	}

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
