import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("ERROR: Test scenario file name not found.");
        }
        else {
            Simulator monitorSim = new Simulator();

            //monitorSim.uploadStartingFile("scenario2.csv");
            monitorSim.uploadStartingFile(args[0]);

            while (!monitorSim.isSimulationCompleted()) {
                monitorSim.exploreSpace();
            }

            monitorSim.finaReport();
        }
    }
}
