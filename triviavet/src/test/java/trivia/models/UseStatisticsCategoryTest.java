package trivia.models;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UseStatisticsCategoryTest{
  
  @Before
  public void before(){
    Base.open();
    System.out.println("UseStatisticsCategory test setup");
    Base.openTransaction();
  }

  @After
  public void after(){
    System.out.println("UseStatisticsCategory test tearDown");
    Base.rollbackTransaction();
    Base.close();
  }

  @Test
  public void validatePrecenseOfnombre(){
    UseStatisticsCategory UseStatisticsCategoryTest = new UseStatisticsCategory();
    UseStatisticsCategoryTest.set("nombre", "");
    assertEquals(UseStatisticsCategoryTest.isValid(), false);
  }
  
  @Test
  public void validatePrecenseOfSomethingInNombre(){
    UseStatisticsCategory UseStatisticsCategoryTest = new UseStatisticsCategory();
    UseStatisticsCategoryTest.set("nombre", "anatomia");
    assertEquals(UseStatisticsCategoryTest.isValid(), true);
  }

  @Test
  public void validateUniqueLeague(){
    assertTrue(new UseStatisticsCategory().set("nombre", "fisica").saveIt());
    UseStatisticsCategory useStatisticsCategoryTest = new UseStatisticsCategory();
    useStatisticsCategoryTest.set("nombre", "fisica");
    assertEquals(useStatisticsCategoryTest.isValid(), false);
  }
}