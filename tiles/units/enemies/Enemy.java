package tiles.units.enemies;

import CommandLine.UI;
import game.CombatSystem;
import game.GameBoard;
import game.Position;
import tiles.EmptyTile;
import tiles.Tile;
import tiles.units.Unit;
import tiles.units.player.Player;
import utilities.Points.Exp;

import java.util.Vector;

public abstract class Enemy extends Unit {
    protected Exp experienceValue;
    protected int visionRange;
    protected GameBoard gameBoard;  // Add reference to GameBoard

    // Constructor
    public Enemy(char tile, String name, int healthCapacity, int attackPoints, int defensePoints, int visionRange, Exp experienceValue, GameBoard gameBoard) {
        super(tile, name, healthCapacity, attackPoints, defensePoints);
        this.experienceValue = experienceValue;
        this.visionRange = visionRange;
        this.gameBoard = gameBoard;  // Initialize the gameBoard reference
    }

    // Getters & Setters
    public int getVisionRange() {
        return this.visionRange;
    }
    public void setVisionRange(int visionRange) {
        this.visionRange = visionRange;
    }

    public Exp getExperienceValue() {
        return experienceValue;
    }
    public void setExperienceValue(Exp experienceValue) {
        this.experienceValue = experienceValue;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    // The rest of the methods
    public boolean isPlayer() {
        return false;
    }

    public Tile playerLocation(Player player) {
        Position actionPos = this.action(player);  // Determine the action position - actionPos is the position where the Player is

        if (!gameBoard.isValid(actionPos)) {
            throw new IndexOutOfBoundsException("The position " + actionPos + " is out of bounds.");
        }

        return gameBoard.get(actionPos).orElseThrow(() -> new IndexOutOfBoundsException("No Such Tile"));
    } // returns the tile where the player is

    public boolean isTileInRange(Tile t) {
        return this.position.range(t.getPosition()) <= this.visionRange;
    } // Check if the tile is in range of the enemy

    public boolean isPlayerInRange(Player player) {
        return this.isTileInRange(this.playerLocation(player));
    } // Check if the player is in range of the enemy

    public int onDeath() {
        return experienceValue.getCurrent();
    }

    // Visitor pattern - Visitor, visited will be implemented in the subclasses
    @Override
    public void visit(Enemy enemy) {
        return;
    }
    @Override
    public void visit(Player player) {
        CombatSystem.engageCombat(player, this);
    }

    // public abstract void takeAction(Player player);
    public void takeAction(Player player) {
        Tile targetTile = this.playerLocation(player);  // Get the target tile based on the player's position
        this.interact(targetTile);  // Interact with the tile at the action position
        gameBoard.swapTiles(this, targetTile);  // Swap positions if needed
    } // Needs to be implemented in the subclasses - Monster and Trap.

    public void interact(Tile tile) {
        tile.accept(this);  // Assuming a Visitor pattern, where Tile can accept an Enemy
    }

    public abstract Position action(Player player);

    public abstract void gameTick(Player player);
}
