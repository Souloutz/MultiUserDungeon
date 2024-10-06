package multiuserdungeon.commands.map;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class LoadMapAction implements Action<Void> {

	private Game receiver;
	private String uri;
	
	public LoadMapAction(Game game, String uri) {
		receiver = game;
		this.uri = uri;
	}

	@Override
	public Void execute() {
		receiver.handleLoadMap(uri);
		return null;
	}
}
