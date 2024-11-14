package multiuserdungeon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.inventory.elements.Armor;
import multiuserdungeon.inventory.elements.Bag;
import multiuserdungeon.inventory.elements.Buff;

import org.junit.jupiter.api.Test;

public class itemsTest {

    @Test
    public void testGetRandomList() throws IOException{
        int expectedSize = 2;
        int actualSize = Items.getInstance().getRandomList(2).size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testGetItem() throws IOException{
        InventoryElement bag = Items.getInstance().getItem(0);
        InventoryElement armor = Items.getInstance().getItem(1);
        InventoryElement buff = Items.getInstance().getItem(2);
        InventoryElement food = Items.getInstance().getItem(3);
        InventoryElement weapon = Items.getInstance().getItem(4);
        assertTrue(bag instanceof Bag);
        assertTrue(armor instanceof Armor);
        assertTrue(buff instanceof Armor);
        assertTrue(food instanceof Armor);
        assertTrue(weapon instanceof Armor);
    }

    @Test
    public void testGetItemDNE() throws IOException{
        InventoryElement item = Items.getInstance().getItem(82379);
        assertEquals(item, null);
    }
}
