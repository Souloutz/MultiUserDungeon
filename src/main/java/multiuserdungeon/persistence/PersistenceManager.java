package multiuserdungeon.persistence;

import multiuserdungeon.Game;
import multiuserdungeon.persistence.adapters.CSVAdapter;
import multiuserdungeon.persistence.adapters.JSONAdapter;
import multiuserdungeon.persistence.adapters.XMLAdapter;

import java.util.ArrayList;
import java.util.List;

public class PersistenceManager {

	private static PersistenceManager INSTANCE = null;
	private final List<FileAdapter> adapters;

	public PersistenceManager() {
		this.adapters = new ArrayList<>(List.of(new JSONAdapter(), new XMLAdapter(), new CSVAdapter()));
	}

	public static PersistenceManager getInstance() {
		if(INSTANCE == null) INSTANCE = new PersistenceManager();
		return INSTANCE;
	}

	public boolean saveGame(Game game) {
		for(FileAdapter adapter : this.adapters) {
			if(!adapter.saveGame(game)) return false;
		}
		return true;
	}

	public Game loadGame(String filename) {
		for(FileAdapter adapter : this.adapters) {
			Game game = adapter.loadGame(filename);
			if(game != null) return game;
		}
		return null;
	}

	public boolean saveUser(User user) {
		for(FileAdapter adapter : this.adapters) {
			if(!adapter.saveUser(user)) return false;
		}
		return true;
	}

	public Game loadUser(String filename) {
		for(FileAdapter adapter : this.adapters) {
			User user = adapter.loadUser(filename);
			if(user != null) return user;
		}
		return null;
	}

}
