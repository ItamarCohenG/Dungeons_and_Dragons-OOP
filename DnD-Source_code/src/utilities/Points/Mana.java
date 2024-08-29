package utilities.Points;

public class Mana extends abilityPoints {

    // Constructor
    public Mana(int capacity) {
    super(capacity);
    this.setCurrent(this.getCapacity() / 4);
    }
}
