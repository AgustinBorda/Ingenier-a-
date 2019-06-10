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
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import org.javalite.activejdbc.Model;
import org.json.JSONObject;
import java.lang.Thread;

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
  static Object preg_id;
  static Thread thread;

    public static void main( String[] args )
    {
      before((request, response) -> {
        thread = new Thread();
        thread.start();
        thread.run();
        Base.open();

        String headerToken = (String) request.headers("Authorization");

        if (
          headerToken == null ||
          headerToken.isEmpty() ||
          !BasicAuth.authorize(headerToken)
        ) {
          halt(401,"Usuario o clave invalidos \n");
        }

        currentUser = BasicAuth.getUser(headerToken);

        Base.close();
        });

        after((request, response) -> {
          //Base.close();
          thread.stop();
          thread = null;
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


      get("/question", (req,res) -> {
        Base.open();
        Random r = new Random();
        JSONObject resp = new JSONObject();
        List<Question> questions = Question.where("active = ?",true);
        Question question = questions.get(r.nextInt(questions.size()));
        List<Option> options = Option.where("question_id = ?", question.get("id"));
        preg_id = question.get("id");
        resp.put("description",question.get("description"));;
        int i = 1;
       for(Option o : options){
          resp.put("answer"+i, o.get("description"));
          i++;
        }
        Base.close();
        return resp;

      });

      /*get("/game", (req,res) ->{
          return res.redirect("localhost:4567/question").toString();

      });*/

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



      get("/statistics", (req,res) -> {
       Base.open();
        List<UserStatistic> estadisticas = UserStatistic.findAll();
        String resp ="";
        for (UserStatistic e : estadisticas) {
          resp +="Id: " + e.get("id")+", ";
          resp +="User: " + e.get("user")+", ";
          resp +="Points: " + e.get("points")+", ";
          resp +="Answer Correct: " + e.get("correct_answer")+", ";
          resp +="Answer Incorrect: " + e.get("incorrect_answer")+", ";
          resp += "\n";
        }
        Base.close();
        return resp;
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

      post("/admin", (req,res) -> {
        if((boolean)currentUser.get("admin")){
          Base.open();
          Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
          User user = User.findFirst("username = ?",bodyParams.get("username"));
          user.set("admin",true);
          user.saveIt();
          Base.close();
          res.type("application/json");
          return user.toJson(true);
        }
        else{
          res.type("application/json");
          return currentUser.toJson(true);
        }
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



        post("/answer", (req,res) -> {
        Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
        Base.open();
        JSONObject resp = new JSONObject();
        List<Option> options = Option.where("question_id = ?",preg_id);
        int i = Integer.parseInt((String)bodyParams.get("answer"));
        Option option = options.get(i-1);
        List<UserStatistic> stats = UserStatistic.where("user = ?",currentUser.get("username"));
        UserStatistic stat = stats.get(0);
        if((boolean)option.get("correct")){
          List<Question> questions = Question.where("id = ?",preg_id);
          Question question = questions.get(0);
          question.set("active",false);
          question.saveIt();
          int j = (int)stat.get("points")+1;
          System.out.println(j);
          stat.set("points",j);
          j = (int)stat.get("correct_answer")+1;
          stat.set("correct_answer",j);
          stat.saveIt();
          Base.close();
          resp.put("answer","Correcto!");
          return resp;
        }
        else{
          stat.set("incorrect_answer",(int)stat.get("incorrect_answer")+1);
          stat.saveIt();
          Base.close();
          resp.put("answer","Incorrecto!");
          return resp;
        }


      });

        post("/questions", (req, res) -> {
          if((boolean)currentUser.get("admin")){
            Base.open();
            QuestionParam bodyParams = new Gson().fromJson(req.body(), QuestionParam.class);
            Question question = new Question();
            question.set("description", bodyParams.description);
            question.set("active",true);
            question.save();
            for(OptionParam item: bodyParams.options) {
              Option option = new Option();
              option.set("description", item.description).set("correct", item.correct);
              question.add(option);
            }
            Base.close();
            return question.toJson(true);
          }
          else{
            return "No tenes permiso para crear preguntas";
          }
        });



      post("/login", (req, res) -> {
        return currentUser.toJson(true);
      });



    post("/users", (req, res) -> {
        Base.open();
        Map<String, Object> bodyParams = new Gson().fromJson(req.body(), Map.class);
        List<User> aux = User.where("Username = ?", bodyParams.get("username"));
        if (aux.size() == 0){
          if(((String)bodyParams.get("username")).length()>=1 && ((String)bodyParams.get("password")).length()>=1){
            User user = new User();
            user.set("username", bodyParams.get("username"));
            user.set("password", bodyParams.get("password"));
            user.set("admin", false);
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
            halt(403,"Usuario o clave invalidos");
            return "";
          }
        }
        else{
          Base.close();
          halt(401,"Usuario o clave invalidos \n");
          return "Usuario ya existente";
        }
      });


    }
}
