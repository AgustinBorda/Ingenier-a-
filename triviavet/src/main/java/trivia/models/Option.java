package trivia.models;

import org.javalite.activejdbc.Model;
import trivia.structures.OptionParam;
import java.util.ArrayList;

public class Option extends Model{

	static {
		validatePresenceOf("description").message("Please, provide your description");
	}
	
	public static void setOptions(ArrayList<OptionParam> options,Question question) {
		for (OptionParam item : options) {
			Option option = new Option();
			option.set("description", item.description).set("correct", item.correct);
			option.set("question_id", question.getId());
			option.saveIt();
		}
	}
}