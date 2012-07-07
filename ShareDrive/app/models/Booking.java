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
	
	public Booking(User user, Ride ride) {
		this.userId = user.id;
		this.rideId = ride.id;
		System.out.println(toString());
	}

	public String toString() {
		return "Booking(" + userId + "," + rideId + ")";
	}
}
