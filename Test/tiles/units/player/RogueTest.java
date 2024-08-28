package Test.tiles.units.player;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import game.Position;
import tiles.units.player.Rogue;

public class RogueTest {
    private Rogue rogue;
    private Position startPosition;

    @Before
    public void setUp() {
        startPosition = new Position(0, 0);
        rogue = new Rogue("Thief", 80, 25, 15, 10);
    }

    @Test
    public void energy_IsCorrectlyInitialized() {
        // בודק אם האנרגיה הוזנה נכון בהתחלה כרבע מהאנרגיה הכוללת
        int expectedInitialEnergy = rogue.getEnergyPool() / 4;
        assertEquals("Initial energy should be a quarter of the energy pool", expectedInitialEnergy, rogue.getEnergyCurrent());
    }
}
