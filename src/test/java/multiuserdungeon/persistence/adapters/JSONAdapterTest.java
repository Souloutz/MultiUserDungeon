package multiuserdungeon.persistence.adapters;

import multiuserdungeon.authentication.Profile;
import multiuserdungeon.persistence.PersistenceManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSONAdapterTest {

	@BeforeAll
	public static void setup() {
		new JSONAdapter().saveProfile(new Profile("Bob", "abc123", "I like trains"));
	}

	@Test
	public void testSaveProfile() {
		// Setup
		JSONAdapter adapter = new JSONAdapter();
		Profile profile = new Profile("Jack", "Password123", "A really cool person!");

		// Invoke
		String path = adapter.saveProfile(profile);

		// Analyze
		assertEquals(PersistenceManager.DATA_FOLDER + profile.getUsername() + ".json", path);
	}

	@Test
	public void testLoadProfile() {
		// Setup
		JSONAdapter adapter = new JSONAdapter();
		String username = "Bob";

		// Invoke
		Profile profile = adapter.loadProfile(username);

		// Analyze
		assertEquals(username, profile.getUsername());
	}

}
