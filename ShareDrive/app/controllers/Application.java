package controllers;

import play.*;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

	public static void register() {
		render();
	}

	static User connected() {
		String username = session.get("user");
		System.out.println(session.get("user"));
		if (username != null) {
			return User.find("byUsername", username).first();
		}
		return null;
	}


	public static void index() {
		if (connected() != null) {
			Rides.index();
		}
		render();
	}

	public static void login(String username, String password) {
		System.out.println("user:" + username);
		User user = User.find("byUsernameAndPassword", username, password)
				.first();
		if (user != null) {
			session.put("user", user.username);
			flash.success("Welcome, " + user.lname);
			Rides.index();
		}
		// Oops
		System.out.print("user not found");
		flash.put("username", username);
		flash.error("Login failed");
		index();
	}

	public static void saveUser(String username, String fname, String lname,
			String email, String password) {
		User user = new User(username, fname, lname, email, password);
		user.save();
		user.create();
		session.put("user", user.username);
		flash.success("Welcome, " + user.fname);
		Rides.index();
	}
	
	public static void logout() {
        session.clear();
        index();
    }

}