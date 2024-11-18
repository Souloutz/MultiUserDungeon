package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.map.Compass;

public class AttackAction implements Action<Integer> {

	private final Game receiver;
	private final Compass direction;

	public AttackAction(Game game, Compass direction) {
		this.receiver = game;
		this.direction = direction;
	}

	@Override
	public Integer execute() {
		if(!canExecute()) return -1;
		return this.receiver.handleAttack(this.direction);
	}

	@Override
	public boolean canExecute() {
		return this.receiver != null;
	}
}
