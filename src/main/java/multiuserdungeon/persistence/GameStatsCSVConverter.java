package multiuserdungeon.persistence;

import com.google.gson.Gson;
import com.opencsv.bean.AbstractCsvConverter;
import multiuserdungeon.authentication.GameStats;

public class GameStatsCSVConverter extends AbstractCsvConverter {

	@Override
	public String convertToWrite(Object value) {
		return new Gson().toJson(value);
	}

	@Override
	public Object convertToRead(String s) {
		return new Gson().fromJson(s, GameStats.class);
	}

}
