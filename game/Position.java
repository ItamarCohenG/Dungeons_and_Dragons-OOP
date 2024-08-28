package game;

public class Position {
    private int x;
    private int y;

    // Constructor to initialize the position with x and y coordinates
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters for x and y
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Method to calculate the Euclidean distance between two positions
    public double range(Position compare) {
        return Math.sqrt(Math.pow(this.x - compare.getX(), 2) + Math.pow(this.y - compare.getY(), 2));
    }

    // Method to translate the position by a given delta in x and y directions
    public Position translate(int dx, int dy) {
        return new Position(this.x + dx, this.y + dy);
    }

    // String representation of the position
    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    // Method to compare two positions for equality
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }

    // Method to generate hash code for the position
    @Override
    public int hashCode() {
        int result = Integer.hashCode(x);
        result = 31 * result + Integer.hashCode(y);
        return result;
    }
}
