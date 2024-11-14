package multiuserdungeon.commands.game;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class LoadMapAction implements Action<Boolean> {

	private final Game receiver;
    private final User user;
	private final String uri;
	
	public LoadMapAction(Game game, User user, String uri) {
		this.receiver = game;
        this.user = user;
		this.uri = uri;
	}

	@Override
	public Boolean execute() {
        if (canExecute())
		    return this.receiver.handleLoadMap(this.uri);
        
        return false;
	}

    @Override
    public boolean canExecute() {
        //TODO{Make very specific to determine if this action should be an option}
        if (user instanceof Profile)
            return true;

        return false;
    }
}