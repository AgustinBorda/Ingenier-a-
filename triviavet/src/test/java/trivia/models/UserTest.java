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
    userTest.set("password", "jose" , "admin", true, "email", "123");
    assertEquals(userTest.isValid(), false);
  }
  
  @Test
  public void validateUniqueUsername(){
    assertTrue(new User().set("username", "jose98523168541", "password", "jose98523168541" , "admin", true, "email", "123@gmail.com").saveIt());
    User userTest = new User();
    userTest.set("username", "jose98523168541", "password", "jose98523168541" , "admin", true, "email", "123@gmail.com");
    assertEquals(userTest.isValid(), false);
  }

  @Test
  public void validatePrecenseOfpassword(){
    User userTest = new User();
    userTest.set("username", "jose", "admin", true, "email", "123");
    assertEquals(userTest.isValid(), false);
  }
  
  @Test
  public void validatePrecenseOfadmin(){
    User userTest = new User();
    userTest.set("username", "jose", "password", "jose", "email", "123");
    assertEquals(userTest.isValid(), false);
  }
  
  @Test
  public void validatePrecenseOfSomethingIn(){
    User userTest = new User();
    userTest.set("username", "jose98523168541", "password", "jose98523168541" , "admin", true, "email", "theemail@gmail.com");
    assertEquals(userTest.isValid(), true);
  }
}