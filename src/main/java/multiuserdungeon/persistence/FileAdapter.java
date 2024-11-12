package multiuserdungeon.persistence;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;

public interface FileAdapter {

	String saveGame(Game game);

	Game loadGame(String filename);

	String saveProfile(Profile profile);

	Profile loadProfile(String username);

}
