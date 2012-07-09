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

import play.mvc.Http.Response;
import play.test.FunctionalTest;

public class RidesTest extends FunctionalTest{
	
	@Test
    public void testBookRide(){
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("nameOfDriver", "Karl Bubikopf");
    	params.put("startPoint", "München, Arcisstraße");
    	params.put("destinationCampusId", "1");
    	params.put("datep", "2012/07/30");
    	params.put("timepicker", "10:00");
    	params.put("numOfSeatsAvailable", "4");
    	params.put("regularity", "1");
    	params.put("comments", "bla");
    	
    	long numOfRides = Ride.count();
    	Response response = POST("/Rides/bookRide", params, new HashMap<String, File>());
    	assertIsOk(response);
    	Assert.assertEquals(numOfRides+1, Ride.count());
    }
	
	@Test
    public void testBookNewRide(){
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("id", new Long(((Ride)Ride.find("").from(0).first()).getId()).toString());
    	
    	long numOfBooking = Booking.count();
    	Response response = POST("/Rides/bookNewRide", params, new HashMap<String, File>());
    	Ride resultRides = (Ride) renderArgs("newRide");
    	assertIsOk(response);
    	Assert.assertEquals(numOfBooking+1, Booking.count());
    }
	
	@Test
    public void testBookNewRideSameRide(){
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("id", new Long(((Ride)Ride.find("").from(0).first()).getId()).toString());
    	
    	long numOfBooking = Booking.count();
    	
    	Response response = POST("/Rides/bookNewRide", params, new HashMap<String, File>());
    	assertIsOk(response);
    	//Same Booking as in testBookNewRide, so just added one time
    	Assert.assertEquals(numOfBooking, Booking.count());
    }
	
	@Test
    public void testSearchResult(){
		Map<String, String> params = new HashMap<String, String>();
    	params.put("nameOfDriver", "Karl Bubikopf");
    	params.put("startPoint", "Muenchen");
    	params.put("destinationCampusId", "1");
    	params.put("datep", "2012/07/30");
    	params.put("timepicker", "10:00");
    	params.put("numOfSeatsAvailable", "4");
    	params.put("regularity", "1");
    	params.put("comments", "bla");
    	
    	long numOfRides = Ride.count();
    	Response response = POST("/Rides/bookRide", params, new HashMap<String, File>());
    	
    	
    	Map<String, String> params2 = new HashMap<String, String>();
    	params2.put("startPoint", "Muenchen");
    	params2.put("destinationCampusId", "1");
    	
    	Response response2 = POST("/Rides/searchResults", params2, new HashMap<String, File>());
    	
    	List<Ride> resultRides = (List<Ride>) renderArgs("rides");
    	assertEquals(1, resultRides.size());
    	assertIsOk(response2);
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

	    // Login here so the following requests will be authenticated:
	    Response response = POST("/Application/login", loginUserParams);
	}
}
