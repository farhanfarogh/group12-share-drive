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


    public static void saveUser(@Valid User user, String verifyPassword) {
        validation.required(verifyPassword);
        validation.equals(verifyPassword, user.password).message("Your password doesn't match");
        User objUser = User.UserExist(user.username); 
     
        if(validation.hasErrors()) { //show form validation error
            render("@register", user, verifyPassword);
        }
        else if(objUser != null) { //show user exist error
            if(validation.equals(user.username, objUser.username) != null)
            {
            	String userExist = "**Username exist! Please use different username.";
            	render("@register", user, verifyPassword, userExist);
            }
        }
        
        user.create();
        session.put("user", user.username);
        flash.success("Welcome, " + user.lname);
        Rides.index();
    }
	
    public static void login(String username, String password) {
        User user = User.find("byUsernameAndPassword", username, password).first();
      
        if(user != null) { //executes when the username and password is empty
        	if(user.username != "") { //login when the user object is not null and also the return obj has a username
                session.put("user", user.username);
                flash.success("Welcome, " + user.lname);
                Rides.index();         

        	}
        	else { //executes when the return obj is emty, i.e. it does not contain any username
                flash.put("username", username);
                flash.error("Login failed! Please try again.");
                index();

        	}
        }
        else { //executes the query returns a null when the username and password does not match
            // Oops
           flash.put("username", username);
            flash.error("Login failed! Please try again.");
            index();
        }

    }

	public static void logout() {
        session.clear();
        index();
    }

}