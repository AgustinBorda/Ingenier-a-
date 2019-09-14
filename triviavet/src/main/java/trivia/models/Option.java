package trivia.models;

import org.javalite.activejdbc.Model;
import trivia.structures.OptionParam;
import java.util.ArrayList;

public class Option extends Model{

	static {
		validatePresenceOf("description").message("Please, provide your description");
	}
	
	public static void createOptions(ArrayList<OptionParam> options,Question question) {
		for (OptionParam item : options) {
			Option option = new Option();
			option.set("description", item.description).set("correct", item.correct);
			question.add(option);
		}
	}
}