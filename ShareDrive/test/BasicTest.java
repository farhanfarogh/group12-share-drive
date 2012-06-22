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
		new Ride("testing" , "Munchen", 1,
				null, 4, 1, "some comments", null).save();
	    // Retrieve the ride
	    Ride testRide = Ride.find("nameOfDriver", "testing").first();
	    
	    // Test 
	    assertNotNull(testRide);
	    assertEquals("testing", testRide.nameOfDriver);
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
