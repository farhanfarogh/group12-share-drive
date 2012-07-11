package controllers;

import play.*;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.libs.Mail;
import play.mvc.*;

import java.util.*;

import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.EmailException;

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
			Rides.search();
		}
		render();
	}

    public static void saveUser(@Valid User user, String verifyPassword) {
        validation.required(verifyPassword);
        validation.equals(verifyPassword, user.password).message("Your password doesn't match");
        User objUser = User.UserExist(user.username); 

        if(validation.hasErrors()) { //show form validation error
            render("@register", user, verifyPassword);
            return;
        }
        else if(objUser != null) { //show user exist error
            if(validation.equals(user.username, objUser.username) != null)
            {
            	String userExist = "**Username exist! Please use different username.";
            	render("@register", user, verifyPassword, userExist);
            	return;
            }
        }
        else {
            objUser = User.UserExistByEmail(user.email);
            if(objUser != null){
            	String userExist = "**User already exists with this email address.";
            	render("@register", user, verifyPassword, userExist);
            	return;
            }
        }
        if(AppModel.ValidateEmail(user.email) != true)
        {
        	String invalidEmail = "Please use university email address.";
        	render("@register", user, verifyPassword, invalidEmail);
        	return;
        }
        
        sendActivationCode(user);
        /*
        user.create();
        session.put("user", user.username);
        flash.success("Welcome, " + user.lname);
        Rides.index();*/
    }
	
    public static void login(String username, String password, String login, String register) {
    	if(login != null){
            User user = User.find("byUsernameAndPassword", username, password).first();
            if(user != null) { //executes when the username and password is empty
            	//if(user.username != "" && user.isActivated)
            	if(user.username != ""){ //login when the user object is not null and also the return obj has a username
                    session.put("user", user.username);
                    flash.success("Welcome, " + user.lname);
                    Rides.search();         

            	}
            	else { //executes when the return obj is emty, i.e. it does not contain any username
            		flash.put("username", username);
                    if(user.isActivated) 
                    	flash.error("Login failed! Please try again.");
                    else 
                    	flash.error("Please activate your account.");
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
    	
    	else if(register != null){
    		register();
    	}
    }

	public static void logout() {
        session.clear();
        index();
    }
	
	private static void sendActivationCode(User user) {
		Date date = new Date();
		long code = date.getTime();
		String hexString = Long.toHexString(code);
		user.activationCode = hexString;
		user.isActivated = false;
		String link = "http://localhost:9000/application/activateAccount?email=" + user.email + "&activationCode=" + hexString;
		//String message = "";
		try {
			sendEmail(user, link);
			user.create();
			render("@sendActivationCode");
			}
		catch (EmailException e) {
			render("@registrationfailed");
		}
	}
	
	private static void sendEmail(User user, String link) throws EmailException {
		HtmlEmail email = new HtmlEmail();
		email.addTo(user.email);
		email.setFrom("seba.group.12@gmail.com","Uni-CarPool");
		email.setSubject("Uni-CarPool: Activation link");
		email.setHtmlMsg("<html> Hello, "+ user.fname+"! <br><br>"+
				 "Please click on the link to activate your account. <br><br>" +
				 " <a href='" + link + "'>"+ link +"</a><br>Thanks<br>Uni-CarPool Team</html>");
		email.setTextMsg("Hello " + user.fname + "! There is some problem with your email client." +
						" However, please follow this link <" + link + "> to activate your account. Thanks");
		Mail.send(email);
	}

	public static void activateAccount(String email, String activationCode) {
		String message = "" ;
		User user = User.UserExistByEmail(email);
		if(user == null) 
			message = "User does not exists. Please register";
		else {
			if(user.isActivated) 
				message = "Account has already been activated.";
			else if(user.activationCode.equals(activationCode)) {
				user.isActivated = true;
				user.activationCode = "";
				message = "Account has been activated.";
				user.save();
				session.put("user", user.username);
			}
			else 
				message = "Invalid activation code.";
		}
		render(message);
		return;
	}
	
}