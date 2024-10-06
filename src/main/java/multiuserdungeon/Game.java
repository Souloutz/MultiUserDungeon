package multiuserdungeon;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import multiuserdungeon.clock.Clock;
import multiuserdungeon.inventory.InventoryElement;
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
	private final Clock clock;
	private ProgressDB progressDB;
	private Map map;
	private boolean gameQuit;

	public Game(Player player) {
		instance = this;
		this.player = player;
		this.clock = new Clock();
		this.progressDB = null;
		this.map = null;
		this.gameQuit = false;
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

	// Setter Methods
	public void setProgressDB(ProgressDB progressDB) {
		this.progressDB = progressDB;
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

	public void handleAttack(Compass direction) {
		player.attack(direction);
		endTurn();
	}

	public void handleMove(Compass direction) {
		Room playerRoom = this.map.getPlayerRoom();
		Tile playerTile = this.player.getTile();
		int playerX = playerTile.getX();
		int playerY = playerTile.getY();

		Tile newTile = playerRoom.getTile(playerX + direction.getX(), playerY + direction.getY());
		playerTile.removeObjects();
		newTile.addObject(this.player);
		this.player.setTile(newTile);
		playerRoom.setPlayerTile(playerTile);

		endTurn();
	}

	public void handleExitRoom(Compass direction) {
		map.getPlayerRoom().handleExitRoom(direction);

		endTurn();
	}

	public void handleDisarmTrap(Compass direction) {
		Tile playerTile = player.getTile();
		Room playerRoom = map.getPlayerRoom();
		int playerX = playerTile.getX();
		int playerY = playerTile.getY();

		Tile tile = playerRoom.getTile(playerX + direction.getX(), playerY + direction.getY());
		Trap trap = tile.getTrap();
		if(trap == null || !trap.isDetected()) return; // TODO: pretend like there's no trap there

		trap.disarmAttempt();
		endTurn();
	}

	public List<InventoryElement> handleOpenChest() {
		Chest chest = player.getTile().getChest();
		if(chest == null) return null;
		return chest.getContents();
	}

	public List<InventoryElement> handlePickupItem(int index) {
		Chest chest = player.getTile().getChest();
		if(chest == null) return null;

		player.pickupItem(chest.getContents().get(index));
		return chest.getContents();
	}

	public void handleCloseChest() {
		endTurn();
	}

	public String handleViewInventory() {
		return player.getInventory().toString();
	}

	public String handleViewBag(int bagPos) {
		return player.getInventory().getBag(bagPos).toString();
	}

	public String handleViewItem(int bagPos, int itemPos) {
		return player.getInventory().getItem(bagPos, itemPos).toString();
	}

	public boolean handleEquipItem(int bagPos, int itemPos) {
		InventoryElement item = this.player.getInventory().getItem(bagPos, itemPos);
		return item.handleEquip(this.player);
	}

	public boolean handleUnequipItem(boolean isWeapon) {
		return isWeapon ? this.player.unequipWeapon() : this.player.unequipArmor();
	}

	public void handleUseItem(int bagPos, int itemPos) {
		InventoryElement item = player.getInventory().getItem(bagPos, itemPos); 
		boolean success = item.handleUse(this.player);

		if(success) {
			handleDestroyItem(bagPos, itemPos);
			endTurn();
		} else {
			// TODO: return not usable
		}
	}

	public boolean handleDestroyItem(int bagPos, int itemPos) {
		return player.destroyItem(bagPos, itemPos);
	}

	public void handleSwapBag(int sourceBagPos, int destBagPos, int destItemPos) {
		player.getInventory().swapBag(sourceBagPos, destBagPos, destItemPos);
	}

	public void handleQuitGame() {
		this.gameQuit = true;
		progressDB.save(map);
	}

	public boolean hasGameEnd() {
		return gameQuit || player.getHealth() == 0 || map.playerReachedGoal();
	}
	
	private void endTurn() {
		this.clock.completeTurn();
	}

	public Map handleLoadMap(String uri) {
		this.map = progressDB.load(uri);
		return this.map;
	}

}
