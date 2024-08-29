package utilities.Randomizer;

public class FixedGenerator implements Generator {

    @Override
    public int generate(int max) {
        return max / 2;
    }

    public int generate(int min, int max) {
        return max / 2;
    }

}
