package multiuserdungeon.persistence.adapters;

import multiuserdungeon.Game;
import multiuserdungeon.persistence.FileAdapter;

public class XMLAdapter implements FileAdapter {

	@Override
	public boolean saveGame(Game game) {
		return false;
	}

	@Override
	public Game loadGame(String filename) {
		return null;
	}

	@Override
	public boolean saveUser(User user) {
		return false;
	}

	@Override
	public Game loadUser(String filename) {
		return null;
	}

}
