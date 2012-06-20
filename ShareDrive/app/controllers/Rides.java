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
	
//	Author: Dragan
//	This is the function for the search but it is not linked yet we have to discuss this thing i need some help or advice from somebody
// I think that I am doing everything exactly like in the HotelBooking app but it doestn work
	public static void list(String search, Integer size, Integer page) {
        List<Ride> rides = null;
        page = page != null ? page : 1;
        if(search.trim().length() == 0) {
            rides = Ride.all().fetch(page, size);
        } else {
            search = search.toLowerCase();
            rides = Ride.find("lower(nameOfDriver) like ? ", search).fetch(page, size);
        
        }
        render(rides, search, size, page);
    }

	public static void showRides() {
		List<Ride> rides = Ride.find("order by nameOfDriver desc").from(0)
				.fetch();
		AppModel unis = new AppModel();
		render(rides, unis);
	}
}
