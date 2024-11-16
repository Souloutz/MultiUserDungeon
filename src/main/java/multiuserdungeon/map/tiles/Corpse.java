package multiuserdungeon.map.tiles;

import multiuserdungeon.inventory.InventoryElement;
import java.util.List;

public class Corpse extends Chest {

    @Override
    public char getASCII() {
        return 'X';
    }

    public Corpse(String name, List<InventoryElement> contents) {
        super(name,contents);
    }

}