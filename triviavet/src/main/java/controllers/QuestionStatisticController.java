package controllers;

import trivia.models.QuestionStatistic;
/**
 * Class that provides a controller for the QUestionStatistic model.
 */
public class QuestionStatisticController {
	/**
	 * Create a QuestionStatistic.
	 * @param question the question
	 */
	public static void generateQuestionStatistic(String question) {
		QuestionStatistic stat = new QuestionStatistic();
		stat.setQuestion(question);
		stat.setWrongAttempts(0);
		stat.setRightAttempts(0);
		stat.setTotalAttempts(0);
		stat.saveIt();
	}
	/**
	 * Update a given stat
	 * @param stat the stat
	 */
	public static void updateCorrectAnswer(QuestionStatistic stat) {
		stat.setRightAttempts(stat.getRightAttempts()+1);
		stat.setTotalAttempts(stat.getTotalAttempts()+1);
		stat.saveIt();
	}
	
	/**
	 * Update a given stat
	 * @param stat the stat
	 */
	public static void updateIncorrectAnswer(QuestionStatistic stat) {
		stat.setWrongAttempts(stat.getWrongAttempts()+1);
		stat.setTotalAttempts(stat.getTotalAttempts()+1);
		stat.saveIt();
	}
}
