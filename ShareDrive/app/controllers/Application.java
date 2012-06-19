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
		render();
	}

	public static void bookRide(@Required String nameOfDriver,
			String startPoint, int destinationCampusId,
			int numOfSeatsAvailable, int regularity, String comments) {
		if (validation.hasErrors()) {
			flash.error("Oops, please enter your name!");
			index();
		}
		Ride newRide = new Ride(nameOfDriver, startPoint, destinationCampusId,
				null, numOfSeatsAvailable, regularity, comments, null);

		AppModel unis = new AppModel();
		newRide.save();
		render(newRide, unis);
	}

	public static void showRides() {
		List<Ride> rides = Ride.find("order by nameOfDriver desc").from(0)
				.fetch();
		// List<Ride> newRide2 = Ride.find("nameOfDriver like ?", "Pawel").fetch();
		// System.out.println(newRide2);
		
		// System.out.println(rides.get(3).regularityMap);
		AppModel unis = new AppModel();
		render(rides, unis);
	}

}