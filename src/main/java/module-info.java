module MultiUserDungeon {

	requires java.sql;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.media;
	requires com.opencsv;
	requires com.google.gson;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.dataformat.xml;

	opens multiuserdungeon.authentication to com.google.gson;
	exports multiuserdungeon.authentication;
	exports multiuserdungeon.clock;
	exports multiuserdungeon.commands;
	exports multiuserdungeon.inventory;
	exports multiuserdungeon.map;
	exports multiuserdungeon.map.tiles;
	exports multiuserdungeon.map.tiles.shrine;
	exports multiuserdungeon.map.tiles.trap;
	exports multiuserdungeon.persistence;
	exports multiuserdungeon.persistence.adapters;
	exports multiuserdungeon.ui;
	exports multiuserdungeon.ui.views;
	exports multiuserdungeon;

}