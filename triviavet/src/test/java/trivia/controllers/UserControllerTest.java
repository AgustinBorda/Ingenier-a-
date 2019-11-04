package trivia.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import controllers.UserController;
import trivia.models.User;
import trivia.testUtils.DBSpecAlternative;

public class UserControllerTest extends DBSpecAlternative {
	
	@Before
	public void setUp() {
		User u2 = new User();
		u2.set("username", "Hackerman", "password", "jak1ad0r" , "admin", false, "email", "hackingsa@gmail.com");
		u2.saveIt();
	}
	
	@Test
	public void createUserTest() {
		Map<String,Object> bodyParams = new HashMap<String, Object>();
		bodyParams.put("username", "user");
		bodyParams.put("password", "iamtheuser");
		bodyParams.put("admin", false);
		bodyParams.put("email", "user@usador.com");
		User u = UserController.createUser(bodyParams);
		assertEquals(u.getUsername(),"user");
		assertEquals(u.getPassword(),"iamtheuser");
		assertFalse(u.getAdmin());
		assertEquals(u.getEmail(),"user@usador.com");
	}
	
	@Test
	public void giveAdminPermissionTest() {
		User u = User.findFirst("username = ?", "Hackerman");
		assertFalse(u.getAdmin());
		UserController.giveAdminPermissions(u);
		assertTrue(u.getAdmin());
	}

}
