package uqtran;

import uqtran.utils.VertexUtil;

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

    public void sweep(boolean verbose) {
        if(terminated) {
            return;
        }
        if(!VertexUtil.isCritical(current) && current.getState() == BURNING) {
            current.extinguish();
            if(verbose) {
                String text = "agent" + priority + " extinguish " + current.getCoordinateText();
                System.out.println(text);
            }
        }
        Direction moveTo = null;
        boolean clockwise = priority % 2 == 0;

        if(previous == null) {
            previous = UP;
        }

        moveTo = previous;

        /*if(clockwise) {
            moveTo = previous.prev.prev;
        } else {
            moveTo = previous.next.next;
        }*/

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
                    current = vertexToCheck;
                    break;
                }
            }

            /*if(fourNeighbor[moveTo.getValue()] != null) {
                if(fourNeighbor[moveTo.getValue()] != null) {
                    if(fourNeighbor[moveTo.getValue()].getState() == BURNING) {
                        previous = moveTo;
                        current = current.getFourNeighbors()[ moveTo.getValue() ];
                        break;
                    }
                    if(fourNeighbor[moveTo.next.next.getValue()] != null) {
                        if(fourNeighbor[moveTo.next.next.getValue()].getState() == BURNING && i == 0) {
                            previous = moveTo.next.next;
                            current = current.getFourNeighbors()[moveTo.next.next.getValue()];
                            break;
                        }
                    }
                }
            }*/

        }

        if(shouldTerminate()) {
            terminated = true;
        }
    }
}
