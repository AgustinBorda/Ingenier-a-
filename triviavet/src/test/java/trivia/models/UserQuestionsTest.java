
package trivia.models;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserQuestionsTest {
  
  @Before
  public void before(){
    Base.open();
    System.out.println("UserQuestions test setup");
    Base.openTransaction();
  }

  @After
  public void after(){
    System.out.println("UserQuestions test tearDown");
    Base.rollbackTransaction();
    Base.close();
  }

  @Test
  public void validatePrecenseOfuser_id(){
    UserQuestion UserQuestionsTest = new UserQuestion();
    UserQuestionsTest.set("user_id", null);
    assertEquals(UserQuestionsTest.isValid(), false);
  }
  
  @Test
  public void validatePrecenseOfquestion_id(){
    UserQuestion userQuestionsTest = new UserQuestion();
    userQuestionsTest.set("user_id", 1, "question_id", null);
    assertEquals(userQuestionsTest.isValid(), false);
  }
  
  @Test
  public void validatePrecenseOfSomethingIn(){
    UserQuestion userQuestionsTest = new UserQuestion();
    userQuestionsTest.set("user_id", 1,"question_id", 1);
    assertEquals(userQuestionsTest.isValid(), true);
  }
}