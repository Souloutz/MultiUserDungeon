package multiuserdungeon.map;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import multiuserdungeon.map.tiles.*;

import java.util.List;

public class EndlessMap implements GameMap {
    private Map<Player,Room> playerRooms;
    private Map<Player,Room> playerStartRooms;
    private Player currentPlayer;

    private List<Room> rooms;

    public EndlessMap(Player player, List<Room> rooms) {
        this.playerRooms = new HashMap<>();
        this.playerStartRooms = new HashMap<>();
        this.currentPlayer = player;
        this.rooms = rooms;
    }

    //copy constructor 
    public EndlessMap(EndlessMap oldMap){
        this.playerRooms = new HashMap<>();
        this.playerStartRooms = new HashMap<>();
        this.currentPlayer =  new Player(currentPlayer);
        this.rooms = new ArrayList<>();

        //create all the rooms first
        for(Room oldRoom : oldMap.rooms){
            Room newRoom = new Room(oldRoom);
            this.rooms.add(newRoom);
        }
        //set the connections
        for(int i = 0; i < this.rooms.size(); i++){
            Room newRoom = this.rooms.get(i);
            Room oldRoom = oldMap.rooms.get(i);
            Map<Tile,Room> oldConnections = oldRoom.getConnections();

            for(Tile tile : oldConnections.keySet()){
                int connectingRoomIndex = oldMap.rooms.indexOf(oldConnections.get(tile));
                newRoom.addConnection(tile.getRow(), tile.getCol(), this.rooms.get(connectingRoomIndex));
            }
        }

        //set each player's start room, current room, and tiles with the new room and tile instances
        for(Player player : oldMap.playerStartRooms.keySet()){
            int startRoomIndex = oldMap.rooms.indexOf(oldMap.playerStartRooms.get(player));
            int playerRoomIndex = oldMap.rooms.indexOf(oldMap.playerRooms.get(player));

            Room newStartRoom = this.rooms.get(startRoomIndex);
            Room newPlayerRoom = this.rooms.get(playerRoomIndex);

            if(player == oldMap.currentPlayer){
                this.playerStartRooms.put(this.currentPlayer, newStartRoom);
                this.playerRooms.put(this.currentPlayer, newPlayerRoom);

                //setting player tile
                Tile oldPlayerTile = oldMap.getPlayerRoom().getPlayerTile();
                newPlayerRoom.setPlayerTile(newPlayerRoom.getTile(oldPlayerTile.getRow(), oldPlayerTile.getCol()));
                newPlayerRoom.getPlayerTile().addObject(this.currentPlayer);
                this.currentPlayer.setTile(newPlayerRoom.getPlayerTile());

            }
            else{
                this.playerStartRooms.put(player, newStartRoom);
                this.playerRooms.put(player, newPlayerRoom);

                //setting player tile
                Tile oldPlayerTile = oldMap.playerRooms.get(player).getPlayerTile();
                newPlayerRoom.setPlayerTile(newPlayerRoom.getTile(oldPlayerTile.getRow(), oldPlayerTile.getCol()));
                newPlayerRoom.getPlayerTile().addObject(player);
                player.setTile(newPlayerRoom.getPlayerTile());
            }
        };
    }

    @Override
    public Room getPlayerRoom () {
        return playerRooms.get(currentPlayer);
    }
    @Override
    public void setPlayerRoom (Room room) {
        this.playerRooms.put(currentPlayer,room);
    }

    public void handleExitRoom(Compass direction) {
        Room room = getPlayerRoom();
        try {
            if (room.getConnections().get(currentPlayer.getTile()) == null) {
                Room newRoom = RoomGenerator.generateRoom(direction,room);
                rooms.add(newRoom);
                room.getConnections().put(currentPlayer.getTile(),newRoom);
            }
        } catch (NullPointerException e) {}
    }

    public boolean isStartRoom() {
        return getPlayerRoom().equals(playerStartRooms.get(currentPlayer));
    }

    public Player getCurrentPlayer(){
        return this.currentPlayer;
    }

    @Override
    public List<Room> getRooms() {
        return rooms;
    }

}