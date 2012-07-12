package controllers;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import models.Booking;
import models.Ride;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.data.validation.Required;
import play.mvc.Http.Response;
import play.test.FunctionalTest;

public class RidesTest extends FunctionalTest{
	
	@Test
    public void testBookRide(){
		
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("nameOfDriver", ((User)User.findAll().get(0)).username);
    	params.put("startPoint", "Muenchen, Arcisstraße");
    	params.put("destinationCampusId", "1");
    	params.put("datep", "2012/07/30");
    	params.put("timepicker", "10:00");
    	params.put("numOfSeatsAvailable", "4");
    	params.put("regularity", "1");
    	params.put("comments", "bla");
    	params.put("offer", "a");
    	params.put("search", "b");
    	params.put("logout", "c");
    	
    	long numOfRides = Ride.count();
    	Response response = POST("/Rides/bookRide", params, new HashMap<String, File>());
    	assertIsOk(response);
    	Assert.assertEquals(numOfRides+1, Ride.count());
    }
	
	@Test
    public void testBookNewRide(){
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("id", new Long(((Ride)Ride.find("").from(0).first()).getId()).toString());
    	params.put("book", "b");
    	params.put("back", "c");
    	long numOfBooking = Booking.count();
    	Response response = POST("/Rides/bookNewRide", params, new HashMap<String, File>());
    	assertIsOk(response);
    	Assert.assertEquals(numOfBooking+1, Booking.count());
    }
	
	@Test
    public void testBookNewRideSameRide(){
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("id", new Long(((Ride)Ride.find("").from(0).first()).getId()).toString());
    	params.put("book", "b");
    	params.put("back", "c");
    	long numOfBooking = Booking.count();
    	
    	Response response = POST("/Rides/bookNewRide", params, new HashMap<String, File>());
    	assertIsOk(response);
    	//Same Booking as in testBookNewRide, so just added one time
    	Assert.assertEquals(numOfBooking, Booking.count());
    }
	
	@Test
    public void testSearchResult(){
		Map<String, String> params = new HashMap<String, String>();
    	params.put("nameOfDriver", ((User)User.findAll().get(0)).username);
    	params.put("startPoint", "Muenchen, Arcisstrasse");
    	params.put("destinationCampusId", "1");
    	params.put("datep", "2012/07/30");
    	params.put("timepicker", "10:00");
    	params.put("numOfSeatsAvailable", "4");
    	params.put("regularity", "1");
    	params.put("comments", "bla");
    	params.put("offer", "a");
    	params.put("search", "b");
    	params.put("logout", "c");
    	POST("/Rides/bookRide", params, new HashMap<String, File>());
    	
		
    	Map<String, String> params2 = new HashMap<String, String>();
    	params2.put("startPoint", "Muenchen, Arcisstrasse");
    	params2.put("destinationCampusId", "1");
    	params2.put("regularity", "1");
    	params2.put("search", "a");
    	params2.put("logout", "b");
    	
    	
    	Response response2 = POST("/Rides/searchResults", params2, new HashMap<String, File>());
    	
    	List<Ride> resultRides = (List<Ride>) renderArgs("rides");
    	assertIsOk(response2);
    	assertEquals(0, resultRides.size());
    }
	
	@Test
    public void testShowRides(){
    	Map<String, String> params = new HashMap<String, String>();
    	Response response = POST("/Rides/showRides", params, new HashMap<String, File>());
    	assertIsOk(response);
    }
	
	@Before
	public void loginTest(){
		User u=new User("Testuser", "Testuser", "t@tum.de", "test", "test");
		u.create();
		Map<String, String> loginUserParams = new HashMap<String, String>(); 
		loginUserParams.put("username", "test"); 
	    loginUserParams.put("password", "test");
	    loginUserParams.put("login", "test"); 
	    loginUserParams.put("register", "test");

	    // Login here so the following requests will be authenticated:
	    POST("/Application/login", loginUserParams);
	}
}
