package controllers;

import java.util.List;

import models.AppModel;
import models.Ride;
import play.data.validation.Required;
import play.mvc.Before;

public class Rides extends Application {

	@Before
	static void checkUser() {
		if (connected() == null) {
			flash.error("Please log in first");
			Application.index();
		}
	}

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
		newRide.create();
		render(newRide, unis);
	}

	public static void showRides() {
		List<Ride> rides = Ride.find("order by nameOfDriver desc").from(0)
				.fetch();
		AppModel unis = new AppModel();
		render(rides, unis);
	}
}
