package multiuserdungeon.commands.map;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class LoadMapAction implements Action {

	private Game receiver;
	private String uri;
	
	public LoadMapAction(Game game, String uri) {
		receiver = game;
		this.uri = uri;
	}

	@Override
	public void execute() {
		receiver.handleLoadMap(uri);
	}
}
