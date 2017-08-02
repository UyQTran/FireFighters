package uqtran;

import uqtran.utils.VertexUtil;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import static uqtran.State.*;
import static uqtran.Direction.*;

/**
 * Created by uqton on 11.06.2017.
 */
public class Agent {
    Vertex current;
    int priority;
    Direction previous;
    boolean terminated;

    /*
     * @invariant priority >= 0
     */

    Agent(int priority, Vertex startVertex) {
        this.priority = priority;
        this.current = startVertex;
        terminated = false;
    }

    public void move(int direction) {
        Vertex nextVertex = current.getFourNeighbors()[direction];
        if(nextVertex == null) {
            return;
        }
        current = nextVertex;
    }

    public boolean shouldTerminate() {
        Vertex[] fourNeighbor = current.getFourNeighbors();
        for(int i = 0; i < fourNeighbor.length; i++) {
            if(fourNeighbor[i] != null) {
                if(fourNeighbor[i].getState() == BURNING) {
                    return false;
                }
            }
        }
        return current.getState() != BURNING;
    }

    int calculateDirection( int priority, int[] paths ) {
        int pathsCount = paths.length;
        int goToDirection = paths[priority % pathsCount];
        return goToDirection;
    }

    int[] getBurningList( Vertex[] fourNeighbors ) {
        List<Integer> burningFourNeighbors = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            //assumed states are static declared
            if(fourNeighbors[i].getState() == BURNING) {
                burningFourNeighbors.add(i);
            }
        }
        int[] arrayFormat = new int[burningFourNeighbors.size()];
        for(int i = 0; i < arrayFormat.length; i++) {
            arrayFormat[i] = burningFourNeighbors.get(i);
        }
        return arrayFormat;
    }

    void handleJunction() {
        int[] burningFourNeighbors = getBurningList(current.getFourNeighbors();
        int goToDirection = calculateDirection(priority, burningFourNeighbors);
        current = current.getFourNeighbors()[goToDirection];
    }


    public void sweep(boolean verbose) {
        if(terminated) {
            return;
        }
        if(!VertexUtil.isCritical(current) && current.getState() == BURNING) {
            current.extinguish();
            if(verbose) {
                String text = "\tagent" + priority + " extinguish(" + current.getCoordinateText() + ")";
                System.out.println(text);
            }
        }
        Direction moveTo = null;
        boolean clockwise = priority % 2 == 0;

        if(previous == null) {
            previous = UP;
        }

        moveTo = previous;

        Vertex[] fourNeighbor = current.getFourNeighbors();
        boolean foundEdge = false;
        for(int i = 0; i < 8; i++) {
            if(clockwise) {
                moveTo = moveTo.next;
            } else {
                moveTo = moveTo.prev;
            }

            Vertex vertexToCheck = fourNeighbor[moveTo.getValue()];
            if(vertexToCheck != null) {
                if(vertexToCheck.getState() != BURNING) {
                    foundEdge = true;
                }
                if(vertexToCheck.getState() == BURNING && foundEdge) {
                    previous = moveTo;
                    String text = "\tagent" + priority + " move(" + current.getCoordinateText() + "," + vertexToCheck.getCoordinateText() + ")";
                    System.out.println(text);
                    current = vertexToCheck;
                    break;
                }
            }
        }



        if(shouldTerminate()) {
            System.out.println("\tagent" + priority + " terminated");
            terminated = true;
        }
    }
}
