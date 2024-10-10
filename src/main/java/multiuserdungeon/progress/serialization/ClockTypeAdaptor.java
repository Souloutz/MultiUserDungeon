package multiuserdungeon.progress.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import multiuserdungeon.clock.Clock;
import multiuserdungeon.clock.Day;
import multiuserdungeon.clock.Night;
import multiuserdungeon.clock.Time;

import java.lang.reflect.Type;

public class ClockTypeAdaptor implements JsonSerializer<Clock>, JsonDeserializer<Clock> {

	@Override
	public JsonElement serialize(Clock clock, Type type, JsonSerializationContext ctx) {
		JsonObject json = new JsonObject();
		json.addProperty("turnCounter", clock.getTurn());
		json.addProperty("time", clock.isDay() ? "day" : "night");
		return json;
	}

	@Override
	public Clock deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext ctx) throws JsonParseException {
		JsonObject json = jsonElement.getAsJsonObject();

		Clock clock = new Clock(json.get("turnCounter").getAsInt());
		Time time = json.get("time").getAsString().equals("day") ? new Day(clock) : new Night(clock);
		clock.setCurrentTime(time);
		return clock;
	}

}
