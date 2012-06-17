package models;

import java.util.*;

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

	@Required(message="Destination university is required")
	public String destinationUniveristy;
	
	@Required(message="Destination campus is required")
	public String destinationCampus;

	@Required(message="Time of arrival is required")
	public String timeOfArrival;

	@Required(message="Number of seats")
	public int numOfSeatsAvailable;
	
	public String comments;

	@Required
	@Temporal(TemporalType.DATE)
	public Date rideDate;

	public Ride(String nameOfDriver, String startPoint, String destinationUniveristy,
			String destinationCampus, String timeOfArrival, int numOfSeatsAvailable,
			String comments, Date rideDate) {

		this.nameOfDriver = nameOfDriver;
		this.startPoint = startPoint;
		this.destinationUniveristy = destinationUniveristy;
		this.destinationCampus = destinationCampus;
		this.timeOfArrival = timeOfArrival;
		this.numOfSeatsAvailable = numOfSeatsAvailable;
		this.comments = comments;
		this.rideDate = rideDate;
	}
		
	public Ride(String nameOfDriver){
		this.nameOfDriver=nameOfDriver;
	}
}
