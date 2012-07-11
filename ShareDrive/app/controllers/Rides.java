package controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import models.AppModel;
import models.Booking;
import models.Ride;
import models.User;
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
			String startPoint, int destinationCampusId, String datep,
			String timepicker, int numOfSeatsAvailable, int regularity,
			String comments) {
		if (validation.hasErrors()) {
			flash.error("Oops, please enter your name!");
			index();
		}
		Date dp = new Date(datep);
		int hour = Integer.parseInt(timepicker.substring(0, 2));
		int min = Integer.parseInt(timepicker.substring(3, 5));
		dp.setHours(hour);
		dp.setMinutes(min);
		Ride newRide = new Ride(nameOfDriver, startPoint, destinationCampusId,
				null, numOfSeatsAvailable, regularity, comments, dp);

		AppModel unis = new AppModel();
		newRide.create();
		render(newRide, unis);
	}

	public static void showRides() {
		List<Ride> rides = Ride.find("").from(0).fetch();

		AppModel unis = new AppModel();
		render(rides, unis);
	}

	public static void show(Long id) {
		Ride newRide = Ride.findById(id);
		System.out.print(newRide.nameOfDriver + " id: " + id);
		AppModel unis = new AppModel();
		render(newRide, unis);
	}

	public static void showBookingDetails(Long id) {
		Ride newRide = Ride.findById(id);
		AppModel unis = new AppModel();
		render(newRide, unis);
	}

	public static void bookNewRide(Long id) {
		Ride newRide = Ride.findById(id);
		String username = session.get("user");
		System.out.println("user: " + session.get("user"));
		User user = User.find("byUsername", username).first();
		Booking newBooking = new Booking(user, newRide);

		// so that the user doesn't save have multiple instances of same ride
		boolean dontSave = false;
		List<Booking> bookings = Booking.find("byUserId", user.id).fetch();
		for (Booking bok : bookings) {
			if (bok.rideId == id)
				dontSave = true;
		}
		if (!dontSave)
			newBooking.save();
		render(newRide);
	}

	public static void showBookings() {
		String username = session.get("user");
		System.out.println("user: " + session.get("user"));
		User user = User.find("byUsername", username).first();
		List<Booking> bookings = Booking.find("byUserId", user.id).fetch();
		List<Ride> rides= new LinkedList<Ride>();
		Ride tmp;
		for(Booking bok : bookings){
			tmp = Ride.findById(bok.rideId);
			rides.add(tmp);
		}
		
		render(bookings, rides);
	}

	public static void deleteBooking(Long id) {
		Booking booking = Booking.find("byRideId", id).first();
		booking.delete();
		showBookings();
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

					System.out.println("ride date: " + ride.rideDate);
					finalRides.add(ride);
				}
			}

			System.out.println("search: " + startPoint + " " + finalRides);

		}
		List<Ride> finalRides2 = new LinkedList<Ride>();
		rides = finalRides;

		for(Ride rid : finalRides){
			  Calendar cal = Calendar.getInstance();
			if(rid.rideDate.after(cal.getTime()) || rid.rideDate ==null)
				finalRides2.add(rid);
		}
		rides = finalRides2;
		render(rides, unis);
	}
}
