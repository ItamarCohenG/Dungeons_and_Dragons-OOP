package tiles;

import CommandLine.UI;
import game.Position;
import tiles.units.Unit;

public class WallTile extends Tile {
    public static final char wallTile = '#';

    // Constructor
    public WallTile(Position position) {
        super(wallTile, position);
    }

    // Visitor pattern - Visited, Overridden.
    @Override
    public void accept(Unit unit) {
        UI.print("Unit encountered a wall and cannot pass.");
    }

    public boolean isWall() {
        return true;
    }
}
