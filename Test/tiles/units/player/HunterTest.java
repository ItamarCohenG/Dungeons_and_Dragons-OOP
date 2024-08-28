package Test.tiles.units.player;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import tiles.units.player.Hunter;
import utilities.Points.Arrows;


public class HunterTest {
    private Hunter hunter;
    private Arrows arrows;

    @Before
    public void setUp() {
        arrows = new Arrows(10);
        hunter = new Hunter("Legolas", 100, 20, 15, 5, arrows);
    }

    @Test
    public void abilityName_IsCorrect() {
        assertEquals("Ability name should be 'Shoot'", "Shoot", hunter.getAbilityName());
    }

    @Test
    public void arrowsAreInitialized_Correctly() {
        assertEquals("Arrows should be initialized with 10 arrows", 10, hunter.getArrowsAbs().getCurrent());
    }

    @Test
    public void canCastAbility_EnoughArrows() {
        assertTrue("Hunter should be able to cast ability if there are enough arrows", hunter.canCastAbility());
    }

    @Test
    public void canCastAbility_NotEnoughArrows() {
        hunter.getArrowsAbs().setCurrent(0);
        assertFalse("Hunter should not be able to cast ability if there are no arrows", hunter.canCastAbility());
    }

}
