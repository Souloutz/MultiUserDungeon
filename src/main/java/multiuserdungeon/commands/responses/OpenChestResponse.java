package multiuserdungeon.commands.responses;

import multiuserdungeon.map.tiles.Chest;

public class OpenChestResponse {
    
    Chest chest;

    public OpenChestResponse(Chest chest) {
        this.chest = chest;
    }

    public Chest getChest() {
        chest.handleLoot();
        return chest;
    }
}
