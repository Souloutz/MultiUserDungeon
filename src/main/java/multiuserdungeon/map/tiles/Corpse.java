package multiuserdungeon.map.tiles;

import multiuserdungeon.inventory.InventoryElement;

import java.util.ArrayList;
import java.util.List;

public class Corpse extends Chest {

    @Override
    public char getASCII() {
        return 'X';
    }

    public Corpse(String name, List<InventoryElement> contents) {
        super(name,contents);
    }

    public Corpse(Corpse corpse) {
        this(corpse.getName(), new ArrayList<>(corpse.getContents()));
    }

}