package multiuserdungeon.commands.map;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class LoadMapAction implements Action<Void> {

	private final Game receiver;
	private final String uri;
	
	public LoadMapAction(Game game, String uri) {
		this.receiver = game;
		this.uri = uri;
	}

	@Override
	public Void execute() {
		this.receiver.handleLoadMap(this.uri);
		return null;
	}

}
