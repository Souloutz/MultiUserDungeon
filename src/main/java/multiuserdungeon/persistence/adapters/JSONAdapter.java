package multiuserdungeon.persistence.adapters;

import com.google.gson.Gson;
import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.persistence.FileAdapter;
import multiuserdungeon.persistence.PersistenceManager;

import java.io.FileReader;
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
			String path = PersistenceManager.DATA_FOLDER + profile.getUsername() + ".json";
			FileWriter writer = new FileWriter(path);
			new Gson().toJson(profile, writer);
			writer.flush();
			writer.close();
			return path;
		} catch(IOException e) {
			System.out.println("Error saving user to JSON!");
			return null;
		}
	}

	@Override
	public Profile loadProfile(String username) {
		try {
			String path = PersistenceManager.DATA_FOLDER + username + ".json";
			FileReader reader = new FileReader(path);
			Profile profile = new Gson().fromJson(reader, Profile.class);
			reader.close();
			return profile;
		} catch(IOException e) {
			System.out.println("Error loading user from JSON!");
			return null;
		}
	}

}
