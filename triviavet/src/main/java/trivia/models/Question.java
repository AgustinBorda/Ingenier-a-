package trivia.models;

import trivia.structures.Pair;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DBException;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.validation.UniquenessValidator;
import org.json.JSONObject;

import controllers.OptionController;
import controllers.QuestionStatisticController;
import controllers.UserQuestionController;
import controllers.UserStatisticsCategoryController;
import trivia.structures.QuestionParam;

public class Question extends Model {

	static {
		validatePresenceOf("description").message("Please, provide description");
		validateWith(new UniquenessValidator("description")).message("This description is already used.");
	}

	public void setQuestion(QuestionParam bodyParams) {
		this.set("description", bodyParams.description);
		this.set("category", bodyParams.category);
		this.saveIt();
		OptionController.setOptions(bodyParams.options, this);
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
	
	public JSONObject answerQuestion(String answer, String username, List<Option> options) {
		JSONObject resp;
		int i = Integer.parseInt(answer);
		Option option = options.get(i - 1);
		if ((boolean) option.getCorrect()) {
			resp = this.updateCorrectAnswer(username);
		} else {
			resp = this.updateWrongAnswer(username);
		}
		return resp;
	}

	public static Question getQuestionById(String id) {
		return Question.findFirst("id = ?", id);
	}
	
	public JSONObject updateCorrectAnswer(String username) {
		JSONObject resp = new JSONObject();;
		Base.openTransaction();
		try {
			QuestionStatistic questionStat = QuestionStatistic.findFirst("question = ?", this.get("description"));
			QuestionStatisticController.updateCorrectAnswer(questionStat);
			UserStatisticsCategory stat = UserStatisticsCategory.findFirst("user = ? AND nombre = ?",
					username,this.get("category"));
			UserQuestionController.createUserQuestion(username, this.get("id").toString());
			UserStatisticsCategoryController.updateCorrectAnswer(stat);
			Base.commitTransaction();
			resp.put("answer", "Correcto!");
		}
		catch(DBException e) {
			Base.rollbackTransaction();
			resp.put("answer", "Ocurrio un error inesperado");
		}
		return resp;
	}
	
	public JSONObject updateWrongAnswer(String username) {
		JSONObject resp = new JSONObject();
		Base.openTransaction();
		try {
			QuestionStatistic questionStat = QuestionStatistic.findFirst("question = ?", this.get("description"));
			QuestionStatisticController.updateIncorrectAnswer(questionStat);			
			UserStatisticsCategory stat = UserStatisticsCategory.findFirst("user = ? AND nombre = ?",
					username,this.get("category"));
			UserStatisticsCategoryController.updateIncorrecrAnswer(stat);
			Base.commitTransaction();
			resp.put("answer", "Incorrecto!");
		}
		catch(DBException e) {
			Base.rollbackTransaction();
			resp.put("answer", "Ocurrio un error inesperado");
		}
		return resp;
	}
}
