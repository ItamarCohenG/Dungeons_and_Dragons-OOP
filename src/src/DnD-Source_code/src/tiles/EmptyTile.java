package tiles;

import game.Position;
import tiles.units.Unit;

public class EmptyTile extends Tile {
    public static final char emptyTile = '.';

    // Constructor
    public EmptyTile(Position position) {
        super(emptyTile);
        this.position = position;
    }

    // Visitor pattern - Visited, Overridden.
    public void accept(Unit unit) {
        unit.visit(this);
    }

}
