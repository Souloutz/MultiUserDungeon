package multiuserdungeon.commands.responses;

import multiuserdungeon.map.tiles.Chest;

public class OpenChestResponse {

    public OpenChestResponse() {
        this.chest = chest;
    }

    public Chest getChest() {
        chest.handleLoot();
        return chest;
    }
}
