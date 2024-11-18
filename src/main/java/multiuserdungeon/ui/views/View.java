package multiuserdungeon.ui.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import multiuserdungeon.ui.GUI;

public abstract class View {

	private final GUI gui;

	public View(GUI gui) {
		this.gui = gui;
	}

	public void switchView(View view) {
		this.gui.setScene(view.getScene());
	}

	public GUI getGUI() {
		return gui;
	}

	public VBox generateVBox() {
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setMaxWidth(Double.MAX_VALUE);
		vbox.setMaxHeight(Double.MAX_VALUE);
		return vbox;
	}

	public HBox generateHBox() {
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setMaxWidth(Double.MAX_VALUE);
		hbox.setMaxHeight(Double.MAX_VALUE);
		hbox.setPadding(new Insets(20));
		return hbox;
	}

	public Label generateHeader() {
		Label header = new Label("WELCOME TO THE MULTI-USER DUNGEON (R2)\nCreated by Team 5: Jack, Quinton, Luke, Mandy, Howard");
		header.setMaxWidth(Double.MAX_VALUE);
		header.setMaxHeight(Double.MAX_VALUE);
		header.setAlignment(Pos.CENTER);
		header.setTextAlignment(TextAlignment.CENTER);
		return header;
	}

	public Label generateLabel(String text) {
		Label label = new Label(text);
		label.setMaxWidth(Double.MAX_VALUE);
		label.setMaxHeight(Double.MAX_VALUE);
		label.setAlignment(Pos.CENTER);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setPadding(new Insets(20));
		return label;
	}

	public abstract Scene getScene();

}
