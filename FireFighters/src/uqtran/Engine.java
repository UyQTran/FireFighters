package uqtran;

/**
 * Created by uqton on 04.07.2017.
 */
public class Engine {
    Forest mainForest;
    Agent[] mainAgents;

    Engine() {
        init();
        sweep(true);
    }

    public void init() {
        new DirectionHandler().init();
    }

    public void sweep(boolean verbose) {
        ForestReader fr = new ForestReader();
        try {
            fr.read("test_data/testForest1.txt");
            if(verbose) {
                System.out.println("Reading file...");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        mainForest = fr.getInForest();
        mainAgents = fr.getAgentArray();
        if(verbose) {
            System.out.println("Executing sweep...");
        }

        terminated: while(true) {
            for(int i = 0; i < mainAgents.length; i++) {
                if(!mainAgents[i].terminated) {
                    break;
                }
                if(i == mainAgents.length-1) {
                    break terminated;
                }
            }

            mainForest.sweep(verbose);
            for(int i = 0; i < mainAgents.length; i++) {
                mainAgents[i].sweep(verbose);
            }
        }
    }
}
