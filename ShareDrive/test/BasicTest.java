import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.delete();
		Fixtures.deleteDatabase();
		Fixtures.loadModels("data.yml");
	}

	@Test
	public void createAndRetrieveUser() {
		// Create a new ride
		User driver = (User)User.findAll().get(0);
		new Ride(driver, "Munchen", 1, null, 4, 1, "some comments", null)
				.save();
		// Retrieve the ride
		Ride testRide = Ride.find("nameOfDriver", "testing").first();

		// Test
		assertNotNull(testRide);
		assertEquals(driver, testRide.driver);
	}

	@Test
	public void countDataModelTest() {
		Fixtures.loadModels("data.yml");

		// Count things
		assertEquals(4, Ride.count());

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

}
