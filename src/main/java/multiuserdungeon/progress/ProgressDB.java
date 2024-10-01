package multiuserdungeon.progress;

import multiuserdungeon.map.Map;

public interface ProgressDB {

	void load(String uri);

	String save(Map map);

}
