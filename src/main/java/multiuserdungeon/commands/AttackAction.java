package multiuserdungeon.commands;

import multiuserdungeon.Game;
import multiuserdungeon.map.Compass;

public class AttackAction implements Action<Void> {

	private Game receiver;
	private Compass direction;

	public AttackAction(Game game, Compass direction) {
		receiver = game;
		this.direction = direction;
	}

	@Override
	public Void execute() {
		receiver.handleAttack(direction);
		return null;
	}
}
