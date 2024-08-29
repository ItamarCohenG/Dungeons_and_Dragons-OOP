package tiles.units;

import CommandLine.SpaceSetter;
import tiles.EmptyTile;
import tiles.Tile;
import tiles.WallTile;
import tiles.units.enemies.Enemy;
import tiles.units.player.Player;
import utilities.Points.Exp;
import utilities.Points.Health;
import utilities.Randomizer.RandomGenerator;

public abstract class Unit extends Tile {
    private RandomGenerator random; // needs to extend tile
    protected String name;
    protected Health health; // Protected? Private?
    protected int attackPoints, defensePoints;

    // Constructor
    public Unit(char tile, String name, int healthCapacity, int attackPoints, int defensePoints) {
        super(tile);
        this.setCharTile(tile);
        this.name = name;
        this.health = new Health(healthCapacity);
        this.attackPoints = attackPoints;
        this.defensePoints = defensePoints;
        this.random = new RandomGenerator();
    }

    public abstract Exp getExperienceValue();

    // Getters & Setters
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentHealth() {
        return this.health.getCurrent();
    }
    public void setCurrentHealth(int health) {
        this.health.setCurrent(Math.max(health, 0));
    } // The health cannot be negative

    public int getAttackPoints() {
        return this.attackPoints;
    }
    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    public int getDefensePoints() {
        return this.defensePoints;
    }
    public void setDefensePoints(int defensePoints) {
        this.defensePoints = defensePoints;
    }

    public int getHealthCapacity() {
        return this.health.getCapacity();
    }
    public void setHealthCapacity(int healthCapacity) {
        this.health.setCapacity(healthCapacity);
    }

    public Health getHealth() {
        return this.health;
    }

    // Visitor pattern - Visited
    public abstract void accept(Unit u);

    // Visitor pattern - Visitor
    public void visit(EmptyTile empty) {
        this.swapPosition(empty);
    }
    public void visit(WallTile wall) {
        return;
    }
    public abstract void visit(Enemy enemy);
    public abstract void visit(Player Player);

    // The rest of the methods:
    public abstract boolean isPlayer();

    public boolean isAlive() {
        return getCurrentHealth() > 0;
    }

    public String toString() {
        return String.valueOf(this.getCharTile());
    }
    public boolean isDead(){
        return this.health.isDead();
    }

    public int rollingDefensePoints(){
        return random.generate(this.attackPoints);
    }

    public String spaceNeeded() {
        if (attackPoints < 10) {
            return " |";
        }
        return "|";
    }

    public String format() {
        return (this.getName() + SpaceSetter.evenSpacing(this.getName(), 25) + "|HP: " + this.getCurrentHealth() + '/' + this.getHealthCapacity() + SpaceSetter.evenSpacing("" + this.getCurrentHealth() + '/' + this.getHealthCapacity(), 13) + "|     " + "|Attack: " + this.getAttackPoints() + this.spaceNeeded() + SpaceSetter.evenSpacing(this.getAttackPoints() + this.spaceNeeded(), 13) + "|Defence: " + this.getDefensePoints() + "|     ");
    }
}
