package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {
	/*
	 * @Before static void addDefaults() { renderArgs.put("blogTitle",
	 * Play.configuration.getProperty("blog.title"));
	 * renderArgs.put("blogBaseline",
	 * Play.configuration.getProperty("blog.baseline")); }
	 */

	public static void index() {
		Ride frontPost = Ride.find("order by nameOfDriver desc").first();
		List<Ride> olderPosts = Ride.find("order by nameOfDriver desc").from(1)
				.fetch(10);
		render(frontPost, olderPosts);

	}

	public static void bookRide(@Required String nameOfDriver,
			String startPoint, int destinationCampusId,
			int numOfSeatsAvailable, int regularity, String comments) {
		if (validation.hasErrors()) {
			flash.error("Oops, please enter your name!");
			index();
		}
		Ride newRide = new Ride(nameOfDriver, startPoint, destinationCampusId, null, numOfSeatsAvailable, comments, null);
		newRide.save();

		render(newRide);
	}

	public static void showRides() {
		Ride frontPost = Ride.find("order by nameOfDriver desc").first();
		List<Ride> olderPosts = Ride.find("order by nameOfDriver desc").from(1)
				.fetch(10);
		List<Ride> rides = Ride.find("order by nameOfDriver desc").from(1)
				.fetch();
		render(frontPost, olderPosts, rides);
	}

}