package tiles.units.player;

import CommandLine.UI;
import tiles.units.enemies.Enemy;
import utilities.Points.Arrows;
import utilities.Points.Cooldown;
import utilities.Points.abilityPoints;
import utilities.Randomizer.RandomGenerator;

import java.util.List;
import java.util.Vector;

public class Hunter extends Player{
    private final String abilityName;
    private final int abilityRange;
    private Arrows arrows;
    private RandomGenerator Random;
    private int ticksCount;

    public Hunter(String name, int healthCapacity, int attackPoints, int defensePoints, int abilityRange, Arrows arrows) {
        super(name, healthCapacity, attackPoints, defensePoints);
        this.abilityName = "Shoot";
        this.arrows = arrows;
        this.Random = new RandomGenerator();
        this.ticksCount = 0;
        this.abilityRange = abilityRange;
    }

    // Setters
    public String getAbilityName() {
        return abilityName;
    }

    public Arrows getArrowsAbs() {
        return arrows;
    }

    // Getter
    public abilityPoints getAbilityPoints() {
        return arrows;
    }

    // The rest of the methods
    public boolean canCastAbility() {
        return !arrows.isCurrentZero();
    }

    @Override
    protected void levelUp() {
        super.levelUp();
        this.arrows.setCurrent(this.arrows.getCurrent() + (10 * this.getLevel()));
        health.increaseCapacity(5 * this.getLevel());
        attackPoints += 2 * this.getLevel();
        defensePoints += this.getLevel();
        UI.print(this.getName() + " reached level " + this.getLevel() + ": +" + (10 * this.getLevel()) + " Arrows, +"
                + (6 * this.getLevel()) + " Attack, +" + (2 * this.getLevel()) + " Defense");        if (!getExperience().isLevelUp()) {
            return;
        }
        levelUp();
    }

    @Override
    public void castAbility(List< Enemy > enemies ){
        Vector<Enemy> closest = new Vector<Enemy>();
        Enemy chosenTarget;
        for (Enemy unit : enemies){
            if (this.getPosition().range(unit.getPosition()) < abilityRange && !unit.isDead()) {
                if (closest.isEmpty() && unit.getCharTile() != '@' && unit.toString() != "." ) {
                    closest.add(unit);
                }
                // if the unit is not the player and the unit are closer than the current closest units
                else if (!closest.isEmpty() && unit.getCharTile() != '@' && unit.toString() != "."  && this.getPosition().range(unit.getPosition()) < this.getPosition().range(closest.get(0).getPosition())) {
                    closest.clear();
                    closest.add(unit);
                } else if (!closest.isEmpty() && unit.getCharTile() != '@' && unit.toString() != "."  && this.getPosition().range(unit.getPosition()) == this.getPosition().range(closest.get(0).getPosition())) {
                    closest.add(unit);
                }
            }
        }
        if (closest.isEmpty() && canCastAbility()) {
            UI.print("Hunter " + this.getName() + " tried to shoot an enemy, but there are no targets in range!");
            return; // no targets in range to cast ability on - do nothing and be sad
        } else if (!this.canCastAbility()){
            UI.print("Hunter " + this.getName() + " tried to cast ability, but failed! no arrows available" + (10 - this.ticksCount) + " turns left until he can cast ability again!");
            return;
        }

        this.arrows.setCurrent(this.arrows.getCurrent() - 1);
        chosenTarget = closest.get(Random.generate(closest.size()));
        int def = chosenTarget.rollingDefensePoints();
        int hit = this.getAttackPoints();
        int dmg = hit - def ;
        chosenTarget.setCurrentHealth(chosenTarget.getCurrentHealth() - dmg);
        UI.print("\n" + this.getName() + " casted " + this.getAbilityName() + " for " + hit + " dmg! ");
        UI.print(chosenTarget.getName() + " rolled " + def + " defense points.");
        UI.print(this.getName() + " dealt " + dmg + " damage to " + chosenTarget.getName() + "!\nreducing their health to |HP: " + chosenTarget.getCurrentHealth() + '/' + chosenTarget.getHealthCapacity() + "|\n");
    }

    @Override
    public void updatePoints(Player player) {
        if (this.ticksCount == 10) {
            this.arrows.setCurrent(this.arrows.getCurrent() + 1);
            this.ticksCount = 0;
        }
        else {
            this.ticksCount++;
        }
    }

    @Override
    public String getPointFormat() {

        return "|Arrows: " + (this.getArrowsAbs().getCapacity())+ "/" + this.getArrowsAbs().getCapacity() + "  |";
    }

    public String getPointFormat2() {

        return "|Arrows: " + (this.getArrowsAbs().getCapacity())+ "/" + this.getArrowsAbs().getCapacity() + "|";
    }
}

