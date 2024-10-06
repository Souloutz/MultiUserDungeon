package multiuserdungeon;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import multiuserdungeon.clock.Clock;
import multiuserdungeon.clock.Time;
import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.inventory.elements.Bag;
import multiuserdungeon.map.Compass;
import multiuserdungeon.map.Map;
import multiuserdungeon.map.Room;
import multiuserdungeon.map.Tile;
import multiuserdungeon.map.tiles.Chest;
import multiuserdungeon.map.tiles.Player;
import multiuserdungeon.map.tiles.trap.Trap;
import multiuserdungeon.progress.ProgressDB;

public class Game {

	private static Game instance;
	private final Player player;
	private Map map;
	private final Clock clock;
	private ProgressDB progressDB;

	public Game(Player player) {
		instance = this;
		this.player = player;
		this.clock = new Clock();
		this.progressDB = null;
		this.map = null;
	}

	public static Game getInstance() {
		return instance;
	}

	public Player getPlayer() {
		return this.player;
	}

	public Map getMap() {
		return this.map;
	}

	public Time getCurrentTime() {
		return this.clock.getCurrentTime();
	}

	public void setProgressDB(ProgressDB progressDB) {
		this.progressDB = progressDB;
	}

	public void handleLoadMap(String uri) {
		this.map = this.progressDB.load(uri);
	}

	// TODO get the direction from player tile?
	public String getAvailableCommands() {
		String availableCommands = """
			---------------------------------
            Directions
            ---------------------------------
                NORTH, SOUTH, EAST, WEST, NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST

            ---------------------------------
            Turn Commands
            ---------------------------------
		""";

		Tile playerTile = player.getTile();
		Set<Integer> commandSet = new HashSet<>(4);

		for (Tile tile : playerTile.getAdjacent()) {
			if (tile.getNPC() != null)
				commandSet.add(1);

			else if (tile.passable())
				commandSet.add(2);

			else if (map.getPlayerRoom().getConnections().containsKey(tile))
				commandSet.add(3);

			else if (tile.getTrap() != null) {
				if (tile.getTrap().isDetected())
					commandSet.add(4);
			}
		}

		if (commandSet.contains(1))
			availableCommands += "\tATTACK [DIRECTION]\n";
		if (commandSet.contains(2))
			availableCommands += "\tMOVE [DIRECTION]\n";
		if (commandSet.contains(3))
			availableCommands += "\tEXIT [DIRECTION]\n";
		if (playerTile.getChest() != null)
			availableCommands += "\tLOOT\n";
		if (commandSet.contains(4))
			availableCommands += "\tDISARM [DIRECTION]\n";

		availableCommands += """
			---------------------------------
			Inventory Commands
			--------------------------------
                VIEW (INVENTORY)
                VIEW [BAG]
                VIEW [BAG] [ITEM]
                EQUIP [BAG] [ITEM] [WEAPON/ARMOR]
                UNEQUIP [WEAPON/ARMOR]
                USE [BAG] [ITEM]
                DESTROY [BAG] [ITEM]
                SWAP [OLD BAG] [NEW BAG] [ITEM]

			----------------------------------
			Game Commands
			----------------------------------
                LOAD [MAP]
                QUIT
            ----------------------------------
			""";

		return availableCommands;
	}

	public boolean handleAttack(Compass direction) {
		if(this.player.attack(direction)) {
			endTurn();
			return true;
		} else {
			return false;
		}
	}

	public boolean handleMove(Compass direction) {
		Room playerRoom = this.map.getPlayerRoom();
		Tile playerTile = this.player.getTile();
		int playerX = playerTile.getX();
		int playerY = playerTile.getY();

		Tile newTile = playerRoom.getTile(playerX + direction.getX(), playerY + direction.getY());
		if(newTile == null || !newTile.passable()) return false;

		playerTile.removeObjects();
		newTile.addObject(this.player);
		this.player.setTile(newTile);
		playerRoom.setPlayerTile(playerTile);

		for(Tile adjacent : newTile.getAdjacent()) {
			Trap trap = adjacent.getTrap();
			if(trap != null && !trap.isDetected()) {
				trap.detected();
			}
		}

		endTurn();
		return true;
	}

	public boolean handleExitRoom(Compass direction) {
		if(this.map.getPlayerRoom().handleExitRoom(direction)) {
			endTurn();
			return true;
		} else {
			return false;
		}
	}

	public boolean handleDisarmTrap(Compass direction) {
		Tile playerTile = player.getTile();
		Room playerRoom = map.getPlayerRoom();
		int playerX = playerTile.getX();
		int playerY = playerTile.getY();

		Tile tile = playerRoom.getTile(playerX + direction.getX(), playerY + direction.getY());
		if(tile == null) return false;
		Trap trap = tile.getTrap();
		if(trap == null || !trap.isDetected()) return false;

		trap.disarmAttempt();
		endTurn();
		return true;
	}

	public List<InventoryElement> handleOpenChest() {
		Chest chest = player.getTile().getChest();
		if(chest == null) return null;
		return chest.getContents();
	}

	public void handleCloseChest() {
		endTurn();
	}

	public List<InventoryElement> handlePickupItem(int index) {
		Chest chest = this.player.getTile().getChest();
		if(chest == null) return null;

		InventoryElement pickedUp = chest.handleLoot(index);
		if(pickedUp == null) return null;

		if(pickedUp instanceof Bag bag) {
			this.player.getInventory().addBag(bag);
		} else {
			this.player.getInventory().addItem(pickedUp);
		}

		return chest.getContents();
	}

	public boolean handleEquipItem(int bagPos, int itemPos) {
		InventoryElement item = this.player.getInventory().getItem(bagPos, itemPos);
		if(item == null) return false;
		return item.handleEquip(this.player);
	}

	public boolean handleUnequipItem(boolean isWeapon) {
		return isWeapon ? this.player.unequipWeapon() : this.player.unequipArmor();
	}

	public boolean handleUseItem(int bagPos, int itemPos) {
		InventoryElement item = this.player.getInventory().getItem(bagPos, itemPos);
		if(item == null) return false;

		if(item.handleUse(this.player)) {
			handleDestroyItem(bagPos, itemPos);
			endTurn();
			return true;
		} else {
			return false;
		}
	}

	public boolean handleDestroyItem(int bagPos, int itemPos) {
		return this.player.getInventory().removeItem(bagPos, itemPos);
	}

	public boolean handleSwapBag(int sourceBagPos, int destBagPos, int destItemPos) {
		return this.player.getInventory().swapBag(sourceBagPos, destBagPos, destItemPos);
	}

	public void handleQuitGame() {
		this.progressDB.save(this.map);
	}
	
	private void endTurn() {
		this.clock.completeTurn();
	}

	public boolean isOver() {
		return player.getHealth() == 0 || map.playerReachedGoal();
	}

}
