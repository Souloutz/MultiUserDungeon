package multiuserdungeon;

import java.util.*;

import multiuserdungeon.authentication.User;
import multiuserdungeon.clock.*;
import multiuserdungeon.inventory.*;
import multiuserdungeon.inventory.elements.*;
import multiuserdungeon.map.*;
import multiuserdungeon.map.tiles.*;
import multiuserdungeon.map.tiles.shrine.Shrine;
import multiuserdungeon.map.tiles.trap.Trap;
import multiuserdungeon.persistence.PersistenceManager;
import multiuserdungeon.map.tiles.shrine.Snapshot;

public class Game {

	private static Game instance;
	private Player player;
	private GameMap map;
	private Clock clock;
	private Shrine shrine;
	private boolean quit;
	private boolean browsing; // true or false

	public Game(Player player, GameMap map, User user) {
		instance = this;
		this.player = player;
		this.map = map;
		this.clock = new Clock();
		this.shrine = null;
		this.quit = false;
	}

	public void setMap(GameMap map) {
		this.map = map;
	}

	public static Game getInstance() {
		return instance;
	}

	public Player getPlayer() {
		return this.player;
	}

	public GameMap getMap() {
		return this.map;
	}

	public Time getCurrentTime() {
		return this.clock.getCurrentTime();
	}

	public boolean handleLoadMap(String uri) {
		Game loaded = PersistenceManager.getInstance().loadGame(uri);
		if(loaded != null) {
			instance = loaded;
			return true;
		} else {
			return false;
		}
	}

	public int handleAttack(Compass direction) {
		int damage = this.player.attack(direction);
		if(damage != -1) endTurn();
		return damage;
		//TODO{add functionality here or in NPC to make corpse if it dies}
	}

	public boolean handleMove(Compass direction) {
		Room playerRoom = this.map.getPlayerRoom();
		Tile playerTile = this.player.getTile();
		int playerRow = playerTile.getRow();
		int playerCol = playerTile.getCol();

		Tile newTile = playerRoom.getTile(playerRow + direction.getRowOffset(), playerCol + direction.getColOffset());
		if(newTile == null || !newTile.passable()) return false;

		playerRoom.setPlayerTile(newTile);
		this.player.setTile(newTile);
		playerTile.removeObject(this.player);
		newTile.addObject(this.player);

		for(Tile adjacent : newTile.getAdjacent().values()) {
			Trap trap = adjacent.getTrap();
			if(trap != null && !trap.isDetected()) trap.detected();
		}
		Trap trap = newTile.getTrap();
		if(trap != null && !trap.isDetected()) trap.detected();

		endTurn();
		return true;
	}

	public boolean handleExitRoom(Compass direction) {
		if (this.map instanceof EndlessMap) {
			EndlessMap o = (EndlessMap)this.map;
			o.handleExitRoom(direction);
		}
		if(this.map.getPlayerRoom().handleExitRoom(direction)) {
			endTurn();
			return true;
		} else {
			return false;
		}
	}

	public boolean handleDisarmTrap(Compass direction) {
		Tile playerTile = this.player.getTile();
		Room playerRoom = this.map.getPlayerRoom();
		int playerRow = playerTile.getRow();
		int playerCol = playerTile.getCol();

		Tile tile = playerRoom.getTile(playerRow + direction.getRowOffset(), playerCol + direction.getColOffset());
		if(tile == null) return false;
		Trap trap = tile.getTrap();
		if(trap == null || !trap.isDetected()) return false;

		endTurn();
		return trap.disarmAttempt();
	}

	public boolean handlePray(){
		Shrine newShrine = this.player.getTile().getShrine();
		if(map.getPlayerRoom().isSafe() && shrine != null){
			this.shrine = newShrine;
			shrine.storeSnapshot();
			return true;
		}
		else{
			return false;
		}

	}

	public boolean handleBuyItem(int index) {
		// TODO;
		return false;
	}

	public boolean handleSellItem(int bagPos, int itemPos) {
		// TODO;
		return false;
	}

	public List<InventoryElement> handleOpen() {
		Chest chest = this.player.getTile().getChest();
		if (chest == null) return null;
		
		return chest.getContents();
	}

	public void handleClose() {
		endTurn();
	}

	public boolean handlePickupItem(int index) {
		Chest chest = this.player.getTile().getChest();
		if (chest == null) return false;

		if (index == -1) {
			InventoryElement pickedUp;
			while ((pickedUp = chest.handleLoot(0)) != null) {
				if (pickedUp instanceof Bag bag) {
					if (!this.player.getInventory().addBag(bag)) return false;
				} else {
					if (!this.player.getInventory().addItem(pickedUp)) return false;
				}
			}
			return chest.getContents().isEmpty();
		} else {
			InventoryElement pickedUp = chest.handleLoot(index);
			if (pickedUp == null) return false;

			if (pickedUp instanceof Bag bag)
				return this.player.getInventory().addBag(bag);
			else
				return this.player.getInventory().addItem(pickedUp);
		}
	}

	public String handleViewInventory() {
		StringBuilder inventoryString = new StringBuilder();
		Inventory inventory = this.player.getInventory();

		for (int index = 0; index < 6; index++)
			inventoryString.append("\n").append(inventory.viewBag(index));
		
		return inventoryString.toString();
	}

	public boolean handleEquipItem(int bagPos, int itemPos) {
		InventoryElement item = this.player.getInventory().getItem(bagPos, itemPos);
		if (item == null) return false;

		this.player.getInventory().removeItem(bagPos, itemPos);
		return item.handleEquip(this.player);
	}

	public boolean handleUnequipItem(boolean isWeapon) {
		return isWeapon ? this.player.unequipWeapon() : this.player.unequipArmor();
	}

	public boolean handleUseItem(int bagPos, int itemPos) {
		InventoryElement item = this.player.getInventory().getItem(bagPos, itemPos);
		if (item == null) return false;

		if (item.handleUse(this.player)) {
			handleDestroyItem(bagPos, itemPos);
			return true;
		} 

		return false;
	}

	public boolean handleDestroyItem(int bagPos, int itemPos) {
		return this.player.getInventory().removeItem(bagPos, itemPos);
	}

	public boolean handleSwapBag(int sourceBagPos, int destBagPos, int destItemPos) {
		return this.player.getInventory().swapBag(sourceBagPos, destBagPos, destItemPos);
	}

	public Snapshot createSnapshot(){

		//TODO:copy map
		GameMap newMap = new EndlessMap((EndlessMap)map);

		//player is part of map, so no need to copy again
		Player newPlayer = newMap.getPlayerRoom().getPlayerTile().getPlayer();

		//copy clock
		Clock newClock = new Clock();
		Time newTime;
		if(clock.getCurrentTime().isDay()){
			newTime = new Day(newClock);
		}
		else{
			newTime = new Night(newClock);
		}
		newClock.setCurrentTime(newTime);
		newClock.setTurnCounter(clock.getTurnCounter());


		return new Snapshot(newPlayer, newMap, newClock);
	}

	public void restoreGame(Snapshot snapshot){
		this.player = snapshot.getPlayer();
		this.map = snapshot.getMap();
		this.clock = snapshot.getClock();
	}

	public void handleQuitGame() {
		this.quit = true;
		this.shrine = null;
	}

	public String handleSaveGame() {
		return PersistenceManager.getInstance().saveGame(this);
	}
	
	public void endTurn() {
		this.player.getTile().getAdjacent().forEach((direction, tile) -> {
			NPC npc = tile.getNPC();
			if(npc == null) return;
			npc.attack(direction.getOpposite());
		});

		this.player.depleteBuffs();
		this.clock.completeTurn();
	}

	public boolean isOver() {
		//TODO: add if shrine is null (for endless map)
		return this.quit || this.player.getHealth() == 0 || this.map.playerReachedGoal();
	}
}
