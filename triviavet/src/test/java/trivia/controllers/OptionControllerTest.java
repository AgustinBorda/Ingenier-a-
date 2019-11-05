package trivia.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.junit.Before;
import org.junit.Test;

import controllers.CategoryController;
import controllers.OptionController;
import trivia.models.Option;
import trivia.models.Question;
import trivia.structures.OptionParam;
import trivia.testUtils.DBSpecAlternative;

public class OptionControllerTest extends DBSpecAlternative {
	@Before
	public void setUp() {
		CategoryController.createCategory("cat");
		Question question = new Question();
		question.setDescription("test");
		question.setCategory("cat");
		question.saveIt();

	}
	
	@Test
	public void CreateOptionsTest() {
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
		Question question = Question.findFirst("description = ?", "test");
		OptionController.setOptions(o, question);
		LazyList<Model> res = Option.where("question_id = ?", question.getId());
		assertEquals(res.size(),4);
		assertEquals(res.get(0).getString("description"),"respuesta 1");
		assertFalse(res.get(0).getBoolean("correct"));
		assertEquals(res.get(1).getString("description"),"respuesta 2");
		assertFalse(res.get(1).getBoolean("correct"));
		assertEquals(res.get(2).getString("description"),"respuesta 3");
		assertFalse(res.get(2).getBoolean("correct"));
		assertEquals(res.get(3).getString("description"),"respuesta 4");
		assertTrue(res.get(3).getBoolean("correct"));	
	}

}
