package utilities.Points;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CooldownTest {
    private Cooldown cooldown;

    @Before
    public void setUp() {
        cooldown = new Cooldown(10);
    }

    @Test
    public void isReady_AfterReachingCapacity() {
        cooldown.setCurrent(10);
        assertTrue("Cooldown should be ready when current reaches capacity", cooldown.isReady());
    }

    @Test
    public void reset_ResetsCooldown() {
        cooldown.reset();
        assertEquals("Reset should set current to -1", -1, cooldown.getCurrent());
    }

    @Test
    public void getRemainingCooldown_AfterUpdate() {
        cooldown.setCurrent(5);
        assertEquals("Remaining cooldown after setting current to 5 should be 5", 5, cooldown.getRemainingCooldown());
    }
}
