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

      get("/questions", (req,res) -> {
        List<Question> questions = Question.findAll();
        String resp ="";
        for (Question q : questions) {
          resp +="Id: " + q.get("id")+", ";
          resp +="Question: " + q.get("description")+", ";
          List<Option> options = Option.where("question_id = " + q.get("id"));
          for (Option o:options){
            resp += "Answer: " + o.get("description");
          }
          resp += "\n";
        }
        return resp;
      });


      post("/loadquestion", (req, res) -> {
      	Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);

      	Question question = new Question();
      	question.set("description", bodyParams.get("description"));
      	question.saveIt();
        Option option = new Option();
        option.set("question_id",question.get("id"));
        option.set("description",bodyParams.get("description1"));
        option.set("correct",bodyParams.get("correct1"));
        option.saveIt();
        Option option2 = new Option();
        option2.set("question_id",question.get("id"));
        option2.set("description",bodyParams.get("description2"));
        option2.set("correct",bodyParams.get("correct2"));
        option2.saveIt();
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

       post("/usersdelete", (req,res) -> {
          List<User> users = User.findAll();
          for(User u : users){
          	u.delete();
          }
          return "Todos los usuario eliminados";
       });

       post("/userdelete", (req , res ) -> {
       Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
       List<User> usuarios = User.where("username = ?", bodyParams.get("username"));
       User usuario = usuarios.get(0);
       if(usuario != null){
       	 usuario.delete();
       	 res.type("application/json");
       	 return "se ha borrado";
       }else{
       	return "Nose encontro usuario para borrar";
       }

       });

    }
}
