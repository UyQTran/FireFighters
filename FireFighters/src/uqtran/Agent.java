package uqtran;

import uqtran.utils.VertexUtil;

import static uqtran.State.*;

/**
 * Created by uqton on 11.06.2017.
 */
public class Agent {
    Vertex current;
    int priority;
    Direction previous;
    boolean terminated;

    Agent( int priority, Vertex startVertex ) {
        this.priority = priority;
        this.current = startVertex;
    }

    public void move( int direction ) {
        Vertex nextVertex = current.getFourNeighbors()[ direction ];
        if( nextVertex == null ) {
            return;
        }
        current = nextVertex;
    }

    public void sweep(boolean verbose) {
        if(!VertexUtil.isCritical( current )) {
            current.extinguish();
        }
        Direction moveTo = null;
        boolean clockwise = priority % 2 == 0;

        if( clockwise ) {
            moveTo = previous.prev.prev;
        } else {
            moveTo = previous.next.next;
        }

        for(int i = 0; i < 4; i++) {
            if( clockwise ) {
                moveTo = moveTo.next;
            } else {
                moveTo = moveTo.prev;
            }

            if( current.getFourNeighbors()[ moveTo.getValue() ] != null ) {
                if( current.getFourNeighbors()[ moveTo.getValue() ].getState() == BURNING ) {
                    previous = moveTo;
                    current = current.getFourNeighbors()[ moveTo.getValue() ];
                    break;
                } else if( current.getFourNeighbors()[ moveTo.next.next.getValue() ].getState() == BURNING && i == 0 ) {
                    previous = moveTo.next.next;
                    current = current.getFourNeighbors()[ moveTo.next.next.getValue() ];
                    break;
                }
            }
        }
    }
}
