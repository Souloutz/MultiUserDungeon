package multiuserdungeon.persistence.adapters;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.persistence.FileAdapter;
import multiuserdungeon.persistence.PersistenceManager;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class XMLAdapter implements FileAdapter {

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
			XmlMapper mapper = new XmlMapper();
			String path = PersistenceManager.DATA_FOLDER + profile.getUsername() + ".xml";
			FileWriter writer = new FileWriter(path);
			mapper.writeValue(writer, profile);
			return path;
		} catch(IOException e) {
			System.out.println("Error saving profile to XML!");
			return null;
		}
	}

	@Override
	public Profile loadProfile(String username) {
		try {
			XmlMapper mapper = new XmlMapper();
			String path = PersistenceManager.DATA_FOLDER + username + ".xml";
			FileReader reader = new FileReader(path);
			return mapper.readValue(reader, Profile.class);
		} catch(IOException e) {
			System.out.println("Error loading profile from XML!");
			return null;
		}
	}

}
