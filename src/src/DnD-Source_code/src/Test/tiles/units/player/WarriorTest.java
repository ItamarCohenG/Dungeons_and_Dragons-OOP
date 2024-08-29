package Test.tiles.units.player;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import tiles.units.player.Warrior;
import utilities.Points.Cooldown;

public class WarriorTest {
    private Warrior warrior;
    private Cooldown cooldown;

    @Before
    public void setUp() {
        cooldown = new Cooldown(3); // Assuming cooldown needs 3 ticks to reset
        warrior = new Warrior("Conan", 120, 30, 20, cooldown);
    }

    @Test
    public void cooldown_IsReady() {
        cooldown.setCurrent(3);
        assertTrue("Warrior should be able to cast ability if cooldown is ready", warrior.canCastAbility());
    }

    @Test
    public void cannotCastAbility_CooldownNotReady() {
        cooldown.setCurrent(1);
        assertFalse("Warrior should not be able to cast ability if cooldown is not ready", warrior.canCastAbility());
    }

}
