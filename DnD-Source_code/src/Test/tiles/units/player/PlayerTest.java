package Test.tiles.units.player;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import game.Position;
import tiles.units.player.Warrior;
import utilities.Points.Cooldown;

public class PlayerTest {
    private Warrior warrior;

    @Before
    public void setUp() {
        Position position = new Position(0, 0);  // Assuming you have a Position class
        Cooldown cooldown = new Cooldown(3);  // Assuming the cooldown time is 3 ticks
        warrior = new Warrior("TestPlayer", 100, 10, 5, cooldown);
    }

    @Test
    public void getName() {
        assertEquals("Name should be 'TestPlayer'", "TestPlayer", warrior.getName());
    }

    @Test
    public void setName() {
        warrior.setName("NewName");
        assertEquals("Name should be changed to 'NewName'", "NewName", warrior.getName());
    }

    @Test
    public void getCurrentHealth() {
        assertEquals("Initial health should be 100", 100, warrior.getCurrentHealth());
    }

    @Test
    public void setCurrentHealth() {
        warrior.setCurrentHealth(80);
        assertEquals("Health should be set to 80", 80, warrior.getCurrentHealth());
    }

    @Test
    public void getAttackPoints() {
        assertEquals("Attack points should be 10", 10, warrior.getAttackPoints());
    }

    @Test
    public void setAttackPoints() {
        warrior.setAttackPoints(15);
        assertEquals("Attack points should be updated to 15", 15, warrior.getAttackPoints());
    }
}
