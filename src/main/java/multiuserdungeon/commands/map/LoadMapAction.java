package multiuserdungeon.commands.map;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class LoadMapAction implements Action<Boolean> {

	private final Game receiver;
	private final String uri;
	
	public LoadMapAction(Game game, String uri) {
		this.receiver = game;
		this.uri = uri;
	}

	@Override
	public Boolean execute() {
		return this.receiver.handleLoadMap(this.uri);
	}

}
