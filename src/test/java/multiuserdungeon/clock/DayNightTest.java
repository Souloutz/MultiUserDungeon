package multiuserdungeon.clock;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DayNightTest {

    @Test
    public void testDay() {
        Day d = new Day(new Clock());
        assertTrue(d.isDay()); //should be day

        assertEquals(1.1, d.getStatChange(CreatureBuff.DIURNAL)); //diurnal creature stat multiplier during day
        assertEquals(0.8, d.getStatChange(CreatureBuff.NOCTURNAL)); //nocturnal creature stat multiplier during day
    }

    @Test
    public void testNight() {
        Night n = new Night(new Clock());
        assertFalse(n.isDay()); // should be night

        assertEquals(0.9, n.getStatChange(CreatureBuff.DIURNAL)); //diurnal creature stat multiplier during night
        assertEquals(1.2, n.getStatChange(CreatureBuff.NOCTURNAL)); //nocturnal creature stat multiplier during night
    }
}

