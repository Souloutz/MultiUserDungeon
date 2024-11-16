package multiuserdungeon;

import multiuserdungeon.clock.CreatureBuff;
import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.map.*;
import multiuserdungeon.map.tiles.Chest;
import multiuserdungeon.map.tiles.Merchant;
import multiuserdungeon.map.tiles.NPC;
import multiuserdungeon.map.tiles.Obstacle;
import multiuserdungeon.map.tiles.shrine.Shrine;
import multiuserdungeon.map.tiles.trap.Trap;
import multiuserdungeon.map.TileObject;

import java.util.Random;


import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RoomGenerator {

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

        int[] x = permutation(random.nextInt(7) + 4);
        int[] y = permutation(random.nextInt(7) + 4);
        int max = (x.length > y.length) ? y.length : x.length;

        Room room = new Room(y.length,x.length,grabDescription());
        int row;
        int col;
        if(direction == Compass.NORTH) {
            row = y.length - 1;
            col = random.nextInt(x.length);
        } else if (direction == Compass.EAST) {
            row = random.nextInt(y.length);
            col = 0;
        } else if (direction == Compass.SOUTH) {
            row = 0;
            col = random.nextInt(x.length);
        } else {
            row = random.nextInt(y.length);
            col =  x.length - 1;
        }
        room.addConnection(row,col,attached);

        // {0: obstacle, 1: NPC, 2: Trap, 3: chest}
        // Permutation to fill rooms is len = 32
        // modulo 4, but 30 = shrine and 31 = merchant

        int[] objects = permutation(32);

        for (int i = 0;i < max;i++) {
            Tile tile = room.getTile(y[i],x[i]);
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
                ncol = random.nextInt(x.length);
            } else if (c == Compass.EAST) {
                nrow = random.nextInt(y.length);
                ncol = x.length - 1;
            } else if (c == Compass.SOUTH) {
                nrow = y.length - 1;
                ncol = random.nextInt(x.length);
            } else {
                nrow = random.nextInt(y.length);
                ncol = 0;
            }
            
            room.addConnection(nrow,ncol,null);
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

    private static String grabDescription(){

        try (BufferedReader br = new BufferedReader(new FileReader("data/room/descriptions.csv"))) {
            String line;
            int cindex = 0;
            int rindex = new Random().nextInt(1,100);

            br.readLine();

            while ((line = br.readLine()) != null) {
                if (cindex == rindex) {
                    break;
                }
                cindex++;
            }
            return line;
        } catch (IOException e) {
            return "error getting a cool description";
        }
    }

    private static Merchant generateMerchant(){
        try (BufferedReader br = new BufferedReader(new FileReader("data/room/merchants.csv"))) {
            String line;
            int cindex = 0;
            int rindex = new Random().nextInt(1,16);

            br.readLine();

            while ((line = br.readLine()) != null) {
                if (cindex == rindex){
                    break;
                }
                cindex++;
            }

            int count = 3;
            List<InventoryElement> items = new ArrayList<>();
            Items collections = Items.getInstance();
            for (int i = 0; i < count;i++) {
                items.add(collections.getItem(new Random().nextInt(1,100)));
            }

            return new Merchant(line,items);

        } catch (IOException e) {
            return null;
        }
    }

    private static Chest generateChest() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/room/chests.csv"))) {
            String line;
            int cindex = 0;
            int rindex = new Random().nextInt(1,16);

            br.readLine();

            while ((line = br.readLine()) != null) {
                if (cindex == rindex){
                    break;
                }
                cindex++;
            }

            int count = new Random().nextInt(2,10);
            List<InventoryElement> items = new ArrayList<>();
            Items collections = Items.getInstance();
            for (int i = 0; i < count;i++) {
                items.add(collections.getItem(new Random().nextInt(1,100)));
            }

            return new Chest(line,items);

        } catch (IOException e) {
            return null;
        }
    }

    private static Obstacle generateObstacle() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/room/obstacles.csv"))) {
            String line;
            int cindex = 0;
            int rindex = new Random().nextInt(1,101);

            br.readLine();

            while ((line = br.readLine()) != null) {
                if (cindex == rindex){
                    break;
                }
                cindex++;
            }

            return new Obstacle(line);

        } catch (IOException e) {
            return null;
        }
    }

    private static NPC generateNPC() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/room/npcs.csv"))) {
            String line;
            int cindex = 0;
            int rindex = new Random().nextInt(1,66);

            br.readLine();

            while ((line = br.readLine()) != null) {
                if (cindex == rindex){
                    break;
                }
                cindex++;
            }
            String[] tokens = line.split(",");
            CreatureBuff cb;
            if (new Random().nextBoolean()) {
                cb = CreatureBuff.DIURNAL;
            } else {
                cb = CreatureBuff.NOCTURNAL;
            }

            return new NPC(tokens[0],tokens[1],cb);

        } catch (IOException e) {
            return null;
        }
    }

    private static Trap generateTrap() {
        return new Trap(new Random().nextInt(5,50));
    }

    private static Shrine generateShrine() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/room/shrines.csv"))) {
            String line;
            int cindex = 0;
            int rindex = new Random().nextInt(1,16);

            br.readLine();

            while ((line = br.readLine()) != null) {
                if (cindex == rindex){
                    break;
                }
                cindex++;
            }

            return new Shrine(line);

        } catch (IOException e) {
            return null;
        }
    }

}
