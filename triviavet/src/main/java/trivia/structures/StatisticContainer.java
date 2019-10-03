package trivia.structures;
import org.json.JSONObject;

import trivia.models.*;

public class StatisticContainer {
	String cat;
	int correctAnswer;
	int incorrectAnswer;
	int level;
	double correctPercentage; 
	
	public StatisticContainer(UserStatisticsCategory stat) {
		cat = (String)stat.get("nombre");
		correctAnswer = (int) stat.get("correct_answer");
		incorrectAnswer = (int) stat.get("incorrect_answer");
		level = stat.calculateLevel();
		if(correctAnswer+incorrectAnswer != 0){
			correctPercentage = ((correctAnswer+0.0)/(correctAnswer+incorrectAnswer+0.0));	
		}
		else {
			correctPercentage = 0.0;
		}
	}
	
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("cat", this.cat);
		json.put("correct_answer", this.correctAnswer);
		json.put("incorrect_answer", this.incorrectAnswer);
		json.put("level", this.level);
		json.put("correct_percentage", this.correctPercentage);
		return json;		
	}
	

}
