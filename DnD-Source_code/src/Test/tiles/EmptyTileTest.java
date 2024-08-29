package Test.tiles;

import game.Position;
import org.junit.Before;
import org.junit.Test;
import tiles.EmptyTile;

import static org.junit.Assert.*;

public class EmptyTileTest {
    EmptyTile emptyTile;

    @Before
    public void setUp() {
        emptyTile = new EmptyTile(new Position(5, 5));
    }

    @Test
    public void testInitialize() {
        assertEquals("Initial position should be (5,5)", new Position(5, 5), emptyTile.getPosition());
    }

    @Test
    public void testGetCharTile() {
        assertEquals("Char tile should be '.'", '.', emptyTile.getCharTile());
    }
}
