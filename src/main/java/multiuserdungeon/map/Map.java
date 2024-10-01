package multiuserdungeon.map;

import java.util.ArrayList;

import multiuserdungeon.clock.Clock;

public class Map {

	ArrayList<Room> rooms;
	Room playerLocation;
	Clock time;
	int roomCount;
	Room start;
	Room goal;


	public Map() {
		
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
		rooms.add(room);
	}

	public void handleExitRoom(Compass direction) {
		
	}

	public Room getPlayerRoom() {
		return null;
	}

	public void setPlayerRoom() {

	}


}
