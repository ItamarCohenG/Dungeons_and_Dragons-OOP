package CommandLine;

import game.GameBoard;
import game.Position;
import tiles.EmptyTile;
import tiles.Tile;
import tiles.WallTile;
import tiles.units.enemies.Enemy;
import tiles.units.enemies.Monster;
import tiles.units.enemies.Trap;
import tiles.units.player.*;
import utilities.Points.Arrows;
import utilities.Points.Cooldown;
import utilities.Points.Exp;

public class TileFactory {
    private GameBoard gameBoard;

    public TileFactory() {
    }

    public Enemy createEnemy(char x) {
        Enemy e;
    if (x == '\0') {
        throw new IllegalArgumentException("Null character encountered in input.");
    }
        switch (x) { // can add more
            case ('s') -> e = new Monster(x, "Lannister Soldier", 80, 8, 3, 3, new Exp(25), gameBoard);
            case ('k') -> e = new Monster(x, "Lannister Knight", 200, 14, 8, 4, new Exp(50), gameBoard);
            case ('q') -> e = new Monster(x, "Queen’s Guard", 400, 20, 15, 5, new Exp(100), gameBoard);
            case ('z') -> e = new Monster(x, "Wright", 600, 30, 15, 3, new Exp(100), gameBoard);
            case ('b') -> e = new Monster(x, "Bear-Wright", 1000, 75, 30, 4, new Exp(250), gameBoard);
            case ('g') -> e = new Monster(x, "Giant-Wright", 1500, 100, 40, 5, new Exp(500), gameBoard);
            case ('w') -> e = new Monster(x, "White Walker", 2000, 150, 50, 6, new Exp(1000), gameBoard);
            case ('M') -> e = new Monster(x, "The Mountain", 1000, 60, 25, 6, new Exp(500), gameBoard);
            case ('C') -> e = new Monster(x, "Queen Cersei", 100, 10, 10, 1, new Exp(1000), gameBoard);
            case ('K') -> e = new Monster(x, "Night’s King", 5000, 300, 150, 8, new Exp(5000), gameBoard);
            case ('B') -> e = new Trap(x, "Bonus", 1, 1, 1, new Exp(250), 1, 5, gameBoard);
            case ('Q') -> e = new Trap(x, "Queen’s", 250, 50, 10, new Exp(100), 3, 7, gameBoard);
            case ('D') -> e = new Trap(x, "Death", 500, 100, 20, new Exp(250), 1, 10, gameBoard);
            case ('@') -> { return null; }
            default -> {throw new IllegalStateException("Unexpected value: " + x);}
        }
        return e;
    }

    public Tile createTile(char x) {
        switch (x) {
            case ('.') -> {
                return new EmptyTile(new Position(0, 0));
            }
            case ('#') -> {
                return new WallTile(new Position(0, 0));
            }
            default -> throw new IllegalStateException("Unexpected value: " + x);
        }
    }

    public Player createPlayer(int i) { // can add more
        Player p = switch (i) {
            case 1 -> new Warrior("Jon Snow", 300, 30, 4, new Cooldown(3));
            case 2 -> new Warrior("The Hound", 400, 20, 6, new Cooldown(5));
            case 3 -> new Mage("Melisandre", 100, 5, 1, 300, 30, 15, 5, 6);
            case 4 -> new Mage("Thoros of Myr", 250, 25, 4, 150, 20, 20, 3, 4);
            case 5 -> new Rogue("Arya Stark", 150, 40, 2, 20);
            case 6 -> new Rogue("Bronn", 250, 35, 3, 50);
            case 7 -> new Hunter("Ygritte", 220, 30, 2,6, new Arrows(10));
            case 8 -> new Warrior("Itamar", 300, 30, 4, new Cooldown(1));
            case 9 -> new Rogue("Cohen", 150, 40, 2, 0);
            case 10 -> new Mage("Tester", 1, 9999, 1, 99, 1, 9999, 99, 999);
            default -> throw new IllegalStateException("Unexpected value: " + i);
        };
        return p;
    }


}
