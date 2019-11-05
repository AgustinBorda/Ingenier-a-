package trivia.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import controllers.CategoryController;
import controllers.QuestionController;
import controllers.QuestionStatisticController;
import controllers.UserStatisticsCategoryController;
import trivia.models.Category;
import trivia.models.Option;
import trivia.models.Question;
import trivia.models.User;
import trivia.structures.OptionParam;
import trivia.structures.Pair;
import trivia.structures.QuestionParam;
import trivia.testUtils.DBSpecAlternative;

public class QuestionControllerTest extends DBSpecAlternative {
	
	@Before
	public void setUp() {
		CategoryController.createCategory("cat");
		Question.deleteAll();
		QuestionParam bodyParams = new QuestionParam();
		bodyParams.description = "testing";
		bodyParams.category = "cat";
		ArrayList<OptionParam> o = new ArrayList<OptionParam>();
		OptionParam op1 = new OptionParam();
		op1.description = "respuesta 1";
		op1.correct = false;
		o.add(op1);
		OptionParam op2 = new OptionParam();
		op2.description = "respuesta 2";
		op2.correct = false;
		o.add(op2);
		OptionParam op3 = new OptionParam();
		op3.description = "respuesta 3";
		op3.correct = false;
		o.add(op3);
		OptionParam op4 = new OptionParam();
		op4.description = "respuesta 4";
		op4.correct = true;
		o.add(op4);
		bodyParams.options = o;
		Question question = new Question();
		QuestionController.setQuestion(bodyParams, question);
		User u2 = new User();
		u2.set("username", "Hackerman", "password", "jak1ad0r" , "admin", true, "email", "hackingsa@gmail.com");
		u2.saveIt();
		UserStatisticsCategoryController.createUserStatistic(u2, Category.findFirst("nombre = ?", "cat"));
		QuestionStatisticController.generateQuestionStatistic(question.getDescription());
	}
	
	@Test
	public void createQuestionTest() {
		QuestionParam bodyParams = new QuestionParam();
		bodyParams.description = "test";
		bodyParams.category = "cat";
		ArrayList<OptionParam> o = new ArrayList<OptionParam>();
		OptionParam op1 = new OptionParam();
		op1.description = "respuesta 1";
		op1.correct = false;
		o.add(op1);
		OptionParam op2 = new OptionParam();
		op2.description = "respuesta 2";
		op2.correct = false;
		o.add(op2);
		OptionParam op3 = new OptionParam();
		op3.description = "respuesta 3";
		op3.correct = false;
		o.add(op3);
		OptionParam op4 = new OptionParam();
		op4.description = "respuesta 4";
		op4.correct = true;
		o.add(op4);
		bodyParams.options = o;
		Question question = new Question();
		QuestionController.setQuestion(bodyParams, question);	
		Question q = Question.findFirst("description = ?", "test");
		assertEquals(question.getDescription(),q.getDescription());
	}
	
	@Test
	public void getQuestionTest() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("category", "cat");
		User u = User.findFirst("username = ?", "Hackerman");
		Question q = Question.findFirst("description = ?", "testing");
		LazyList<Model> o = Option.where("question_id = ?", q.getId());
		Pair<JSONObject,Pair<String,List<Option>>> res =QuestionController.getQuestion(params, u.getId().toString());
		assertEquals(res.getFirst().get("description"),"testing");
		assertEquals(q.getId().toString(), res.getSecond().getFirst());
		for(Model m:res.getSecond().getSecond()) {
			assertTrue(m.getId().toString().contentEquals(o.get(0).getId().toString())||
					m.getId().toString().contentEquals(o.get(1).getId().toString())||
					m.getId().toString().contentEquals(o.get(2).getId().toString())||
					m.getId().toString().contentEquals(o.get(3).getId().toString()));
		}
	}
	
	@Test
	public void answerQuestionTest1() {
		Question q = Question.findFirst("description = ?", "testing");
		List<Option> o = Option.where("question_id = ?", q.getId());
		JSONObject resp = QuestionController.answerQuestion("1", "Hackerman", o, q);
		if(o.get(0).getCorrect()) {
			assertEquals(resp.get("answer"), "Correcto!");
		}
		else {
			assertEquals(resp.get("answer"), "Incorrecto!");
		}
		
	}
	
	@Test
	public void answerQuestionTest2() {
		Question q = Question.findFirst("description = ?", "testing");
		List<Option> o = Option.where("question_id = ?", q.getId());
		JSONObject resp = QuestionController.answerQuestion("2", "Hackerman", o, q);
		if(o.get(1).getCorrect()) {
			assertEquals(resp.get("answer"), "Correcto!");
		}
		else {
			assertEquals(resp.get("answer"), "Incorrecto!");
		}
		
	}
	
	@Test
	public void answerQuestionTest3() {
		Question q = Question.findFirst("description = ?", "testing");
		List<Option> o = Option.where("question_id = ?", q.getId());
		JSONObject resp = QuestionController.answerQuestion("3", "Hackerman", o, q);
		if(o.get(2).getCorrect()) {
			assertEquals(resp.get("answer"), "Correcto!");
		}
		else {
			assertEquals(resp.get("answer"), "Incorrecto!");
		}
		
	}
	
	@Test
	public void answerQuestionTest4() {
		Question q = Question.findFirst("description = ?", "testing");
		List<Option> o = Option.where("question_id = ?", q.getId());
		JSONObject resp = QuestionController.answerQuestion("4", "Hackerman", o, q);
		if(o.get(3).getCorrect()) {
			assertEquals(resp.get("answer"), "Correcto!");
		}
		else {
			assertEquals(resp.get("answer"), "Incorrecto!");
		}
		
	}
	
	@Test
	public void getQuestionByIdTest() {
		Question q = Question.findFirst("description = ?", "testing");
		Question question = QuestionController.getQuestionById(q.getId().toString());
		assertEquals(q.getDescription(), question.getDescription());
	}
	
	@Test
	public void getQuestionWithoutCategoryTest() {
		Map<String,Object> params = new HashMap<String,Object>();
		User u = User.findFirst("username = ?", "Hackerman");
		Question q = Question.findFirst("description = ?", "testing");
		LazyList<Model> o = Option.where("question_id = ?", q.getId());
		Pair<JSONObject,Pair<String,List<Option>>> res =QuestionController.getQuestion(params, u.getId().toString());
		assertEquals(res.getFirst().get("description"),"testing");
		assertEquals(q.getId().toString(), res.getSecond().getFirst());
		for(Model m:res.getSecond().getSecond()) {
			assertTrue(m.getId().toString().contentEquals(o.get(0).getId().toString())||
					m.getId().toString().contentEquals(o.get(1).getId().toString())||
					m.getId().toString().contentEquals(o.get(2).getId().toString())||
					m.getId().toString().contentEquals(o.get(3).getId().toString()));
		}
	}
	
	@Test
	public void updateCorrectAnswerTest() {
		User u = User.findFirst("username = ?", "Hackerman");
		Question q = Question.findFirst("description = ?", "testing");
		JSONObject resp = QuestionController.updateCorrectAnswer(u.getUsername(), q);
		assertEquals(resp.get("answer"), "Correcto!");
	}
	
	@Test
	public void updateIncorrectAnswerTest() {
		User u = User.findFirst("username = ?", "Hackerman");
		Question q = Question.findFirst("description = ?", "testing");
		JSONObject resp = QuestionController.updateWrongAnswer(u.getUsername(), q);
		assertEquals(resp.get("answer"), "Incorrecto!");
	}

}
