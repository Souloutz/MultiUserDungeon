package multiuserdungeon.map.tiles;

import java.util.ArrayList;
import java.util.List;

import multiuserdungeon.Game;
import multiuserdungeon.map.Tile;
import multiuserdungeon.map.TileObject;
import multiuserdungeon.inventory.*;

public class Merchant implements TileObject {
    
    private final String name;
    private final List<InventoryElement> store;
    private Tile tile;

    public Merchant (String name, List<InventoryElement> contents) {
        this.name = name;
        this.store = new ArrayList<>(contents);
        this.tile = null;
    }

    public Merchant(Merchant merchant) {
        this.name = merchant.name;
        this.store = new ArrayList<>(merchant.store);
        this.tile = null;
    }

    public String getName() {
        return this.name;
    }

    public Tile getTile() {
        return this.tile;
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

    public List<InventoryElement> getStore() {
        return this.store;
    }

    public int buyItem(InventoryElement item) {
        return item.getGoldValue() / 2;
    }

    public InventoryElement handleSale(int index) {
        if(index >= this.store.size()) return null;
        return this.store.remove(index);
    }

    public boolean isOpen() {
        return Game.getInstance().getMap().getPlayerRoom().isSafe() && Game.getInstance().getCurrentTime().isDay();
    }

    @Override
    public String toString() {
        return this.name + ", who has " + this.store.size() + " items(s) for sale";
    }

}
