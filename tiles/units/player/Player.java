package tiles.units.player;

import CommandLine.SpaceSetter;
import CommandLine.UI;
import game.CombatSystem;
import game.Position;
import tiles.units.Unit;
import tiles.units.enemies.Enemy;
import utilities.Points.Exp;
import utilities.Points.abilityPoints;

import java.util.List;

public abstract class Player extends Unit {
    private Exp experience;
    private int level;
    private Position position;

    // Constructor for Player
    public Player(String name, int healthCapacity, int attackPoints, int defensePoints) {
        super('@', name, healthCapacity, attackPoints, defensePoints);
        this.experience = new Exp(50);
        this.experience.setCapacity(50);
        this.experience.setCurrent(0);
        this.level = 1;
    }

    public abstract void updatePoints(Player player);

    @Override
    public void initialize(Position position) {
        this.position = position;
    }


    // Getters and Setters
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Exp getExperience() {
        return experience;
    }

    public void gainExperience(int experience) {
        this.getExperience().setCurrent(this.getExperience().getCurrent() + experience);
        checkLevelUp();
    }

    public int getLevel() {
        return level;
    }

    // Visitor pattern - Visited, Overridden.
    public void accept(Unit visitor) {
        visitor.visit(this);
    }

    // Visitor pattern - Visitor, all is Overridden
    public void visit(Enemy enemy) {
        CombatSystem.engageCombat(enemy, this);
    }

    public void visit(Player player) {
        // No action needed when visiting another player
    }

    // Abstract methods to be implemented by subclasses (e.g., Warrior, Mage)
    public abstract void castAbility(List<Enemy> enemies);
    public abstract abilityPoints getAbilityPoints();

    // Level up the player if enough experience is gained
    protected void checkLevelUp() {
        if (experience.isLevelUp()) {
            levelUp();
        }
    }

    // Increase player's level
    protected void levelUp() {
        experience.decreaseCurrent(experience.getCapacity());
        updateStatsAfterLvlUp();
        level++;
    }

    // Update the stats
    private void updateStatsAfterLvlUp() {
        experience.increaseCapacity(50 * level);
        health.increaseCapacity(10 * level);
        health.increaseCurrentToCapacity();
        attackPoints += 4 * level;
        defensePoints += level;
    }

    @Override
    public Exp getExperienceValue() {
        return this.getExperience();
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    public String spaceNeeded() {
        if (attackPoints < 10) {
            return " |";
        }
        return "|";
    }

    public String intro(){
        return (" " + this.getName() + SpaceSetter.evenSpacing(this.getName(), 15) + "|HP: " + this.getCurrentHealth()
                    + '/' + this.getHealthCapacity() + "|     " + "|Attack: " + this.getAttackPoints() + this.spaceNeeded() + SpaceSetter.evenSpacing(this.getAttackPoints() + this.spaceNeeded(), 8)
            + "|Defence: " + this.getDefensePoints() + "|     " + this.getPointFormat());
    }

    public String description(){
        return intro() + "     |Level: " + this.getLevel() + "|" + "     |Experience: " + this.getExperience().getCurrent()  + "/" + this.getExperience().getCapacity() + "|";
    }

    public String description3(){
        String s = "   ";
        return ("|" +
                this.getName()+ "|" + s + "|HP: " + this.getCurrentHealth() + '/' + this.getHealthCapacity() + "|" + s
                + "|Attack: " + this.getAttackPoints()+ '|' + s + "|Defence: " + this.getDefensePoints() + "|" + s +
                this.getPointFormat2() + s + "|Level: " + this.getLevel() + "|" + s + "|Experience: " +
                this.getExperience().getCurrent()  + "/" + this.getExperience().getCapacity() + "|");
    }

    public StringBuilder description2(){
        String s = "  ";
        String txt2 = ("|" +
                this.getName()+ "|" + s + "   |Level: " + this.getLevel() + "  |" + s + "  |Experience: " +
                this.getExperience().getCurrent()  + "/" + this.getExperience().getCapacity() + "|" );
        String txt = "|HP: " + this.getCurrentHealth() + '/' + this.getHealthCapacity() + "|" + s + "|Attack: " + this.getAttackPoints()+ '|' + s + "|Defence: " + this.getDefensePoints() + "|" + s + this.getPointFormat2();
        int x = txt.length(); //╚║╝╔╟╗═
        StringBuilder res = new StringBuilder();
        res.append(  "╔═").append("═".repeat(x)).append("═╗");
        res.append("\n║ ").append(txt2).append( " ".repeat(txt.length() - txt2.length()) +  " ║");
        res.append("\n║ ").append(txt).append(          " ║\n");
        res.append(  "╚═").append("═".repeat(x)).append("═╝");
        return res;

    }


    public abstract String getPointFormat();

    public abstract String getPointFormat2();

    public abstract String getAbilityName();
}
