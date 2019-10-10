package trivia.models;

import org.javalite.activejdbc.Model;

public class QuestionStatistic extends Model {
	public static void generateQuestionStatistic(int questionId) {
		QuestionStatistic stat = new QuestionStatistic();
		stat.set("question_id", questionId);
		stat.set("wrong_attempts", 0);
		stat.set("right_attempts", 0);
		stat.set("total_attempts", 0);
		stat.saveIt();
	}
	
	public void updateCorrectAnswer() {
		this.set("right_attempts",(int)this.get("right_attempts")+1);
		this.set("total_attempts",(int)this.get("total_attempts")+1);
		this.saveIt();
	}
	
	public void updateIncorrectAnswer() {
		this.set("wrong_attempts",(int)this.get("wrong_attempts")+1);
		this.set("total_attempts",(int)this.get("total_attempts")+1);
		this.saveIt();
	}
}
