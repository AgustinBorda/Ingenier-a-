package trivia.models;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.CategoryController;
import trivia.testUtils.DBSpecAlternative;

import static org.junit.Assert.*;

public class CategoryTest extends DBSpecAlternative{
  
  @Before
  public void before(){
    System.out.println("Category test setup");
    CategoryController.createCategory("test");
  }

  @After
  public void after(){
    System.out.println("Category test tearDown");

  }

  @Test
  public void validatePrecenseOfnombre(){
    Category categoryTest = new Category();
    categoryTest.set("nombre", null);
    assertFalse(categoryTest.isValid());
  }
  
  @Test
  public void validatePrecenseOfSomethingInNombre(){
    Category categoryTest = new Category();
    categoryTest.setNombre("test2");
    assertTrue(categoryTest.isValid());
  }

  @Test
  public void validateUniqueCategory(){
    Category categoryTest = new Category();
    categoryTest.setNombre("test");
    assertFalse(categoryTest.isValid());
  }
  
  @Test
  public void getNombreTest() {
	  Category cat = Category.findFirst("nombre = ?", "test");
	  assertEquals(cat.getString("nombre"), cat.getNombre());
  }
  
  @Test
  public void setNombreTest() {
	  Category cat = Category.findFirst("nombre = ?", "test");
	  cat.setNombre("modificado");
	  assertEquals(cat.getNombre(), "modificado");
  }
  
}
