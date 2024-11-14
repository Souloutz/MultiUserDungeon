package multiuserdungeon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.inventory.elements.Armor;
import multiuserdungeon.inventory.elements.Bag;
import multiuserdungeon.inventory.elements.Buff;
import multiuserdungeon.inventory.elements.Food;
import multiuserdungeon.inventory.elements.Weapon;

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
        assertTrue(buff instanceof Buff);
        assertTrue(food instanceof Food);
        assertTrue(weapon instanceof Weapon);
    }

    @Test
    public void testGetItemDNE() throws IOException{
        InventoryElement item = Items.getInstance().getItem(82379);
        assertEquals(item, null);
    }

    @Test
    public void testGetItemOne() throws IOException{
        InventoryElement item = Items.getInstance().getItem(0);
        assertEquals("Starter Bag", item.getName());
        assertEquals("Default bag you start with.", item.getDescription());
        assertEquals(0, item.getGoldValue());

    }
}
