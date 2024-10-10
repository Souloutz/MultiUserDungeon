package multiuserdungeon.progress;

import com.google.gson.GsonBuilder;
import multiuserdungeon.Game;
import multiuserdungeon.clock.Clock;
import multiuserdungeon.map.Room;
import multiuserdungeon.map.Tile;
import multiuserdungeon.progress.serialization.ClockTypeAdaptor;
import multiuserdungeon.progress.serialization.RoomTypeAdaptor;
import multiuserdungeon.progress.serialization.TileTypeAdaptor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONProgressDB implements ProgressDB {

	private static final String FILE_NAME = "progress.json";
	private static final GsonBuilder GSON = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(Clock.class, new ClockTypeAdaptor())
			.registerTypeAdapter(Room.class, new RoomTypeAdaptor())
			.registerTypeAdapter(Tile.class, new TileTypeAdaptor());

	@Override
	public Game load(String uri) {
		try {
			FileReader reader = new FileReader(uri);
			Game game = GSON.create().fromJson(reader, Game.class);
			game.setProgressDB(this);
			reader.close();
			return game;
		} catch(IOException e) {
			return null;
		}
	}

	@Override
	public String save(Game game) {
		try {
			FileWriter writer = new FileWriter(FILE_NAME);
			GSON.create().toJson(game, writer);
			writer.flush();
			writer.close();
		} catch(IOException e) {
			return "Error while saving game.";
		}

		return "Successfully saved to \"" + FILE_NAME + "\"!";
	}

}
