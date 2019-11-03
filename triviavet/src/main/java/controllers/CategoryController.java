package controllers;

import org.javalite.activejdbc.LazyList;

import trivia.models.Category;
import trivia.models.User;
import trivia.models.UserStatisticsCategory;

public class CategoryController {
	
	public static void createCategory(String name) {
		Category category = new Category();
		category.setNombre(name);
		category.saveIt();
		LazyList<User> users = User.findAll();
		for(User u : users) {
			UserStatisticsCategory.createUserStatistic(u,category);
		}
	}
	
	public static void deleteCategory(String name) {
		Category cat = Category.findFirst("nombre = ?", name);
		System.out.println(cat);
		Category.delete("nombre = ?", cat.getNombre()); //unorthodox, but effective way to delete a category.
	}
	
	
	public static void modifyCategory(String oldName, String newName) {
		Category cat = Category.findFirst("nombre = ?", oldName);
		cat.setNombre(newName);
		cat.saveIt();
	}
}
