package tiles.units.player;

import CommandLine.UI;
import tiles.units.enemies.Enemy;
import utilities.Points.Cooldown;
import utilities.Randomizer.RandomGenerator;
import utilities.Points.abilityPoints;

import java.util.List;
import java.util.Vector;

public class Warrior extends Player {
    private final String abilityName;
    private Cooldown cooldown;
    private RandomGenerator Random;

    public Warrior(String name, int healthCapacity, int attackPoints, int defensePoints, Cooldown abilityCooldown) {
        super(name, healthCapacity, attackPoints, defensePoints);
        this.abilityName = "Avenger’s Shield";
        this.cooldown = abilityCooldown;
        this.Random = new RandomGenerator();
    }

    // Setters
    public String getAbilityName() {
        return abilityName;
    }
    public Cooldown getAbilityCooldown() {
        return cooldown;
    }

    // Getter
    public abilityPoints getAbilityPoints() {
        return cooldown;
    }

    // The rest of the methods
    public boolean canCastAbility() {
        return cooldown.isReady();
    }

    @Override
    protected void levelUp() {
        super.levelUp();
        cooldown.increaseCurrentToCapacity(); // Reset cooldown
        health.increaseCapacity(5 * this.getLevel());
        health.increaseCurrentToCapacity();
        attackPoints += 2 * this.getLevel();
        defensePoints += this.getLevel();
        UI.print("Warrior " + this.getName() + " reached level " + this.getLevel() + ": +" + (15 * this.getLevel()) + " Health, +"
                + (6 * this.getLevel()) + " Attack, +" + (2 * this.getLevel()) + " Defense");        if (!getExperience().isLevelUp()) {
            return;
        }
        levelUp();
    }

    @Override
    public void castAbility(List<Enemy> enemies ){
        Vector<Enemy> closest = new Vector<Enemy>();
        Enemy chosenTarget;
        for (Enemy unit : enemies){
            if (this.getPosition().range(unit.getPosition()) < 3 && !unit.isDead()) {
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
            UI.print("Warrior " + this.getName() + " tried to cast ability, but failed! (no targets in range!)");
            return; // no targets in range to cast ability on - do nothing and be sad
        } else if (!this.canCastAbility()){
            UI.print("Warrior " + this.getName() + " tried to cast ability, but failed! " + this.cooldown.getRemainingCooldown() + " turns left until he can cast ability again!");
            return;
        }
        cooldown.reset();
        chosenTarget = closest.get(Random.generate(closest.size()));
        int def = chosenTarget.rollingDefensePoints();
        int hit = this.getHealthCapacity() / 10;
        int dmg = hit - def ;
        chosenTarget.setCurrentHealth(chosenTarget.getCurrentHealth() - dmg);
        this.setCurrentHealth(Math.min(this.getCurrentHealth() + (10 * this.getDefensePoints()), this.getHealthCapacity()));
        UI.print("\n" + this.getName() + " casted " + this.getAbilityName() + " for " + hit + " dmg! ");
        UI.print(chosenTarget.getName() + " rolled " + def + " defense points.");
        UI.print(this.getName() + " dealt " + dmg + " damage to " + chosenTarget.getName() + "!\nreducing their health to |HP: " + chosenTarget.getCurrentHealth() + '/' + chosenTarget.getHealthCapacity() + "|\n");
    }

    @Override
    public void updatePoints(Player player) {
        this.getAbilityCooldown().setCurrent(Math.min((this.getAbilityCooldown().getCurrent() + 1), this.getAbilityCooldown().getCapacity()));
    }

    @Override
    public String getPointFormat() {
        return "|    CD: " + (cooldown.getCapacity() - cooldown.getCurrent())+ "/" + this.getAbilityCooldown().getCapacity() + "    |";
    }

    public String getPointFormat2() {
        return "|CD: " + (cooldown.getCapacity() - cooldown.getCurrent())+ "/" + this.getAbilityCooldown().getCapacity() + "|";
    }
}
