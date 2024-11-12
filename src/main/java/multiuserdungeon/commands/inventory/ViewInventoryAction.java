package multiuserdungeon.commands.inventory;

import java.util.List;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;
import multiuserdungeon.inventory.InventoryElement;

public class ViewInventoryAction implements Action<List<InventoryElement>> {
    
    private final Game receiver;
    private final Profile profile;

    public ViewInventoryAction(Game game, Profile profile) {
        this.receiver = game;
        this.profile = profile; 
    }

    @Override
    public List<InventoryElement> execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public boolean canExecute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canExecute'");
    }
}
