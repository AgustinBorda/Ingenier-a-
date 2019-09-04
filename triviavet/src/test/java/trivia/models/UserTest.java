package trivia.models;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
  
  @Before
  public void before(){
    Base.open();
    System.out.println("User test setup");
    Base.openTransaction();
  }

  @After
  public void after(){
    System.out.println("User test tearDown");
    Base.rollbackTransaction();
    Base.close();
  }

  @Test
  public void validatePrecenseOfusername(){
    User userTest = new User();
    userTest.set("password", "jose" , "admin", true);
    assertEquals(userTest.isValid(), false);
  }
  
  @Test
  public void validateUniqueUsername(){
    assertTrue(new User().set("username", "jose", "password", "jose" , "admin", true).saveIt());
    User userTest = new User();
    userTest.set("username", "jose", "password", "jose" , "admin", true);
    assertEquals(userTest.isValid(), false);
  }

  @Test
  public void validatePrecenseOfpassword(){
    User userTest = new User();
    userTest.set("username", "jose", "admin", true);
    assertEquals(userTest.isValid(), false);
  }
  
  @Test
  public void validatePrecenseOfadmin(){
    User userTest = new User();
    userTest.set("username", "jose", "password", "jose");
    assertEquals(userTest.isValid(), false);
  }
  
  @Test
  public void validatePrecenseOfSomethingIn(){
    User userTest = new User();
    userTest.set("username", "jose", "password", "jose" , "admin", true);
    assertEquals(userTest.isValid(), true);
  }
}