package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.*;

@Entity
@Table(name = "booking")
public class Booking extends Model {
    
	@ManyToOne
	public User user;
	
    @ManyToOne
	public Ride ride;
	
	public Booking(User user, Ride ride) {
		this.user = user;
		this.ride = ride;
	}

	public String toString() {
		return "Booking(" + user + "," + ride + ")";
	}
}
