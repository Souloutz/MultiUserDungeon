package multiuserdungeon.ui;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.commands.authentication.BrowseMapAction;
import multiuserdungeon.commands.authentication.ChangePasswordAction;
import multiuserdungeon.commands.authentication.LoginAction;
import multiuserdungeon.commands.authentication.LogoutAction;
import multiuserdungeon.commands.authentication.QuitAction;
import multiuserdungeon.commands.authentication.RegisterAction;
import multiuserdungeon.commands.authentication.ViewHistoryAction;
import multiuserdungeon.commands.game.JoinGameAction;
import multiuserdungeon.commands.game.StartGameAction;
import multiuserdungeon.commands.inventory.DestroyItemAction;
import multiuserdungeon.commands.inventory.EquipItemAction;
import multiuserdungeon.commands.inventory.SwapBagAction;
import multiuserdungeon.commands.inventory.UnequipItemAction;
import multiuserdungeon.commands.inventory.UseItemAction;
import multiuserdungeon.commands.inventory.ViewBagAction;
import multiuserdungeon.commands.inventory.ViewInventoryAction;
import multiuserdungeon.commands.player.AttackAction;
import multiuserdungeon.commands.player.BuyItemAction;
import multiuserdungeon.commands.player.CloseAction;
import multiuserdungeon.commands.player.DisarmTrapAction;
import multiuserdungeon.commands.player.ExitRoomAction;
import multiuserdungeon.commands.player.MoveAction;
import multiuserdungeon.commands.player.OpenChestAction;
import multiuserdungeon.commands.player.PickupItemAction;
import multiuserdungeon.commands.player.PrayAction;
import multiuserdungeon.commands.player.SellItemAction;
import multiuserdungeon.commands.player.TalkToMerchantAction;
import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.map.Compass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class PTUI {

	private static final String DIVIDER  = "=".repeat(150);
	private static final Scanner scanner = new Scanner(System.in);
	private static boolean quit = false;

	private static boolean inMenu = false;
	private static final List<String> ALLOWED_MENU_COMMANDS = new ArrayList<>(List.of(
		"open", "talk", "pickup", "buy", "sell", "close"
	));

	public static void main(String[] args) {
		printWelcomeMsg();
		promptGuestLogin();
		promptStarterCommands();
		scanner.close();
	}

	private static void printBlock(String msg) {
		System.out.println(msg + "\n" + DIVIDER);
	}

	private static void printWelcomeMsg() {
		printBlock(DIVIDER + "\nWELCOME TO THE MULTI-USER DUNGEON (R2)\n" +
				"\tCreated by Team 5:\n" +
				"\t\tJack Barter, Quinton Miller, Luke Edwards, Mandy Yu, and Howard Kong");
	}

	private static void promptGuestLogin() {
		printBlock("LOGIN AS GUEST FIRST");
		System.out.print("Enter Username: ");
		String username = scanner.nextLine();
		System.out.print("Enter Player Description: ");
		String description = scanner.nextLine();
		Authenticator.getInstance().loginAsGuest(username, description);
	}

	private static void promptStarterCommands() {
		while(!quit) {
			printStarterCommands();

			try {
				processStarterCommand();
				if(Authenticator.getInstance().getUser() == null) promptGuestLogin();
				if(Game.getInstance() != null) promptGameCommands();
			} catch(IndexOutOfBoundsException | IllegalArgumentException ignored) {
				printBlock("Unable to parse command arguments, please try again.");
			}
		}
	}

	private static void promptGameCommands() {
		printRoomDescription();

		while(Game.getInstance() != null) {
			printRoomLayout();
			printGameCommands();

			try {
				processGameCommand();
			} catch(IndexOutOfBoundsException | IllegalArgumentException ignored) {
				printBlock("Unable to parse command arguments, please try again.");
			}
		}
	}

	private static boolean promptToQuit() {
		printBlock("Congrats! You have reached your goal or safe room, would you like to quit and save? (Y/N)");
		System.out.print(">>> ");
		String response = scanner.nextLine();
		return response.equals("Y");
	}

	private static void printStarterCommands() {
		printBlock("STARTER COMMANDS\n\n" +
				"\tregister <username> <passwd> <confirm passwd> -=- Register a new profile if logged out.\n" +
				"\tlogin <username> <password> -=- Login to an existing profile to continue your journey.\n" +
				"\tpasswd <passwd> <confirm passwd> -=- Change your password if logged in.\n" +
				"\tlogout -=- Logout of the current profile if logged in.\n" +
				"\thistory -=- View your profile's history if logged in.\n" +
				"\tbrowse <save> -=- Browse a premade map without starting a game.\n" +
				"\tstart <save/endless> -=- Start a new game using a premade save or an endless game if logged in.\n" +
				"\tjoin <endless save> -=- Join a pre-existing endless save if logged in.\n" +
				"\tquit -=- Quits the application, saving your profile if logged in.\n");
	}

	public static void printGameCommands() {
		String directions = String.join(", ", Arrays.stream(Compass.values()).map(Compass::name).toArray(String[]::new));
		printBlock("ALL COMMANDS\n\n\tDirections: " + directions + "\n\n" +
				"\tinventory -=- Views all of your bags and inventory stats.\n" +
				"\tbag <bag pos> -=- Views the specified bag and its stats.\n" +
				"\tequip <bag pos> <item pos> -=- Equips the specified item (weapon/armor).\n" +
				"\tunequip <weapon/armor> -=- Unequips the current weapon or armor.\n" +
				"\tuse <bag pos> <item pos> -=- Uses the specified item (food/buffs).\n" +
				"\tdestroy <bag pos> <item pos> -=- Destroys the specified item to clear space.\n" +
				"\tswap <src bag pos> <dest bag pos> <dest bag pos> -=- Swaps a larger unequipped bag with an equipped one, copying all items over.\n" +
				"\topen -=- Opens the chest you are currently standing on.\n" +
				"\ttalk <direction> -=- Talks to a nearby merchant if they are open.\n" +
				"\tpickup [chest pos] -=- Pickups all items in a chest, or just specific items.\n" +
				"\tbuy <direction> <merchant pos> -=- Buys an item from a nearby merchant.\n" +
				"\tsell <direction> <bag pos> <item pos> -=- Sells an item to a nearby merchant.\n" +
				"\tclose -=- Closes the chest or merchant you are currently interacting with.\n" +
				"\tdisarm <direction> -=- Attempts to disarm a detected trap in the specified direction.\n" +
				"\tpray -=- Prays at the current shrine if the room is safe.\n" +
				"\tmove <direction> -=- Moves in the specified direction within the room.\n" +
				"\tattack <direction> -=- Attacks a nearby creature.\n" +
				"\texit <direction> -=- Exits the room with the given direction.\n" +
				"\tquit -=- Quits the current game, saving progress if not browsing.\n");
	}

	private static void processStarterCommand() {
		System.out.print(">>> ");
		String[] args = scanner.nextLine().split(" ");

		switch(args[0]) {
			case "register" -> {
				Authenticator auth = Authenticator.getInstance();
				boolean result = new RegisterAction(auth, args[1], auth.getUser().getDescription(), args[2], args[3]).execute();
				if(result) {
					printBlock("Successfully registered as " + args[1] + "!");
				} else {
					printBlock("You cannot run this command, or the passwords did not match, please try again.");
				}
			}
			case "login" -> {
				boolean result = new LoginAction(Authenticator.getInstance(), args[1], args[2]).execute();
				if(result) {
					printBlock("Successfully logged in!");
				} else {
					printBlock("You cannot run this command, or the username and password were invalid.");
				}
			}
			case "passwd" -> {
				boolean result = new ChangePasswordAction(Authenticator.getInstance(), args[1], args[2], args[3]).execute();
				if(result) {
					printBlock("Successfully changed your password!");
				} else {
					printBlock("You cannot run this command, or there was an error with the passwords.");
				}
			}
			case "logout" -> {
				boolean result = new LogoutAction(Authenticator.getInstance()).execute();
				if(result) {
					printBlock("Successfully logged out!");
				} else {
					printBlock("You cannot run this command.");
				}
			}
			case "history" -> {
				String history = new ViewHistoryAction(Authenticator.getInstance()).execute();
				printBlock(Objects.requireNonNullElse(history, "You cannot run this command."));
			}
			case "browse" -> {
				new BrowseMapAction(Authenticator.getInstance(), args[1]).execute();
				if(Game.getInstance() == null) {
					printBlock("Error browsing that game save! Is it a premade template?");
				}
			}
			case "start" -> {
				if(args[1].equals("endless")) {
					new StartGameAction(Authenticator.getInstance(), "endless_template").execute();
				} else {
					new StartGameAction(Authenticator.getInstance(), args[1]).execute();
				}
				if(Game.getInstance() == null) {
					printBlock("Error starting the game! Are you logged in?");
				}
			}
			case "join" -> {
				new JoinGameAction(Authenticator.getInstance(), args[1]).execute();
				if(Game.getInstance() == null) {
					printBlock("Error joining the game! Are you logged in? Is it an endless save?");
				}
			}
			case "quit" -> {
				new QuitAction(Authenticator.getInstance(), Game.getInstance()).execute();
				quit = true;
			}
		}
	}

	private static void processGameCommand() throws IndexOutOfBoundsException, IllegalArgumentException {
		System.out.print(">>> ");
		String[] args = scanner.nextLine().toLowerCase().split(" ");

		if(inMenu && !ALLOWED_MENU_COMMANDS.contains(args[0])) {
			printBlock("You cannot run this command without running the \"close\" command first.");
			return;
		}

		switch(args[0]) {
			case "inventory" -> printBlock(new ViewInventoryAction(Game.getInstance()).execute());
			case "bag" -> {
				int bagPos = Integer.parseInt(args[1]);
				printBlock(new ViewBagAction(Game.getInstance(), bagPos).execute());
			}
			case "equip" -> {
				int bagPos = Integer.parseInt(args[1]);
				int itemPos = Integer.parseInt(args[2]);
				boolean result = new EquipItemAction(Game.getInstance(), bagPos, itemPos).execute();
				if(result) {
					printBlock("Successfully equipped the item.");
				} else {
					printBlock("Unknown item or item is not able to be equipped, please try again.");
				}
			}
			case "unequip" -> {
				boolean isWeapon = args[1].equals("weapon");
				boolean result = new UnequipItemAction(Game.getInstance(), isWeapon).execute();
				if(result) {
					printBlock("Successfully unequipped the item.");
				} else {
					printBlock("That type of item is not currently equipped.");
				}
			}
			case "use" -> {
				int bagPos = Integer.parseInt(args[1]);
				int itemPos = Integer.parseInt(args[2]);
				boolean result = new UseItemAction(Game.getInstance(), bagPos, itemPos).execute();
				if(result) {
					printBlock("Successfully used the item.");
				} else {
					printBlock("Unknown item or item is not usable, please try again.");
				}
			}
			case "destroy" -> {
				int bagPos = Integer.parseInt(args[1]);
				int itemPos = Integer.parseInt(args[2]);
				boolean result = new DestroyItemAction(Game.getInstance(), bagPos, itemPos).execute();
				if(result) {
					printBlock("Successfully destroyed the item.");
				} else {
					printBlock("Unknown item, please try again.");
				}
			}
			case "swap" -> {
				int sourceBagPos = Integer.parseInt(args[1]);
				int destBagPos = Integer.parseInt(args[2]);
				int destItemPos = Integer.parseInt(args[3]);
				boolean result = new SwapBagAction(Game.getInstance(), sourceBagPos, destBagPos, destItemPos).execute();
				if(result) {
					printBlock("Successfully swapped the bags.");
				} else {
					printBlock("Unknown bags or the destination bag is smaller than the source, please try again.");
				}
			}
			case "open" -> {
				List<InventoryElement> contents = new OpenChestAction(Game.getInstance()).execute();
				if(contents != null) {
					StringBuilder builder = new StringBuilder("Chest Contents (" + contents.size() + " items)");
					for(int i = 0; i < contents.size(); i++) {
						builder.append("\n\t").append(i).append(": ").append(contents.get(i));
					}
					printBlock(builder.toString());
					inMenu = true;
				} else {
					printBlock("You are not currently on a chest tile, please try again.");
				}
			}
			case "talk" -> {
				Compass direction = Compass.valueOf(args[1].toUpperCase());
				List<InventoryElement> store = new TalkToMerchantAction(Game.getInstance(), direction).execute();
				if(store != null) {
					StringBuilder builder = new StringBuilder("Items for Sale (" + store.size() + " items)");
					for(int i = 0; i < store.size(); i++) {
						builder.append("\n\t").append(i).append(": ").append(store.get(i));
					}
					printBlock(builder.toString());
					inMenu = true;
				} else {
					printBlock("A merchant is not open in that direction, please try again.");
				}
			}
			case "pickup" -> {
				int chestPos = args.length == 1 ? -1 : Integer.parseInt(args[1]);
				boolean result = new PickupItemAction(Game.getInstance(), chestPos).execute();
				if(result) {
					printBlock("Successfully picked up the item(s).");
				} else {
					printBlock("You are not currently on a chest tile, your inventory is full, or the chest position is incorrect, please try again.");
				}
			}
			case "buy" -> {
				Compass direction = Compass.valueOf(args[1].toUpperCase());
				int chestPos = Integer.parseInt(args[2]);
				boolean result = new BuyItemAction(Game.getInstance(), direction, chestPos).execute();
				if(result) {
					printBlock("Successfully bought the item.");
				} else {
					printBlock("A merchant is not open in that direction, you cannot afford the item, your inventory is full, or the merchant item is incorrect, please try again.");
				}
			}
			case "sell" -> {
				Compass direction = Compass.valueOf(args[1].toUpperCase());
				int bagPos = Integer.parseInt(args[2]);
				int itemPos = Integer.parseInt(args[3]);
				boolean result = new SellItemAction(Game.getInstance(), direction, bagPos, itemPos).execute();
				if(result) {
					printBlock("Successfully sold the item.");
				} else {
					printBlock("A merchant is not open in that direction, or an unknown item was provided, please try again.");
				}
			}
			case "close" -> {
				if(!inMenu) {
					printBlock("You do not currently have a chest open, please try again.");
					break;
				}
				new CloseAction(Game.getInstance()).execute();
				printBlock("Successfully closed the chest.");
				inMenu = false;
			}
			case "disarm" -> {
				Compass direction = Compass.valueOf(args[1].toUpperCase());
				boolean result = new DisarmTrapAction(Game.getInstance(), direction).execute();
				if(result) {
					printBlock("Successfully disarmed the trap! Great job.");
				} else {
					printBlock("You failed to disarm the trap, or there is no trap on that tile.");
				}
			}
			case "pray" -> {
				boolean result = new PrayAction(Game.getInstance()).execute();
				if(result) {
					printBlock("You feel a snapshot of the world around you being captured.");
				} else {
					printBlock("You are not atop a shrine, or the room is not yet safe to pray here.");
				}
			}
			case "move" -> {
				Compass direction = Compass.valueOf(args[1].toUpperCase());
				boolean result = new MoveAction(Game.getInstance(), direction).execute();
				if(result) {
					printBlock("Successfully moved " + direction + ".");
				} else {
					printBlock("Unable to move in that direction, please try again.");
				}
			}
			case "attack" -> {
				// TODO: Find a way to add more information on who they attacked in response
				Compass direction = Compass.valueOf(args[1].toUpperCase());
				int damage = new AttackAction(Game.getInstance(), direction).execute();
				if(damage != -1) {
					printBlock("Successfully attacked " + direction + " and dealt " + damage + " damage.");
				} else {
					printBlock("Unable to attack that tile, please try again.");
				}
			}
			case "exit" -> {
				Compass direction = Compass.valueOf(args[1].toUpperCase());
				boolean result = new ExitRoomAction(Game.getInstance(), direction).execute();
				if(result) {
					printRoomDescription();

					if(Game.getInstance().isGoal() && promptToQuit()) {
						new QuitAction(Authenticator.getInstance(), Game.getInstance()).execute();
						printBlock("Successfully quit the game!");
					}
				} else {
					printBlock("A doorway in that direction does not exist, or you are not at a doorway, please try again.");
				}
			}
			case "quit" -> {
				new QuitAction(Authenticator.getInstance(), Game.getInstance()).execute();
				printBlock("Successfully quit the game!");
			}
			default -> printBlock("Unrecognized command, please try again.");
		}
	}

	private static void printRoomDescription() {
		printBlock("The room becomes clearer as you make your way through the doorway. You see: " +
				Game.getInstance().getMap().getPlayerRoom().getFormattedDescription());
	}

	private static void printRoomLayout() {
		printBlock("You, " + Game.getInstance().getPlayer() + ", find a vantage point of the room. The skylight reveals that it is currently " +
				Game.getInstance().getCurrentTime() + ".\n" + "From above, the room appears like:\n\n" + Game.getInstance().getMap().getPlayerRoom());
	}

}