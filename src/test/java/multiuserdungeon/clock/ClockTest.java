package multiuserdungeon.clock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import multiuserdungeon.clock.Clock;
import multiuserdungeon.clock.Day;
import multiuserdungeon.clock.Night;
import multiuserdungeon.clock.Time;

public class ClockTest {

    @Test
    public void testCompleteTurn() {
        Clock c = new Clock();

        assertEquals(0, c.getTurn());
        assertTrue(c.isDay());

        for (int i = 0; i < 10; i ++) {
            c.completeTurn();
        }
        assertFalse(c.isDay());
    }

    @Test
    public void testStateChange() {
        Clock c = new Clock();
        assertTrue(c.getCurrentTime()  instanceof  Day);
        for (int i = 0; i < 10; i ++) {
            c.completeTurn();
        }

        assertTrue(c.getCurrentTime() instanceof  Night);


    }
}