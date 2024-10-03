package multiuserdungeon.commands.movement;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.commands.responses.MoveResponse;
import multiuserdungeon.map.Compass;

public class MoveAction implements Action<MoveResponse> {

	private Game receiver;
	private Compass direction;

	public MoveAction(Game game, Compass direction) {
		receiver = game;
		this.direction = direction;
	}

	@Override
	public MoveResponse execute() {
		receiver.handleMove(direction);
		
		// TODO 
		return MoveResponse.SUCCESS;
	}
}
