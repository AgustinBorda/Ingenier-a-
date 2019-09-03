package trivia;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import trivia.models.User;

import org.javalite.activejdbc.Base;
import java.util.List;

public class UserTest{
  @BeforeClass
  public static void before(){
    Base.open();
    System.out.println("UserTest setup");
    Base.openTransaction();
  }

  @AfterClass
  public static void after(){
    System.out.println("UserTest tearDown");
    Base.rollbackTransaction();
    Base.close();
  }

  @Test
  public void nonRepeatedUsername(){
    User user1 = new User();
    user1.set("username","John");
    user1.set("password","1234");
    user1.saveIt();
    User user2 = new User();
    user2.set("username","John");
    user2.set("password","1234");
    try{
      user2.saveIt();
    }
    catch (Exception e) {
        List<User> users = User.where("username = 'John' AND password = '1234'");
        assertTrue("This will be a success",users.size() == 1);
    }
  }

  @Test
  public void validUsername(){
    User user1 = new User();
    user1.set("username","");
    user1.set("password","1234");
    user1.saveIt();
    assertEquals(user1.isValid(),true);
  }
}
