package multiuserdungeon.ui.views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.ui.GUI;

public class PreAuthView extends View {

	private final Scene scene;

	public PreAuthView(GUI gui) {
		super(gui);

		VBox root = generateVBox();
		HBox usernameBox = generateHBox();
		HBox descriptionBox = generateHBox();

		// Headers
		root.getChildren().add(generateHeader());
		root.getChildren().add(generateLabel("LOGIN AS GUEST"));

		// Username
		usernameBox.getChildren().add(generateLabel("Enter Username"));
		TextField username = new TextField();
		username.setPromptText("Username");
		usernameBox.getChildren().add(username);
		root.getChildren().add(usernameBox);

		// Description
		descriptionBox.getChildren().add(generateLabel("Enter Description"));
		TextField description = new TextField();
		description.setPromptText("User Description");
		descriptionBox.getChildren().add(description);
		root.getChildren().add(descriptionBox);

		// Enter
		Button enter = new Button("Enter");
		enter.setOnAction(e -> {
			Authenticator.getInstance().loginAsGuest(username.getText(), description.getText());
			switchView(new GuestView(getGUI()));
		});
		root.getChildren().add(enter);

		this.scene = new Scene(root);
	}

	@Override
	public Scene getScene() {
		return this.scene;
	}

}
