package multiuserdungeon.persistence;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.User;

public interface FileAdapter {

	String saveGame(Game game);

	Game loadGame(String filename);

	String saveUser(User user);

	User loadUser(String filename);

}
