package utilities.Points;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrowsTest {
    private Arrows arrows;

    @Before
    public void setUp() {
        arrows = new Arrows(10);
    }

    @Test
    public void setCapacity_UpdatesCapacityAndCurrent() {
        arrows.setCapacity(20);
        assertEquals("Capacity should be updated to 20", 20, arrows.getCapacity());
        assertEquals("Current should also be updated to 20 when capacity is set", 20, arrows.getCurrent());
    }

    @Test
    public void setCurrent_UpdatesCurrentAndCapacity() {
        arrows.setCurrent(5);
        assertEquals("Current should be updated to 5", 5, arrows.getCurrent());
        assertEquals("Capacity should also be updated to 5 when current is set", 5, arrows.getCapacity());
    }

    @Test
    public void getCapacity_ReturnsCorrectCapacity() {
        assertEquals("Initial capacity should be 10", 10, arrows.getCapacity());
        arrows.setCapacity(15);
        assertEquals("Updated capacity should be 15", 15, arrows.getCapacity());
    }

    @Test
    public void getCurrent_ReturnsCorrectCurrent() {
        assertEquals("Initial current should be 10", 10, arrows.getCurrent());
        arrows.setCurrent(7);
        assertEquals("Updated current should be 7", 7, arrows.getCurrent());
    }
}
