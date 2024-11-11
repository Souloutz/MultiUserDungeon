package multiuserdungeon;


import multiuserdungeon.map.Map;
import javafx.scene.layout.GridPane;
import multiuserdungeon.map.tiles.Player;

public class GUI {
    private Game game;
    private GridPane gui;

    private void updateMessage(String message) {
        return;
    }

    private Map createMap(String filename) {
        //This method will generate a map from a given 
        //filename and begin the gameloop.
        return new Map();
    }

    private Player createPlayer() {
        //This method will generate a player from the given
        //username. Will be called by createMap()
        return new Player(null,null);
    }

    private void updateRoom() {
        //This method will update the room section of the
        //display, either changing the current room to a 
        //new state or replacing the room with a new one.
        return;
    }

    public void refreshDisplay() {
        //This method will simply refresh the display 
        //with updated versions of all of it's components
        //at once rather then buttons slowly shifting and
        //morphing slowly.
        return;
    }

    private void disableCommands() {
        //This method will go through each command and 
        //disable the buttons+grey them out if they 
        //can't be executed, or enable them+green them
        //out if they can be executed.
        return;
    }

    private void waitForCommand() {
        //This method will pause the gameloop until another 
        //command is entered by the player. This function
        //will then process that command.
        return;
    }


}
