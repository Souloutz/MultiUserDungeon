package multiuserdungeon.map;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import multiuserdungeon.clock.CreatureBuff;
import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.inventory.Items;
import multiuserdungeon.map.tiles.Chest;
import multiuserdungeon.map.tiles.Merchant;
import multiuserdungeon.map.tiles.NPC;
import multiuserdungeon.map.tiles.Obstacle;
import multiuserdungeon.map.tiles.shrine.Shrine;
import multiuserdungeon.map.tiles.trap.Trap;

import java.util.Random;
import java.util.HashMap;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RoomGenerator {

    static int MIN_ROOM_SIZE = 4;
    static int MAX_ROOM_SIZE = 10;

    private static long xorshift(long state) {
        state ^= state << 13;
        state ^= state >>> 7;
        state ^= state << 17;
        return state;
    }

    private static int[] permutation(int len) {
        long[] pre = new long[len];
        pre[0] = new Random().nextLong((long)Math.pow(2,64));
        for (int i = 1;i < len; i++){
            pre[i] = xorshift(pre[i-1]);
        }
        HashMap<Long,Integer> dict = new HashMap<>();
        for(int i = 0; i < len;i++){
            dict.put(pre[i],i);
        }
        Arrays.sort(pre);
        int[] post = new int[len];
        for (int i = 0; i < len;i++){
            post[i] = dict.get(pre[i]);
        }
        return post;
    }
    
    public static Room generateRoom (Compass direction, Room attached) {

        Random random = new Random();

        int[] x = permutation(random.nextInt(MAX_ROOM_SIZE - 3) + MIN_ROOM_SIZE);
        int[] y = permutation(random.nextInt(MAX_ROOM_SIZE - 3) + MIN_ROOM_SIZE);
        int max = (x.length > y.length) ? y.length : x.length;

        Room room = new Room(y.length,x.length, generateRoomDescription());
        int row;
        int col;
        if(direction == Compass.NORTH) {
            row = y.length - 1;
            col = random.nextInt(1,x.length-1);
        } else if (direction == Compass.EAST) {
            row = random.nextInt(1,y.length-1);
            col = 0;
        } else if (direction == Compass.SOUTH) {
            row = 0;
            col = random.nextInt(1,x.length-1);
        } else {
            row = random.nextInt(1,y.length-1);
            col =  x.length - 1;
        }
        room.addConnection(row,col,attached);

        int maxc = random.nextInt(1,4);
        List<Compass> ways = new ArrayList<>();
        int count = 0;
        while (true) {
            if (count >= maxc) {
                break;
            }
            Compass newc = getCompass();
            if (ways.contains(newc) || newc == direction.getOpposite()) {
                continue;
            }
            ways.add(newc);
            count++;
        }

        for (Compass c : ways) {
            int nrow;
            int ncol;
            if(c == Compass.NORTH) {
                nrow = 0;
                ncol = random.nextInt(1,x.length-1);
                if (room.getConnections().keySet().contains(room.getTile(nrow,ncol))) {
                    ncol = (ncol == 0) ? ncol++ : ncol--;
                }
            } else if (c == Compass.EAST) {
                nrow = random.nextInt(1,y.length-1);
                ncol = x.length - 1;
                if (room.getConnections().keySet().contains(room.getTile(nrow,ncol))) {
                    nrow = (nrow == 0) ? nrow++ : nrow--;
                }
            } else if (c == Compass.SOUTH) {
                nrow = y.length - 1;
                ncol = random.nextInt(1,x.length-1);
                if (room.getConnections().keySet().contains(room.getTile(nrow,ncol))) {
                    ncol = (ncol == 0) ? ncol++ : ncol--;
                }
            } else {
                nrow = random.nextInt(1,y.length-1);
                ncol = 0;
                if (room.getConnections().keySet().contains(room.getTile(nrow,ncol))) {
                    nrow = (nrow == 0) ? nrow++ : nrow--;
                }
            }
            
            room.addConnection(nrow,ncol,null);
        }

        // {0: obstacle, 1: NPC, 2: Trap, 3: chest}
        // Permutation to fill rooms is len = 32
        // modulo 4, but 30 = shrine and 31 = merchant

        int[] objects = permutation(32);

        for (int i = 0;i < max;i++) {
            Tile tile = room.getTile(y[i],x[i]);
            if (room.getConnections().keySet().contains(tile) || tile.getObjects().size() > 0) {
                continue;
            }
            int place = objects[i] % 5;
            TileObject object = null;

            if (objects[i] == 31) {
                object = (TileObject)generateMerchant();
            } else if (objects[i] == 30) {
                object = (TileObject)generateShrine();
            } else if (place == 0) {
                object = (TileObject)generateObstacle();
            } else if (place == 1 || place == 4) {
                object = (TileObject)generateNPC();
            } else if (place == 2) {
                object = (TileObject)generateTrap();
            } else if (place == 3) {
                object = (TileObject)generateChest();
            }
            tile.addObject(object);
            object.setTile(tile);
        }

        return room;
    }

    public static Room populateRoom(Room room) {

        int[] x = permutation(room.getRows());
        int[] y = permutation(room.getColumns());
        int max = (x.length > y.length) ? y.length : x.length;

        int[] objects = permutation(31);

        for (int i = 0;i < max;i++) {
            Tile tile = room.getTile(x[i],y[i]);
            if (room.getConnections().keySet().contains(tile) || tile.getObjects().size() > 0) {
                continue;
            }
            int place = objects[i] % 5;
            TileObject object = null;

            if (objects[i] == 30) {
                object = (TileObject)generateMerchant();
            } else if (place == 0) {
                object = (TileObject)generateObstacle();
            } else if (place == 1 || place == 4) {
                object = (TileObject)generateNPC();
            } else if (place == 2) {
                object = (TileObject)generateTrap();
            } else if (place == 3) {
                object = (TileObject)generateChest();
            }
            tile.addObject(object);
            object.setTile(tile);
        }

        return room;
    }

    private static Compass getCompass() {
        return Compass.values()[(new Random().nextInt(0,4)) *2];
    }

    private static String generateRoomDescription(){
        try(CSVReader reader = new CSVReaderBuilder(new FileReader("data/room/descriptions.csv")).build()) {
            List<String[]> lines = reader.readAll();
            return lines.get(new Random().nextInt(1, lines.size()))[0];
        } catch (IOException | CsvException e) {
            System.out.println("Error generating room description!");
            return null;
        }
    }

    public static Merchant generateMerchant(){
	    try(CSVReader reader = new CSVReaderBuilder(new FileReader("data/room/merchants.csv")).build()) {
		    List<String[]> lines = reader.readAll();
            String name = lines.get(new Random().nextInt(1, lines.size()))[0];
            List<InventoryElement> store = Items.getInstance().getRandomList(3);
            return new Merchant(name, store);
	    } catch (IOException | CsvException e) {
            System.out.println("Error generating merchant!");
		    return null;
	    }
    }

    private static Chest generateChest() {
        try(CSVReader reader = new CSVReaderBuilder(new FileReader("data/room/chests.csv")).build()) {
            List<String[]> lines = reader.readAll();
            String name = lines.get(new Random().nextInt(1, lines.size()))[0];
            int items = new Random().nextInt(2, 6);
            List<InventoryElement> contents = Items.getInstance().getRandomList(items);
            return new Chest(name, contents);
        } catch (IOException | CsvException e) {
            System.out.println("Error generating chest!");
            return null;
        }
    }

    private static Obstacle generateObstacle() {
        try(CSVReader reader = new CSVReaderBuilder(new FileReader("data/room/obstacles.csv")).build()) {
            List<String[]> lines = reader.readAll();
            String name = lines.get(new Random().nextInt(1, lines.size()))[0];
            return new Obstacle(name);
        } catch (IOException | CsvException e) {
            System.out.println("Error generating obstacle!");
            return null;
        }
    }

    private static NPC generateNPC() {
        try(CSVReader reader = new CSVReaderBuilder(new FileReader("data/room/npcs.csv")).build()) {
            List<String[]> lines = reader.readAll();
            String[] line = lines.get(new Random().nextInt(1, lines.size()));
            return new NPC(line[0], line[1], new Random().nextBoolean() ? CreatureBuff.DIURNAL : CreatureBuff.NOCTURNAL);
        } catch (IOException | CsvException e) {
            System.out.println("Error generating NPC!");
            return null;
        }
    }

    private static Trap generateTrap() {
        return new Trap(new Random().nextInt(5, 50));
    }

    public static Shrine generateShrine() {
        try(CSVReader reader = new CSVReaderBuilder(new FileReader("data/room/shrines.csv")).build()) {
            List<String[]> lines = reader.readAll();
            String name = lines.get(new Random().nextInt(1, lines.size()))[0];
            return new Shrine(name);
        } catch (IOException | CsvException e) {
            System.out.println("Error generating shrine!");
            return null;
        }
    }

}
