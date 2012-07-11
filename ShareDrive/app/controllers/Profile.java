package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.AppModel;
import models.Booking;
import models.CarInformation;
import models.Timetable;
import models.User;
import play.data.validation.Valid;
import play.mvc.Before;

public class Profile extends Application {
	
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
	
	
	public static void timetable(String acknowledgementMsg){
		
		String username = session.get("user");
		User user = User.find("byUsername", username).first();
		Timetable timetable = Timetable.find("byUser", username).first();
		
		if(timetable != null){
			render(timetable, user, acknowledgementMsg);
		}
		
		else {
			render(user, acknowledgementMsg);
		}
	}
	
	public static void initiateTimetable(String yes, String logout){
		if(yes != null){
			Timetable timetable = new Timetable(session.get("user"), false, false, false, false, false, false, false, false, false, false, false, false, 
					"00:00", "00:00", "00:00", "00:00", "00:00", "00:00", "00:00", "00:00", "00:00", "00:00", "00:00", "00:00");
			
			timetable.create();
			
			timetable("Timetable sucessfully initialized!");
		}
		
		else if(logout != null){
			Application.logout();
		}
	}
	
	public static void saveTimetable(@Valid Timetable timetable, String save, String delete, String logout){		
		String user = session.get("user");
		Timetable myTimetable = Timetable.find("byUser", user).first();
		
		if(save != null){
			if(!isValidDate(timetable.startTimeMonday) || !isValidDate(timetable.startTimeTuesday) || !isValidDate(timetable.startTimeWednesday) 
					|| !isValidDate(timetable.startTimeThursday) || !isValidDate(timetable.startTimeFriday) || !isValidDate(timetable.startTimeSaturday)
					|| !isValidDate(timetable.leaveTimeMonday) || !isValidDate(timetable.leaveTimeTuesday) || !isValidDate(timetable.leaveTimeWednesday) 
					|| !isValidDate(timetable.leaveTimeThursday) || !isValidDate(timetable.leaveTimeFriday) || !isValidDate(timetable.leaveTimeSaturday)){
				timetable("Please enter valid times for every start and leaving time!");
			}
			
			else if(!isLeaveAfterStart(timetable.startTimeMonday, timetable.leaveTimeMonday, timetable.driveMonday) || 
					!isLeaveAfterStart(timetable.startTimeTuesday, timetable.leaveTimeTuesday, timetable.driveTuesday) || 
					!isLeaveAfterStart(timetable.startTimeWednesday, timetable.leaveTimeWednesday, timetable.driveWednesday) || 
					!isLeaveAfterStart(timetable.startTimeThursday, timetable.leaveTimeThursday, timetable.driveThursday) || 
					!isLeaveAfterStart(timetable.startTimeFriday, timetable.leaveTimeFriday, timetable.driveFriday) || 
					!isLeaveAfterStart(timetable.startTimeSaturday, timetable.leaveTimeSaturday, timetable.driveSaturday)){
				timetable("Please make sure that start time is before leaving time!");
			}
			
			else if(!checkCar(timetable.driveMonday, timetable.carMonday) || !checkCar(timetable.driveTuesday, timetable.carTuesday) 
					|| !checkCar(timetable.driveWednesday, timetable.carWednesday) || !checkCar(timetable.driveThursday, timetable.carThursday) 
					|| !checkCar(timetable.driveFriday, timetable.carFriday) || !checkCar(timetable.driveSaturday, timetable.carSaturday)){
				timetable("You shlould also select drive if you select that you have a car!");
			}
			
			else{
				myTimetable.driveMonday = timetable.driveMonday;
				myTimetable.driveTuesday = timetable.driveTuesday;
				myTimetable.driveWednesday = timetable.driveWednesday;
				myTimetable.driveThursday = timetable.driveThursday;
				myTimetable.driveFriday = timetable.driveFriday;
				myTimetable.driveSaturday = timetable.driveSaturday;
				myTimetable.carMonday = timetable.carMonday;
				myTimetable.carTuesday = timetable.carTuesday;
				myTimetable.carWednesday = timetable.carWednesday;
				myTimetable.carThursday = timetable.carThursday;
				myTimetable.carFriday = timetable.carFriday;
				myTimetable.carSaturday = timetable.carSaturday;
				myTimetable.startTimeMonday = timetable.startTimeMonday;
				myTimetable.startTimeTuesday = timetable.startTimeTuesday;
				myTimetable.startTimeWednesday = timetable.startTimeWednesday;
				myTimetable.startTimeThursday = timetable.startTimeThursday;
				myTimetable.startTimeFriday = timetable.startTimeFriday;
				myTimetable.startTimeSaturday = timetable.startTimeSaturday;
				myTimetable.leaveTimeMonday = timetable.leaveTimeMonday;
				myTimetable.leaveTimeTuesday = timetable.leaveTimeTuesday;
				myTimetable.leaveTimeWednesday = timetable.leaveTimeWednesday;
				myTimetable.leaveTimeThursday = timetable.leaveTimeThursday;
				myTimetable.leaveTimeFriday = timetable.leaveTimeFriday;
				myTimetable.leaveTimeSaturday = timetable.leaveTimeSaturday;
				
				myTimetable.save();
				
				timetable("Timetable successfully saved!");			
			}
		}
		
		else if(delete != null){
			myTimetable.delete();
			
			timetable("Timetable deleted!");
		}
		
		else if(logout != null){
			Application.logout();
		}

	}
	
	private static boolean isValidDate(String inDate) {

		if (inDate == null)
		  return false;
		
		//set the format to use as a constructor argument
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		
		if (inDate.trim().length() != dateFormat.toPattern().length()){
			return false;
		}	  
		
		else{
			dateFormat.setLenient(false);
			
			try {
			  //parse the inDate parameter
			  dateFormat.parse(inDate.trim());
			}
			catch (ParseException e) {
			  return false;
			}
			
			return true;
		}
    }
	
	private static boolean isLeaveAfterStart(String startTime, String leaveTime, boolean drive){
		if (drive){
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
			
			try {
				Date start = dateFormat.parse(startTime);
				Date leave = dateFormat.parse(leaveTime);
				
				if(start.before(leave) && leave.after(start)){
					return true;
				}
			} catch (ParseException e) {
				return false;
			}
			
			return false;
		}
		
		else{
			return true;
		}
	}
	
	private static boolean checkCar(boolean drive, boolean car){
		if(!drive && car){
			return false;
		}
		
		else{
			return true;
		}
	}
	
	public static void changeCarInfo(CarInformation carInfo) {
		User user=connected();
		CarInformation carInfoActual = CarInformation.findByUser(user.username);
		System.out.println("Anzahl: " + CarInformation.count());
		System.out.println("CIA:" + carInfoActual);
		if(carInfoActual!=null && (carInfo==null || ((carInfo.car==null || carInfo.car.isEmpty()) && carInfo.ageOfCar==0))){
			carInfoActual.delete();
		}
		else if(carInfoActual==null){
			carInfoActual = new CarInformation(carInfo.ageOfCar, carInfo.car, connected().username);
			carInfo.create();
		}
		else{
			carInfoActual.ageOfCar=carInfo.ageOfCar;
			carInfoActual.car=carInfo.car;
		}
		AppModel unis=new AppModel();
		carInfo=carInfoActual;
		System.out.println("hier CarInfo:" + carInfo);
		renderTemplate("Profile/showCarInfo.html", carInfo, unis);
	}
	
	
	public static void showCarInfo(){
		User user = connected();
		CarInformation carInfo = CarInformation.findByUser(user.username);
		if(carInfo==null){
			carInfo=new CarInformation(0, "", connected().username);
		}
		AppModel unis=new AppModel();
		render(carInfo, unis);
	}
	
	public static void manageAccount(String acknowledgementMsg)
	{
		String username = session.get("user");
		System.out.println("user: "+  session.get("user"));
		User myUserAccount = User.find("byUsername", username).first();

		render(myUserAccount, acknowledgementMsg);
	}
	
    public static void updateAccount(@Valid User user) 
    {
		String username = session.get("user");
		System.out.println("user: "+  session.get("user"));
		User myUserAccount = User.find("byUsername", username).first();

        myUserAccount.aboutMe = user.aboutMe;
        myUserAccount.university = user.university;
        myUserAccount.contactNumber = user.contactNumber;
        myUserAccount.fieldOfStudy = user.fieldOfStudy;
        myUserAccount.save();
		manageAccount("Changes updated successfully");
		return;
    }
    
    public static void changePassword(String acknowledgementMsg)
    {
    	render(acknowledgementMsg);
    }
    
    public static void updatePassword(@Valid User user, String NewPassword, String verifyNewPassword)
    {
		String username = session.get("user");
		System.out.println("user: "+  session.get("user"));
		User myUserAccount = User.find("byUsername", username).first();

		if(user.password != null) {
	        if(user.password.equals(myUserAccount.password) != true) { //this check always gets true even the always are equal
	        	changePassword("Old password is incorrect");
	        	return;
	        }
	        if(NewPassword.equals(verifyNewPassword) != true) { //show form validation error
	        	changePassword("New passowrd doesn't match");
	        	return;
	        }
		}
		else if(NewPassword != null || verifyNewPassword != null) {
			changePassword("Please enter your old passowrd too");
        	return;
		}
        myUserAccount.password = NewPassword;
        myUserAccount.save();
        changePassword("Password changed successfully!");
    }
}
