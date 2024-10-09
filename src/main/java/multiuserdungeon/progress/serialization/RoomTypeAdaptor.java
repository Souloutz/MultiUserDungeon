package multiuserdungeon.progress.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import multiuserdungeon.map.Room;

import java.lang.reflect.Type;

public class RoomTypeAdaptor implements JsonSerializer<Room>, JsonDeserializer<Room> {

	@Override
	public JsonElement serialize(Room room, Type type, JsonSerializationContext jsonSerializationContext) {
		JsonObject json = new JsonObject();
		json.addProperty("rows", room.getRows());
		json.addProperty("columns", room.getColumns());
		json.addProperty("description", room.getDescription());
		return json;
	}

	@Override
	public Room deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		// TODO
		return null;
	}

}
