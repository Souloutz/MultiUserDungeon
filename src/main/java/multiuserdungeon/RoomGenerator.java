package multiuserdungeon.QuintonsMagicFolder;

import multiuserdungeon.map.*;
import multiuserdungeon.map.tiles.Chest;
import multiuserdungeon.map.tiles.Merchant;
import multiuserdungeon.map.tiles.NPC;
import multiuserdungeon.map.tiles.Obstacle;
import multiuserdungeon.map.tiles.shrine.Shrine;
import multiuserdungeon.map.tiles.trap.Trap;

import java.util.Random;
import java.util.HashMap;
import java.util.Arrays;

public class RoomGenerator {

    private static Random random = new Random();

    private static long xorshift(long state) {
        state ^= state << 13;
        state ^= state >>> 7;
        state ^= state << 17;
        return state;
    }

    private static int[] permutation(int len) {
        long[] pre = new long[len];
        pre[0] = random.nextLong((long)Math.pow(2,64));
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

        int[] x = permutation(random.nextInt(6) + 4);
        int[] y = permutation(random.nextInt(6) + 4);
        int max = (x.length > y.length) ? y.length : x.length;

        Room room = new Room(x.length,y.length,grabDescription());
        int row;
        int col;
        if(direction == Compass.NORTH) {
            row = 0;
            col = random.nextInt(x.length);
        } else if (direction == Compass.EAST) {
            row = random.nextInt(y.length);
            col = x.length - 1;
        } else if (direction == Compass.SOUTH) {
            row = y.length - 1;
            col = random.nextInt(x.length);
        } else {
            row = random.nextInt(y.length);
            col = 0;
        }
        room.addConnection(row,col,attached);

        // {0: obstacle, 1: NPC, 2: Trap, 3: chest}
        // Permutation to fill rooms is len = 32
        // modulo 4, but 30 = shrine and 31 = merchant

        int[] objects = permutation(32);

        for (int i = 0;i < max;i++) {
            Tile tile = room.getTile(y[i],x[i]);
            int place = objects[i] % 4;
            if (objects[i] == 31) {
                tile.addObject((TileObject)generateMerchant());
            } else if (objects[i] == 30) {
                tile.addObject((TileObject)generateShrine());
            } else if (place == 0) {
                tile.addObject((TileObject)generateObstacle());
            } else if (place == 1) {
                tile.addObject((TileObject)generateNPC());
            } else if (place == 2) {
                tile.addObject((TileObject)generateTrap());
            } else if (place == 3) {
                tile.addObject((TileObject)generateChest());
            }

        }
    }

    private static String grabDescription(){
        //TODO{have chatgpt generate like 50 random descriptions of dungeon rooms and return one}
    }

    private static Merchant generateMerchant(){
        //TODO{Generate merchant, use Itemgenerator for the items}
    }

    private static Chest generateChest() {
        //TODO{Generate chest, user Itemgenerator}
    }

    private static Obstacle generateObstacle() {
        //TODO{ Similar to grab description, select set of predetermined values}
    }

    private static NPC generateNPC() {
        //TODO{Dynamically generate NPCs}
    }

    private static Trap generateTrap() {
        //TODO{generate traps}
    }

    private static Shrine generateShrine() {
        //TODO{Pretty sure all shhrines are the same}
    }

    public static void main(String[] args) {
    }
}
