package multiuserdungeon.map;

import multiuserdungeon.Game;
import multiuserdungeon.clock.CreatureBuff;
import multiuserdungeon.map.tiles.NPC;
import multiuserdungeon.map.tiles.trap.Trap;

public class Map {

	private Room playerRoom;
	private final Room goal;

	public Map() {
		// Room 1
		Room room1 = new Room(5, 5, "Bunker");
		NPC room1npc = new NPC("Oager", "Deals heavy attack, try to avoid them at all costs!", CreatureBuff.NOCTURNAL);
		room1npc.setTile(room1.getTile(1, 1));
		room1.getTile(1, 1).addObject(room1npc);

		// Room 2
		Room room2 = new Room(4, 3, "Labyrinth");
		NPC room2npc = new NPC("Troll", "Not really the smartest of guys.", CreatureBuff.DIURNAL);
		room2npc.setTile(room2.getTile(1, 2));
		room2.getTile(1, 2).addObject(room2npc);

		// Room 3
		Room room3 = new Room(6, 5, "Trophy Room");
		Trap room3trap = new Trap(33);
		room3trap.setTile(room3.getTile(3, 4));
		room3.getTile(3, 4).addObject(room3trap);

		// Connect Room 1 & Room 2
		room1.addConnection(0, 3, room2);
		room2.addConnection(3, 1, room1);

		// Connect Room 2 & Room 3
		room2.addConnection(2, 2, room3);
		room3.addConnection(4, 0, room2);

		this.playerRoom = room1;
		this.playerRoom.setPlayerTile(this.playerRoom.getTile(4, 4)); // Starts in bottom right.
		this.playerRoom.getPlayerTile().addObject(Game.getInstance().getPlayer());
		this.goal = room3;
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
