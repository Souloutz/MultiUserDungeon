package multiuserdungeon.persistence.adapters;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.User;
import multiuserdungeon.persistence.FileAdapter;

public class XMLAdapter implements FileAdapter {

	@Override
	public String saveGame(Game game) {
		return null;
	}

	@Override
	public Game loadGame(String filename) {
		return null;
	}

	@Override
	public String saveUser(User user) {
		return null;
	}

	@Override
	public User loadUser(String filename) {
		return null;
	}

}
