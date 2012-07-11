package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.*;

@Entity
@Table(name = "booking")
public class Booking extends Model {
    public Long userId;
	
	public Long rideId;

	public String startPoint;
	
	public String destination;
	
	public Date date;
	
	public Booking(User user, Ride ride) {
		this.userId = user.id;
		this.rideId = ride.id;
		AppModel unis = new AppModel();
		this.destination = unis.destinationCampusMap.get(ride.destinationCampusId);
		this.startPoint = ride.startPoint;
		this.date = ride.rideDate;
		System.out.println(toString());
	}

	public String toString() {
		return "Booking(" + userId + "," + rideId + "," + startPoint +","+ destination + "," + date + ")";
	}
}
