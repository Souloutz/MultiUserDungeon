package multiuserdungeon.progress;

import com.google.gson.Gson;
import multiuserdungeon.map.Map;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONProgressDB implements ProgressDB {

	private static final String FILE_NAME = "progress.json";

	@Override
	public Map load(String uri) {
		// TODO: Reimplement
		Map map = null;
		try {
			map = new Gson().fromJson(new BufferedReader(new FileReader(uri)), Map.class);
		} catch (FileNotFoundException e) {
			System.err.println("Error loading map from " + uri);
		}

		return map;
	}

	@Override
	public String save(Map map) {
		// TODO: Reimplement
		try {
			new Gson().toJson(map, new BufferedWriter(new FileWriter(FILE_NAME)));
		} catch (IOException e) {
			System.err.println("Error saving map to " + FILE_NAME);
		}

		return FILE_NAME;
	}

}
