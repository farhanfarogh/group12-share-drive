package controllers;

import models.AppModel;
import models.CarInformation;
import models.Timetable;
import models.User;
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
	
	
	public static void timetable(){
		
		String user = session.get("user");
		Timetable timetable = Timetable.find("byUser", user).first();
		
		if(timetable != null){
			render(timetable);
		}
		
		else {
			render();
		}
	}
	
	public static void initiateTimetable(){
		Timetable timetable = new Timetable(false, false, false, false, false, false, false, false, false, false, false, false, 
				session.get("user"), "00:00", "00:00", "00:00", "00:00", "00:00", "00:00", "00:00", "00:00", "00:00", "00:00", "00:00", "00:00");
		
		timetable.create();
		
		timetable();
	}
	
	@SuppressWarnings("deprecation")
	public static void saveTimetable(Timetable timetable){
		timetable.edit("timetable", params.all());
		
		timetable.save();
		
		timetable();
	}	
	
	public static void changeCarInfo(CarInformation carInfo) {
		User user=connected();
		CarInformation carInfoActual = CarInformation.findByUser(user);
		System.out.println("Anzahl: " + CarInformation.count());
		System.out.println("CIA:" + carInfoActual);
		if(carInfoActual!=null && (carInfo==null || ((carInfo.car==null || carInfo.car.isEmpty()) && carInfo.ageOfCar==0))){
			carInfoActual.delete();
		}
		else if(carInfoActual==null){
			carInfoActual = new CarInformation(carInfo.ageOfCar, carInfo.car, connected());
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
		CarInformation carInfo = CarInformation.findByUser(user);
		if(carInfo==null){
			carInfo=new CarInformation(0, "", connected());
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
