package models;

import java.util.*;

import javax.persistence.*;

import play.data.validation.Required;
import play.db.jpa.*;

@Entity
@Table(name = "ride")
public class Ride extends Model {

	@Required(message = "User can not be found")
	
	@ManyToOne
	public User driver;

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
	
	public Ride(User driver, String startPoint,
			int destinationCampusId, String timeOfArrival,
			int numOfSeatsAvailable, int regularity, String comments,
			Date rideDate) {
		this.regularity = regularity;
		this.driver = driver;
		this.startPoint = startPoint;
		this.destinationCampusId = destinationCampusId;
		this.timeOfArrival = timeOfArrival;
		this.numOfSeatsAvailable = numOfSeatsAvailable;
		this.comments = comments;
		this.rideDate = rideDate;

	}

	public Ride(User driver) {
		this.driver = driver;
	}

	public Ride(User driver, int regularity, int destinationCampusId) {
		this.driver = driver;
		this.regularity = regularity;
		this.destinationCampusId = destinationCampusId;
	}
}
