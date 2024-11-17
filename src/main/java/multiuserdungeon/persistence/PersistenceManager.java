package multiuserdungeon.persistence;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.persistence.adapters.CSVAdapter;
import multiuserdungeon.persistence.adapters.XMLAdapter;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class PersistenceManager {

	public static final String DATA_FOLDER = "data/";

	private static PersistenceManager INSTANCE = null;
	private final List<FileAdapter> adapters;

	public PersistenceManager() {
		this.adapters = new LinkedList<>(List.of(new JSONAdapter(), new XMLAdapter(), new CSVAdapter()));
		File dataFolder = new File(DATA_FOLDER);
		if(!dataFolder.exists()) {
			if(!dataFolder.mkdirs()) {
				System.out.println("Failed to create data folder!");
			}
		}
	}

	public static PersistenceManager getInstance() {
		if(INSTANCE == null) INSTANCE = new PersistenceManager();
		return INSTANCE;
	}

	public String saveGame(Game game) {
		List<String> files = new LinkedList<>();
		for(FileAdapter adapter : this.adapters) {
			String uri = adapter.saveGame(game);
			if(uri != null) files.add(uri);
		}
		return String.join(", ", files);
	}

	public Game loadGame(String filename) {
		for(FileAdapter adapter : this.adapters) {
			Game game = adapter.loadGame(filename);
			if(game != null) return game;
		}
		return null;
	}

	public String saveProfile(Profile profile) {
		List<String> files = new LinkedList<>();
		for(FileAdapter adapter : this.adapters) {
			String uri = adapter.saveProfile(profile);
			if(uri != null) files.add(uri);
		}
		return String.join(", ", files);
	}

	public Profile loadProfile(String username) {
		for(FileAdapter adapter : this.adapters) {
			Profile profile = adapter.loadProfile(username);
			if(profile != null) return profile;
		}
		return null;
	}

}
