package multiuserdungeon.progress;

import multiuserdungeon.map.Map;

public interface ProgressDB {

	Map load(String uri);

	String save(Map map);

}
