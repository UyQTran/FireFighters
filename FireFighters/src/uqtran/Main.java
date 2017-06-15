package uqtran;

/**
 * Created by uqton on 11.06.2017.
 */
public class Main {
    Forest mainForest;
    Agent[] mainAgents;

    Main() {
        init();
    }

    public static void main(String[] args) {
        new Main();
    }

    public void init() {
        new DirectionHandler().init();
    }

    public void sweep() {
        ForestReader fr = new ForestReader();
        try {
            fr.read("test_data/testForest1.txt");
        } catch (Exception e) {
            System.out.println("Path does not exist");
            System.exit(0);
        }
        mainForest = fr.getInForest();
        mainAgents = fr.getAgentArray();
    }
}
