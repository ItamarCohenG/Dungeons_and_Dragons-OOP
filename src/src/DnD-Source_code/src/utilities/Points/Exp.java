package utilities.Points;

import CommandLine.UI;

public class Exp extends abilityPoints {

    // Constructor
    public Exp(int capacity) {
        super(capacity);
        this.setCurrent(0);
    }

    @Override
    public void increaseCurrent(int num) {
        this.setCurrent(this.getCurrent() + num);
    }

    public void decreaseCurrent(int num) {
        this.setCurrent(this.getCurrent() - num);
    }


    // The rest of the methods
    public boolean isLevelUp() {
        return this.getCurrent() >=  this.getCapacity();
    }
}
