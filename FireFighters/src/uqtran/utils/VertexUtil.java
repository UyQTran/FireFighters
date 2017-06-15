package uqtran.utils;
import uqtran.Vertex;

import static uqtran.Direction.*;
import static uqtran.State.*;
/**
 * Created by uqton on 15.06.2017.
 */
public class VertexUtil {
    public static Vertex[][] generateEightNeighbor(Vertex incomingVertex) {
        Vertex[][] eightNeighbors = new Vertex[3][3];
        Vertex[] fourNeighbors = incomingVertex.getFourNeighbors();

        eightNeighbors[0][1] = fourNeighbors[UP.getValue()];
        if( fourNeighbors[UP.getValue()] != null ) {
            eightNeighbors[0][0] = fourNeighbors[UP.getValue()].getFourNeighbors()[LEFT.getValue()];
            eightNeighbors[0][2] = fourNeighbors[UP.getValue()].getFourNeighbors()[RIGHT.getValue()];
        }
        eightNeighbors[1][0] = fourNeighbors[LEFT.getValue()];
        eightNeighbors[1][1] = incomingVertex;
        eightNeighbors[1][2] = fourNeighbors[RIGHT.getValue()];
        eightNeighbors[2][1] = fourNeighbors[DOWN.getValue()];
        if( fourNeighbors[DOWN.getValue()] != null) {
            eightNeighbors[2][0] = fourNeighbors[DOWN.getValue()].getFourNeighbors()[LEFT.getValue()];
            eightNeighbors[2][2] = fourNeighbors[DOWN.getValue()].getFourNeighbors()[RIGHT.getValue()];
        }

        return eightNeighbors;
    }

    public static int countFourneighborBurningStates(Vertex incomingVertex) {
        int fourNeighborBurnCount = 0;

        for(int i = 0; i < 4; i++) {
            if(incomingVertex.getFourNeighbors()[i].getState() == BURNING) {
                fourNeighborBurnCount++;
            }
        }
        return fourNeighborBurnCount;
    }

    public static int countFourneighborSquares(Vertex incomingVertex) {
        Vertex[][] eightNeighbors = generateEightNeighbor(incomingVertex);
        int squareCount = 0;

        for(int i = 0; i < 4; i++) {
            int x = i % 2 == 0 ? 0 : 1;
            int y = i > 1 ? 0 : 1;
            boolean isSquare = true;
            done: for(int j = 0; j < 2; j++) {
                for(int k = 0; k < 2; k++) {
                    if(eightNeighbors[j+y][k+x] == null) {
                        isSquare = false;
                        break done;
                    } else if(eightNeighbors[j+y][k+x].getState() != BURNING) {
                        isSquare = false;
                        break done;
                    }
                }
            }
            if(isSquare) {
                squareCount++;
            }
        }
        return squareCount;
    }

    public static boolean isCritical(Vertex incomingVertex) {
        if(incomingVertex == null) {
            return false;
        }

        int squareCount = countFourneighborSquares(incomingVertex);
        int fourNeighborBurnCount = countFourneighborBurningStates(incomingVertex);
        return !(squareCount >= fourNeighborBurnCount - 1 || squareCount == 4 || fourNeighborBurnCount == 0);
    }
}
