package uqtran;

import java.io.File;
import java.util.Scanner;

/**
 * Created by uqton on 11.06.2017.
 */
public class ForestReader {
    private Forest inForest;
    private Agent[] agentArray;

    public void read() throws Exception {
        Scanner scanner = new Scanner(new File("test_data/testForest1.txt"));

        agentArray = new Agent[scanner.nextInt()];
        int sizeX = scanner.nextInt();
        int sizey = scanner.nextInt();
        int fireSpreadRate = scanner.nextInt();

        inForest = new Forest(sizeX, sizey, fireSpreadRate);
    }

    public Agent[] getAgentArray() {
        return agentArray;
    }

    public Forest getInForest() {
        return inForest;
    }
}
