package trivia.routes;

import org.javalite.activejdbc.Base;
import spark.*;

public class Public {

	public static Filter BaseOpen = (request, response) -> {
		if (!Base.hasConnection())
			Base.open();	
	};
	
	public static Filter BaseClose = (request, response) -> {
		if(Base.hasConnection())
			Base.close();

		response.header("Access-Control-Allow-Origin", "*");
		response.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
		response.header("Access-Control-Allow-Headers",
				"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
	};
}
