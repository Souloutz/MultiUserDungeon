package multiuserdungeon;

import multiuserdungeon.commands.AttackAction;
import multiuserdungeon.commands.QuitGameAction;
import multiuserdungeon.commands.inventory.DestroyItemAction;
import multiuserdungeon.commands.inventory.EquipItemAction;
import multiuserdungeon.commands.inventory.PickupItemAction;
import multiuserdungeon.commands.inventory.SwapBagAction;
import multiuserdungeon.commands.inventory.UnequipItemAction;
import multiuserdungeon.commands.inventory.UseItemAction;
import multiuserdungeon.commands.map.CloseChestAction;
import multiuserdungeon.commands.map.DisarmTrapAction;
import multiuserdungeon.commands.map.LoadMapAction;
import multiuserdungeon.commands.map.OpenChestAction;
import multiuserdungeon.commands.movement.ExitRoomAction;
import multiuserdungeon.commands.movement.MoveAction;
import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.map.Compass;
import multiuserdungeon.map.tiles.Player;
import multiuserdungeon.persistence.JSONProgressDB;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UI {

	private static final String DIVIDER  = "=".repeat(150);
	private static final int DELAY_MS = 3000;
	private static final Scanner scanner = new Scanner(System.in);
	private static Game game;
	private static boolean inChest = false;

	public static void main(String[] args) throws InterruptedException {
		printWelcomeMsg();
		game = new Game(createPlayer());
		game.setProgressDB(new JSONProgressDB());

		printBlock("You enter the first narrow doorway to begin your journey...");
		Thread.sleep(DELAY_MS);
		printRoomDescription();

		while(!game.isOver()) {
			printRoomLayout();
			printAllCommands();

			try {
				processCommand();
			} catch(IndexOutOfBoundsException | IllegalArgumentException ignored) {
				printBlock("Unable to parse command arguments, please try again.");
			}
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

	public static void printAllCommands() {
		// TODO: Cut down to only contextual relevant commands
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
	}

	private static void processCommand() throws IndexOutOfBoundsException, IllegalArgumentException, InterruptedException {
		System.out.print(">>> ");
		String[] args = scanner.nextLine().toLowerCase().split(" ");

		if(!(args[0].equals("open") || args[0].equals("pickup") || args[0].equals("close")) && inChest) {
			printBlock("Please either pickup items from the chest or close the chest.");
			Thread.sleep(DELAY_MS);
			return;
		}

		switch(args[0]) {
			case "inventory" -> printBlock(game.getPlayer().getInventory() + "\n\n\tWeapon: " + game.getPlayer().getWeapon() + "\n\tArmor: " + game.getPlayer().getArmor());
			case "bag" -> {
				int bagPos = Integer.parseInt(args[1]);
				String result = game.getPlayer().getInventory().viewBag(bagPos);
				printBlock(Objects.requireNonNullElse(result, "Invalid bag specified, please try again."));
			}
			case "equip" -> {
				int bagPos = Integer.parseInt(args[1]);
				int itemPos = Integer.parseInt(args[2]);
				boolean result = new EquipItemAction(game, bagPos, itemPos).execute();
				if(result) {
					printBlock("Successfully equipped the item.");
				} else {
					printBlock("Unknown item or item is not able to be equipped, please try again.");
				}
			}
			case "unequip" -> {
				boolean isWeapon = args[1].equals("weapon");
				boolean result = new UnequipItemAction(game, isWeapon).execute();
				if(result) {
					printBlock("Successfully unequipped the item.");
				} else {
					printBlock("That type of item is not currently equipped.");
				}
			}
			case "use" -> {
				int bagPos = Integer.parseInt(args[1]);
				int itemPos = Integer.parseInt(args[2]);
				boolean result = new UseItemAction(game, bagPos, itemPos).execute();
				if(result) {
					printBlock("Successfully used the item.");
				} else {
					printBlock("Unknown item or item is not usable, please try again.");
				}
			}
			case "destroy" -> {
				int bagPos = Integer.parseInt(args[1]);
				int itemPos = Integer.parseInt(args[2]);
				boolean result = new DestroyItemAction(game, bagPos, itemPos).execute();
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
				boolean result = new SwapBagAction(game, sourceBagPos, destBagPos, destItemPos).execute();
				if(result) {
					printBlock("Successfully swapped the bags.");
				} else {
					printBlock("Unknown bags or the destination bag is smaller than the source, please try again.");
				}
			}
			case "load" -> {
				new LoadMapAction(game, args[1]).execute();
				printBlock("Successfully loaded the map.");
			}
			case "open" -> {
				List<InventoryElement> contents = new OpenChestAction(game).execute();
				if(contents != null) {
					StringBuilder builder = new StringBuilder("Contents (" + contents.size() + " items)");
					for(int i = 0; i < contents.size(); i++) {
						builder.append("\n\t").append(i).append(": ").append(contents.get(i));
					}
					printBlock(builder.toString());
					inChest = true;
				} else {
					printBlock("You are not currently on a chest tile, please try again.");
				}
			}
			case "pickup" -> {
				int chestPos = args.length == 1 ? -1 : Integer.parseInt(args[1]);
				boolean result = new PickupItemAction(game, chestPos).execute();
				if(result) {
					printBlock("Successfully picked up the item(s).");
				} else {
					printBlock("You are not currently on a chest tile, your inventory is full, or the chest position is incorrect, please try again.");
				}
			}
			case "close" -> {
				if(!inChest) {
					printBlock("You do not currently have a chest open, please try again.");
					break;
				}
				new CloseChestAction(game).execute();
				printBlock("Successfully closed the chest.");
				inChest = false;
			}
			case "disarm" -> {
				Compass direction = Compass.valueOf(args[1].toUpperCase());
				boolean result = new DisarmTrapAction(game, direction).execute();
				if(result) {
					printBlock("Successfully disarmed the trap! Great job.");
				} else {
					printBlock("You failed to disarm the trap, or there is no trap on that tile.");
				}
			}
			case "move" -> {
				Compass direction = Compass.valueOf(args[1].toUpperCase());
				boolean result = new MoveAction(game, direction).execute();
				if(result) {
					printBlock("Successfully moved " + direction + ".");
				} else {
					printBlock("Unable to move in that direction, please try again.");
				}
			}
			case "exit" -> {
				Compass direction = Compass.valueOf(args[1].toUpperCase());
				boolean result = new ExitRoomAction(game, direction).execute();
				if(result) {
					printRoomDescription();
				} else {
					printBlock("A doorway in that direction does not exist, or you are not at a doorway, please try again.");
				}
			}
			case "attack" -> {
				// TODO: Find a way to add more information on who they attacked in response
				Compass direction = Compass.valueOf(args[1].toUpperCase());
				int damage = new AttackAction(game, direction).execute();
				if(damage != -1) {
					printBlock("Successfully attacked " + direction + " and dealt " + damage + " damage.");
				} else {
					printBlock("Unable to attack that tile, please try again.");
				}
			}
			case "quit" -> {
				String result = new QuitGameAction(game).execute();
				printBlock(result);
			}
			default -> printBlock("Unrecognized command, please try again.");
		}
		Thread.sleep(DELAY_MS);
	}

}
