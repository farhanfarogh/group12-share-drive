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
		new Ride("testing", "Munchen", 1, null, 4, 1, "some comments", null)
				.save();
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

	@Test
	public void registerUser() {
		User user = new User("Test", "User", "TestUser@gmail.com", "password",
				"testUser");
		long userCountBefore = User.count();
		assertNotNull(user);
		user.create();
		assertEquals(userCountBefore + 1, User.count());
	}

	@Test
	public void addRide() {
		Ride newRide = new Ride("TestDriver", "TestSite", 0, null, 2, 1,
				"Not punctual", null);
		long rideCountBefore = Ride.count();
		assertNotNull(newRide);
		newRide.create();
		assertEquals(rideCountBefore + 1, Ride.count());
	}
}
