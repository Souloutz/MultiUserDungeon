package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;
import multiuserdungeon.map.Map;

public class PrayAction implements Action<Boolean> {
    
    private final Game receiver;
    private final Profile profile;
    private final Map map;

    public PrayAction(Game game, Profile profile, Map map) {
        this.receiver = game;
        this.profile = profile;
        this.map = map;
    }

    @Override
    public Boolean execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public boolean canExecute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canExecute'");
    }
}
