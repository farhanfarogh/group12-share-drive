import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {
	
	@Before
    public void setup() {
		Fixtures.delete();
        Fixtures.deleteDatabase();
    }
	
	@Test
	public void createAndRetrieveUser() {
	    // Create a new ride
	    /*new Ride("DriverXYZ", "Munchner Freiheit", "TUM",
				"Garching", "11:00", 5,
				"Sample ride", new Date()).save();
	    // Retrieve the ride
	    Ride testRide = Ride.find("nameOfDriver", "DriverXYZ").first();
	    
	    // Test 
	    assertNotNull(testRide);
	    assertEquals("DriverXYZ", testRide.nameOfDriver);*/
	}
	
	@Test
	public void fullTest() {
	    Fixtures.loadModels("data.yml");
	 
	    // Count things
	    assertEquals(4, Ride.count());
		 
	    Ride testRide = Ride.find("nameOfDriver", "abc").first();
	    assertNotNull(testRide);
	    assertEquals("abc", testRide.nameOfDriver);
}

}
