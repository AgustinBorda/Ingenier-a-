package controllers;

import trivia.models.QuestionStatistic;

public class QuestionStatisticController {
	
	public static QuestionStatistic generateQuestionStatistic(String question) {
		QuestionStatistic stat = new QuestionStatistic();
		stat.setQuestion(question);
		stat.setWrongAttempts(0);
		stat.setRightAttempts(0);
		stat.setTotalAttempts(0);
		stat.saveIt();
		return stat;
	}
	
	public static void updateCorrectAnswer(QuestionStatistic stat) {
		stat.setRightAttempts(stat.getRightAttempts()+1);
		stat.setTotalAttempts(stat.getTotalAttempts()+1);
		stat.saveIt();
	}
	
	public static void updateIncorrectAnswer(QuestionStatistic stat) {
		stat.setWrongAttempts(stat.getWrongAttempts()+1);
		stat.setTotalAttempts(stat.getTotalAttempts()+1);
		stat.saveIt();
	}
}
