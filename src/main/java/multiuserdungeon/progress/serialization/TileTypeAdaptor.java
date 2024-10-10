package multiuserdungeon.progress.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import multiuserdungeon.map.Tile;

import java.lang.reflect.Type;

public class TileTypeAdaptor implements JsonSerializer<Tile>, JsonDeserializer<Tile> {

	@Override
	public JsonElement serialize(Tile tile, Type type, JsonSerializationContext jsonSerializationContext) {
		JsonObject json = new JsonObject();
		json.addProperty("row", tile.getRow());
		json.addProperty("col", tile.getCol());
		return json;
	}

	@Override
	public Tile deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		return null;
	}

}
