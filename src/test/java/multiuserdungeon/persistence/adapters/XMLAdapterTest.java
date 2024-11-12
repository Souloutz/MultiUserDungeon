package multiuserdungeon.persistence.adapters;

import multiuserdungeon.authentication.Profile;
import multiuserdungeon.persistence.PersistenceManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XMLAdapterTest {

	@BeforeAll
	public static void setup() {
		new XMLAdapter().saveProfile(new Profile("Bob", "abc123", "I like trains"));
	}

	@Test
	public void testSaveProfile() {
		// Setup
		XMLAdapter adapter = new XMLAdapter();
		Profile profile = new Profile("Jack", "Password123", "A really cool person!");

		// Invoke
		String path = adapter.saveProfile(profile);

		// Analyze
		assertEquals(PersistenceManager.DATA_FOLDER + profile.getUsername() + ".xml", path);
	}

	@Test
	public void testLoadProfile() {
		// Setup
		XMLAdapter adapter = new XMLAdapter();
		String username = "Bob";

		// Invoke
		Profile profile = adapter.loadProfile(username);

		// Analyze
		assertEquals(username, profile.getUsername());
	}

}
