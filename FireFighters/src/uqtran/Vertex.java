package uqtran;
import static uqtran.State.*;

/**
 * Created by uqton on 11.06.2017.
 */
public class Vertex {
    private Vertex[] fourNeighbors;
    private State state;
    boolean preBurn;
    final int x,y;
    int[] agentsVisiting;

    /*
     * @invariant x >= 0 && y >= 0 &&
     * fourNeighbors.length == 4
     */

    Vertex(int x, int y, State state) {
        this.x = x;
        this.y = y;
        this.state = state;
        preBurn = false;
        fourNeighbors = new Vertex[4];
    }

    public String getCoordinateText() {
        return "Z[" + x + "," + y + "]";
    }

    public void burn() {
        state = BURNING;
    }

    public void extinguish() {
        state = DAMAGED;
    }

    public Vertex[] getFourNeighbors() {
        return fourNeighbors;
    }

    public State getState() {
        return state;
    }
}
