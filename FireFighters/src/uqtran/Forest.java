package uqtran;
import java.util.ArrayList;
import static uqtran.Direction.*;
import static uqtran.State.*;

/**
 * Created by uqton on 11.06.2017.
 */

public class Forest {
    Vertex[][] forestGrid;
    ArrayList<Vertex> burningArea;
    final int fireSpreadRate;
    int fireSpreadCounter;

    Forest( int sizeX, int sizeY, int fireSpreadRate ) {
        forestGrid = new Vertex[sizeX][sizeY];
        this.fireSpreadRate = fireSpreadRate;
    }

    public void sweep() {
        if( fireSpreadCounter >= fireSpreadRate ){
            spreadFire();
            fireSpreadCounter = 0;
        } else {
            fireSpreadCounter++;
        }
    }

    public boolean shouldBurn(int x, int y, int dir) {
        State vertexState = forestGrid[x][y].getFourNeighbors()[dir].getState();
        return vertexState != BURNING
                && vertexState != DAMAGED;
    }

    public void burnVertex(int x, int y, int dir) {
        forestGrid[x][y].getFourNeighbors()[dir].burn();
        burningArea.add(forestGrid[x][y].getFourNeighbors()[dir]);
        forestGrid[x][y].getFourNeighbors()[dir].preBurn = true;
    }

    public void spreadFire() {
        for(int i = 0; i < forestGrid.length; i++) {
            for(int j = 0; j < forestGrid[i].length; j++) {
                if(forestGrid[i][j].getState() == BURNING
                        && !forestGrid[i][j].preBurn) {
                    if(i > 0 && shouldBurn(i,j,LEFT.getValue()))
                        burnVertex(i,j,LEFT.getValue());
                    if(i < forestGrid.length-1 && shouldBurn(i,j,RIGHT.getValue()))
                        burnVertex(i,j,RIGHT.getValue());
                    if(j > 0 && shouldBurn(i,j,UP.getValue()))
                        burnVertex(i,j,UP.getValue());
                    if(j < forestGrid[i].length-1
                            && shouldBurn(i,j,DOWN.getValue()))
                        burnVertex(i,j,DOWN.getValue());
                }
            }
        }
        for(int i = 0; i < forestGrid.length; i++) {
            for(int j = 0; j < forestGrid[i].length; j++) {
                forestGrid[i][j].preBurn = false;
            }
        }
    }
}
