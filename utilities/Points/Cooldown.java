package utilities.Points;

public class Cooldown extends abilityPoints {

    // Constructor
    public Cooldown(int capacity) {
        super(capacity);
    }

    // The rest of the methods
    public boolean isReady() {
        return this.getCurrent() >= this.getCapacity() ;
    }

    public void reset() {
        this.setCurrent(-1);
    }

    public int getRemainingCooldown() {
        return (this.getCapacity() - this.getCurrent() );
    }
}
