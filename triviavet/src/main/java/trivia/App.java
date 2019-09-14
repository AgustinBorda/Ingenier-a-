package trivia;

import static spark.Spark.*;

import trivia.routes.*;

public class App {

	public static void main(String[] args) {

		before("*",PublicRoutes.BaseOpen);

		after("*", PublicRoutes.BaseClose);

		before("/logged/*", PrivateRoutes.CheckSession);

		post("/logged/question", PrivateRoutes.PostQuestion);

		get("/logged/statistics", PrivateRoutes.GetStatistics);

		post("/logged/admin", PrivateRoutes.PostAdmin);

		post("/logged/userdelete", PrivateRoutes.PostUserDelete);

		post("/logged/answer", PrivateRoutes.PostAnswer);

		post("/logged/questions", PrivateRoutes.PostQuestions);

		post("/login", PublicRoutes.PostLogin);
		
		post("/users", PublicRoutes.PostUsers);

		get("/logged/category", PrivateRoutes.GetCategory);
	}
}
