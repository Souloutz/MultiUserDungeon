package multiuserdungeon;

import java.util.*;

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
	private final boolean browsing;

	public Game(Player player, GameMap map, Clock clock, boolean browsing) {
		instance = this;
		this.player = player;
		this.map = map;
		this.clock = clock;
		this.shrine = null;
		this.browsing = browsing;
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

	public int handleAttack(Compass direction) {
		int damage = this.player.attack(direction);
		if(damage != -1) endTurn();
		return damage;
		// TODO: Corpses?
	}

	public boolean handleMove(Compass direction) {
		Room playerRoom = this.map.getPlayerRoom();
		Tile playerTile = this.player.getTile();
		int playerRow = playerTile.getRow();
		int playerCol = playerTile.getCol();

		Tile newTile = playerRoom.getTile(playerRow + direction.getRowOffset(), playerCol + direction.getColOffset());
		if(newTile == null || !newTile.passable()) return false;

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
		this.map.handleExitRoom(direction);
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
		Shrine shrine = this.player.getTile().getShrine();
		if(shrine == null) return false;
		if(!this.map.getPlayerRoom().isSafe()) return false;

		this.shrine = shrine;
		shrine.storeSnapshot();
		endTurn();
		return true;
	}

	public List<InventoryElement> handleTalkToMerchant(Compass direction) {
		Tile merchantTile = this.player.getTile().getTile(direction);
		Merchant merchant = merchantTile.getMerchant();
		if(merchant == null || !merchant.isOpen()) return null;
		return merchant.getStore();
	}

	public boolean handleBuyItem(Compass direction, int index) {
		Tile merchantTile = this.player.getTile().getTile(direction);
		Merchant merchant = merchantTile.getMerchant();
		if(merchant == null || !merchant.isOpen()) return false;
		if(this.player.getGold() < merchant.getStore().get(index).getGoldValue()) return false;

		InventoryElement newItem = merchant.handleSale(index);
		this.player.loseGold(newItem.getGoldValue());
		this.player.getInventory().addItem(newItem);
		return true;
	}

	public boolean handleSellItem(Compass direction, int bagPos, int itemPos) {
		Tile merchantTile = this.player.getTile().getTile(direction);
		Merchant merchant = merchantTile.getMerchant();
		if(merchant == null || !merchant.isOpen()) return false;

		InventoryElement selling = this.player.getInventory().getItem(bagPos,itemPos);
		if(selling == null) return false;

		this.player.getInventory().removeItem(bagPos, itemPos);
		this.player.gainGold(merchant.buyItem(selling));
		return true;
	}

	public List<InventoryElement> handleOpenChest() {
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
		return this.player.getInventory().toString() + "\n\n\tWeapon: " + this.player.getWeapon() + "\n\tArmor: " + this.player.getArmor();
	}

	public String handleViewBag(int bagPos) {
		String bagString = this.player.getInventory().viewBag(bagPos);

		if (bagString != null) return bagString;
		return "Invalid bag specified, please try again.";
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
		if(item == null) return false;

		if(item.handleUse(this.player)) {
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

	public Snapshot createSnapshot() {
		EndlessMap newMap = new EndlessMap((EndlessMap) this.map);
		Player newPlayer = new Player(this.player);

		Clock newClock = new Clock();
		Time newTime = this.clock.getCurrentTime().isDay() ? new Day(newClock) : new Night(newClock);
		newClock.setCurrentTime(newTime);
		newClock.setTurnCounter(this.clock.getTurnCounter());

		return new Snapshot(newPlayer, newMap, newClock);
	}

	public void restoreGame(Snapshot snapshot){
		this.player = snapshot.getPlayer();
		this.map = snapshot.getMap();
		this.clock = snapshot.getClock();
	}

	public void handleQuitGame() {
		if(!this.browsing) {
			PersistenceManager.getInstance().saveGame(this);
		}
		instance = null;
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

	public boolean isGoal() {
		if(this.map instanceof PremadeMap m) {
			return m.playerReachedGoal();
		} else {
			return this.map.isInStartRoom();
		}
	}

	public boolean isDead() {
		return this.player.getHealth() <= 0 && this.shrine == null;
	}

	public void respawn() {
		// TODO: Restore via snapshot OR create corpse, call from handleAttack or something
	}

}
