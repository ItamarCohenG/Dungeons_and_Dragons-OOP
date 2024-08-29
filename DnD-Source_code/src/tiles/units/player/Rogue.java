package tiles.units.player;

import CommandLine.UI;
import tiles.units.enemies.Enemy;
import utilities.Points.Energy;
import utilities.Points.Exp;
import utilities.Points.abilityPoints;

import java.util.List;
import java.util.Vector;

public class Rogue extends Player {
    private final String abilityName;
    private Energy energy;
    private final int energyCost;

    public Rogue(String name, int healthCapacity, int attackPoints, int defensePoints, int energyCost) {
        super(name, healthCapacity, attackPoints, defensePoints);
        this.energy = new Energy(100);
        this.energyCost = energyCost;
        this.abilityName = "Fan of Knives";
    }

    // Getters & Setters
    public int getEnergyPool() {
        return 100;
    }
    public int getEnergyCurrent() {
        return this.energy.getCurrent();
    }
    public Energy getEnergy() {
        return this.energy;
    }

    public String getAbilityName() {
        return abilityName;
    }
    public int getEnergyCost() {
        return energyCost;
    }

    @Override
    public void updatePoints(Player player) {
        this.energy.setCurrent(Math.min(this.energy.getCurrent() + 10, 100));
    }

    // abstract methods that needs to be implemented
    public void castAbility(List<Enemy> enemies) {
        if (!this.canCastAbility()) {
            UI.print(String.format("%s tried to cast %s, but there was not enough energy: %d/%d", this.getName(), this.getAbilityName(), this.energy.getCurrent(), this.energy.getCapacity()));
            return;
        }
        Vector<Enemy> enemiesInRange = new Vector<>();
        for (Enemy unit : enemies) {
            if (this.getPosition().range(unit.getPosition()) <= 2) {
                enemiesInRange.add(unit);
            }
        }
        if (enemiesInRange.isEmpty()) {
        UI.print(this.getName() + " tried to cast " + this.getAbilityName() + ", but there were no enemies in range");
        return;
        }

        UI.print("\n" + this.getName() + " casted " + this.getAbilityName() + " on all the enemies around him" + " for " + this.getAttackPoints() + " dmg! ");
        this.getEnergy().setCurrent(this.getEnergyCurrent() - this.getEnergyCost());
        int i = 0;
        for (Enemy unit : enemiesInRange) {
            i++;
            if (unit.isDead()) {
                continue;
            }
            UI.print("Cast number " + i + ":");
            int def = unit.rollingDefensePoints();
            int atk = this.getAttackPoints();
            int dmg = Math.max(0, atk - def);
            unit.setCurrentHealth(unit.getCurrentHealth() - dmg);
            UI.print(unit.getName() + " rolled " + def + " defense points.");
            UI.print(this.getName() + " dealt " + dmg + " damage to " + unit.getName() + "!\nreducing their health to |HP: " + unit.getCurrentHealth() + '/' + unit.getHealthCapacity() + "|\n");
        }
    }

    public abilityPoints getAbilityPoints() {
        return this.energy;
    }

    public void refillEnergy() {
        this.energy.increaseCurrentToCapacity();
    }

    protected void levelUp() {
        super.levelUp();
        refillEnergy();
        this.attackPoints += (3 * getLevel());
        UI.print(this.getName() + " reached level " + this.getLevel() + ": +" + (10 * this.getLevel()) + " Health, +" + (7 * this.getLevel()) + " Attack, +" + this.getLevel() + " Defense");
        if (!getExperience().isLevelUp()) {
            return;
        }
        levelUp();
    }

    private boolean canCastAbility() {
        return energy.getCurrent() >= getEnergyCost();
    }

    @Override
    public Exp getExperienceValue() {
        return this.getExperience();    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    public String getPointFormat() {
        return "|Energy: " + this.getEnergyCurrent() + "/" + this.getEnergyPool() + "|";
    }

    public String getPointFormat2() {
        return "|Energy: " + this.getEnergyCurrent() + "/" + this.getEnergyPool() + "|";
    }
}
