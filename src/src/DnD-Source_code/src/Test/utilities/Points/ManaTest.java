package utilities.Points;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ManaTest {
    private Mana mana;

    @Before
    public void setUp() {
        mana = new Mana(100);
    }

    @Test
    public void getCapacity_InitialCapacity() {
        assertEquals("Initial capacity should be 100", 100, mana.getCapacity());
    }

    @Test
    public void setCapacity_UpdatesCapacity() {
        mana.setCapacity(150);
        assertEquals("Updated capacity should be 150", 150, mana.getCapacity());
    }

    @Test
    public void reduceCurrent_ReduceByQuarter() {
        int initialCurrent = mana.getCurrent();
        mana.reduceCurrent(25);
        assertEquals("Current should be reduced by 25", initialCurrent - 25, mana.getCurrent());
    }

    @Test
    public void increaseCurrent_IncreaseByQuarter() {
        int initialCurrent = mana.getCurrent();
        mana.increaseCurrent(25);
        assertEquals("Current should be increased by 25", initialCurrent + 25, mana.getCurrent());
    }
}
