package models;

import java.util.*;
import java.util.Map;

import javax.persistence.*;

import play.data.validation.Required;
import play.db.jpa.*;

@Entity
/**
 * @author Pawel
 *
 */
public class Ride extends Model {

	// public User driver (will be created during registration, for now
	// @Required(message = "Name of the driver is required")
	public String nameOfDriver;
  
	@Required(message = "Start point is required")
	public String startPoint;

	@Required(message="Destination campus is required")
	public int destinationCampusId;
	public HashMap<Integer, String> destinationCampusMap;

	@Required(message="Time of arrival is required")
	public String timeOfArrival;

	@Required(message="Number of seats")
	public int numOfSeatsAvailable;
	
	public String comments;
	
	/**
	 * 1 - once
	 * 2 - multiple
	 * 3 - weekly
	 */
	public int regularity;
	public HashMap<Integer, String> regularityMap;
	
	@Required
	@Temporal(TemporalType.DATE)
	public Date rideDate;

	public Ride(String nameOfDriver, String startPoint, int destinationCampusId, String timeOfArrival, int numOfSeatsAvailable,
			String comments, Date rideDate) {

		this.nameOfDriver = nameOfDriver;
		this.startPoint = startPoint;
		this.destinationCampusId = destinationCampusId;
		this.timeOfArrival = timeOfArrival;
		this.numOfSeatsAvailable = numOfSeatsAvailable;
		this.comments = comments;
		this.rideDate = rideDate;
		initializeMap();

	}
	public void initializeMap(){
		destinationCampusMap = new HashMap<Integer, String>();
		destinationCampusMap.put(1, "TUM, Garching");
		destinationCampusMap.put(2, "TUM, Stammgelende");
		destinationCampusMap.put(2, "LMU, City center");

		regularityMap = new HashMap<Integer, String>();
		regularityMap.put(1, "once");
		regularityMap.put(1, "multiple");
		regularityMap.put(1, "weekly");
	}
	
	public Ride(String nameOfDriver){
		this.nameOfDriver=nameOfDriver;
	}	
	public Ride(String nameOfDriver, int regularity, int destinationCampusId){
		this.nameOfDriver=nameOfDriver;
		this.regularity = regularity;
		this.destinationCampusId = destinationCampusId;
	}
}
