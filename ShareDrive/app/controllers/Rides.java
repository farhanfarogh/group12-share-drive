package controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
	
	//this is bookRIde

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

	public static void search() {
		render();
	}

	public static void searchResults(String startPoint,
			String destinationCampusId) {
		AppModel unis = new AppModel();
		List<Ride> rides = new LinkedList<Ride>();
		List<Ride> finalRides = new LinkedList<Ride>();
		if (destinationCampusId.equals("")) {
			finalRides = Ride.find("startPoint like ? ", startPoint).from(0)
					.fetch();
		} else if (startPoint.equals("")) {
			Set<Integer> unisId = unis.destinationCampusMap.keySet();
			int destinationCampusIndex = -1;
			for (Integer uni : unisId) {
				if (unis.destinationCampusMap.get(uni).contains(
						destinationCampusId)) {
					destinationCampusIndex = uni;
					break;
				}
			}
			finalRides = Ride
					.find("destinationCampusId like ? ", destinationCampusIndex)
					.from(0).fetch();
		} else {
			rides = Ride.find("startPoint like ? ", startPoint).from(0).fetch();
			Set<Integer> unisId = unis.destinationCampusMap.keySet();
			int destinationCampusIndex = -1;
			for (Integer uni : unisId) {
				if (unis.destinationCampusMap.get(uni).contains(
						destinationCampusId)) {
					destinationCampusIndex = uni;
					break;
				}
			}
			for (Ride ride : rides) {
				System.out.println("destinationCampusIndex: "
						+ destinationCampusIndex
						+ " ride.destinationCampusId: "
						+ ride.destinationCampusId);
				if (ride.destinationCampusId == destinationCampusIndex) {
					finalRides.add(ride);
				}
			}

			System.out.println("search: " + startPoint + " " + finalRides);

		}

		rides = finalRides;
		render(rides, unis);
	}
}
