package multiuserdungeon;

import multiuserdungeon.commands.player.AttackAction;
import multiuserdungeon.commands.player.BuyItemAction;
import multiuserdungeon.commands.authentication.QuitAction;
import multiuserdungeon.commands.inventory.DestroyItemAction;
import multiuserdungeon.commands.inventory.EquipItemAction;
import multiuserdungeon.commands.inventory.SwapBagAction;
import multiuserdungeon.commands.inventory.UnequipItemAction;
import multiuserdungeon.commands.inventory.UseItemAction;
import multiuserdungeon.commands.inventory.ViewBagAction;
import multiuserdungeon.commands.inventory.ViewInventoryAction;
import multiuserdungeon.commands.player.CloseAction;
import multiuserdungeon.commands.player.DisarmTrapAction;
import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.commands.authentication.ChangePasswordAction;
import multiuserdungeon.commands.authentication.LoginAction;
import multiuserdungeon.commands.authentication.LogoutAction;
import multiuserdungeon.commands.authentication.RegisterAction;
import multiuserdungeon.commands.player.OpenChestAction;
import multiuserdungeon.commands.player.ExitRoomAction;
import multiuserdungeon.commands.player.MoveAction;
import multiuserdungeon.commands.player.PickupItemAction;
import multiuserdungeon.commands.player.PrayAction;
import multiuserdungeon.commands.player.SellItemAction;
import multiuserdungeon.commands.player.TalkToMerchantAction;
import multiuserdungeon.commands.authentication.BrowseMapAction;
import multiuserdungeon.commands.game.JoinGameAction;
import multiuserdungeon.commands.game.StartGameAction;
import multiuserdungeon.commands.authentication.ViewHistoryAction;
import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.map.Compass;
import multiuserdungeon.map.EndlessMap;
import multiuserdungeon.map.tiles.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PTUI {

	private static final String DIVIDER  = "=".repeat(150);
	private static final int DELAY_MS = 3000;
	private static final Scanner scanner = new Scanner(System.in);
	private static final Authenticator authenticator = Authenticator.getInstance();
	private static Game game;
	private static boolean inGame = false;
	private static boolean inMenu = false;
	private static boolean exit = false;

	public static void main(String[] args) throws InterruptedException {
		printWelcomeMsg();
		printProfileCommands();
		
		while (true) {

			try {
				processProfileCommand();
			} catch (IndexOutOfBoundsException | IllegalArgumentException ignored) {
				printBlock("Unable to parse command arguments, please try again.");
			}

			while (!game.isOver() && inGame) {
				printGameCommands();
				printRoomLayout();
	
				try {
					processInGameCommand();
				} catch (IndexOutOfBoundsException | IllegalArgumentException ignored) {
					printBlock("Unable to parse command arguments, please try again.");
				}
			}

			if (exit)
				break;
		}

		printBlock("GAME OVER\n\n\tThank you for playing, " + game.getPlayer().getName() + "!");
		scanner.close();
	}

	private static void printBlock(String msg) {
		System.out.println(msg + "\n" + DIVIDER);
	}

	private static void printWelcomeMsg() {
		printBlock(DIVIDER + "\nWELCOME TO THE MULTI-USER DUNGEON (R1)\n" +
				"\tCreated by Team 5:\n" +
				"\t\tJack Barter, Quinton Miller, Luke Edwards, Mandy Yu, and Howard Kong");
	}

	private static Player createPlayer() {
		System.out.print("Enter your adventurer's name: ");
		String name = scanner.nextLine();
		System.out.print("Enter your adventurer's description: ");
		String description = scanner.nextLine();
		System.out.println(DIVIDER);
		return new Player(name, description);
	}

	private static void printRoomDescription() throws InterruptedException {
		printBlock("The room becomes clearer as you make your way through the doorway. You see: " + game.getMap().getPlayerRoom().getFormattedDescription());
		Thread.sleep(DELAY_MS);
	}

	private static void printRoomLayout() {
		printBlock("You, " + game.getPlayer() + ", find a vantage point of the room. The skylight reveals that it is currently " + game.getCurrentTime() + ".\n" +
				"From above, the room appears like:\n\n" + game.getMap().getPlayerRoom());
	}

	public static void printProfileCommands() {
		// TODO: Cut down to only contextual relevant commands
		// This will  be done by checking canExecute of each command
		String directions = String.join(", ", Arrays.stream(Compass.values()).map(Compass::name).toArray(String[]::new));
		printBlock("ALL COMMANDS\n\n\tDirections: " + directions + "\n\n" +
				"\tinventory -=- Views all of your bags and inventory stats.\n" +
				"\tbag <bag pos> -=- Views the specified bag and its stats.\n" +
				"\tequip <bag pos> <item pos> -=- Equips the specified item (weapon/armor).\n" +
				"\tunequip <weapon/armor> -=- Unequips the current weapon or armor.\n" +
				"\tuse <bag pos> <item pos> -=- Uses the specified item (food/buffs).\n" +
				"\tdestroy <bag pos> <item pos> -=- Destroys the specified item to clear space.\n" +
				"\tswap <src bag pos> <dest bag pos> <dest bag pos> -=- Swaps a larger unequipped bag with an equipped one, copying all items over.\n" +
				"\tload <uri> -=-= Loads a different saved map.\n" +
				"\topen -=- Opens the chest you are currently standing on.\n" +
				"\tpickup [chest pos] -=- Pickups all items in a chest, or just specific items.\n" +
				"\tclose -=- Closes the chest you are currently standing on.\n" +
				"\tdisarm <direction> -=- Attempts to disarm a detected trap in the specified direction.\n" +
				"\tmove <direction> -=- Moves in the specified direction within the room.\n" +
				"\texit <direction> -=- Exits the room with the given direction.\n" +
				"\tattack <direction> -=- Attacks a nearby creature.\n" +
				"\tquit -=- Quits the current game, saving all progress.\n");
		printBlock("PROFILE COMMANDS\n\n\t: \n\n" +
				"\tlogin <username> <password> -=- Login to your profile and gain access to other commands.\n" +
				"\tlogout -=- Logout of your profile.\n" +
				"\tpassword <old password> <new password> <confirm password> -=- Change your profile password.\n" +
				"\tregister <username> <password> -=- Register a new profile (username must be unique).\n" +
				"\tbrowse <file path> -=- Browse a premade map without playing.\n" +
				"\tjoin <file path> -=- Join an existing endless adventure map game.\n" +
				"\tresume <file path> -=- Resume an existing game.\n" +
				"\tstart <map type> <file path> -=-= Start a new game (endless/premade).\n" +
				"\thistory -=- View game history and accumulated stats.\n" +
				"\tquit -=- Quit the application\n" + 
				"\thelp -=- Displays all profile commands.\n");
	}

	public static void printGameCommands() {
		// TODO: Cut down to only contextual relevant commands
		String directions = String.join(", ", Arrays.stream(Compass.values()).map(Compass::name).toArray(String[]::new));
		printBlock("ALL GAME COMMANDS\n\n\tDirections: " + directions + "\n\n" +
				"\tattack <direction> -=- Attack a nearby creature.\n" +
				"\tmove <direction> -=- Move in the specified direction within the room.\n" +
				"\tdisarm <direction> -=- Attempt to disarm a detected trap in the specified direction.\n" +
				"\tpray -=- Pray to a shrine to gain a second chance at life if you die (resets when you die or quit).\n" +
				"\texit <direction> -=- Exit the room with the given direction.\n" +
				"\topen -=- Open the chest or corpse you are currently standing on.\n" +
				"\tpickup [chest pos] -=- Pickup all items in a chest, or just specific items.\n" +
				"\tclose -=- Close the menu you are currently in.\n" +
				"\ttalk -=- Talk to a merchant to either buy or sell items.\n" + 
				"\tbuy -=- Buy an item from the merchant.\n" +
				"\tsell <bag pos> <item pos> -=- Sell an item to the merchant at half price.\n" +
				"\tinventory -=- View all of your bags and inventory stats.\n" +
				"\tbag <bag pos> -=- View the specified bag and its stats.\n" +
				"\tuse <bag pos> <item pos> -=- Use the specified item (food/buffs).\n" +
				"\tequip <bag pos> <item pos> -=- Equip the specified item (weapon/armor).\n" +
				"\tunequip <weapon/armor> -=- Unequip the current weapon or armor.\n" +
				"\tdestroy <bag pos> <item pos> -=- Destroy the specified item to clear space.\n" +
				"\tswap <src bag pos> <dest bag pos> <dest bag pos> -=- Swap a larger unequipped bag with an equipped one, copying all items over.\n" +
				"\tload <file path> -=-= Load a different saved map.\n" +
				"\tsave -=- Save the current game.\n" +
				"\tquit -=- Quit the current game.\n");
	}

	/**
	 * Handles processing application commands
	 * 	- Login
	 * 	- Logout
	 * 	- Change password
	 * 	- Register
	 * 	- Browse map
	 * 	- Join game
	 * 	- Resume game
	 * 	- Start game
	 * 	- View history
	 * 	- Help
	 * 	- Quit
	 */
	private static void processProfileCommand() throws InterruptedException {
		System.out.println(">>> ");
		String[] args = scanner.nextLine().toLowerCase().split(" ");
		
		switch(args[0]) {
			case "help" -> {
				printProfileCommands();
			}
			case "login" -> {
				String username = args[1];
				String password = args[2];
				boolean result = new LoginAction(authenticator, username, password).execute();

				if (result)
					printBlock("Successfully logged in.");
				else
					printBlock("Username and/or password does not match.");
			}
			case "logout" -> {
				boolean result = new LogoutAction(authenticator).execute();

				if (result)
					printBlock("Successfully logged out.");
				else
					printBlock("Not currently logged in or did not successfully log out, please try again.");
			}
			case "register" -> {
				String username = args[1];
				String password = args[2];
				boolean result = new RegisterAction(authenticator, username, password).execute();
			
				if (result)
					printBlock("Successfully created account.");
				else
					printBlock("Username might already exist, please try again.");
			}
			case "password" -> {
				String oldPassword = args[1];
				String newPassword = args[2];
				String confirmPassword = args[3];
				boolean result = new ChangePasswordAction(authenticator, oldPassword, newPassword, confirmPassword).execute();

				if (result)
					printBlock("Successfully changed password.");
				else
					printBlock("Could not change password, please try again.");
			}


			// TODO: Finish implementation
			case "browse" -> {
				String filePath = args[1];
				new BrowseMapAction(authenticator.getUser(), filePath).execute();
			}
			case "start" -> {
				String mapType = args[1].toLowerCase();
				String filePath = args[2];
				new StartGameAction(authenticator.getUser(), mapType, filePath).execute();

				printBlock("You enter the first narrow doorway to begin your journey...");
				Thread.sleep(DELAY_MS);
				printRoomDescription();
			}
			case "resume" -> {
				String filePath = args[1];
				new ResumeGameAction(authenticator.getUser(), filePath).execute();

				printBlock("You re-enter the labrinth to continue on your quest...");
				Thread.sleep(DELAY_MS);
				printRoomDescription();
			}
			case "join" -> {
				String filePath = args[1];
				new JoinGameAction(authenticator.getUser(), filePath).execute();

				printBlock("You enter the pits of darkness. Only death awaits...");
				Thread.sleep(DELAY_MS);
				printRoomDescription();
			}
			case "history" -> {
				new ViewHistoryAction(authenticator.getUser()).execute();
			}
			case "quit" -> {
				inGame = false;
				exit = true;
			}
			default -> printBlock("Unrecognized command, please try again.");
		}

		Thread.sleep(DELAY_MS);
	}

	/**
	 * Handles processing in-game commands
	 * 	- Attack
	 * 	- Move
	 * 	- Disarm trap
	 * 	- Pray
	 * 	- Exit room
	 * 	- Open
	 * 	- Close
	 * 	- Pickup item
	 * 	- Talk to merchant
	 * 	- Buy item
	 * 	- Sell item
	 * 	- View inventory
	 * 	- View bag
	 * 	- Use item
	 * 	- Equip item
	 * 	- Unequip item
	 * 	- Destroy item
	 * 	- Swap bag
	 * 	- Save game
	 * 	- Quit game
	 * 	- Help
	 */
	private static void processInGameCommand() throws IndexOutOfBoundsException, IllegalArgumentException, InterruptedException {
		System.out.print(">>> ");
		String[] args = scanner.nextLine().toLowerCase().split(" ");

		if(!(args[0].equals("open") || args[0].equals("pickup") || args[0].equals("close")) && inMenu) {
			printBlock("Please either pickup items from the chest or close the chest.");
			Thread.sleep(DELAY_MS);
			return;
		}

		switch(args[0]) {
			case "help" -> {
				printGameCommands();
			}
			case "attack" -> {
				// TODO: Find a way to add more information on who they attacked in response
				Compass direction = Compass.valueOf(args[1].toUpperCase());
				int damage = new AttackAction(game, authenticator.getUser(), direction).execute();
				
				if (damage != -1)
					printBlock("Successfully attacked " + direction + " and dealt " + damage + " damage.");
				else
					printBlock("Unable to attack that tile, please try again.");
			}
			case "disarm" -> {
				Compass direction = Compass.valueOf(args[1].toUpperCase());
				boolean result = new DisarmTrapAction(game, authenticator.getUser(), direction).execute();
				
				if (result)
					printBlock("Successfully disarmed the trap! Great job.");
				else
					printBlock("You failed to disarm the trap, or there is no trap on that tile.");
			}
			case "move" -> {
				Compass direction = Compass.valueOf(args[1].toUpperCase());
				boolean result = new MoveAction(game, authenticator.getUser(), direction).execute();
				
				if (result)
					printBlock("Successfully moved " + direction + ".");
				else
					printBlock("Unable to move in that direction, please try again.");
			}
			case "exit" -> {
				Compass direction = Compass.valueOf(args[1].toUpperCase());
				boolean result = new ExitRoomAction(game, authenticator.getUser(), direction).execute();
				
				if (result)
					printRoomDescription();
				else
					printBlock("A doorway in that direction does not exist, or you are not at a doorway, please try again.");
			}
			case "pray" -> {
				// TODO
				if(game.getMap() instanceof EndlessMap map) {
					boolean result = new PrayAction(game, authenticator.getUser(), map).execute();

					if (result)
						printBlock("The God of Subsystems, Bobby, has heard your prayers. You have been granted with a second chance at life.");
					else
						printBlock("A shrine in that direction does not exist or now is not a safe time to pray, please try again.");
				} else {
					printBlock("Shrines do not exist on premade maps.");
				}
			}
			case "open" -> {
				List<InventoryElement> contents = new OpenChestAction(game, authenticator.getUser()).execute();
				
				if (contents != null) {
					StringBuilder builder = new StringBuilder("Contents (" + contents.size() + " items)");
					for(int i = 0; i < contents.size(); i++) {
						builder.append("\n\t").append(i).append(": ").append(contents.get(i));
					}
					printBlock(builder.toString());
					inMenu = true;
				}
				else
					printBlock("You are not currently on a tile containing either a chest or corpse, please try again.");
			}
			case "pickup" -> {
				int chestPos = args.length == 1 ? -1 : Integer.parseInt(args[1]);
				boolean result = new PickupItemAction(game, authenticator.getUser(), chestPos).execute();
				
				if (result)
					printBlock("Successfully picked up the item(s).");
				else
					printBlock("You are not currently on a chest tile, your inventory is full, or the chest position is incorrect, please try again.");
			}
			case "close" -> {
				if (!inMenu) {
					printBlock("You do not currently have a menu open, please try again.");
					break;
				}

				new CloseAction(game, authenticator.getUser()).execute();
				printBlock("Successfully closed the menu.");
				inMenu = false;
			}	
			case "talk" -> {
				Compass direction = Compass.valueOf(args[1].toUpperCase());
				List<InventoryElement> contents = new TalkToMerchantAction(game, authenticator.getUser(), direction).execute();
				
				if (contents != null) {
					StringBuilder builder = new StringBuilder("Merchant: \"Welcome to my shop, adventurer! We buy and sell only the finest of goods. Here, take a look.\"\n\n");

					builder.append("Shop Contents (" + contents.size() + " items)");
					for (int i = 0; i < contents.size(); i++) {
						builder.append("\n\t").append(i).append(": ").append(contents.get(i));
					}
					printBlock(builder.toString());
					inMenu = true;
				}
				else
					printBlock("A merchant in that direction does not exist or now is not a safe time to talk, please try again.");
			}
			case "buy" -> {
				int itemPos = Integer.parseInt(args[1]);
				boolean result = new BuyItemAction(game, authenticator.getUser(), itemPos).execute();
				
				if (result)
					printBlock("Successfully bought up the item(s).\nMerchant: \"Excellent choice! Thank you for shopping. Anything else I can do for you?\"");
				else
					printBlock("You are not currently talking to a merchant, your inventory is full, the item position is incorrect, or you do not have sufficient gold, please try again.");
			}
			case "sell" -> {
				int bagPos = Integer.parseInt(args[1]);
				int itemPos = Integer.parseInt(args[2]);
				boolean result = new SellItemAction(game, authenticator.getUser(), bagPos, itemPos).execute();

				if (result)
					printBlock("Successfully sold the item(s).\nMerchant: \"Pleasure doing business with ya!\"");
				else
					printBlock("Unknown item, item does not exist, or the bag/item position is incorrect, please try again.");
			}
			case "inventory" -> {
				String inventoryString = new ViewInventoryAction(game, authenticator.getUser()).execute();
				
				if (inventoryString != null)
					printBlock(inventoryString);
				else
					printBlock("You are not able to view your inventory, please try again.");
			}
			case "bag" -> {
				int bagPos = Integer.parseInt(args[1]);
				String bagString = new ViewBagAction(game, authenticator.getUser(), bagPos).execute();
				
				if (bagString != null)
					printBlock(bagString);
				else 
					printBlock("You are not able to view your bag or that bag does not exist, please try again.");
			}
			case "use" -> {
				int bagPos = Integer.parseInt(args[1]);
				int itemPos = Integer.parseInt(args[2]);
				boolean result = new UseItemAction(game, authenticator.getUser(), bagPos, itemPos).execute();
				
				if (result)
					printBlock("Successfully used the item.");
				else
					printBlock("Unknown item or item is not usable, please try again.");
			}
			case "equip" -> {
				int bagPos = Integer.parseInt(args[1]);
				int itemPos = Integer.parseInt(args[2]);
				boolean result = new EquipItemAction(game, authenticator.getUser(), bagPos, itemPos).execute();
				
				if (result)
					printBlock("Successfully equipped the item.");
				else
					printBlock("Unknown item or item is not able to be equipped, please try again.");
			}
			case "unequip" -> {
				boolean isWeapon = args[1].equals("weapon");
				boolean result = new UnequipItemAction(game, authenticator.getUser(), isWeapon).execute();
				
				if (result)
					printBlock("Successfully unequipped the item.");
				else
					printBlock("That type of item is not currently equipped.");
			}
			case "destroy" -> {
				int bagPos = Integer.parseInt(args[1]);
				int itemPos = Integer.parseInt(args[2]);
				boolean result = new DestroyItemAction(game, authenticator.getUser(), bagPos, itemPos).execute();
				
				if (result)
					printBlock("Successfully destroyed the item.");
				else
					printBlock("Unknown item, please try again.");
			}
			case "swap" -> {
				int sourceBagPos = Integer.parseInt(args[1]);
				int destBagPos = Integer.parseInt(args[2]);
				int destItemPos = Integer.parseInt(args[3]);
				boolean result = new SwapBagAction(game, authenticator.getUser(), sourceBagPos, destBagPos, destItemPos).execute();
				
				if (result)
					printBlock("Successfully swapped the bags.");
				else
					printBlock("Unknown bags or the destination bag is smaller than the source, please try again.");
			}
			case "load" -> {
				new LoadMapAction(game, authenticator.getUser(), args[1]).execute();
				printBlock("Successfully loaded the map.");
			}
			case "save" -> {
				new SaveGameAction(game, authenticator.getUser()).execute();
				printBlock("Successfully saved the game.");
			}
			case "quit" -> {
				new QuitAction(game, authenticator.getUser()).execute();
				printBlock("Successfully quit the game.");
			}
			default -> printBlock("Unrecognized command, please try again.");
		}

		Thread.sleep(DELAY_MS);
	}
}
