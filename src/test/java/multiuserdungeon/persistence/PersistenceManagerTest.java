package multiuserdungeon.persistence;

import multiuserdungeon.authentication.GameStats;
import multiuserdungeon.authentication.Profile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PersistenceManagerTest {

	@BeforeAll
	public static void setup() {
		Profile profile = new Profile("Bob", "abc123", "I like trains");
		GameStats stats = new GameStats();
		stats.addToGoldEarned(100);
		profile.addToStats(stats);
		PersistenceManager.getInstance().saveProfile(profile);
	}

	@Test
	public void testSingleton() {
		assertNotNull(PersistenceManager.getInstance());
	}

	@Test
	public void testSaveProfile() {
		// Setup
		Profile profile = new Profile("Jack", "Password123", "A really cool person!");

		// Invoke
		String files = PersistenceManager.getInstance().saveProfile(profile);

		// Analyze
		assertEquals("data/Jack.json, data/Jack.xml, data/profiles.csv", files);
	}

	@Test
	public void testLoadProfile() {
		// Setup
		String username = "Bob";

		// Invoke
		Profile profile = PersistenceManager.getInstance().loadProfile(username);

		// Analyze
		assertEquals(username, profile.getUsername());
		assertEquals(100, profile.getStats().get(0).getGoldEarned());
	}

}
