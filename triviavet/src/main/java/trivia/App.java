package trivia;

import static spark.Spark.*;

import trivia.models.*;
import trivia.routes.*;
import trivia.structures.*;

import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class App {

	public static void main(String[] args) {

		before("*",PublicRoutes.BaseOpen);

		after("*", PublicRoutes.BaseClose);

		before("/loged/*", PrivateRoutes.CheckSession);

		post("/loged/categoryquestion", PrivateRoutes.PostCategoryQuestion);

		get("/loged/question", PrivateRoutes.GetQuestion);

		get("/loged/statistics", PrivateRoutes.GetStatistics);

		post("/loged/admin", PrivateRoutes.PostAdmin);

		post("/loged/userdelete", PrivateRoutes.PostUserDelete);

		post("/loged/answer", PrivateRoutes.PostAnswer);

		post("/loged/questions", PrivateRoutes.PostQuestions);

		post("/login", PublicRoutes.PostLogin);
		
		//rename?
		post("/users", PublicRoutes.PostUsers);

		get("/loged/category", PrivateRoutes.GetCategory);
	}
}
