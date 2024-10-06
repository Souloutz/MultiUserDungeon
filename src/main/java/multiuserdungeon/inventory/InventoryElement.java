package multiuserdungeon.inventory;

import multiuserdungeon.map.tiles.Player;

public interface InventoryElement {

	String getName();

	String getDescription();

	int getGoldValue();

	int getOccupancy();

	boolean handleEquip(Player player);

	boolean handleUse(Player player);
    
}
