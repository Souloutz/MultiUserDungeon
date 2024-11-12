package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;
import multiuserdungeon.map.Compass;

public class AttackAction implements Action<Integer> {

	private final Game receiver;
	private final Profile profile;
	private final Compass direction;

	public AttackAction(Game game, Profile profile, Compass direction) {
		this.receiver = game;
		this.profile = profile;
		this.direction = direction;
	}

	@Override
	public Integer execute() {
		if (canExecute())
			return this.receiver.handleAttack(this.direction);

		return -1;
	}

	@Override
	public boolean canExecute() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'canExecute'");
	}
}
