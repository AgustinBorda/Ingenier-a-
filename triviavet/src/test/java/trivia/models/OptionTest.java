package trivia.models;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OptionTest {
  
  @Before
  public void before(){
    Base.open();
    System.out.println("Option test setup");
    Base.openTransaction();
  }

  @After
  public void after(){
    System.out.println("Option test tearDown");
    Base.rollbackTransaction();
    Base.close();
  }

  @Test
  public void validatePrecenseOfDescription(){
    Option optionTest = new Option();
    optionTest.set("description", "");
    assertEquals(optionTest.isValid(), false);
  }
  
  @Test
  public void validatePrecenseOfSomethingInDescription(){
    Option optionTest = new Option();
    optionTest.set("description", "perro");
    assertEquals(optionTest.isValid(), true);
  }
}