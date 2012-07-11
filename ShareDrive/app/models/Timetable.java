package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Timetable extends Model {
	
	public boolean driveMonday, driveTuesday, driveWednesday, driveThursday, driveFriday, driveSaturday;
	
	public boolean carMonday, carTuesday, carWednesday, carThursday, carFriday, carSaturday;
	
	@OneToOne
	public User user;
	
	public String startTimeMonday, startTimeTuesday, startTimeWednesday, startTimeThursday, startTimeFriday, startTimeSaturday;
	
	public String leaveTimeMonday, leaveTimeTuesday, leaveTimeWednesday, leaveTimeThursday, leaveTimeFriday, leaveTimeSaturday;

	public Timetable(User user, boolean driveMonday, boolean driveTuesday,
			boolean driveWednesday, boolean driveThursday, boolean driveFriday,
			boolean driveSaturday, boolean carMonday, boolean carTuesday,
			boolean carWednesday, boolean carThursday, boolean carFriday,
			boolean carSaturday, String startTimeMonday, String startTimeTuesday,
			String startTimeWednesday, String startTimeThursday,
			String startTimeFriday, String startTimeSaturday,
			String leaveTimeMonday, String leaveTimeTuesday,
			String leaveTimeWednesday, String leaveTimeThursday,
			String leaveTimeFriday, String leaveTimeSaturday) {
		this.user = user;
		this.driveMonday = driveMonday;
		this.driveTuesday = driveTuesday;
		this.driveWednesday = driveWednesday;
		this.driveThursday = driveThursday;
		this.driveFriday = driveFriday;
		this.driveSaturday = driveSaturday;
		this.carMonday = carMonday;
		this.carTuesday = carTuesday;
		this.carWednesday = carWednesday;
		this.carThursday = carThursday;
		this.carFriday = carFriday;
		this.carSaturday = carSaturday;
		this.startTimeMonday = startTimeMonday;
		this.startTimeTuesday = startTimeTuesday;
		this.startTimeWednesday = startTimeWednesday;
		this.startTimeThursday = startTimeThursday;
		this.startTimeFriday = startTimeFriday;
		this.startTimeSaturday = startTimeSaturday;
		this.leaveTimeMonday = leaveTimeMonday;
		this.leaveTimeTuesday = leaveTimeTuesday;
		this.leaveTimeWednesday = leaveTimeWednesday;
		this.leaveTimeThursday = leaveTimeThursday;
		this.leaveTimeFriday = leaveTimeFriday;
		this.leaveTimeSaturday = leaveTimeSaturday;
	}
	
	
}
