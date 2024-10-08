package multiuserdungeon.progress;

import multiuserdungeon.Game;

public interface ProgressDB {

	Game load(String uri);

	String save(Game game);

}
