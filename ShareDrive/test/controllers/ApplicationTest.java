package controllers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Scope.Session;
import play.mvc.Http.*;
import models.*;

public class ApplicationTest extends FunctionalTest {

	@Test
	public void testThatIndexPageWorks() {
		Response response = GET("/");
		assertIsOk(response);
		assertContentType("text/html", response);
		assertCharset(play.Play.defaultWebEncoding, response);
	}

	@Test
	public void testLogin() {
		User u=new User("Testuser", "Testuser", "t@tum.de", "test", "test");
		u.create();
		Map<String, String> loginUserParams = new HashMap<String, String>(); 
	    loginUserParams.put("username", "test"); 
	    loginUserParams.put("password", "test");

	    // Login here so the following requests will be authenticated:
	    POST("/Application/login", loginUserParams);
    	assertNotNull(Session.current().get("user"));
	}
	
	@Test
	public void testLoginFailed() {
		Map<String, String> loginUserParams = new HashMap<String, String>(); 
	    loginUserParams.put("username", "test"); 
	    loginUserParams.put("password", "test");

	    // Login here so the following requests will be authenticated:
	    POST("/Application/login", loginUserParams);
    	assertNull(Session.current().get("user"));
	}

}