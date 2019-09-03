package trivia.models;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {
  
  @Before
  public void before(){
    Base.open();
    System.out.println("Category test setup");
    Base.openTransaction();
  }

  @After
  public void after(){
    System.out.println("Category test tearDown");
    Base.rollbackTransaction();
    Base.close();
  }

  @Test
  public void validatePrecenseOfnombre(){
    Category categoryTest = new Category();
    categoryTest.set("nombre", "");
    assertEquals(categoryTest.isValid(), false);
  }
  
  @Test
  public void validatePrecenseOfSomethingInNombre(){
    Category categoryTest = new Category();
    categoryTest.set("nombre", "anatomia");
    assertEquals(categoryTest.isValid(), true);
  }

  @Test
  public void validateUniqueLeague(){
    assertTrue(new Category().set("nombre", "fisica").saveIt());
    Category categoryTest = new Category();
    categoryTest.set("nombre", "fisica");
    assertEquals(categoryTest.isValid(), false);
  }
}