package Test.tiles.units.player;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import game.Position;
import tiles.units.player.Mage;

public class MageTest {
    private Mage mage;

    @Before
    public void setUp() {
        Position startPosition = new Position(0, 0); // נניח שיש פוזיציה התחלתית כלשהי
        mage = new Mage("Gandalf", 100, 15, 10, 100, 10, 20, 5, 3);
    }

    @Test
    public void mana_IsCorrectlyInitialized() {
        // נניח שהמנה ההתחלתית היא רבע מכמות המנה הכוללת
        int expectedInitialMana = mage.getManaPool() / 4;
        assertEquals("Initial mana should be a quarter of the mana pool", expectedInitialMana, mage.getManaCurrent());
    }
}
