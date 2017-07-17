package uqtran;

/**
 * Created by uqton on 15.06.2017.
 */
public enum Direction {
    UP(0), RIGHT(1), DOWN(2), LEFT(3);

    private int getValue;
    Direction next, prev;
    Direction(int value) {
        this.getValue = value;
    }

    public int getValue() {
        return getValue;
    }
}