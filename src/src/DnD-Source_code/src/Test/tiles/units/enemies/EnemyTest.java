package tiles.units.enemies;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import game.GameBoard;
import utilities.Points.Exp;

public class EnemyTest {
    Enemy enemy;

    @Before
    public void setUp() {
        GameBoard gameBoard = new GameBoard(null, 10, 10);  // Assuming constructor GameBoard(MessageCallBack, int, int)
        enemy = new Monster('M', "Goblin", 100, 10, 5, 3, new Exp(100), gameBoard);  // Using Monster as a concrete class of Enemy
    }

    @Test
    public void getName() {
        assertEquals("Name should be 'Goblin'", "Goblin", enemy.getName());
    }

    @Test
    public void setName() {
        enemy.setName("Orc");
        assertEquals("Name should be changed to 'Orc'", "Orc", enemy.getName());
    }

    @Test
    public void getCurrentHealth() {
        assertEquals("Initial health should be 100", 100, enemy.getCurrentHealth());
    }

    @Test
    public void setCurrentHealth() {
        enemy.setCurrentHealth(80);
        assertEquals("Health should be set to 80", 80, enemy.getCurrentHealth());
    }

    @Test
    public void getAttackPoints() {
        assertEquals("Attack points should be 10", 10, enemy.getAttackPoints());
    }
}
