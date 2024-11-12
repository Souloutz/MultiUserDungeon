package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;
import multiuserdungeon.map.Compass;

// TODO turn into interface
public class ExitRoomAction implements Action<Boolean> {

	private final Game receiver;
	private final Profile profile;
	private final Compass direction;

	public ExitRoomAction(Game game, Profile profile, Compass direction) {
		this.receiver = game;
		this.profile = profile;
		this.direction = direction;
	}

	@Override
	public Boolean execute() {
		if (canExecute())
			return this.receiver.handleExitRoom(this.direction);

		return false;
	}

	@Override
	public boolean canExecute() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'canExecute'");
	}
}
