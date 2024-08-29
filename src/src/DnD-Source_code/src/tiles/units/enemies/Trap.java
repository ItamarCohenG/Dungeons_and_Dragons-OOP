package tiles.units.enemies;

import game.CombatSystem;
import game.GameBoard;
import game.Position;
import tiles.units.Unit;
import tiles.units.player.Player;
import utilities.Points.Exp;


public class Trap extends Enemy {
    private final int visibilityTime;
    private final int invisibilityTime;
    private int ticksCount;
    private boolean visible;


    public Trap(char tile, String name, int healthCapacity, int attackPoints, int defensePoints, Exp experienceValue, int visibilityTime, int invisibilityTime, GameBoard gameBoard) {
        super(tile, name, healthCapacity, attackPoints, defensePoints, 0, experienceValue, gameBoard);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
        this.visible = true;
    }

    // Getters & Setters
    public int getVisibilityTime() {
        return visibilityTime;
    }

    public int getInvisibilityTime() {
        return invisibilityTime;
    }

    public int getTicksCount() {
        return ticksCount;
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public Position action(Player player) {
        gameTick(player);
        return this.getPosition();
    }

    @Override
    public void gameTick(Player player) {
        this.ticksCount++;

        if (this.ticksCount % (this.visibilityTime + this.invisibilityTime) == 0) {
            this.ticksCount = 0;
            this.visible = true;
        }

        if (this.ticksCount == this.visibilityTime) {
            this.visible = false;
        }

        if (player.getCharTile() == '@' && player.getPosition().range(this.getPosition()) < 2) {
            CombatSystem.engageCombat(this, player);
        }
    }

    @Override
    public void accept(Unit visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return visible ? String.valueOf(getCharTile()) : ".";
    }
}
