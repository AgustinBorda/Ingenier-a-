package trivia.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import controllers.CategoryController;
import trivia.models.Category;
import trivia.testUtils.DBSpecAlternative;

public class CategoryControllerTest extends DBSpecAlternative {
	@Before
	public void setUp() {
		CategoryController.createCategory("cat");
	}
	
	@Test
	public void createCategoryTest() {
		CategoryController.createCategory("test");
		Category cat = Category.findFirst("nombre = ?", "test");
		assertNotNull(cat);
		assertEquals("test",cat.getNombre());
	}
	
	@Test
	public void deleteCategoryTest() {
		CategoryController.deleteCategory("cat");
		Category cat = Category.findFirst("nombre = ?", "cat");
		assertNull(cat);
	}
	
	@Test
	public void modifyCategoryTest() {
		CategoryController.modifyCategory("cat","test1");
		Category cat = Category.findFirst("nombre = ?", "test1");
		assertNotNull(cat);
		assertEquals("test1",cat.getNombre());
	}
	
}
