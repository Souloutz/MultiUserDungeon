package multiuserdungeon;

import java.util.List;

import multiuserdungeon.authentication.User;
import multiuserdungeon.clock.Clock;
import multiuserdungeon.clock.Time;
import multiuserdungeon.inventory.Inventory;
import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.inventory.elements.Bag;
import multiuserdungeon.map.Compass;
import multiuserdungeon.map.Map;
import multiuserdungeon.map.Room;
import multiuserdungeon.map.Tile;
import multiuserdungeon.map.tiles.Chest;
import multiuserdungeon.map.tiles.NPC;
import multiuserdungeon.map.tiles.Player;
import multiuserdungeon.map.tiles.trap.Trap;
import multiuserdungeon.persistence.PersistenceManager;

public class Game {

	private static Game instance;
	private final Player player;
	private final Map map;
	private final Clock clock;
	private boolean quit;
	private boolean browsing; // true or false

	public Game(Player player, Map map, User user) {
		instance = this;
		this.player = player;
		this.map = new Map();
		this.clock = new Clock();
		this.quit = false;
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
		// TODO handle exiting room based on type of map
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

	public boolean handlePray() {
		// TODO
		return false;
	}

	public List<InventoryElement> handleTalkToMerchant(Compass direction) {
		// TODO
		return null;
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
		StringBuilder inventoryString = new StringBuilder(this.player.getInventory().toString());
		inventoryString.append("\n\n\tWeapon: ").append(player.getWeapon()).append("\n\tArmor: ").append(player.getArmor());
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

	public void handleQuitGame() {
		this.quit = true;
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
		return this.quit || this.player.getHealth() == 0 || this.map.playerReachedGoal();
	}
}
