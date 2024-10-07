package multiuserdungeon;

import multiuserdungeon.map.Compass;
import multiuserdungeon.map.tiles.Player;
import multiuserdungeon.progress.JSONProgressDB;

import java.util.Arrays;
import java.util.Scanner;

public class UI {

	private static final String DIVIDER  = "=".repeat(150);
	private static final Scanner scanner = new Scanner(System.in);
	private static Game game;

	public static void main(String[] args) throws InterruptedException {
		printWelcomeMsg();
		game = new Game(createPlayer());
		game.setProgressDB(new JSONProgressDB());

		System.out.println(DIVIDER + "\nYou enter the first narrow doorway to begin your journey...");
		Thread.sleep(3000);
		printRoomDescription();
		printRoomLayout();

		while(!game.isOver()) {
			printAllCommands();
			processCommand();
		}

		scanner.close();
	}

	private static void printWelcomeMsg() {
		System.out.println(DIVIDER + "\n" +
						"WELCOME TO THE MULTI-USER DUNGEON (R1)\n" +
						"\tCreated by Team 5:\n" +
						"\t\tJack Barter, Quinton Miller, Luke Edwards, Mandy Yu, and Howard Kong\n" +
						DIVIDER);
	}

	private static Player createPlayer() {
		System.out.print("Enter your adventurer's name: ");
		String name = scanner.nextLine();
		System.out.print("Enter your adventurer's description: ");
		String description = scanner.nextLine();
		return new Player(name, description);
	}

	private static void printRoomDescription() {
		System.out.println(DIVIDER + "\nThe room becomes clearer as you make your way through the doorway. You see: " + game.getMap().getPlayerRoom().getFullDescription());
	}

	private static void printRoomLayout() {
		System.out.println(DIVIDER + "\nFrom a vantage point, the room looks like following:\n\n" + game.getMap().getPlayerRoom());
	}

	public static void printAllCommands() {
		String directions = String.join(", ", Arrays.stream(Compass.values()).map(Compass::name).toArray(String[]::new));
		System.out.println(DIVIDER + "\nALL COMMANDS\n\nDirections: " + directions + "\n\n" +
				"\tinventory -=- Views all of your bags and inventory stats.\n" +
				"\tbag <bag num> -=- Views the specified bag and its stats.\n" +
				"\tequip <bag num> <item num> -=- Equips the specified item (weapon/armor).\n" +
				"\tunequip <weapon/armor> -=- Unequips the current weapon or armor.\n" +
				"\tuse <bag num> <item num> -=- Uses the specified item (food/buffs).\n" +
				"\tdestroy <bag num> <item num> -=- Destroys the specified item to clear space.\n" +
				"\tswap <src bag num> <dest bag num> <dest bag num> -=- Swaps a larger unequipped bag with an equipped one, copying all items over.\n" +
				"\tload <uri> -=-= Loads a different saved map.\n" +
				"\topen -=- Opens the chest you are currently standing on.\n" +
				"\tpickup <chest num> -=- Pickups an item from the currently open chest.\n" +
				"\tclose -=- Closes the chest you are currently standing on.\n" +
				"\tmove <direction> -=- Moves in the specified direction within the room.\n" +
				"\texit <direction> -=- Exits the room with the given direction.\n" +
				"\tattack <direction> -=- Attacks a nearby creature.\n" +
				"\tquit -=- Quits the current game, saving all progress.\n");
	}

	private static void processCommand() {
		System.out.print("Enter a command: ");
		String command = scanner.nextLine();
	}

}
