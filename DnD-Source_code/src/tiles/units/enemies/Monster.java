package tiles.units.enemies;

import CommandLine.UI;
import game.CombatSystem;
import game.GameBoard;
import game.Position;
import tiles.EmptyTile;
import tiles.Tile;
import tiles.WallTile;
import tiles.units.Unit;
import tiles.units.player.Player;
import utilities.Points.Exp;

import java.util.Random;

public class Monster extends Enemy {

    // Constructor
    public Monster(char tile, String name, int healthCapacity, int attackPoints, int defensePoints, int visionRange, Exp experienceValue, GameBoard gameBoard) {
        super(tile, name, healthCapacity, attackPoints, defensePoints, visionRange, experienceValue, gameBoard);
    }

    // The rest of the methods
    @Override
    public Position action(Player player) {
        Position newPosition = position;
        if (getPosition().range(player.getPosition()) < this.getVisionRange()) { // If the player is in range
            int dx = player.getPosition().getX() - getPosition().getX();
            int dy = player.getPosition().getY() - getPosition().getY();
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    newPosition = move("right");
                } else {
                    newPosition = move("left");
                }
            } else {
                if (dy > 0) {
                    newPosition = move("down");
                } else {
                    newPosition = move("up");
                }
            }
        } else { // If the player is not in range
            String[] directions = {"down", "right", "up", "left", "stay"};
            Random random = new Random();
            int select = random.nextInt(directions.length);
            newPosition = move(directions[select]);
        }
        if (!gameBoard.isValid(newPosition) || (gameBoard.isOccupied(newPosition) && !gameBoard.isOccupiedWithPlayer(newPosition))) {
            return this.getPosition();  // If out of bounds or occupied, stay in the current position
        }

        return newPosition;
    }

    @Override
    public void gameTick(Player player) {
        if (isPlayerInRange(player)) {
            takeAction(player);
        } else {
            Position newPosition = action(player);
            Tile nextTile = gameBoard.get(newPosition).orElseThrow(() -> new IndexOutOfBoundsException("No Such Tile"));

            if (nextTile instanceof EmptyTile) {
                gameBoard.swapTiles(this, nextTile);
            } else if (nextTile instanceof WallTile) {
            } else {
                interact(nextTile);
            }
        }
    }

    public Position move(String direction) {
        Position newPosition = switch (direction) {
            case ("left") -> getPosition().translate(-1, 0);
            case ("right") -> getPosition().translate(1, 0);
            case ("up") -> getPosition().translate(0, -1);
            case ("down") -> getPosition().translate(0, 1);
            default -> this.getPosition();
        };
        return newPosition;
    }

    @Override
    public boolean isPlayerInRange(Player player) {
        return this.getPosition().range(player.getPosition()) <= this.getVisionRange();
    }

    @Override
    public void accept(Unit visitor) {
        visitor.visit(this);
    }
}
