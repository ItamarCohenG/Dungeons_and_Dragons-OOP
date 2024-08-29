package tiles.units.enemies;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import game.GameBoard;
import utilities.Points.Exp;

public class MonsterTest {
    Monster monster;

    @Before
    public void setUp() {
        GameBoard gameBoard = new GameBoard(null, 10, 10);
        monster = new Monster('M', "Dragon", 300, 40, 20, 5, new Exp(500), gameBoard);
    }

    @Test
    public void getVisionRange() {
        assertEquals("Vision range should be 5", 5, monster.getVisionRange());
    }

    @Test
    public void setVisionRange() {
        monster.setVisionRange(10);
        assertEquals("Vision range should be updated to 10", 10, monster.getVisionRange());
    }

    @Test
    public void setExperienceValue() {
        Exp newExp = new Exp(1000);
        monster.setExperienceValue(newExp);
        assertEquals("Experience value should be set to 1000", 1000, monster.getExperienceValue().getCapacity());
    }

    @Test
    public void setGameBoard() {
        GameBoard newGameBoard = new GameBoard(null, 20, 20);
        monster.setGameBoard(newGameBoard);
        assertNotNull("GameBoard should be set", monster.getGameBoard());
    }

    @Test
    public void getGameBoard() {
        assertNotNull("GameBoard should not be null", monster.getGameBoard());
    }
}
