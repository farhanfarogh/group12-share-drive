package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import models.AppModel;
import models.Booking;
import models.Ride;
import models.Timetable;
import models.User;
import play.data.validation.Required;
import play.db.jpa.JPABase;
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
		String username = session.get("user");
		render(username);
	}

	public static void bookRide(@Required String nameOfDriver,
			String startPoint, int destinationCampusId, String datep,
			String timepicker, int numOfSeatsAvailable, int regularity,
			String comments, String offer, String search, String logout) {
		if(offer != null){
			if (validation.hasErrors()) {
				flash.error("Oops, please enter your name!");
				index();
			}
			
			User user = User.findByUsername(nameOfDriver);
			if(user==null){
				flash.error("Driver not found.");
				return;
			}
			
			if(regularity == 2){
				Timetable timetable = Timetable.find("byUser", user).first();
				
				if(timetable == null){
					flash.error("You have to deposite a timetable if you want to create a weekly drive!");
					index();
				}
				
				else{
					Ride newRide = new Ride(user, startPoint, destinationCampusId, null, numOfSeatsAvailable, regularity, comments, null);
					AppModel unis = new AppModel();
					newRide.create();
					render(newRide, unis);
				}
			}
			
			else{
				Date dp = new Date(datep);
				int hour = Integer.parseInt(timepicker.substring(0, 2));
				int min = Integer.parseInt(timepicker.substring(3, 5));
				dp.setHours(hour);
				dp.setMinutes(min);
				Ride newRide = new Ride(user, startPoint, destinationCampusId,
						null, numOfSeatsAvailable, regularity, comments, dp);
				
				AppModel unis = new AppModel();
				newRide.create();
				render(newRide, unis);
			}
		}
		
		else if(search != null){
			search();
		}
		
		else if(logout!= null){
			Application.logout();
		}
	}

	public static void showRides() {
		List<Ride> rides = Ride.find("").from(0).fetch();

		AppModel unis = new AppModel();
		render(rides, unis);
	}

	public static void show(Long id) {
		Ride newRide = Ride.findById(id);
		AppModel unis = new AppModel();
		render(newRide, unis);
	}

	public static void showBookingDetails(Long id) {
		Ride newRide = Ride.findById(id);
		AppModel unis = new AppModel();
		render(newRide, unis);
	}

	public static void bookNewRide(Long id, String book, String back) {
		if(book != null){
			Ride newRide = Ride.findById(id);
			User user = connected();
			Booking newBooking = new Booking(user, newRide);

			// so that the user doesn't save have multiple instances of same ride
			boolean dontSave = false;
			List<Booking> bookings = Booking.find("byUser", user).fetch();
			for (Booking bok : bookings) {
				if (bok.ride.equals(newRide))
					dontSave = true;
			}
			if (!dontSave)
				newBooking.save();
			render(newRide);
		}
		
		else if(back != null){
			showRides();
		}
	}

	public static void showBookings() {
		User user = connected();
		List<Booking> bookings = Booking.find("byUser", user).fetch();
		List<Ride> rides= new LinkedList<Ride>();
		Ride tmp;
		for(Booking bok : bookings){
			tmp = bok.ride;
			rides.add(tmp);
		}
		
		render(bookings, rides);
	}

	public static void deleteBooking(Long id) {
		Ride ride = Ride.findById(id);
		Booking booking = Booking.find("byRide", ride).first();
		booking.delete();
		showBookings();
	}

	public static void search() {
		render();
	}

	public static void searchResults(String startPoint,
			String destinationCampusId, int regularity, String search, String logout) throws ParseException {
		if(search != null){
			AppModel unis = new AppModel();
			List<Ride> rides = new LinkedList<Ride>();
			List<Ride> finalRides = new LinkedList<Ride>();
			
			if (destinationCampusId.equals("")) {
				finalRides = Ride.find("startPoint like ? ", startPoint).from(0)
						.fetch();
			} 
			
			else if (startPoint.equals("")) {
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
			} 
			
			else {
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
			
			if (regularity < 2){
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
			
			else if (regularity == 2){
				List<Ride> weeklyRides = new LinkedList<Ride>();
				List<Ride> finalRides2 = new LinkedList<Ride>();
				int numberOfMatches = 0;
				User user = connected();
				Timetable userTimetable = Timetable.find("byUser", user).first();
				
				if(userTimetable == null){
					flash.error("You have to deposite a timetable if you want to search a weekly drive!");
					search();
				}
				
				else{
					for (Ride ride : finalRides){
						if(ride.regularity == 2){
							weeklyRides.add(ride);
						}
					}
					
					for (Ride ride : weeklyRides){
						Timetable timetable = Timetable.find("byUser", ride.driver).first();
						
						numberOfMatches = checkTimetable(userTimetable, timetable);
						
						if(numberOfMatches >= 1){
							finalRides2.add(ride);
						}
					}
					
					rides = finalRides2;
					
					render(rides, unis, numberOfMatches);
				}
			}
		}
		
		else if(logout != null){
			Application.logout();
		}
	}
	
	private static int checkTimetable(Timetable userTimetable, Timetable timetable) throws ParseException{
		int numberOfMatches = 0;
		
		if(userTimetable.driveMonday == true && timetable.carMonday == true){
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
			
			Date startUser = dateFormat.parse(userTimetable.startTimeMonday);
			Date leaveUser = dateFormat.parse(userTimetable.leaveTimeMonday);
			Date startRide = dateFormat.parse(timetable.startTimeMonday);
			Date leaveRide = dateFormat.parse(timetable.leaveTimeMonday);
			Long tmp1 = startRide.getTime()+(31*60*1000);
			Date tmp2 = new Date(tmp1);
			String tmp3 = dateFormat.format(tmp2);
			Date startRide2 = dateFormat.parse(tmp3);
			Long tmp4 = leaveRide.getTime()-(31*60*1000);
			Date tmp5 = new Date(tmp4);
			String tmp6 = dateFormat.format(tmp5);
			Date leaveRide2 = dateFormat.parse(tmp6);
				
			if(startUser.equals(startRide) || (startUser.after(startRide) && startUser.before(startRide2))){
				if(leaveUser.equals(leaveRide) || (leaveUser.before(leaveRide) && leaveUser.after(leaveRide2))){
					numberOfMatches++;
				}
			}
		}
		
		if(userTimetable.driveTuesday == true && timetable.carTuesday == true){
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
			
			Date startUser = dateFormat.parse(userTimetable.startTimeMonday);
			Date leaveUser = dateFormat.parse(userTimetable.leaveTimeMonday);
			Date startRide = dateFormat.parse(timetable.startTimeMonday);
			Date leaveRide = dateFormat.parse(timetable.leaveTimeMonday);
			Long tmp1 = startRide.getTime()+(31*60*1000);
			Date tmp2 = new Date(tmp1);
			String tmp3 = dateFormat.format(tmp2);
			Date startRide2 = dateFormat.parse(tmp3);
			Long tmp4 = leaveRide.getTime()-(31*60*1000);
			Date tmp5 = new Date(tmp4);
			String tmp6 = dateFormat.format(tmp5);
			Date leaveRide2 = dateFormat.parse(tmp6);
				
			if(startUser.equals(startRide) || (startUser.after(startRide) && startUser.before(startRide2))){
				if(leaveUser.equals(leaveRide) || (leaveUser.before(leaveRide) && leaveUser.after(leaveRide2))){
					numberOfMatches++;
				}
			}
		}
		
		if(userTimetable.driveWednesday == true && timetable.carWednesday == true){
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
			
			Date startUser = dateFormat.parse(userTimetable.startTimeMonday);
			Date leaveUser = dateFormat.parse(userTimetable.leaveTimeMonday);
			Date startRide = dateFormat.parse(timetable.startTimeMonday);
			Date leaveRide = dateFormat.parse(timetable.leaveTimeMonday);
			Long tmp1 = startRide.getTime()+(31*60*1000);
			Date tmp2 = new Date(tmp1);
			String tmp3 = dateFormat.format(tmp2);
			Date startRide2 = dateFormat.parse(tmp3);
			Long tmp4 = leaveRide.getTime()-(31*60*1000);
			Date tmp5 = new Date(tmp4);
			String tmp6 = dateFormat.format(tmp5);
			Date leaveRide2 = dateFormat.parse(tmp6);
				
			if(startUser.equals(startRide) || (startUser.after(startRide) && startUser.before(startRide2))){
				if(leaveUser.equals(leaveRide) || (leaveUser.before(leaveRide) && leaveUser.after(leaveRide2))){
					numberOfMatches++;
				}
			}
		}
		
		if(userTimetable.driveThursday == true && timetable.carThursday == true){
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
			
			Date startUser = dateFormat.parse(userTimetable.startTimeMonday);
			Date leaveUser = dateFormat.parse(userTimetable.leaveTimeMonday);
			Date startRide = dateFormat.parse(timetable.startTimeMonday);
			Date leaveRide = dateFormat.parse(timetable.leaveTimeMonday);
			Long tmp1 = startRide.getTime()+(31*60*1000);
			Date tmp2 = new Date(tmp1);
			String tmp3 = dateFormat.format(tmp2);
			Date startRide2 = dateFormat.parse(tmp3);
			Long tmp4 = leaveRide.getTime()-(31*60*1000);
			Date tmp5 = new Date(tmp4);
			String tmp6 = dateFormat.format(tmp5);
			Date leaveRide2 = dateFormat.parse(tmp6);
				
			if(startUser.equals(startRide) || (startUser.after(startRide) && startUser.before(startRide2))){
				if(leaveUser.equals(leaveRide) || (leaveUser.before(leaveRide) && leaveUser.after(leaveRide2))){
					numberOfMatches++;
				}
			}
		}
		
		if(userTimetable.driveFriday == true && timetable.carFriday == true){
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
			
			Date startUser = dateFormat.parse(userTimetable.startTimeMonday);
			Date leaveUser = dateFormat.parse(userTimetable.leaveTimeMonday);
			Date startRide = dateFormat.parse(timetable.startTimeMonday);
			Date leaveRide = dateFormat.parse(timetable.leaveTimeMonday);
			Long tmp1 = startRide.getTime()+(31*60*1000);
			Date tmp2 = new Date(tmp1);
			String tmp3 = dateFormat.format(tmp2);
			Date startRide2 = dateFormat.parse(tmp3);
			Long tmp4 = leaveRide.getTime()-(31*60*1000);
			Date tmp5 = new Date(tmp4);
			String tmp6 = dateFormat.format(tmp5);
			Date leaveRide2 = dateFormat.parse(tmp6);
				
			if(startUser.equals(startRide) || (startUser.after(startRide) && startUser.before(startRide2))){
				if(leaveUser.equals(leaveRide) || (leaveUser.before(leaveRide) && leaveUser.after(leaveRide2))){
					numberOfMatches++;
				}
			}
		}
		
		if(userTimetable.driveSaturday == true && timetable.carSaturday == true){
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
			
			Date startUser = dateFormat.parse(userTimetable.startTimeMonday);
			Date leaveUser = dateFormat.parse(userTimetable.leaveTimeMonday);
			Date startRide = dateFormat.parse(timetable.startTimeMonday);
			Date leaveRide = dateFormat.parse(timetable.leaveTimeMonday);
			Long tmp1 = startRide.getTime()+(31*60*1000);
			Date tmp2 = new Date(tmp1);
			String tmp3 = dateFormat.format(tmp2);
			Date startRide2 = dateFormat.parse(tmp3);
			Long tmp4 = leaveRide.getTime()-(31*60*1000);
			Date tmp5 = new Date(tmp4);
			String tmp6 = dateFormat.format(tmp5);
			Date leaveRide2 = dateFormat.parse(tmp6);
				
			if(startUser.equals(startRide) || (startUser.after(startRide) && startUser.before(startRide2))){
				if(leaveUser.equals(leaveRide) || (leaveUser.before(leaveRide) && leaveUser.after(leaveRide2))){
					numberOfMatches++;
				}
			}
		}
		
		return numberOfMatches;
	}
}
