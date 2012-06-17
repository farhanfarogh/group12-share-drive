package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {
	/*@Before
	static void addDefaults() {
	    renderArgs.put("blogTitle", Play.configuration.getProperty("blog.title"));
	    renderArgs.put("blogBaseline", Play.configuration.getProperty("blog.baseline"));
	}*/
	
	public static void index() {
		Ride frontPost = Ride.find("order by nameOfDriver desc").first();
		List<Ride> olderPosts = Ride.find("order by nameOfDriver desc").from(1)
				.fetch(10);
		render(frontPost, olderPosts);

	}

	public static void sayHello(@Required String myName) {
		if (validation.hasErrors()) {
			flash.error("Oops, please enter your name!");
			index();
		}
		render(myName);
	}

	public static void showRides() {
		Ride frontPost = Ride.find("order by nameOfDriver desc").first();
		List<Ride> olderPosts = Ride.find("order by nameOfDriver desc").from(1)
				.fetch(10);
		render(frontPost, olderPosts);
		render();
	}
	
    
}