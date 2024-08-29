package Test.tiles;

import game.Position;
import org.junit.Before;
import org.junit.Test;
import tiles.EmptyTile;
import tiles.Tile;
import tiles.WallTile;

import static org.junit.Assert.*;

public class WallTileTest {
    WallTile wallTile;

    @Before
    public void setUp() {
        wallTile = new WallTile(new Position(2, 2));
    }

    @Test
    public void testInitialize() {
        assertEquals("Initial position should be (2,2)", new Position(2, 2), wallTile.getPosition());
    }

    @Test
    public void testGetCharTile() {
        assertEquals("Char tile should be '#'", '#', wallTile.getCharTile());
    }

    @Test
    public void testToString() {
        assertEquals("toString should return '#'", "#", wallTile.toString());
    }

    @Test
    public void testSwapPosition() {
        Tile otherTile = new EmptyTile(new Position(1, 1));
        wallTile.swapPosition(otherTile);
        assertEquals("WallTile position should be (1,1) after swap", new Position(1, 1), wallTile.getPosition());
        assertEquals("OtherTile position should be (2,2) after swap", new Position(2, 2), otherTile.getPosition());
    }
}
