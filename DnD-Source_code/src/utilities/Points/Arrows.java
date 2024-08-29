package utilities.Points;

public class Arrows extends abilityPoints{

    // Constructor
    public Arrows(int capacity) {
        super(capacity);
        super.setCurrent(capacity);
    }

    // The rest of the methods
    @Override
    public void setCapacity(int capacity) {
        super.setCapacity(capacity);
        super.setCurrent(capacity);
    }

    @Override
    public void setCurrent(int current) {
        super.setCurrent(current);
        super.setCapacity(current);
    }

    @Override
    public int getCapacity() {
        return super.getCapacity();
    }

    @Override
    public int getCurrent() {
        return super.getCapacity();
    }
}
