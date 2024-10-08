package multiuserdungeon.progress.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import multiuserdungeon.map.Map;

import java.lang.reflect.Type;

public class MapTypeAdaptor implements JsonSerializer<Map>, JsonDeserializer<Map> {

	@Override
	public JsonElement serialize(Map map, Type type, JsonSerializationContext jsonSerializationContext) {
		// TODO
		return null;
	}

	@Override
	public Map deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		// TODO
		return null;
	}

}
