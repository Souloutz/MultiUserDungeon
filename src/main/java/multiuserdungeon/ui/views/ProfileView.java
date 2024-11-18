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
import multiuserdungeon.commands.authentication.ChangePasswordAction;
import multiuserdungeon.commands.authentication.LogoutAction;
import multiuserdungeon.commands.authentication.QuitAction;
import multiuserdungeon.commands.authentication.ViewHistoryAction;
import multiuserdungeon.commands.game.JoinGameAction;
import multiuserdungeon.commands.game.StartGameAction;
import multiuserdungeon.ui.JavaFXGUI;

public class ProfileView extends View {

	private final Scene scene;

	public ProfileView(JavaFXGUI gui) {
		super(gui);

		VBox root = generateVBox();
		HBox browseHBox = generateHBox();
		HBox startHBox = generateHBox();
		HBox joinHBox = generateHBox();
		HBox changePasswdHBox = generateHBox();
		HBox controlsHBox = generateHBox();

		root.getChildren().add(generateHeader());

		root.getChildren().add(generateLabel("LOGGED IN AS: " + Authenticator.getInstance().getUser().getUsername()));

		// Browse Command
		Label browseLabel = generateLabel("Browse:");
		TextField browseSaveFile = new TextField();
		browseSaveFile.setPromptText("Save File");
		Button browseBtn = new Button("Enter");
		browseBtn.setOnAction(e -> {
			new BrowseMapAction(Authenticator.getInstance(), browseSaveFile.getText()).execute();
			switchView(new GameView(getGUI()));
		});
		browseHBox.getChildren().add(browseLabel);
		browseHBox.getChildren().add(browseSaveFile);
		browseHBox.getChildren().add(browseBtn);
		root.getChildren().add(browseHBox);

		// Start Command
		Label startLabel = generateLabel("Start:");
		TextField startSaveFile = new TextField();
		startSaveFile.setPromptText("Save File");
		Button startBtn = new Button("Enter");
		startBtn.setOnAction(e -> {
			new StartGameAction(Authenticator.getInstance(), startSaveFile.getText()).execute();
			switchView(new GameView(getGUI()));
		});
		Button startEndlessBtn = new Button("Start (Endless)");
		startEndlessBtn.setOnAction(e -> {
			new StartGameAction(Authenticator.getInstance(), "endless_template").execute();
			switchView(new GameView(getGUI()));
		});
		startHBox.getChildren().add(startLabel);
		startHBox.getChildren().add(startSaveFile);
		startHBox.getChildren().add(startBtn);
		startHBox.getChildren().add(startEndlessBtn);
		root.getChildren().add(startHBox);

		// Join Command
		Label joinLabel = generateLabel("Join:");
		TextField joinSaveFile = new TextField();
		joinSaveFile.setPromptText("Save File");
		Button joinBtn = new Button("Enter");
		joinBtn.setOnAction(e -> {
			new JoinGameAction(Authenticator.getInstance(), joinSaveFile.getText()).execute();
			switchView(new GameView(getGUI()));
		});
		startHBox.getChildren().add(joinLabel);
		startHBox.getChildren().add(joinSaveFile);
		startHBox.getChildren().add(joinBtn);
		root.getChildren().add(joinHBox);

		// Change Password Command
		Label changePasswdLabel = generateLabel("Change Password:");
		TextField currentPassword = new TextField();
		currentPassword.setPromptText("Current Password");
		TextField newPassword = new TextField();
		newPassword.setPromptText("New Password");
		TextField confirmPassword = new TextField();
		confirmPassword.setPromptText("Confirm Password");
		Button changePasswdBtn = new Button("Enter");
		changePasswdBtn.setOnAction(e -> {
			new ChangePasswordAction(Authenticator.getInstance(), currentPassword.getText(), newPassword.getText(), confirmPassword.getText()).execute();
			switchView(new ProfileView(getGUI()));
		});
		changePasswdHBox.getChildren().add(changePasswdLabel);
		changePasswdHBox.getChildren().add(currentPassword);
		changePasswdHBox.getChildren().add(newPassword);
		changePasswdHBox.getChildren().add(confirmPassword);
		changePasswdHBox.getChildren().add(changePasswdBtn);
		root.getChildren().add(changePasswdHBox);

		// Controls
		Button historyBtn = new Button("History");
		historyBtn.setOnAction(e -> {
			String history = new ViewHistoryAction(Authenticator.getInstance()).execute();
			// TODO: Show history in modal?
		});
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
		controlsHBox.getChildren().add(historyBtn);
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
