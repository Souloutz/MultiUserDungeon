package multiuserdungeon.commands.game;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;

public class LoadMapAction implements Action<Boolean> {

	private final Game receiver;
	private final Profile profile;
	private final String uri;
	
	public LoadMapAction(Game game, Profile profile, String uri) {
		this.receiver = game;
		this.profile = profile;
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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'canExecute'");
	}
}
