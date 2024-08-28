package utilities.Points;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ExpTest {
    private Exp exp;

    @Before
    public void setUp() {
        exp = new Exp(100);
    }

    @Test
    public void increaseCurrent_IncreasesBySpecifiedAmount() {
        exp.increaseCurrent(10);
        assertEquals("Current should increase by 10", 10, exp.getCurrent());
    }

    @Test
    public void decreaseCurrent_DecreasesBySpecifiedAmount() {
        exp.setCurrent(50);
        exp.decreaseCurrent(20);
        assertEquals("Current should decrease by 20", 30, exp.getCurrent());
    }

    @Test
    public void isLevelUp_WhenCurrentReachesCapacity() {
        exp.setCurrent(100);
        assertTrue("Should be level up when current reaches capacity", exp.isLevelUp());
    }

    @Test
    public void isLevelUp_WhenCurrentDoesNotReachCapacity() {
        exp.setCurrent(50);
        assertFalse("Should not be level up when current does not reach capacity", exp.isLevelUp());
    }
}
