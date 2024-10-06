package multiuserdungeon.map;

import java.util.Iterator;

public class RoomIterator implements Iterator<Tile> {
    private int index;
    private Room room;

    public RoomIterator (Room room) {
        this.room = room;
        this.index = 0;
    }

    @Override
    public Tile next () {
        int currindex = index;
        index++;
        return room.getTile(currindex % room.getWidth(),(int)Math.floor(currindex / room.getWidth()));
    }
    @Override
    public boolean hasNext() {
        return index < room.getLength() * room.getWidth();
    }
}
