package Test.tiles.units.enemies;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import game.GameBoard;
import tiles.units.enemies.Trap;
import utilities.Points.Exp;

public class TrapTest {
    Trap trap;

    @Before
    public void setUp() {
        GameBoard gameBoard = new GameBoard(null, 10, 10);
        trap = new Trap('T', "Spike Trap", 50, 0, 0, new Exp(50), 2, 3, gameBoard);
    }

    @Test
    public void getVisibilityTime() {
        assertEquals("Visibility time should be 2", 2, trap.getVisibilityTime());
    }

    @Test
    public void setGameBoard() {
        GameBoard newGameBoard = new GameBoard(null, 20, 20);
        trap.setGameBoard(newGameBoard);
        assertNotNull("GameBoard should be set", trap.getGameBoard());
    }

    @Test
    public void getGameBoard() {
        assertNotNull("GameBoard should not be null", trap.getGameBoard());
    }
}
