package multiuserdungeon.persistence.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import multiuserdungeon.Game;
import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.clock.Clock;
import multiuserdungeon.clock.CreatureBuff;
import multiuserdungeon.clock.Day;
import multiuserdungeon.clock.Night;
import multiuserdungeon.inventory.Inventory;
import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.inventory.Items;
import multiuserdungeon.inventory.elements.Armor;
import multiuserdungeon.inventory.elements.Bag;
import multiuserdungeon.inventory.elements.Buff;
import multiuserdungeon.inventory.elements.Weapon;
import multiuserdungeon.map.EndlessMap;
import multiuserdungeon.map.GameMap;
import multiuserdungeon.map.PremadeMap;
import multiuserdungeon.map.Room;
import multiuserdungeon.map.Tile;
import multiuserdungeon.map.tiles.Chest;
import multiuserdungeon.map.tiles.NPC;
import multiuserdungeon.map.tiles.Obstacle;
import multiuserdungeon.map.tiles.Player;
import multiuserdungeon.persistence.FileAdapter;
import multiuserdungeon.persistence.PersistenceManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONAdapter implements FileAdapter {

	@Override
	public String saveGame(Game game) {
		return null;
	}

	@Override
	public Game loadGame(String filename) {
		JsonObject root;
		try {
			String path = PersistenceManager.DATA_FOLDER + filename + ".json";
			FileReader reader = new FileReader(path);
			root = JsonParser.parseReader(reader).getAsJsonObject();
			reader.close();
		} catch (IOException e) {
			System.out.println("Error loading game from JSON!");
			return null;
		}

		String type = root.get("type").getAsString();
		JsonObject mapJson = root.getAsJsonObject("map");

		/*
		  Load Players
		 */

		Player currentPlayer = null;
		List<Player> players = new ArrayList<>();
		for(JsonElement playerElement : mapJson.getAsJsonArray("players")) {
			JsonObject playerJson = playerElement.getAsJsonObject();
			String username = playerJson.get("username").getAsString();
			String description = playerJson.get("description").getAsString();
			int health = playerJson.get("health").getAsInt();

			JsonObject inventoryJson = playerJson.getAsJsonObject("inventory");
			String inventoryName = inventoryJson.get("name").getAsString();
			String inventoryDescription = inventoryJson.get("description").getAsString();

			Inventory inventory = new Inventory(inventoryName, inventoryDescription, false);
			for(JsonElement bagElement : inventoryJson.getAsJsonArray("bags")) {
				JsonObject bagJson = bagElement.getAsJsonObject();
				String bagName = bagJson.get("name").getAsString();
				String bagDescription = bagJson.get("description").getAsString();
				int goldValue = bagJson.get("goldValue").getAsInt();
				int capacity = bagJson.get("capacity").getAsInt();

				Bag bag = new Bag(bagName, bagDescription, goldValue, capacity);
				for(JsonElement itemsElement : bagJson.getAsJsonArray("items")) {
					int itemId = itemsElement.getAsInt();
					InventoryElement item = Items.getInstance().getItem(itemId);
					bag.addItem(item);
				}

				inventory.addBag(bag);
			}

			Map<Buff, Integer> buffs = new HashMap<>();
			for(JsonElement buffElement : playerJson.getAsJsonArray("buffs")) {
				JsonObject buffJson = buffElement.getAsJsonObject();
				Buff item = (Buff) Items.getInstance().getItem(buffJson.get("item").getAsInt());
				int turnsLeft = buffJson.get("turnsLeft").getAsInt();
				buffs.put(item, turnsLeft);
			}

			Player player = new Player(username, description, inventory, buffs);
			player.setHealth(health);

			JsonElement weaponElement = playerJson.get("weapon");
			if(!weaponElement.isJsonNull()) {
				Weapon weapon = (Weapon) Items.getInstance().getItem(weaponElement.getAsInt());
				player.equipWeapon(weapon);
			}

			JsonElement armorElement = playerJson.get("armor");
			if(!armorElement.isJsonNull()) {
				Armor armor = (Armor) Items.getInstance().getItem(armorElement.getAsInt());
				player.equipArmor(armor);
			}

			players.add(player);
			if(player.getName().equals(Authenticator.getInstance().getUser().getUsername())) {
				currentPlayer = player;
			}
		}

		/*
		  Load Rooms
		 */

		List<Room> rooms = new ArrayList<>();
		Map<Player, Integer> playerRooms = new HashMap<>();
		int roomNum = 0;
		for(JsonElement roomElement : mapJson.getAsJsonArray("rooms")) {
			JsonObject roomJson = roomElement.getAsJsonObject();
			int rows = roomJson.get("rows").getAsInt();
			int cols = roomJson.get("cols").getAsInt();
			String description = roomJson.get("description").getAsString();

			Room room = new Room(rows, cols, description);

			int row = 0;
			int col = 0;
			for(JsonElement rowElement : roomJson.getAsJsonArray("tiles")) {
				for(JsonElement tileElement : rowElement.getAsJsonArray()) {
					Tile tile = room.getTile(row, col);

					for(JsonElement tileObjectElement : tileElement.getAsJsonArray()) {
						JsonObject tileObjectJson = tileObjectElement.getAsJsonObject();
						String tileObjectType = tileObjectJson.get("type").getAsString();
						switch(tileObjectType) {
							case "player" -> {
								Player player = players.get(tileObjectJson.get("player").getAsInt());
								player.setTile(tile);
								tile.addObject(player);
								playerRooms.put(player, roomNum);
							}
							case "npc" -> {
								String name = tileObjectJson.get("name").getAsString();
								String npcDescription = tileObjectJson.get("description").getAsString();
								int health = tileObjectJson.get("health").getAsInt();
								int baseMaxHealth = tileObjectJson.get("baseMaxHealth").getAsInt();
								int baseAttack = tileObjectJson.get("baseAttack").getAsInt();
								int baseDefense = tileObjectJson.get("baseDefense").getAsInt();
								CreatureBuff creatureBuff = CreatureBuff.valueOf(tileObjectJson.get("creatureBuff").getAsString());
								NPC npc = new NPC(name, npcDescription, baseMaxHealth, baseAttack, baseDefense, creatureBuff);
								npc.setHealth(health);
								npc.setTile(tile);
								tile.addObject(npc);
							}
							case "obstacle" -> {
								Obstacle obstacle = new Obstacle(tileObjectJson.get("name").getAsString());
								obstacle.setTile(tile);
								tile.addObject(obstacle);
							}
							case "chest" -> {
								String name = tileObjectJson.get("name").getAsString();
								List<InventoryElement> contents = new ArrayList<>();
								for(JsonElement itemElement : tileObjectJson.getAsJsonArray("contents")) {
									int itemId = itemElement.getAsInt();
									InventoryElement item = Items.getInstance().getItem(itemId);
									contents.add(item);
								}
								Chest chest = new Chest(name, contents);
								chest.setTile(tile);
								tile.addObject(chest);
							}
							// TODO: Parse rest of tile objects
						}
					}
					col++;
				}
				row++;
				col = 0;
			}

			rooms.add(room);
			roomNum++;
		}

		/*
		  Connect Rooms
		 */

		for(JsonElement connectionElement : mapJson.getAsJsonArray("connections")) {
			JsonObject connectionJson = connectionElement.getAsJsonObject();

			int fromRoomId = connectionJson.get("fromRoom").getAsInt();
			Room fromRoom = rooms.get(fromRoomId);
			int toRoomId = connectionJson.get("toRoom").getAsInt();
			Room toRoom = rooms.get(toRoomId);
			String[] fromTile = connectionJson.get("fromTile").getAsString().split(",");
			String[] toTile = connectionJson.get("toTile").getAsString().split(",");

			fromRoom.addConnection(Integer.parseInt(fromTile[0]), Integer.parseInt(fromTile[1]), toRoom);
			toRoom.addConnection(Integer.parseInt(toTile[0]), Integer.parseInt(toTile[1]), fromRoom);
		}

		/*
		  Player Start Rooms
		 */

		Map<Player, Integer> playerStartRooms = new HashMap<>();
		for(JsonElement playerStartRoomElement : mapJson.getAsJsonArray("playerStartRooms")) {
			JsonObject playerStartRoomJson = playerStartRoomElement.getAsJsonObject();
			Player player = players.get(playerStartRoomJson.get("player").getAsInt());
			playerStartRooms.put(player, playerStartRoomJson.get("room").getAsInt());
		}

		/*
		  Finalize
		 */

		if(currentPlayer == null) {
			// Joining game for the first time
			if(type.equals("premade")) {
				if(!players.isEmpty()) return null; // Cannot join a premade game

				User user = Authenticator.getInstance().getUser();
				currentPlayer = new Player(
						user.getUsername(),
						user.getDescription(),
						new Inventory("Your Inventory", "A safe place for your items and bags.", true),
						new HashMap<>());

				Room playerRoom = rooms.get(0);
				Tile startingTile = playerRoom.getTile(playerRoom.getRows() - 1, playerRoom.getColumns() - 1);
				currentPlayer.setTile(startingTile);
				startingTile.addObject(currentPlayer);
				playerRooms.put(currentPlayer, 0);
			} else {
				if(!Authenticator.getInstance().loggedIn()) return null; // Cannot browse endless game
				// TODO: Create starting room and connect
			}
		}

		GameMap map;
		if(type.equals("endless")) {
			map = new EndlessMap(rooms, playerRooms, playerStartRooms);
		} else {
			map = new PremadeMap(rooms, playerRooms, playerStartRooms, 0);
		}

		/*
		  Load Clock
		 */

		JsonObject clockJson = root.getAsJsonObject("clock");
		Clock clock = new Clock(clockJson.get("turnCounter").getAsInt());
		clock.setCurrentTime(clockJson.get("time").getAsString().equals("day") ? new Day(clock) : new Night(clock));

		boolean browsing = !Authenticator.getInstance().loggedIn();
		return new Game(currentPlayer, map, clock, browsing);
	}

	@Override
	public String saveProfile(Profile profile) {
		try {
			String path = PersistenceManager.DATA_FOLDER + profile.getUsername() + ".json";
			FileWriter writer = new FileWriter(path);
			new GsonBuilder().setPrettyPrinting().create().toJson(profile, writer);
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
