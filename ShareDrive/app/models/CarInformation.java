package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class CarInformation extends Model{
	
	public int ageOfCar;
	public String car;
	public int maxNumberOfSeats;
	public boolean smoker;

	@OneToOne
	public User user;
	
	
	
	public CarInformation(int ageOfCar, String car, int maxNumberOfSeats,
			boolean smoker, User user) {
		super();
		this.ageOfCar = ageOfCar;
		this.car = car;
		this.maxNumberOfSeats = maxNumberOfSeats;
		this.smoker = smoker;
		this.user = user;
	}

	
	
	public static CarInformation findByUser(User user) {
        return find("byUser", user).first();
    }
	
	@Override
	public String toString() {
		return "AgeOfCar: " + this.ageOfCar + "; Car: " + this.car + "; User: " + this.user;
	}
	

}
