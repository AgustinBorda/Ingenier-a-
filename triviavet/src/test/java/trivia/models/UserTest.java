package trivia.models;



import org.junit.Before;
import org.junit.Test;

import trivia.testUtils.DBSpecAlternative;

import static org.junit.Assert.*;

public class UserTest extends DBSpecAlternative{

	@Before
	public void before(){
		User u2 = new User();
		u2.set("username", "Hackerman", "password", "jak1ad0r" , "admin", true, "email", "hackingsa@gmail.com");
		u2.saveIt();
	}

	@Test
	public void validatePrecenseOfusername(){
		User userTest = new User();
		userTest.set("password", "jose" , "admin", true, "email", "123");
		assertFalse(userTest.isValid());
	}

	@Test
	public void validatePrecenseOfpassword(){
		User userTest = new User();
		userTest.set("username", "jose", "admin", true, "email", "123");
		assertFalse(userTest.isValid());
	}

	@Test
	public void validatePrecenseOfadmin(){
		User userTest = new User();
		userTest.set("username", "jose", "password", "jose", "email", "123");
		assertFalse(userTest.isValid());
	}

	@Test
	public void validatePrecenseOfEmail(){
		User userTest = new User();
		userTest.set("username", "jose", "password", "jose" , "admin", true);
		assertFalse(userTest.isValid());
	}

	@Test
	public void validatePrecenseOfSomethingIn(){
		User userTest = new User();
		userTest.set("username", "jose98523168541", "password", "jose98523168541" , "admin", true, "email", "theemail@gmail.com");
		assertTrue(userTest.isValid());
	}
	
	@Test
	public void getPasswordTest() {
		User u = User.findFirst("username = ?", "Hackerman");
		assertEquals(u.getString("password"),u.getPassword());
	}
	
	@Test
	public void getUsernameTest() {
		User u = User.findFirst("username = ?", "Hackerman");
		assertEquals(u.getString("username"),u.getUsername());
	}
	
	@Test
	public void getAdminTest() {
		User u = User.findFirst("username = ?", "Hackerman");
		assertEquals(u.getBoolean("admin"),u.getAdmin());
	}
	
	@Test
	public void getEmailTest() {
		User u = User.findFirst("username = ?", "Hackerman");
		assertEquals(u.getString("email"),u.getEmail());
	}
	
	@Test
	public void setUsernameTest() {
		User u = User.findFirst("username = ?", "Hackerman");
		u.setUsername("Anti-Hackerman");
		assertEquals("Anti-Hackerman", u.getUsername());
	}
	
	@Test
	public void setPasswordTest() {
		User u = User.findFirst("username = ?", "Hackerman");
		u.setPassword("********");
		assertEquals("********", u.getPassword());
	}
	
	@Test
	public void setAdminTest() {
		User u = User.findFirst("username = ?", "Hackerman");
		assertTrue(u.getAdmin());
		u.setAdmin(false);
		assertFalse(u.getAdmin());
	}
	
	@Test
	public void setEmailTest() {
		User u = User.findFirst("username = ?", "Hackerman");
		u.setEmail("hackingsrl@gmail.com");
		assertEquals("hackingsrl@gmail.com", u.getEmail());
	}
}
