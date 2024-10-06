package multiuserdungeon.map;

import java.util.HashMap;
import java.util.ArrayList;

import multiuserdungeon.clock.Clock;
import multiuserdungeon.map.tiles.Chest;
import multiuserdungeon.map.tiles.EmptyTile;
import multiuserdungeon.map.tiles.NPC;
import multiuserdungeon.map.tiles.Obstacle;
import multiuserdungeon.map.tiles.trap.Trap;
import multiuserdungeon.inventory.BuffStat;
import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.inventory.elements.Armor;
import multiuserdungeon.inventory.elements.Buff;
import multiuserdungeon.inventory.elements.Weapon;

public class Map {

	HashMap<Room,HashMap<Compass,Room>> rooms;
	Room playerLocation;
	Clock time;
	int roomCount;
	Room start;
	Room goal;

	public Map(Clock time) {
		this.rooms = new HashMap<>();
		this.time = time;
		

		Room room1 = new Room(5,5);
		setStart(room1);
		setPlayerRoom(room1);
		Tile[][] layout1 = room1.getLayout();
		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 5; j++) {
				layout1[i][j].addObject(new EmptyTile());
			}
		}
		layout1[1][1].removeObjects();
		layout1[1][1].addObject(new NPC("Diddy","He's gonna Diddle you",true,5));
		layout1[3][3].removeObjects();
		layout1[3][3].addObject(new Trap(15));
		ArrayList<InventoryElement> items1 = new ArrayList<>();
		items1.add(new Weapon("Shmingle","It'll shmingle all over you!",10,15));
		items1.add(new Armor("Thneed","It's a Thneed!",15,10));
		items1.add(new Buff("Wocky Slush","Dave Blunts's drink of choice",20,BuffStat.ATTACK,15));
		layout1[1][3].removeObjects();
		layout1[1][3].addObject(new Chest("Happy Meal",items1));
		layout1[3][1].removeObjects();
		layout1[3][1].addObject(new Obstacle("Statue of Mandy Yu"));
		//layout1[2][4].addObject(new Player("Player","It's me, the Player!"));
		

		Room room2 = new Room(5,5);
		Tile[][] layout2 = room2.getLayout();
		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 5; j++) {
				layout2[i][j].addObject(new EmptyTile());
			}
		}
		layout2[1][1].removeObjects();
		layout2[1][1].addObject(new NPC("The Lorax","He speaks for the trees",false,0));
		layout2[3][3].removeObjects();
		layout2[3][3].addObject(new Trap(15));
		ArrayList<InventoryElement> items2 = new ArrayList<>();
		items2.add(new Weapon("Arrow","Aim for the knee",2,30));
		items2.add(new Armor("N-95 facemask","Covid-20",1,50));
		items2.add(new Buff("Moisturizer","Gotta start skincare",40,BuffStat.DEFENSE,15));
		layout2[1][3].removeObjects();
		layout2[1][3].addObject(new Chest("64 box of crayons",items2));
		layout2[3][1].removeObjects();
		layout2[3][1].addObject(new Obstacle("Statue of Jack Barter"));

		Room room3 = new Room(5,5);
		Tile[][] layout3 = room3.getLayout();
		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 5; j++) {
				layout3[i][j].addObject(new EmptyTile());
			}
		}
		layout3[1][1].removeObjects();
		layout3[1][1].addObject(new NPC("Nettspend","DRANKDRANKDRANK",true,20));
		layout3[3][3].removeObjects();
		layout3[3][3].addObject(new Trap(15));
		ArrayList<InventoryElement> items3 = new ArrayList<>();
		items3.add(new Weapon("Nerf gun","Only effective if you spray paint it black",4,1));
		items3.add(new Armor("Mets hat","Let's go Mets!",5,5));
		items3.add(new Buff("Stroganoff","Polish meat stew",5,BuffStat.DEFENSE,5));
		layout3[1][3].removeObjects();
		layout3[1][3].addObject(new Chest("Kinder Surprise Egg",items3));
		layout3[3][1].removeObjects();
		layout3[3][1].addObject(new Obstacle("Statue of Howard Kong"));

		Room room4 = new Room(5,5);
		setGoal(room4);
		Tile[][] layout4 = room4.getLayout();
		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 5; j++) {
				layout4[i][j].addObject(new EmptyTile());
			}
		}
		layout4[1][1].removeObjects();
		layout4[1][1].addObject(new NPC("Quinton","How'd he get in the multi user dungeon???",true,20));
		layout4[3][3].removeObjects();
		layout4[3][3].addObject(new Trap(15));
		ArrayList<InventoryElement> items4 = new ArrayList<>();
		items4.add(new Weapon("Glock 19","You don't have a permit for it",50,100));
		items4.add(new Armor("Party hat","It's made of cardboard",1,1));
		items4.add(new Buff("Yerky","Shplerky",100,BuffStat.ATTACK,100));
		layout4[1][3].removeObjects();
		layout4[1][3].addObject(new Chest("Normal Chest",items4));
		layout4[3][1].removeObjects();
		layout4[3][1].addObject(new Obstacle("Statue of Luke Edwards"));

		connectRooms(room1,4,2,room2,0,2,Compass.EAST);
		connectRooms(room2,4,2,room3,0,2,Compass.EAST);
		connectRooms(room3,2,0,room4,2,4,Compass.NORTH);

	}

	public Room getStart() {
		return start;
	}

	public void setStart(Room room) {
		this.start = room;
	}

	public void setGoal(Room room) {
		this.goal = room;
	}

	public Room getGoal() {
		return goal;
	}

	public Clock getClock() {
		return time;
	}

	public void addRoom(Room room) {
		rooms.put(room,new HashMap<Compass,Room>());
	}

	public void connectRooms(Room room1, int x1, int y1, Room room2, int x2, int y2, Compass direction) {
		HashMap<Compass,Room> connections = rooms.get(room1);
		connections.put(direction,room2);
		connections = rooms.get(room2);
		connections.put(direction.getOpposite(),room1);

		room1.getDoorways().put(direction,room1.getTile(x1,y1));
		room1.getConnections().put(room1.getTile(x1,y1),room2);
		room2.getDoorways().put(direction,room2.getTile(x2,y2));
		room2.getConnections().put(room2.getTile(x2,y2),room1);
	}

	public void handleExitRoom(Compass direction) {
		this.playerLocation = rooms.get(playerLocation).get(direction);
		// check to see if we moved to goal room?
	}

	public Room getPlayerRoom() {
		return playerLocation;
	}

	public void setPlayerRoom(Room room) {
		this.playerLocation = room;
	}

	public boolean playerReachedGoal() {
		// TODO
		return getPlayerRoom().equals(getGoal());
	}
}
