package multiuserdungeon;

import java.util.*;

import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.authentication.GameStats;
import multiuserdungeon.authentication.Profile;
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
	private final boolean browsing;
	private final GameStats stats;
	private Shrine shrine;

	public Game(Player player, GameMap map, Clock clock, boolean browsing) {
		instance = this;
		this.player = player;
		this.map = map;
		this.clock = clock;
		this.browsing = browsing;
		this.stats = new GameStats();
		this.shrine = null;
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

	public int getTurn() {
		return this.clock.getTurn();
	}

	public Time getCurrentTime() {
		return this.clock.getCurrentTime();
	}

	public GameStats getStats() {
		return this.stats;
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
		if(newItem == null) return false;
		if(newItem instanceof Bag bag) {
			if(this.player.getInventory().addBag(bag)) {
				this.player.loseGold(newItem.getGoldValue());
				return true;
			} else {
				return false;
			}
		} else {
			if(this.player.getInventory().addItem(newItem)) {
				this.stats.addToItems(1);
				this.player.loseGold(newItem.getGoldValue());
				return true;
			} else {
				return false;
			}
		}
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
		if(chest == null) return false;

		if(index == -1) {
			InventoryElement pickedUp;
			while((pickedUp = chest.handleLoot(0)) != null) {
				if(pickedUp instanceof Bag bag) {
					if(!this.player.getInventory().addBag(bag)) return false;
				} else {
					this.stats.addToItems(1);
					if(!this.player.getInventory().addItem(pickedUp)) return false;
				}
			}
			return chest.getContents().isEmpty();
		} else {
			InventoryElement pickedUp = chest.handleLoot(index);
			if(pickedUp == null) return false;

			if(pickedUp instanceof Bag bag) {
				return this.player.getInventory().addBag(bag);
			} else {
				this.stats.addToItems(1);
				return this.player.getInventory().addItem(pickedUp);
			}
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

		Player newPlayer = null;
		for(Player player : newMap.getPlayers()) {
			if(player.getName().equals(this.player.getName())) {
				newPlayer = player;
			}
		}
		if(newPlayer == null) return null;

		Clock newClock = new Clock();
		Time newTime = this.clock.getCurrentTime().isDay() ? new Day(newClock) : new Night(newClock);
		newClock.setCurrentTime(newTime);
		newClock.setTurnCounter(this.clock.getTurn());

		return new Snapshot(newPlayer, newMap, newClock);
	}

	public void restoreGame(Snapshot snapshot) {
		this.player = snapshot.getPlayer();
		this.map = snapshot.getMap();
		this.clock = snapshot.getClock();

		// Place our player at the shrine
		this.player.setTile(this.shrine.getTile());
		this.shrine.getTile().addObject(this.player);
	}

	public void handleQuitGame() {
		if(!this.browsing) {
			((Profile) Authenticator.getInstance().getUser()).addToStats(this.stats);
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

		if(isDead()) {
			this.stats.addToGamesPlayed(1);
			handleQuitGame();
			return;
		} else if(this.player.getHealth() <= 0 && this.shrine != null) {
			this.stats.addToLivesLost(1);
			this.shrine.restoreGame();
			return;
		}

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

}
