package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class CarInformation extends Model{
	
	public int ageOfCar;
	public String car;

	public String userna;
	
	public CarInformation(int ageOfCar, String car, String user) {
		super();
		this.ageOfCar = ageOfCar;
		this.car = car;
		this.userna = user;
	}
	
	
	public static CarInformation findByUser(String user) {
        return find("byUserna", user).first();
    }
	
	@Override
	public String toString() {
		return "AgeOfCar: " + this.ageOfCar + "; Car: " + this.car + "; User: " + this.userna;
	}
	

}
