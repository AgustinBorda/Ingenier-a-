package trivia;

import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.options;

import static spark.Spark.before;
import static spark.Spark.after;
import static spark.Spark.halt;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;

import trivia.User;
import trivia.BasicAuth;

import com.google.gson.Gson;
import java.util.Map;
import java.util.ArrayList;

class QuestionParam
{
  String description;
  ArrayList<OptionParam> options;
}


class OptionParam
{
  String description;
  Boolean correct;
}

public class App
{
  static User currentUser;

    public static void main( String[] args )
    {
      before((request, response) -> {
        Base.open();

        String headerToken = (String) request.headers("Authorization");

        if (
          headerToken == null ||
          headerToken.isEmpty() ||
          !BasicAuth.authorize(headerToken)
        ) {
          System.out.println("Falto poner el usuario");
          halt(401);
        }

        currentUser = BasicAuth.getUser(headerToken);

        Base.close();
        });

        after((request, response) -> {
          //Base.close();
          response.header("Access-Control-Allow-Origin", "*");
          response.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
          response.header("Access-Control-Allow-Headers",
            "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
        });

        options("/*", (request, response) -> {
          return "OK";
        });

      get("/hello/:name", (req, res) -> {
        return "hello" + req.params(":name");
      });


      get("/question/:id", (req,res) -> {
        Base.open();
        List<Question> questions = Question.where("id = ?",req.params(":id"));
        Question question = questions.get(0);
        List<Option> options = Option.where("question_id = ?", question.get("id"));
        String resp = "";
        resp +="Question: " + question.get("description")+", ";
        resp += "\n";
        int i = 1;
        for(Option o : options){
          resp += "Answer "+i+": " + o.get("description");
          resp += "\n";
          i++;
        }
        Base.close();
        return resp;

      });

      get("/questions", (req,res) -> {
        Base.open();
        List<Question> questions = Question.findAll();
        String resp ="";
        for (Question q : questions) {
          resp +="Id: " + q.get("id")+", ";
          resp +="Question: " + q.get("description")+", ";
          List<Option> options = Option.where("question_id = ?", q.get("id"));
          for (Option o:options){
            resp += "Answer: " + o.get("description");
          }
          resp += "\n";
        }
        Base.close();
        return resp;
      });

      post("/answer", (req,res) -> {
        Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
        Base.open();
        List<Option> options = Option.where("question_id = ?",bodyParams.get("id"));
        int i = Integer.parseInt((String)bodyParams.get("answer"));
        Option option = options.get(i-1);
        if((boolean)option.get("correct")){
          Base.close();
          return "Correcto!\n";
        }
        else{
          Base.close();
          return "Incorrecto!\n";
        }


      });

        post("/questions", (req, res) -> {
          Base.open();
          QuestionParam bodyParams = new Gson().fromJson(req.body(), QuestionParam.class);

          Question question = new Question();
          question.set("description", bodyParams.description);
          question.save();

          for(OptionParam item: bodyParams.options) {
            Option option = new Option();
            option.set("description", item.description).set("correct", item.correct);
            question.add(option);
          }

          Base.close();
          return question;
        });



      post("/login", (req, res) -> {
        res.type("application/json");

        // if there is currentUser is because headers are correct, so we only
        // return the current user here
        return currentUser.toJson(true);
      });


	  post("/users", (req, res) -> {
        Base.open();
        Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
        List<User> aux = User.where("Username = ?", bodyParams.get("username"));
        if (aux.size() == 0){
          User user = new User();
          user.set("username", bodyParams.get("username"));
          user.set("password", bodyParams.get("password"));
          user.set("admin", bodyParams.get("admin"));
          user.saveIt();
          UserStatistic stats = new UserStatistic();
          stats.set("user", user.get("username"));
          stats.set("points",0);
          stats.set("correct_answer",0);
          stats.set("incorrect_answer",0);
          stats.saveIt();
          res.type("application/json");
          Base.close();
          return user.toJson(true);
        }
        else{
          Base.close();
          return "Usuario ya existente";
        }
      });



       get("/users", (req, res) -> {
        Base.open();
      	List<User> users = User.findAll();
      	String resp = "";
      	for (User u : users) {
      		resp +="Id: " + u.get("id")+", ";
      		resp +="Username: " + u.get("username")+", ";
      		resp +="Password(falla de seguridad? donde?): " + u.get("password")+"\n";

      	}
        Base.close();
      	return resp;
      });

       post("/usersdelete", (req,res) -> {
          Base.open();
          List<User> users = User.findAll();
          for(User u : users){
          	u.delete();
          }
          Base.close();
          return "Todos los usuario eliminados";
       });

       post("/userdelete", (req , res ) -> {
        Base.open();
       Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
       List<User> usuarios = User.where("username = ?", bodyParams.get("username"));
       User usuario = usuarios.get(0);
       if(usuario != null){
       	 usuario.delete();
       	 res.type("application/json");
         Base.close();
       	 return "se ha borrado";
       }else{
        Base.close();
       	return "No se encontro usuario para borrar";
       }

       });

    }
}
