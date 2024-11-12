package multiuserdungeon.persistence;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.persistence.adapters.CSVAdapter;
import multiuserdungeon.persistence.adapters.JSONAdapter;
import multiuserdungeon.persistence.adapters.XMLAdapter;

import java.util.ArrayList;
import java.util.List;

public class PersistenceManager {

	public static final String DATA_FOLDER = "data/";

	private static PersistenceManager INSTANCE = null;
	private final List<FileAdapter> adapters;

	public PersistenceManager() {
		this.adapters = new ArrayList<>(List.of(new JSONAdapter(), new XMLAdapter(), new CSVAdapter()));
	}

	public static PersistenceManager getInstance() {
		if(INSTANCE == null) INSTANCE = new PersistenceManager();
		return INSTANCE;
	}

	public String saveGame(Game game) {
		List<String> files = new ArrayList<>();
		for(FileAdapter adapter : this.adapters) {
			String uri = adapter.saveGame(game);
			if(adapter.saveGame(game) != null) files.add(uri);
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
		List<String> files = new ArrayList<>();
		for(FileAdapter adapter : this.adapters) {
			String uri = adapter.saveProfile(profile);
			if(adapter.saveProfile(profile) != null) files.add(uri);
		}
		return String.join(", ", files);
	}

	public Profile loadProfile(String filename) {
		for(FileAdapter adapter : this.adapters) {
			Profile profile = adapter.loadProfile(filename);
			if(profile != null) return profile;
		}
		return null;
	}

}
