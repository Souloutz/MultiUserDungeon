package multiuserdungeon.commands;

import multiuserdungeon.Game;
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
		return this.receiver.handleAttack(this.direction);
	}

}
