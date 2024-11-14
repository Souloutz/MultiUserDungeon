package multiuserdungeon.persistence.adapters;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.persistence.FileAdapter;
import multiuserdungeon.persistence.PersistenceManager;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVAdapter implements FileAdapter {

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
			String path = PersistenceManager.DATA_FOLDER + "profiles.csv";
			FileWriter writer = new FileWriter(path, true);
			new StatefulBeanToCsvBuilder<Profile>(writer).build().write(profile);
			writer.flush();
			writer.close();
			return path;
		} catch(CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e) {
			System.out.println("Error saving profile to CSV!");
			return null;
		}
	}

	@Override
	public Profile loadProfile(String username) {
		try {
			String path = PersistenceManager.DATA_FOLDER + "profiles.csv";
			FileReader reader = new FileReader(path);
			Profile profile = new CsvToBeanBuilder<Profile>(reader).withType(Profile.class)
					.withFilter(row -> row[0].equals(username)).build().parse()
					.stream().findFirst().orElse(null);
			reader.close();
			return profile;
		} catch(IOException e) {
			System.out.println("Error loading profile from CSV!");
			return null;
		}
	}

}
