package multiuserdungeon.persistence.adapters;

import multiuserdungeon.authentication.GameStats;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.persistence.PersistenceManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XMLAdapterTest {

	@BeforeAll
	public static void setup() {
		Profile profile = new Profile("Bob", "abc123", "I like trains");
		GameStats stats = new GameStats();
		stats.addToGold(100);
		profile.addToStats(stats);
		new XMLAdapter().saveProfile(profile);
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
		assertEquals(100, profile.getStats().get(0).getTotalGold());
	}

}
