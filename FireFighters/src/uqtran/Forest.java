package uqtran;
import java.util.ArrayList;
import static uqtran.Direction.*;
import static uqtran.State.*;

/**
 * Created by uqton on 11.06.2017.
 */

public class Forest {
    private Vertex[][] forestGrid;
    private ArrayList<Vertex> burningArea;
    private final int fireSpreadRate;
    private int fireSpreadCounter;

    Forest( int sizeX, int sizeY, int fireSpreadRate ) {
        forestGrid = new Vertex[sizeX][sizeY];
        this.fireSpreadRate = fireSpreadRate;
        burningArea = new ArrayList<>();
        init();
    }

    private void init() {
        for(int i = 0; i < forestGrid.length; i++) {
            for(int j = 0; j < forestGrid[i].length; j++) {
                forestGrid[i][j] = new Vertex(i, j, STANDING);

            }
        }
        for(int i = 0; i < forestGrid.length; i++) {
            for(int j = 0; j < forestGrid[i].length; j++) {
                if(i > 0) {
                    forestGrid[i][j].getFourNeighbors()[LEFT.getValue()] = forestGrid[i-1][j];
                }
                if(i < forestGrid.length-1) {
                    forestGrid[i][j].getFourNeighbors()[RIGHT.getValue()] = forestGrid[i+1][j];
                }
                if(j > 0) {
                    forestGrid[i][j].getFourNeighbors()[UP.getValue()] = forestGrid[i][j-1];
                }
                if(j < forestGrid[i].length-1) {
                    forestGrid[i][j].getFourNeighbors()[DOWN.getValue()] = forestGrid[i][j+1];
                }
            }
        }
    }

    public Vertex getVertex(int x, int y) {
        return forestGrid[x][y];
    }

    public void burn(int x, int y) {
        forestGrid[x][y].burn();
        burningArea.add(forestGrid[x][y]);
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
