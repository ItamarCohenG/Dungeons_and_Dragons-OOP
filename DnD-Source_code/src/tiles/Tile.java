package tiles;

import game.Position;
import tiles.units.Unit;
import CommandLine.MessageCallBack;

public abstract class Tile {
    protected char charTile;
    protected Position position;

    // Constructor
    public Tile(char charTile, Position pos) {
        this.charTile = charTile;
        this.position = pos;
    }

    // Constructor without position
    public Tile(char Char) {
        this.charTile = Char;
        this.position = new Position(0, 0);
    }

    // Initialize the position of the tile
    public void initialize(Position position) {
        this.position = position;
    }

    public void initialize(Position position, MessageCallBack messageCallBack, Runnable onDeath) {
        this.position = position;
    }

    // Getters & Setters
    public char getCharTile() {
        return charTile;
    }

    public void setCharTile(char charTile) {
        this.charTile = charTile;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position pos) {
        this.position = pos;
    }

    // Visitor pattern - Visited, Original.
    public abstract void accept(Unit unit);

    // The rest of the methods
    @Override
    public String toString() {
        return String.valueOf(charTile);
    }

    public void swapPosition(Tile other) {
        Position temp = this.position;
        this.setPosition(other.getPosition());
        other.setPosition(temp);
    }
}
