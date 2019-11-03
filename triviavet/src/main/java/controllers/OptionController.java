package controllers;

import java.util.ArrayList;

import trivia.models.Option;
import trivia.models.Question;
import trivia.structures.OptionParam;

public class OptionController {
	
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
