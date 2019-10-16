package trivia.utils;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.commons.lang3.RandomStringUtils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

//Singleton
public class Email {

	private static Email email;

	private String host;

	private String port;

	private String dirEmail;

	private String password;
	
	private Cache<String, String> recuperationKeys = CacheBuilder.newBuilder()
		    .maximumSize(10000)
		    .expireAfterWrite(24, TimeUnit.HOURS)
		    .build();

	private Email() {
		try (InputStream input = Email.class.getClassLoader().getResourceAsStream("email.properties")) {
			if (input == null) {
				System.out.println("Sorry, unable to find config.properties");
				return;
			}

			// load a properties file from class path, inside static method
			Properties prop = new Properties();
			prop.load(input);
			if (!prop.containsKey("email.sender") || !prop.containsKey("email.password")
					|| !prop.containsKey("email.host") || !prop.containsKey("email.port")) {
				System.out.println("Sorry, missing properties in config.properties");
				return;
			}

			// get the property value and print it out
			dirEmail = prop.getProperty("email.sender");
			password = prop.getProperty("email.password");
			host = prop.getProperty("email.host");
			port = prop.getProperty("email.port");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static Email getSingletonInstance() {
		if (email == null)
			email = new Email();
		return email;
	}

	public void sendMail(String to, String username) {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", host);
		prop.put("mail.smtp.port", port);
		prop.put("mail.smtp.ssl.trust", host);
		prop.put("mail.smtp.user", dirEmail);
		prop.put("mail.smtp.password", password);

		Session session = Session.getDefaultInstance(prop);
		MimeMessage message = new MimeMessage(session);
		String code = generateCode(username);
		try {
			message.setFrom(new InternetAddress("titulo?"));
			InternetAddress toAddress = new InternetAddress(to);

			message.addRecipient(Message.RecipientType.TO, toAddress);
				
			message.setSubject("Recuperar contrasena "); // poner la enie
			message.setContent("<h3>Buen dia</h3> \n El siguiente codigo es para recuperar su contrasena [<b>" + code + "</b>]", "text/html");
			// no funciona el <h3> asi pelado pero le podemos mandar bootstrap
			Transport transport = session.getTransport("smtp");
			transport.connect(host, dirEmail, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			recuperationKeys.invalidate(code);
			e.printStackTrace();
		}
	}
	
	private String generateCode(String username) {
		String generatedString = RandomStringUtils.random(5, true, true);
		while(recuperationKeys.getIfPresent(generatedString) != null) {
			generatedString = RandomStringUtils.random(5, true, true);
		}
		recuperationKeys.put(generatedString, username);
		System.out.println(recuperationKeys.getIfPresent(generatedString));
		return generatedString;
	}
	
	public String checkCode(String code) {
		return recuperationKeys.getIfPresent(code);
	}

}