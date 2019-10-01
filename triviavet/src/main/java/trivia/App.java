package trivia;

import static spark.Spark.*;

import trivia.routes.*;

public class App {

	public static void main(String[] args) {

		before("*",PublicRoutes.BaseOpen);

		after("*", PublicRoutes.BaseClose);

		before("/logged/*", PrivateRoutes.CheckSession);
		
		before("/admin/*",AdminRoutes.CheckAdmin);
		
		
		post("/login", PublicRoutes.PostLogin);
		
		post("/users", PublicRoutes.PostUsers);

		get("/logged/category", PrivateRoutes.GetCategory);
		
		get("/logged/statistics", PrivateRoutes.GetStatistics);

		post("/logged/question", PrivateRoutes.PostQuestion);

		post("/logged/userdelete", PrivateRoutes.PostUserDelete);

		post("/logged/answer", PrivateRoutes.PostAnswer);

		post("/admin/admin",AdminRoutes.PostAdmin);
		
		post("/admin/questions", AdminRoutes.CreateQuestions);
		
		post("/admin/removequestion", AdminRoutes.RemoveQuestions);
		
		post("/admin/modifyquestion", AdminRoutes.ModifyQuestions);
	}
}
