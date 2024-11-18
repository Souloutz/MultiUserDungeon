package multiuserdungeon.ui.views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import multiuserdungeon.Game;
import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.commands.authentication.BrowseMapAction;
import multiuserdungeon.commands.authentication.LoginAction;
import multiuserdungeon.commands.authentication.LogoutAction;
import multiuserdungeon.commands.authentication.QuitAction;
import multiuserdungeon.commands.authentication.RegisterAction;
import multiuserdungeon.ui.GUI;

public class GuestView extends View {

	private final Scene scene;

	public GuestView(GUI gui) {
		super(gui);

		VBox root = generateVBox();
		HBox registerHBox = generateHBox();
		HBox loginHBox = generateHBox();
		HBox browseHBox = generateHBox();
		HBox controlsHBox = generateHBox();

		root.getChildren().add(generateHeader());

		root.getChildren().add(generateLabel("LOGGED IN AS GUEST"));

		// Register Command
		Label registerLabel = generateLabel("Register:");
		TextField registerUsername = new TextField();
		registerUsername.setPromptText("Username");
		TextField registerPassword = new TextField();
		registerPassword.setPromptText("Password");
		TextField registerConfirmPassword = new TextField();
		registerConfirmPassword.setPromptText("Confirm Password");
		Button registerBtn = new Button("Enter");
		registerBtn.setOnAction(e -> {
			Authenticator auth = Authenticator.getInstance();
			new RegisterAction(auth, registerUsername.getText(), auth.getUser().getDescription(), registerPassword.getText(), registerConfirmPassword.getText()).execute();
			switchView(new ProfileView(getGUI()));
		});
		registerHBox.getChildren().add(registerLabel);
		registerHBox.getChildren().add(registerUsername);
		registerHBox.getChildren().add(registerPassword);
		registerHBox.getChildren().add(registerConfirmPassword);
		registerHBox.getChildren().add(registerBtn);
		root.getChildren().add(registerHBox);

		// Login Command
		Label loginLabel = generateLabel("Login:");
		TextField loginUsername = new TextField();
		loginUsername.setPromptText("Username");
		TextField loginPassword = new TextField();
		loginPassword.setPromptText("Password");
		Button loginBtn = new Button("Enter");
		loginBtn.setOnAction(e -> {
			new LoginAction(Authenticator.getInstance(), loginUsername.getText(), loginPassword.getText()).execute();
			switchView(new ProfileView(getGUI()));
		});
		loginHBox.getChildren().add(loginLabel);
		loginHBox.getChildren().add(loginUsername);
		loginHBox.getChildren().add(loginPassword);
		loginHBox.getChildren().add(loginBtn);
		root.getChildren().add(loginHBox);

		// Browse Command
		Label browseLabel = generateLabel("Browse:");
		TextField browseSaveFile = new TextField();
		browseSaveFile.setPromptText("Save File");
		Button browseBtn = new Button("Enter");
		browseBtn.setOnAction(e -> {
			new BrowseMapAction(Authenticator.getInstance(), browseSaveFile.getText()).execute();
			// TODO: Go to game view
		});
		browseHBox.getChildren().add(browseLabel);
		browseHBox.getChildren().add(browseSaveFile);
		browseHBox.getChildren().add(browseBtn);
		root.getChildren().add(browseHBox);

		// Controls
		Button logoutBtn = new Button("Logout");
		logoutBtn.setOnAction(e -> {
			new LogoutAction(Authenticator.getInstance()).execute();
			switchView(new PreAuthView(getGUI()));
		});
		Button quitBtn = new Button("Quit");
		quitBtn.setOnAction(e -> {
			new QuitAction(Authenticator.getInstance(), Game.getInstance()).execute();
			System.exit(0);
		});
		controlsHBox.getChildren().add(logoutBtn);
		controlsHBox.getChildren().add(quitBtn);
		root.getChildren().add(controlsHBox);

		this.scene = new Scene(root);
	}

	@Override
	public Scene getScene() {
		return this.scene;
	}

}
