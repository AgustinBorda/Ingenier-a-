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
    userStatistic.set("user", "");
    assertEquals(userStatistic.isValid(), false);
  }
  
  @Test
  public void validatePrecenseOfSomethingInuser(){
    UserStatistic userStatistic = new UserStatistic();
    userStatistic.set("user", "admin");
    assertEquals(userStatistic.isValid(), true);
  }

  @Test
  public void validateUniqueLeague(){
    assertTrue(new UserStatistic().set("user", "admin").saveIt());
    UserStatistic userStatistic = new UserStatistic();
    userStatistic.set("user", "admin");
    assertEquals(userStatistic.isValid(), false);
  }

  @Test
  public void validatePrecenseOfpoints(){
    UserStatistic userStatistic = new UserStatistic();
    userStatistic.set("points", null);
    assertEquals(userStatistic.isValid(), false);
  }
  
  @Test
  public void validatePrecenseOfSomethingInpoints(){
    UserStatistic userStatistic = new UserStatistic();
    userStatistic.set("points", 10);
    assertEquals(userStatistic.isValid(), true);
  }

  @Test
  public void validatePrecenseOfcorrect_answer(){
    UserStatistic userStatistic = new UserStatistic();
    userStatistic.set("correct_answer", null);
    assertEquals(userStatistic.isValid(), false);
  }
  
  @Test
  public void validatePrecenseOfSomethingIncorrect_answer(){
    UserStatistic userStatistic = new UserStatistic();
    userStatistic.set("correct_answer", 10);
    assertEquals(userStatistic.isValid(), true);
  }
}
