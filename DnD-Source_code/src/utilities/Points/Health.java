package utilities.Points;

public class Health extends abilityPoints {


    // Constructor
    public Health(int capacity) {
    super(capacity);
    }

    // The rest of the methods
    public void takeDamage(int damageTaken) {
        super.reduceCurrent(damageTaken);
    }

    public Boolean isDead() {
        return super.isCurrentZero();
    }

}
