package multiuserdungeon.map;

import multiuserdungeon.Game;
import multiuserdungeon.clock.CreatureBuff;
import multiuserdungeon.inventory.BuffStat;
import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.inventory.elements.Bag;
import multiuserdungeon.inventory.elements.Buff;
import multiuserdungeon.inventory.elements.Food;
import multiuserdungeon.inventory.elements.Weapon;
import multiuserdungeon.map.tiles.Chest;
import multiuserdungeon.map.tiles.NPC;
import multiuserdungeon.map.tiles.Obstacle;
import multiuserdungeon.map.tiles.trap.Trap;

import java.util.ArrayList;
import java.util.List;

public class Map {

	private Room playerRoom;
	private final Room goal;

	public Map() {
		// Room 1
		Room room1 = new Room(5, 5, "Bunker");

		NPC room1npc = new NPC("Oager", "Deals a modest attack, try to avoid them at all costs!", CreatureBuff.NOCTURNAL);
		room1npc.setTile(room1.getTile(1, 1));
		room1.getTile(1, 1).addObject(room1npc);

		Obstacle room1obstacle = new Obstacle("Boulder");
		room1obstacle.setTile(room1.getTile(3, 2));
		room1.getTile(3, 2).addObject(room1obstacle);

		List<InventoryElement> contents = new ArrayList<>(List.of(
				new Weapon("Broadsword", "Heavy hitting weapon.", 25, 40),
				new Buff("Shield Elixir", "Tasty potion.", 10, BuffStat.DEFENSE, 15)));
		Chest room1chest = new Chest("Shiny Chest", contents);
		room1chest.setTile(room1.getTile(4, 1));
		room1.getTile(4, 1).addObject(room1chest);

		NPC room1npc2 = new NPC("King's Knight", "Attempting to attack this foe might result in death!", CreatureBuff.DIURNAL);
		room1npc2.setTile(room1.getTile(2, 4));
		room1.getTile(2, 4).addObject(room1npc2);

		// Room 2
		Room room2 = new Room(4, 3, "Labyrinth");

		NPC room2npc = new NPC("Troll", "Not really the smartest of guys.", CreatureBuff.DIURNAL);
		room2npc.setTile(room2.getTile(1, 2));
		room2.getTile(1, 2).addObject(room2npc);

		contents = new ArrayList<>(List.of(
				new Bag("Large Bag", "Stores a lot more valuables.", 20, 8),
				new Food("Medium-Rare Steak", "An explorer's meal.", 6, 30),
				new Food("Rotten Steak", "It still smells...", 0, 0)));
		Chest room2chest = new Chest("Dirty Chest", contents);
		room2chest.setTile(room2.getTile(3, 2));
		room2.getTile(3, 2).addObject(room2chest);

		Trap room2trap = new Trap(33);
		room2trap.setTile(room2.getTile(2, 0));
		room2.getTile(2, 0).addObject(room2trap);

		// Room 3
		Room room3 = new Room(6, 5, "Trophy Room");

		// Connect Room 1 & Room 2
		room1.addConnection(0, 3, room2);
		room2.addConnection(3, 1, room1);

		// Connect Room 2 & Room 3
		room2.addConnection(2, 2, room3);
		room3.addConnection(4, 0, room2);

		// Set player position
		this.playerRoom = room1;
		this.playerRoom.setPlayerTile(this.playerRoom.getTile(4, 4));
		this.playerRoom.getPlayerTile().addObject(Game.getInstance().getPlayer());
		Game.getInstance().getPlayer().setTile(this.playerRoom.getTile(4, 4));
		this.goal = room3;
	}

	public Map(Room playerRoom, Room goal) {
		this.playerRoom = playerRoom;
		Tile playerTile = this.playerRoom.getTile(this.playerRoom.getRows() - 1, this.playerRoom.getColumns() - 1);
		this.playerRoom.setPlayerTile(playerTile);
		this.playerRoom.getPlayerTile().addObject(Game.getInstance().getPlayer());
		Game.getInstance().getPlayer().setTile(playerTile);
		this.goal = goal;
	}

	public Room getGoal() {
		return goal;
	}

	public Room getPlayerRoom() {
		return this.playerRoom;
	}

	public void setPlayerRoom(Room room) {
		this.playerRoom = room;
	}

	public boolean playerReachedGoal() {
		return getPlayerRoom().equals(getGoal());
	}

}
