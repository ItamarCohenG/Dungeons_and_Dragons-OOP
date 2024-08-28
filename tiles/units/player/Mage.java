package tiles.units.player;

import CommandLine.UI;
import tiles.units.enemies.Enemy;
import utilities.Points.Exp;
import utilities.Points.Mana;
import utilities.Points.abilityPoints;

import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Mage extends Player {
    private Mana mana;
    private int spellPower;
    private final int manaCost;
    private final int hitsCount;
    private final int abilityRange;
    private final String abilityName;

    public Mage(String name, int healthCapacity, int attackPoints, int defensePoints,
                int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange) {
        super(name, healthCapacity, attackPoints, defensePoints);
        this.mana = new Mana(manaPool);
        this.mana.setCurrent(manaPool / 4);
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abilityRange = abilityRange;
        this.abilityName = "Blizzard";
    }

    public int getManaPool() {
        return this.mana.getCapacity();
    }
    public int getManaCurrent() {
        return this.mana.getCurrent();
    }
    public Mana getMana() {
        return this.mana;
    }

    public void increaseManaPool(int manaPool) {
        this.mana.increaseCapacity(manaPool);
    }
    public void increaseMana(int mana) {
        this.mana.increaseCurrent(mana);
    }

    public void increaseSpellPower(int spellPower) {
        this.spellPower += spellPower;
    }



    @Override
    public void updatePoints(Player player) {
        this.getMana().setCurrent(Math.min(this.getManaPool(), this.getManaCurrent() + this.getLevel()));
    }

    public void castAbility(List<Enemy> enemies ) {
        Vector<Enemy> enemiesInRange = new Vector<>();
        for (Enemy unit : enemies) {
            if (unit.getPosition().range(this.getPosition()) <= abilityRange && !unit.isDead()) {
                enemiesInRange.add(unit);
            }
        } // enemiesInRange.size() == 0
        java.util.Random Random = new Random();
        if (canCastAbility()) { // this uses mana if able to cast
            if (enemiesInRange.isEmpty()) { // if there are no enemies in range, waste cast
                UI.print(this.getName() + " tried to cast ability, but there were no enemies in range.");
                return;
            }
            UI.print("\n" + this.getName() + " casted " + this.getAbilityName() + " for " + spellPower + " dmg! and will strike up to " + hitsCount + " enemies in range\n");
            this.mana.setCurrent(this.mana.getCurrent() - this.manaCost);
            for (int currHitCount = 0; currHitCount < this.hitsCount; currHitCount++) {
                if (enemiesInRange.isEmpty()) {
                    break;
                } // enemiesInRange.size() == 0
                UI.print("Cast number " + (currHitCount + 1) + ":");
                int randomIndex = Random.nextInt(enemiesInRange.size());
                Enemy curr = enemiesInRange.get(randomIndex);
                int def = curr.rollingDefensePoints();
                int dmg = this.spellPower - def;
                curr.setCurrentHealth(curr.getCurrentHealth() - (dmg));
                UI.print(curr.getName() + " rolled " + def + " defense points.");
                UI.print(this.getName() + " dealt " + dmg + " damage to " + curr.getName() + "!\nreducing their health to |HP: " + curr.getCurrentHealth() + '/' + curr.getHealthCapacity() + "|\n");
                enemiesInRange.remove(randomIndex);
            }
        } else {
                UI.print(this.getName() + " tried to cast ability, but didn't have enough mana.");
        }
    }
    public abilityPoints getAbilityPoints() {
        return mana;
    }

    public String getAbilityName() {
        return abilityName;
    }

    protected void levelUp() {
        super.levelUp();
        increaseManaPool(25 * getLevel()); // Reset cooldown
        increaseMana(Math.min((getManaCurrent() + (getManaPool() / 4)), getManaPool()));
        increaseSpellPower(10 * getLevel());
        UI.print("Mage " + this.getName() + " reached level " + this.getLevel() + ": +" +
          10 * this.getLevel() + " Health, +" + 4 * this.getLevel() + " Attack, +" +
          this.getLevel() + " Defense, +" + 10 * this.getLevel() + " Spell Power, +" +
          this.getLevel() * 25 + " Mana Pool");
        if (!getExperience().isLevelUp()) {
            return;
        }
        levelUp();
    }

    private boolean canCastAbility() {
        return mana.getCurrent() >= manaCost;
    }

    @Override
    public Exp getExperienceValue() {
        return this.getExperience();
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public String getPointFormat() {
        if (this.getManaCurrent() > getManaPool()) {
            mana.setCurrent(getManaPool());
        }
        return "|  Mana: " + this.getManaCurrent() + "/" + this.getManaPool() + " |";
    }

    public String getPointFormat2() {
        if (this.getManaCurrent() > getManaPool()) {
            mana.setCurrent(getManaPool());
        }
        return "|Mana: " + this.getManaCurrent() + "/" + this.getManaPool() + "|";
    }

}
