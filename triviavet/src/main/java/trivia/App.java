package trivia;

import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

import static spark.Spark.before;
import static spark.Spark.after;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;

import trivia.User;

import com.google.gson.Gson;
import java.util.Map;

public class App
{
    public static void main( String[] args )
    {
      before((request, response) -> {
        Base.open();
      });

      after((request, response) -> {
        Base.close();
      });

      get("/hello/:name", (req, res) -> {
        return "hello" + req.params(":name");
      });



      post("/loadquestion", (req, res) -> {
      	Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);

        int id;
      	Question question = new Question();
      	question.set("description", bodyParams.get("description"));
        id = question.get("id");
      	question.saveIt();
        Option option = new Option;
        option.set()
      	res.type("application/json");

      	return question.toJson(true);

      });


	  post("/users", (req, res) -> {
        Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);

        User user = new User();
        user.set("username", bodyParams.get("username"));
        user.set("password", bodyParams.get("password"));
        user.set("admin", bodyParams.get("admin"));
        user.saveIt();

        res.type("application/json");

        return user.toJson(true);
      });



       get("/users", (req, res) -> {
      	List<User> users = User.findAll();
      	String resp = "";
      	for (User u : users) {
      		resp +="Id: " + u.get("id")+", ";
      		resp +="Username: " + u.get("username")+", ";
      		resp +="Password(falla de seguridad? donde?): " + u.get("password")+"\n";

      	}
      	return resp;
      });

    }
}
