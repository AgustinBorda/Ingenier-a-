package trivia.routes;

import static spark.Spark.*;

import java.util.List;
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
		System.out.println(request.headers("IsAdmin"));
		if (request.requestMethod() != "OPTIONS"){ 
			if (headerToken == null || headerToken.isEmpty()
					|| !BasicAuth.authorize(headerToken) ||
					Boolean.parseBoolean(request.headers("IsAdmin")) == false) {
				halt(401,"Token invalido \n");
			}
		}
	};
	
	public static final Route CreateQuestions = (req, res) -> {
		QuestionParam bodyParams = new Gson().fromJson(req.body(), QuestionParam.class);
		Question question = new Question();
		Base.openTransaction();
		System.out.println(bodyParams.options);
		try {
			question.setQuestion(bodyParams);
			Base.commitTransaction();
			res.status(200);
		}
		catch (DBException e) {
			Base.rollbackTransaction();
			res.status(401);
		}

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
			resp.put("Answer", "Modified Question");
			res.status(200);
			
		}
		catch(DBException e) {
			Base.rollbackTransaction();
			resp.put("answer", "Cannot modify Question");
			res.status(401);
		}
		return resp;
	};
	
	public static final Route RemoveQuestions = (req,  res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		Question question = Question.findFirst("description = ?", bodyParams.get("description").toString());
		JSONObject resp = new JSONObject();
		try {
			question.delete();	
			resp.put("answer", "OK");
			res.status(200);
		}
		catch(DBException e) {
			resp.put("answer", "Fail");
			res.status(404);
		}
		return resp;
	};
	
	public static final Route ListQuestions = (req,  res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		List<String> question = Question.where("category = ?", bodyParams.get("category").toString()).collect("description");
		JSONObject resp = new JSONObject();
		try {
			resp.put("questions", question.toArray());
			resp.put("answer", "OK");
			res.status(200);
		}
		catch(DBException e) {
			resp.put("answer", "Fail");
			res.status(404);
		}
		return resp;
	};
	
	public static final Route PostAdmin = (req, res) -> {
		User user;
		JSONObject resp = new JSONObject();
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		user = User.findFirst("username = ?", bodyParams.get("username"));
		try {
			user.giveAdminPermissions();
			resp.put("answer", "OK");
			res.status(200);
		}
		catch (DBException e) {
			resp.put("answer", "Fail");
			res.status(404);
		}
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
			res.status(200);
		}
		catch(DBException e) {
			Base.rollbackTransaction();
			resp.put("answer", "Cannot create category");
			res.status(404);
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
			res.status(200);
		}
		catch(DBException e) {
			Base.rollbackTransaction();
			e.printStackTrace();
			resp.put("answer", "Cannot delete category");
			res.status(404);
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
			res.status(200);
		}
		catch(DBException e) {
			Base.rollbackTransaction();
			resp.put("answer", "Cannot modify category");
			res.status(404);
		}
		return resp;
	};
	
	public static final Route GetSpecificUserStatistics = (req, res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		JSONObject resp = new JSONObject();
		String user = "%"+bodyParams.get("username")+"%";
		LazyList<UserStatisticsCategory> stats = UserStatisticsCategory.where("user = ?",user);
		resp.put("stats", stats.toArray());
		res.status(200);
		return resp;
	};
	
	public static final Route GetAllUserStatistics = (req, res) -> {
		JSONObject resp = new JSONObject();
		LazyList<UserStatisticsCategory> stats = UserStatisticsCategory.findAll();
		resp.put("stats", stats.toArray());
		res.status(200);
		return resp;
	};
	
	public static final Route GetSpecificQuestionStatistics = (req, res) -> {
		Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
		JSONObject resp = new JSONObject();
		String question = "%"+bodyParams.get("question")+"%";
		LazyList<QuestionStatistic> stats = QuestionStatistic.where("question = ?",question);
		resp.put("stats", stats.toArray());
		res.status(200);
		return resp;
	};
	
	public static final Route GetAllQuestionsStatistics = (req, res) -> {
		JSONObject resp = new JSONObject();
		LazyList<QuestionStatistic> stats = QuestionStatistic.findAll();
		resp.put("stats", stats.toArray());
		res.status(200);
		return resp;
	};

}
