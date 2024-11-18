package multiuserdungeon.ui.views;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import multiuserdungeon.ui.JavaFXGUI;

public class GameView extends View {

	private final Scene scene;

	public GameView(JavaFXGUI gui) {
		super(gui);

		VBox root = generateVBox();

		root.getChildren().add(generateHeader());

		root.getChildren().add(generateLabel("GAME IN PROGRESS"));

		this.scene = new Scene(root);
	}

	@Override
	public Scene getScene() {
		return this.scene;
	}

}
