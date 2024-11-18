package multiuserdungeon.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import multiuserdungeon.ui.views.PreAuthView;
import multiuserdungeon.ui.views.View;

public class JavaFXGUI extends Application implements GUI {

	private Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		this.stage = stage;

		PreAuthView view = new PreAuthView(this);
		view.switchView(view);

		this.stage.setTitle("Multi-User Dungeon");
		this.stage.setWidth(1280);
		this.stage.setHeight(720);
		this.stage.setResizable(false);
		this.stage.show();
	}

	@Override
	public void setView(View view) {
		this.stage.setScene(view.getScene());
	}

}
