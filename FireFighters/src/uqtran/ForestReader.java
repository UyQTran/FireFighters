package uqtran;

import java.io.File;
import java.util.Scanner;

/**
 * Created by uqton on 11.06.2017.
 */
public class ForestReader {
    private Forest inForest;
    private Agent[] agentArray;

    /*public void read(String pathName) throws Exception {
        Scanner scanner = new Scanner(new File(pathName));

        agentArray = new Agent[scanner.nextInt()];
        int agentStartX = scanner.nextInt();
        int agentStartY = scanner.nextInt();
        int sizeX = scanner.nextInt();
        int sizeY = scanner.nextInt();
        int fireSpreadRate = scanner.nextInt();

        inForest = new Forest(sizeX, sizeY, fireSpreadRate);
        for(int i = 0; i < sizeY; i++) {
            for(int j = 0; j < sizeX; j++) {
                int stateNumber = scanner.nextInt();
                if(stateNumber == 1) {
                    inForest.burn(j, i);
                }
                System.out.println(stateNumber);
            }
        }

        Vertex startVertex = inForest.getVertex(agentStartX, agentStartY);
        for(int i = 0; i < agentArray.length; i++) {
            agentArray[i] = new Agent(i, startVertex);
        }
    }*/

    public void read(String pathName) {

        agentArray = new Agent[1];
        int agentStartX = 1;
        int agentStartY = 1;
        int sizeX = 5;
        int sizeY = 5;
        int fireSpreadRate = 10;

        inForest = new Forest(sizeX, sizeY, fireSpreadRate);
        int[][] testForest = {{0,0,0,0,0}, {0,1,1,1,0}, {0,1,1,0,0}, {0,1,1,0,0}, {0,0,0,0,0}};
        for(int i = 0; i < sizeY; i++) {
            for(int j = 0; j < sizeX; j++) {
                int stateNumber = testForest[j][i];
                if(stateNumber == 1) {
                    inForest.burn(j, i);
                }
            }
        }

        Vertex startVertex = inForest.getVertex(agentStartX, agentStartY);
        for(int i = 0; i < agentArray.length; i++) {
            agentArray[i] = new Agent(i, startVertex);
        }
    }

    public Agent[] getAgentArray() {
        return agentArray;
    }

    public Forest getInForest() {
        return inForest;
    }
}
