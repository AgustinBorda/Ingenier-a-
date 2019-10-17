package trivia.routes;

import static spark.Spark.*;

import java.util.Map;

import org.javalite.activejdbc.Base;

import org.json.JSONObject;
import com.google.gson.Gson;

import spark.*;
import trivia.BasicAuth;
import trivia.models.*;
import trivia.structures.*;

import org.javalite.activejdbc.DBException;
import org.javalite.activejdbc.LazyList;

public class AdminRoutes {

	public static final Filter CheckAdmin = (request,response) -> {
		String headerToken = (String) request.headers("Authorization");
		if (request.session().attributes().isEmpty() || headerToken == null || headerToken.isEmpty()
				|| !BasicAuth.authorize(headerToken) || (boolean)request.session().attribute("admin") == false)
			halt(401, "Usuario o clave invalidos \n");
	};
	
	public static final Route CreateQuestions = (req, res) -> {
		QuestionParam bodyParams = new Gson().fromJson(req.body(), QuestionParam.class);
		Question question = new Question();
		question.setQuestion(bodyParams);
		return question.toJson(true);
	};
	
	public static final Route ModifyQuestions = (req,res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		JSONObject resp = new JSONObject();
		Base.openTransaction();
		Question question = Question.findFirst("description = ?", bodyParams.get("oldDescription"));
		try {
			question.setQuestion((QuestionParam)bodyParams.get("modifiedQuestion"));
			Base.commitTransaction();
			resp.put("Answer", "Question Modified");
			
		}
		catch(DBException e) {
			Base.rollbackTransaction();
			resp.put("answer", "Cannot modify question");
		}
		return resp;
	};
	
	public static final Route RemoveQuestions = (req,  res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		Question question = Question.findFirst("description = ?", bodyParams.get("description").toString());
		question.delete();
		JSONObject resp = new JSONObject();
		resp.put("answer", "OK");
		return resp;
	};
	
	public static final Route PostAdmin = (req, res) -> {
		User user;
		JSONObject resp = new JSONObject();
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		user = User.findFirst("username = ?", bodyParams.get("username"));
		user.giveAdminPermissions();
		resp.put("answer", "OK");
		return resp;
	};
	
	public static final Route CreateCategory = (req, res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		JSONObject resp = new JSONObject();
		Base.openTransaction();
		try {
			Category.createCategory((String)bodyParams.get("name"));
			Base.commitTransaction();
			resp.put("answer", "Created category");
		}
		catch(DBException e) {
			Base.rollbackTransaction();
			resp.put("answer", "Cannot create category");			
		}
		return resp;
	};
	
	public static final Route DeleteCategory = (req, res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		JSONObject resp = new JSONObject();
		Base.openTransaction();
		try {
			Category.deleteCategory((String)bodyParams.get("name"));
			Base.commitTransaction();
			resp.put("answer","Category deleted");
		}
		catch(DBException e) {
			Base.rollbackTransaction();
			resp.put("answer", "Cannot delete category");
		}
		return resp;
	};
	
	public static final Route ModifyCategory = (req, res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		JSONObject resp = new JSONObject();
		Base.openTransaction();
		try {
			Category.modifyCategory((String)bodyParams.get("old_name"), (String)bodyParams.get("new_name"));
			Base.commitTransaction();
			resp.put("answer","Category modified");	
		}
		catch(DBException e) {
			Base.rollbackTransaction();
			resp.put("answer", "Cannot modify category");			
		}
		return resp;
	};
	
	public static final Route GetSpecificUserStatistics = (req, res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		JSONObject resp = new JSONObject();
		LazyList<UserStatisticsCategory> stats = UserStatisticsCategory.where("user = ?",bodyParams.get("username"));
		resp.put("stats", stats.toArray());
		return resp;
	};
	
	public static final Route GetAllUserStatistics = (req, res) -> {
		JSONObject resp = new JSONObject();
		LazyList<UserStatisticsCategory> stats = UserStatisticsCategory.findAll();
		resp.put("stats", stats.toArray());
		return resp;
	};
	
	public static final Route GetSpecificQuestionStatistics = (req, res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		JSONObject resp = new JSONObject();
		LazyList<QuestionStatistic> stats = QuestionStatistic.where("question = ?",bodyParams.get("question"));
		resp.put("stats", stats.toArray());
		return resp;
	};
	
	public static final Route GetAllQuestionsStatistics = (req, res) -> {
		JSONObject resp = new JSONObject();
		LazyList<QuestionStatistic> stats = QuestionStatistic.findAll();
		resp.put("stats", stats.toArray());
		return resp;
	};

}
