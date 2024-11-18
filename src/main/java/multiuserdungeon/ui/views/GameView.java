package multiuserdungeon.ui.views;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import multiuserdungeon.ui.GUI;

public class GameView extends View {

	private final Scene scene;

	public GameView(GUI gui) {
		super(gui);

		VBox root = generateVBox();

		root.getChildren().add(generateHeader());

		this.scene = new Scene(root);
	}

	@Override
	public Scene getScene() {
		return this.scene;
	}

}
