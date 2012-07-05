package models;

import java.util.*;

import javax.persistence.*;

import play.data.validation.Required;
import play.db.jpa.*;

@Entity
@Table(name = "ride")
public class Ride extends Model {

	// public User driver (will be created during registration, for now
	// @Required(message = "Name of the driver is required")
	public String nameOfDriver;

	@Required(message = "Start point is required")
	public String startPoint;

	@Required(message = "Destination campus is required")
	public int destinationCampusId;
	public HashMap<Integer, String> destinationCampusMap;

	@Required(message = "Time of arrival is required")
	public String timeOfArrival;

	@Required(message = "Number of seats")
	public int numOfSeatsAvailable;

	public String comments;

	/**
	 * 0 - once 1 - multiple 2 - weekly
	 */
	public int regularity;

	@Required
	@Temporal(TemporalType.DATE)
	public Date rideDate;
	
	public Ride(String nameOfDriver, String startPoint,
			int destinationCampusId, String timeOfArrival,
			int numOfSeatsAvailable, int regularity, String comments,
			Date rideDate) {
		this.regularity = regularity;
		this.nameOfDriver = nameOfDriver;
		this.startPoint = startPoint;
		this.destinationCampusId = destinationCampusId;
		this.timeOfArrival = timeOfArrival;
		this.numOfSeatsAvailable = numOfSeatsAvailable;
		this.comments = comments;
		this.rideDate = rideDate;

	}

	public Ride(String nameOfDriver) {
		this.nameOfDriver = nameOfDriver;
	}

	public Ride(String nameOfDriver, int regularity, int destinationCampusId) {
		this.nameOfDriver = nameOfDriver;
		this.regularity = regularity;
		this.destinationCampusId = destinationCampusId;
	}
}
