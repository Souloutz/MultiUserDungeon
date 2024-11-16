package multiuserdungeon.map.tiles;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import multiuserdungeon.map.Tile;
import multiuserdungeon.inventory.*;
import multiuserdungeon.*;
import multiuserdungeon.clock.*;


public class Merchant {
    private String name;
    private Map<InventoryElement, Integer> store;
    private Tile tile;

    public Merchant (String name, List<InventoryElement> contents) {
        this.name = name;
        this.store = new HashMap<>();
        for (InventoryElement item: contents) {
            store.put(item,item.getGoldValue());
            if (store.size() > 3) {
                break;
            }
        }
        this.tile = null;
    }

    public String getName() {
        return this.name;
    }

    public Tile getTile() {
        return tile;
    }
    public void setTile(Tile tile) {
        this.tile = tile;
    }
    public boolean passable() {
        return false;
    }
    public char getASCII() {
        return 'M';
    }
    public Map<InventoryElement, Integer> getStore() {
        return this.store;
    }
    public int buyItem (InventoryElement item) {
        return item.getGoldValue() / 2;
    }
    public Map<InventoryElement,Integer> handleSale(InventoryElement item) {
        Integer value = store.remove(item);
        Map<InventoryElement,Integer> sold = new HashMap<>();
        sold.put(item,value);
        return sold;
    }
    public boolean isOpen() {
        return Game.getInstance().getMap().getPlayerRoom().isSafe() && Game.getInstance().getCurrentTime() instanceof Day;
    }
}
