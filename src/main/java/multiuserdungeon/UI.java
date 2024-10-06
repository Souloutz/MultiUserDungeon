package multiuserdungeon;

import java.lang.reflect.Type;
import java.util.Scanner;

import multiuserdungeon.commands.Action;
import multiuserdungeon.commands.AttackAction;
import multiuserdungeon.commands.QuitGameAction;
import multiuserdungeon.commands.inventory.DestroyItemAction;
import multiuserdungeon.commands.inventory.EquipItemAction;
import multiuserdungeon.commands.inventory.PickupItemAction;
import multiuserdungeon.commands.inventory.SwapBagAction;
import multiuserdungeon.commands.inventory.UnequipItemAction;
import multiuserdungeon.commands.inventory.UseItemAction;
import multiuserdungeon.commands.map.DisarmTrapAction;
import multiuserdungeon.commands.map.LoadMapAction;
import multiuserdungeon.commands.map.OpenChestAction;
import multiuserdungeon.commands.movement.ExitRoomAction;
import multiuserdungeon.commands.movement.MoveAction;
import multiuserdungeon.map.Compass;
import multiuserdungeon.map.tiles.Player;
import multiuserdungeon.progress.JSONProgressDB;

public class UI {
	
	public static Game game = null;

	/*
	 * Displays information about the current room, when player is attacked,
	 * when stat changes occur
	 */
	public static String displayInformation() {
		return "";
	}

	// alternative to returning object type?
	public static Object parseInput(String input) {
		String[] parsedInput = input.split(" ");
		Object commandAction;

		String commandString = parsedInput[0].toLowerCase();

		switch (commandString) {
			case "attack":
				commandAction = new AttackAction(game, Compass.valueOf(parsedInput[1].toUpperCase()));
				break;

			case "move":
				commandAction = new MoveAction(game, Compass.valueOf(parsedInput[1].toUpperCase()));
				break;

			case "exit":
				commandAction = new ExitRoomAction(game, Compass.valueOf(parsedInput[1].toUpperCase()));
				break;

			case "disarm":
				commandAction = new DisarmTrapAction(game, Compass.valueOf(parsedInput[1].toUpperCase()));
				break;

			case "loot":
				commandAction = new OpenChestAction(game);
				break;

			case "pickup":
				commandAction = new PickupItemAction(game, Integer.parseInt(parsedInput[1]));
				break;

			case "view":
				if (parsedInput.length == 1)
					commandAction = new ViewInventoryAction(game);
				else if (parsedInput.length == 2)
					commandAction = new ViewBagAction(game, Integer.parseInt(parsedInput[1]));
				else if (parsedInput.length == 3)
					commandAction = new ViewItemAction(game, Integer.parseInt(parsedInput[1]), Integer.parseInt(parsedInput[2]));
				else
					commandAction = null;
				
				break;

			case "equip":
				commandAction = new EquipItemAction(game, Integer.parseInt(parsedInput[1]), Integer.parseInt(parsedInput[2]));
				break;

			case "unequip":
				commandAction = new UnequipItemAction(game, Boolean.parseBoolean(parsedInput[1]));
				break;

			case "use":
				commandAction = new UseItemAction(game, Integer.parseInt(parsedInput[1]), Integer.parseInt(parsedInput[2]));
				break;

			case "destroy":
				commandAction = new DestroyItemAction(game, Integer.parseInt(parsedInput[1]), Integer.parseInt(parsedInput[2]));
				break;

			case "swap":
				commandAction = new SwapBagAction(game, Integer.parseInt(parsedInput[1]), Integer.parseInt(parsedInput[2]));
				break;

			case "load":
				commandAction = new LoadMapAction(game, parsedInput[1].toLowerCase());
				break;

			case "quit":
				commandAction = new QuitGameAction(game);
				break;

			default:
				commandAction = null;
		}

		return commandAction;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Hello adventurer! What is your name?");
		String playerName = scanner.nextLine();

		System.out.println("Describe your character in a sentence.");
		String playerDescription = scanner.nextLine();

		game = new Game(new Player(playerName, playerDescription));
		game.setProgressDB(new JSONProgressDB());

        while (!game.hasGameEnd()) {
			System.out.println(displayInformation());

			System.out.println(game.getAvailableCommands());

            String input = scanner.nextLine();

			// does this work
            Action<Type> command = (Action<Type>) parseInput(input);

            if (command == null)
                System.out.println("Invalid input!");
            else {
				
				Object executeReturn = command.execute();
				
				if (executeReturn != null)
                	System.out.println(executeReturn);
			}
		}

		scanner.close();
	}

}
