package multiuserdungeon.persistence.adapters;

import com.google.gson.Gson;
import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.persistence.FileAdapter;
import multiuserdungeon.persistence.PersistenceManager;

import java.io.FileWriter;
import java.io.IOException;

public class JSONAdapter implements FileAdapter {

	@Override
	public String saveGame(Game game) {
		return null;
	}

	@Override
	public Game loadGame(String filename) {
		return null;
	}

	@Override
	public String saveProfile(Profile profile) {
		try {
			String path = PersistenceManager.DATA_FOLDER + profile.getPlayer().getName() + ".json";
			new Gson().toJson(profile, new FileWriter(path));
			return path;
		} catch (IOException e) {
			System.out.println("Error saving user to JSON!");
			return null;
		}
	}

	@Override
	public Profile loadProfile(String filename) {

	}

}
