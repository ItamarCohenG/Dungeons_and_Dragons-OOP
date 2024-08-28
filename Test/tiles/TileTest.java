package Test.tiles;

import game.Position;
import org.junit.Before;
import org.junit.Test;
import tiles.Tile;
import tiles.units.Unit;

import static org.junit.Assert.*;

public class TileTest {
    Tile tile;

    @Before
    public void setUp() {
        tile = new Tile('#', new Position(0, 0)) {
            @Override
            public void accept(Unit unit) {

            }
        };
    }

    @Test
    public void testGetCharTile() {
        assertEquals("Char tile should be '#'", '#', tile.getCharTile());
    }

    @Test
    public void testSetCharTile() {
        tile.setCharTile('*');
        assertEquals("Char tile should be changed to '*'", '*', tile.getCharTile());
    }

    @Test
    public void testGetPosition() {
        assertEquals("Position should be (0,0)", new Position(0, 0), tile.getPosition());
    }

    @Test
    public void testSetPosition() {
        Position newPosition = new Position(1, 1);
        tile.setPosition(newPosition);
        assertEquals("Position should be updated to (1,1)", newPosition, tile.getPosition());
    }
}
