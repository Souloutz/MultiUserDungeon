package multiuserdungeon.persistence.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
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
import multiuserdungeon.map.Compass;
import multiuserdungeon.map.EndlessMap;
import multiuserdungeon.map.GameMap;
import multiuserdungeon.map.PremadeMap;
import multiuserdungeon.map.Room;
import multiuserdungeon.map.RoomGenerator;
import multiuserdungeon.map.Tile;
import multiuserdungeon.map.TileObject;
import multiuserdungeon.map.tiles.Chest;
import multiuserdungeon.map.tiles.Merchant;
import multiuserdungeon.map.tiles.NPC;
import multiuserdungeon.map.tiles.Obstacle;
import multiuserdungeon.map.tiles.Player;
import multiuserdungeon.map.tiles.shrine.Shrine;
import multiuserdungeon.map.tiles.trap.DetectedTrap;
import multiuserdungeon.map.tiles.trap.DisarmedTrap;
import multiuserdungeon.map.tiles.trap.Trap;
import multiuserdungeon.persistence.FileAdapter;
import multiuserdungeon.persistence.PersistenceManager;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JSONAdapter implements FileAdapter {

	@Override
	public String saveGame(Game game) {
		JsonObject root = new JsonObject();
		root.addProperty("type", game.getMap() instanceof EndlessMap ? "endless" : "premade");

		JsonObject mapJson = new JsonObject();

		/*
		  Save Players
		 */

		JsonArray playersJson = new JsonArray();
		for(Player player : game.getMap().getPlayers()) {
			JsonObject playerJson = new JsonObject();
			playerJson.addProperty("username", player.getName());
			playerJson.addProperty("description", player.getDescription());
			playerJson.addProperty("health", player.getHealth());

			JsonObject inventoryJson = new JsonObject();
			inventoryJson.addProperty("name", player.getInventory().getName());
			inventoryJson.addProperty("description", player.getInventory().getDescription());
			JsonArray bagsJson = new JsonArray();
			for(Bag bag : player.getInventory().getBags()) {
				JsonObject bagJson = new JsonObject();
				bagJson.addProperty("name", bag.getName());
				bagJson.addProperty("description", bag.getDescription());
				bagJson.addProperty("goldValue", bag.getGoldValue());
				bagJson.addProperty("capacity", bag.getCapacity());

				JsonArray itemsJson = new JsonArray();
				for(InventoryElement item : bag.items()) {
					itemsJson.add(Items.getInstance().getItemId(item));
				}
				bagJson.add("items", itemsJson);

				bagsJson.add(bagJson);
			}
			inventoryJson.add("bags", bagsJson);
			playerJson.add("inventory", inventoryJson);

			playerJson.addProperty("weapon", Items.getInstance().getItemId(player.getWeapon()));
			playerJson.addProperty("armor", Items.getInstance().getItemId(player.getArmor()));

			JsonArray buffsJson = new JsonArray();
			for(Map.Entry<Buff, Integer> buffs : player.getBuffs().entrySet()) {
				JsonObject buffJson = new JsonObject();
				buffJson.addProperty("item", Items.getInstance().getItemId(buffs.getKey()));
				buffJson.addProperty("turnsLeft", buffs.getValue());
				buffsJson.add(buffJson);
			}
			playerJson.add("buffs", buffsJson);

			playersJson.add(playerJson);
		}
		mapJson.add("players", playersJson);

		/*
		  Save Rooms
		 */

		JsonArray roomsJson = new JsonArray();
		for(Room room : game.getMap().getRooms()) {
			JsonObject roomJson = new JsonObject();
			roomJson.addProperty("rows", room.getRows());
			roomJson.addProperty("cols", room.getColumns());
			roomJson.addProperty("description", room.getDescription());

			JsonArray tilesJson = new JsonArray();
			for(int row = 0; row < room.getRows(); row++) {
				JsonArray rowJson = new JsonArray();
				for(int col = 0; col < room.getColumns(); col++) {
					JsonArray tileJson = new JsonArray();
					for(TileObject tileObject : room.getTile(row, col).getObjects()) {
						JsonObject tileObjectJson = new JsonObject();

						if(tileObject instanceof Player player) {
							tileObjectJson.addProperty("type", "player");
							tileObjectJson.addProperty("player", game.getMap().getPlayers().indexOf(player));
						} else if(tileObject instanceof NPC npc) {
							tileObjectJson.addProperty("type", "npc");
							tileObjectJson.addProperty("name", npc.getName());
							tileObjectJson.addProperty("description", npc.getDescription());
							tileObjectJson.addProperty("health", npc.getHealth());
							tileObjectJson.addProperty("baseMaxHealth", npc.getMaxHealth());
							tileObjectJson.addProperty("baseAttack", npc.getBaseAttack());
							tileObjectJson.addProperty("baseDefense", npc.getBaseDefense());
							tileObjectJson.addProperty("creatureBuff", npc.getCreatureBuff().name());
						} else if(tileObject instanceof Obstacle obstacle) {
							tileObjectJson.addProperty("type", "obstacle");
							tileObjectJson.addProperty("name", obstacle.getName());
						} else if(tileObject instanceof Chest chest) {
							tileObjectJson.addProperty("type", "chest");
							tileObjectJson.addProperty("name", chest.getName());
							JsonArray contents = new JsonArray();
							for(InventoryElement item : chest.getContents()) {
								contents.add(Items.getInstance().getItemId(item));
							}
							tileObjectJson.add("contents", contents);
						} else if(tileObject instanceof Merchant merchant) {
							tileObjectJson.addProperty("type", "merchant");
							tileObjectJson.addProperty("name", merchant.getName());
							JsonArray store = new JsonArray();
							for(InventoryElement item : merchant.getStore()) {
								store.add(Items.getInstance().getItemId(item));
							}
							tileObjectJson.add("store", store);
						} else if(tileObject instanceof Trap trap) {
							tileObjectJson.addProperty("type", "trap");
							tileObjectJson.addProperty("damage", trap.getDamage());
							if(trap.isDisarmed()) {
								tileObjectJson.addProperty("status", "disarmed");
							} else if(trap.isDetected()) {
								tileObjectJson.addProperty("status", "detected");
							} else {
								tileObjectJson.addProperty("status", "undetected");
							}
						} else if(tileObject instanceof Shrine shrine) {
							tileObjectJson.addProperty("type", "shrine");
							tileObjectJson.addProperty("name", shrine.getName());
						}

						tileJson.add(tileObjectJson);
					}
					rowJson.add(tileJson);
				}
				tilesJson.add(rowJson);
			}
			roomJson.add("tiles", tilesJson);

			roomsJson.add(roomJson);
		}
		mapJson.add("rooms", roomsJson);

		/*
		  Save Connections
		 */

		JsonArray connectionsJson = new JsonArray();
		for(Room room : game.getMap().getRooms()) {
			Map<Compass, Tile> doorways = new HashMap<>(room.getDoorways());
			Map<Tile, Room> connections = new HashMap<>(room.getConnections());
			for(Map.Entry<Compass, Tile> connection : doorways.entrySet()) {
				Compass direction = connection.getKey();
				Tile tile = connection.getValue();
				Room toRoom = connections.get(tile);

				JsonObject connectionJson = new JsonObject();
				connectionJson.addProperty("fromRoom", game.getMap().getRooms().indexOf(room));
				connectionJson.addProperty("fromTile", tile.getRow() + "," + tile.getCol());
				if(toRoom != null) {
					connectionJson.addProperty("toRoom", game.getMap().getRooms().indexOf(toRoom));
					Tile toTile = toRoom.getDoorway(direction.getOpposite());
					connectionJson.addProperty("toTile", toTile.getRow() + "," + toTile.getCol());
					toRoom.removeConnection(toTile.getRow(), toTile.getCol());
				} else {
					connectionJson.addProperty("toRoom", -1);
					connectionJson.addProperty("toTile", "");
				}

				connectionsJson.add(connectionJson);
			}
		}
		mapJson.add("connections", connectionsJson);

		root.add("map", mapJson);

		try {
			String path = PersistenceManager.DATA_FOLDER + UUID.randomUUID().toString().split("-")[0] + ".json";
			FileWriter writer = new FileWriter(path);
			new GsonBuilder().setPrettyPrinting().create().toJson(root, writer);
			writer.flush();
			writer.close();
			return path;
		} catch (IOException e) {
			System.out.println("Error saving game to JSON!");
			return null;
		}
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
					if(item == null) continue;
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

			int weaponId = playerJson.get("weapon").getAsInt();
			if(weaponId != -1) {
				Weapon weapon = (Weapon) Items.getInstance().getItem(weaponId);
				player.equipWeapon(weapon);
			}

			int armorId = playerJson.get("armor").getAsInt();
			if(armorId != -1) {
				Armor armor = (Armor) Items.getInstance().getItem(armorId);
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
		Map<Integer, Integer> playerRooms = new HashMap<>();
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
								int playerId = tileObjectJson.get("player").getAsInt();
								Player player = players.get(playerId);
								player.setTile(tile);
								tile.addObject(player);
								playerRooms.put(playerId, roomNum);
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
									if(item == null) continue;
									contents.add(item);
								}
								Chest chest = new Chest(name, contents);
								chest.setTile(tile);
								tile.addObject(chest);
							}
							case "merchant" -> {
								String name = tileObjectJson.get("name").getAsString();
								List<InventoryElement> store = new ArrayList<>();
								for(JsonElement storeElement : tileObjectJson.getAsJsonArray("store")) {
									int itemId = storeElement.getAsInt();
									InventoryElement item = Items.getInstance().getItem(itemId);
									if(item == null) continue;
									store.add(item);
								}
								Merchant merchant = new Merchant(name, store);
								merchant.setTile(tile);
								tile.addObject(merchant);
							}
							case "trap" -> {
								int damage = tileObjectJson.get("damage").getAsInt();
								Trap trap = new Trap(damage);
								String status = tileObjectJson.get("status").getAsString();
								if(status.equals("detected")) {
									trap.setStatus(new DetectedTrap(trap));
								} else if(status.equals("disarmed")) {
									trap.setStatus(new DisarmedTrap());
								}
								trap.setTile(tile);
								tile.addObject(trap);
							}
							case "shrine" -> {
								String name = tileObjectJson.get("name").getAsString();
								Shrine shrine = new Shrine(name);
								shrine.setTile(tile);
								tile.addObject(shrine);
							}
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
			String[] fromTile = connectionJson.get("fromTile").getAsString().split(",");
			int toRoomId = connectionJson.get("toRoom").getAsInt();

			if(toRoomId != -1) {
				Room toRoom = rooms.get(toRoomId);
				String[] toTile = connectionJson.get("toTile").getAsString().split(",");

				fromRoom.addConnection(Integer.parseInt(fromTile[0]), Integer.parseInt(fromTile[1]), toRoom);
				toRoom.addConnection(Integer.parseInt(toTile[0]), Integer.parseInt(toTile[1]), fromRoom);
			} else {
				fromRoom.addConnection(Integer.parseInt(fromTile[0]), Integer.parseInt(fromTile[1]), null);
			}
		}

		/*
		  Player Start Rooms
		 */

		Map<Integer, Integer> playerStartRooms = new HashMap<>();
		for(JsonElement playerStartRoomElement : mapJson.getAsJsonArray("playerStartRooms")) {
			JsonObject playerStartRoomJson = playerStartRoomElement.getAsJsonObject();
			int playerId = playerStartRoomJson.get("player").getAsInt();
			int roomId = playerStartRoomJson.get("room").getAsInt();
			playerStartRooms.put(playerId, roomId);
		}

		/*
		  Finalize
		 */

		if(currentPlayer == null) {
			// Joining game for the first time
			User user = Authenticator.getInstance().getUser();
			currentPlayer = new Player(
					user.getUsername(),
					user.getDescription(),
					new Inventory("Your Inventory", "A safe place for your items and bags.", true),
					new HashMap<>());

			if(type.equals("premade")) {
				if(!players.isEmpty()) return null; // Cannot load a premade game with others

				Room playerRoom = rooms.get(0);
				Tile startingTile = playerRoom.getTile(playerRoom.getRows() - 1, playerRoom.getColumns() - 1);
				currentPlayer.setTile(startingTile);
				startingTile.addObject(currentPlayer);
				players.add(currentPlayer);
				playerRooms.put(players.size() - 1, 0);
			} else {
				if(!Authenticator.getInstance().loggedIn()) return null; // Cannot browse endless game

				Room playerRoom = new Room(3, 5, user.getUsername() + "'s Starting Room");

				Tile merchantTile = playerRoom.getTile(1, 0);
				Merchant merchant = RoomGenerator.generateMerchant();
				if(merchant == null) return null; // Error generating merchant
				merchant.setTile(merchantTile);
				merchantTile.addObject(merchant);

				Tile shrineTile = playerRoom.getTile(1, 4);
				Shrine shrine = RoomGenerator.generateShrine();
				if(shrine == null) return null; // Error generating shrine
				shrine.setTile(shrineTile);
				shrineTile.addObject(shrine);

				playerRoom.addConnection(0, 2, null);
				rooms.add(playerRoom);

				Tile startingTile = playerRoom.getTile(2, 2);
				currentPlayer.setTile(startingTile);
				startingTile.addObject(currentPlayer);
				players.add(currentPlayer);
				playerRooms.put(players.size() - 1, rooms.size() - 1);
			}
		}

		GameMap map;
		if(type.equals("endless")) {
			map = new EndlessMap(rooms, players, playerRooms, playerStartRooms);
		} else {
			map = new PremadeMap(rooms, players, playerRooms, playerStartRooms, 0);
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
