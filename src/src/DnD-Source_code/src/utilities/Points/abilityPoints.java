package utilities.Points;

public abstract class abilityPoints {
    private int capacity;
    private int current;

    // Constructor
    public abilityPoints(int capacity) {
        this.capacity = capacity;
        this.current = capacity;
    }

    // Getters & Setters
    public int getCurrent() {
        return this.current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // The rest of the methods
    public void reduceCurrent(int num) {
        num = Math.max(0, num);
        current -= num;
    }

    public void increaseCurrent(int num) {
        num = Math.max(0, num);
        current += num;
    }

    public void increaseCurrentToCapacity() {
        current = capacity;
    }

    public void increaseCapacity(int p) {
        capacity += p;
    }

    public Boolean isCurrentZero() {
        return current <= 0;
    }
}
