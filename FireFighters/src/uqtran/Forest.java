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
    boolean terminated;

    /*
     * @invariant fireSpreadCounter > 0 &&
     * forestGrid != null &&
     * fireSpreadCounter >= 0 &&
     * fireSpreadCounter < fireSpreadRate
     */

    Forest( int sizeX, int sizeY, int fireSpreadRate ) {
        forestGrid = new Vertex[sizeX][sizeY];
        this.fireSpreadRate = fireSpreadRate;
        burningArea = new ArrayList<>();
        terminated = false;
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
                    forestGrid[i][j].getFourNeighbors()[UP.getValue()] = forestGrid[i-1][j];
                }
                if(i < forestGrid.length-1) {
                    forestGrid[i][j].getFourNeighbors()[DOWN.getValue()] = forestGrid[i+1][j];
                }
                if(j > 0) {
                    forestGrid[i][j].getFourNeighbors()[LEFT.getValue()] = forestGrid[i][j-1];
                }
                if(j < forestGrid[i].length-1) {
                    forestGrid[i][j].getFourNeighbors()[RIGHT.getValue()] = forestGrid[i][j+1];
                }
            }
        }
    }

    public void traverse() {
        for(int i = 0; i < forestGrid.length; i++) {
            Vertex cursor = forestGrid[i][0];
            for(int j = 0; j < forestGrid[i].length; j++) {
                System.out.println(cursor.getCoordinateText());
                cursor = cursor.getFourNeighbors()[RIGHT.getValue()];
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

    public void sweep(boolean verbose) {
        if( fireSpreadCounter >= fireSpreadRate ){
            spreadFire();
            fireSpreadCounter = 0;
            if(verbose) {
                //System.out.println("\tallBurning spreadFire 4-neighbor");
            }
        } else {
            fireSpreadCounter++;
        }
    }

    public boolean shouldTerminate() {
        boolean isTerminating = burningArea.size() == 0;
        if(isTerminating) {
            terminated = true;
        }
        return terminated;
    }

    public boolean shouldBurn(int x, int y, int dir) {
        Vertex vertex = forestGrid[y][x].getFourNeighbors()[dir];
        if(vertex == null) {
            return false;
        }
        State vertexState = vertex.getState();
        return vertexState == STANDING;
    }

    public void burnVertex(int x, int y, int dir) {
        Vertex vertex = forestGrid[x][y].getFourNeighbors()[dir];
        if(vertex == null) {
            return;
        }
        vertex.burn();
        burningArea.add(vertex);
        vertex.preBurn = true;
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
