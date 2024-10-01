package multiuserdungeon;

import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.map.Compass;
import multiuserdungeon.map.Map;
import multiuserdungeon.map.tiles.Player;
import multiuserdungeon.progress.ProgressDB;

public class Game {

	private static Game instance;

	public Game(Player player) {
		instance = this;
	}

	public static Game getInstance() {
		return instance;
	}

	public void generateNewMap() {

	}

	public Map handleLoadMap(String uri) {
		return null;
	}

	public void save() {

	}

	public void setProgressDB(ProgressDB progressDB) {

	}

	public void handleMove(Compass direction) {

	}

	public void handleExitRoom(Compass direction) {

	}

	public void handleDisableTrap(Compass direction) {

	}

	public void handleOpenChest() {

	}

	public void handlePickupItem(InventoryElement item) {

	}

	public void handleAttack(Compass direction) {

	}

	public void handleEquipItem(int bagPos, int itemPos) {

	}

	public void handleUnequipItem(int bagPos, int itemPos) {

	}

	public void handleUseItem(int bagPos, int itemPos) {

	}

	public void handleDestroyItem(int bagPos, int itemPos) {

	}

	public void handleSwapBag(int sourceBagPos, int destBagPos) {

	}

	public void handleQuitGame() {

	}

}
