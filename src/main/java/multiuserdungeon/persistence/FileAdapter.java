package multiuserdungeon.persistence;

import multiuserdungeon.Game;

public interface FileAdapter {

	boolean saveGame(Game game);

	Game loadGame(String filename);

	boolean saveUser(User user);

	Game loadUser(String filename);

}
