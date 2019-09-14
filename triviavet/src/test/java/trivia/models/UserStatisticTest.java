package trivia.models;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserStatisticTest {
  
  @Before
  public void before(){
    Base.open();
    System.out.println("UserStatistic test setup");
    Base.openTransaction();
  }

  @After
  public void after(){
    System.out.println("UserStatistic test tearDown");
    Base.rollbackTransaction();
    Base.close();
  }

  @Test
  public void validatePrecenseOfuser(){
    UserStatistic userStatistic = new UserStatistic();
    userStatistic.set("points", 5, "correct_answer", 10, "incorrect_answer", 3);
    assertEquals(userStatistic.isValid(), false);
  }
  
  @Test
  public void validateUniqueUser(){
    assertTrue(new UserStatistic().set("user", "admin", "points", 5, "correct_answer", 10, "incorrect_answer", 3).saveIt());
    UserStatistic userStatistic = new UserStatistic();
    userStatistic.set("user", "admin", "points", 1, "correct_answer", 1, "incorrect_answer", 1);
    assertEquals(userStatistic.isValid(), false);
  }

  @Test
  public void validatePrecenseOfpoints(){
    UserStatistic userStatistic = new UserStatistic();
    userStatistic.set("user", "jose", "correct_answer", 10, "incorrect_answer", 3);
    assertEquals(userStatistic.isValid(), false);
  }
  
  @Test
  public void validatePrecenseOfcorrect_answer(){
    UserStatistic userStatistic = new UserStatistic();
    userStatistic.set("user", "jose", "points", 5, "incorrect_answer", 3);
    assertEquals(userStatistic.isValid(), false);
  }
  
  @Test
  public void validatePrecenseOfincorrect_answer(){
    UserStatistic userStatistic = new UserStatistic();
    userStatistic.set("user", "jose", "points", 5, "correct_answer", 10);
    assertEquals(userStatistic.isValid(), false);
  }
  
  @Test
  public void validatePrecenseOfSomethingIn(){
    UserStatistic userStatistic = new UserStatistic();
    userStatistic.set("user", "jose", "points", 5, "correct_answer", 10, "incorrect_answer", 3);
    assertEquals(userStatistic.isValid(), true);
  }
}
