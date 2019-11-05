package controllers;

import java.util.ArrayList;

import trivia.models.Option;
import trivia.models.Question;
import trivia.structures.OptionParam;
/**
 * Class that provides a controller for Option model.
 */
public class OptionController {
	/**
	 * Create the options of the question.
	 * @param options, the options to be binded with rh question
	 * @param question, the question
	 */
	public static void setOptions(ArrayList<OptionParam> options,Question question) {
		for (OptionParam item : options) {
			Option option = new Option();
			option.setDescription(item.description);
			option.setCorrect(item.correct);
			option.setQuestionId(question.getId());
			option.saveIt();
		}
	}
}
