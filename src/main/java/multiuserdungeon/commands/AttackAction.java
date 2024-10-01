package multiuserdungeon.commands;

import multiuserdungeon.Game;
import multiuserdungeon.map.Compass;

public class AttackAction implements Action {

	Game receiver;
	Compass direction;

	public AttackAction(Game game, Compass direction) {
		receiver = game;
		this.direction = direction;
	}

	@Override
	public void execute() {
		receiver.handleAttack(direction);
	}
}
