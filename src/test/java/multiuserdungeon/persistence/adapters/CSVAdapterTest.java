package multiuserdungeon.persistence.adapters;

import multiuserdungeon.authentication.GameStats;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.persistence.PersistenceManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVAdapterTest {

	@BeforeAll
	public static void setup() {
		Profile profile = new Profile("Bob", "abc123", "I like trains");
		GameStats stats = new GameStats();
		stats.addToGoldEarned(100);
		profile.addToStats(stats);
		new CSVAdapter().saveProfile(profile);
	}

	@Test
	public void testSaveProfile() {
		// Setup
		CSVAdapter adapter = new CSVAdapter();
		Profile profile = new Profile("Jack", "Password123", "A really cool person!");

		// Invoke
		String path = adapter.saveProfile(profile);

		// Analyze
		assertEquals(PersistenceManager.DATA_FOLDER + "profiles.csv", path);
	}

	@Test
	public void testSaveProfileIdempotent() {
		// Setup
		CSVAdapter adapter = new CSVAdapter();
		Profile profile = new Profile("Jack", "Password123", "A really cool person!");
		Profile profile2 = new Profile("Rick", "xyz987", "Another cool person!");

		// Invoke
		String path = adapter.saveProfile(profile);
		String path2 = adapter.saveProfile(profile2);

		// Analyze
		assertEquals(PersistenceManager.DATA_FOLDER + "profiles.csv", path);
		assertEquals(PersistenceManager.DATA_FOLDER + "profiles.csv", path2);
	}

	@Test
	public void testLoadProfile() {
		// Setup
		CSVAdapter adapter = new CSVAdapter();
		String username = "Bob";

		// Invoke
		Profile profile = adapter.loadProfile(username);

		// Analyze
		assertEquals(username, profile.getUsername());
		assertEquals(100, profile.getStats().get(0).getGoldEarned());
	}

}
