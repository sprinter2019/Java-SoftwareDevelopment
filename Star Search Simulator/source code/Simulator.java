import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;
import java.io.*;

public class Simulator {

    private String testFileName;
    private Integer regionWidth;
    private Integer regionHeight;
    private Integer numberOfDrones;
    private Integer droneX, droneY;
    private Directions droneDirection;
    private Integer droneStrategy;
    private Integer turnLimit;
    private Integer turnsCompleted=0;
    private Integer numOfSuns;
    private DroneActions action=DroneActions.scan;
    private Integer numOfExploredSquares=0;
    private static Random randGenerator;


    private Space regionInfo;

    private HashMap<Integer, Drone> droneFleet= new HashMap<>(); // Keeps track of all teh drones

    private HashMap<Square, Contents> spaceKnowledge = new HashMap<>(); //Keeps track of the knowledge acquired by the drones

    //Extract simulation condition from the scenario file
    public void uploadStartingFile(String fileName) {

        this.testFileName=fileName;
        final String DELIMITER = ",";

        try {
            Scanner takeCommand = new Scanner(new File(testFileName));
            String[] tokens;
            int i, j, k;

            // read in the region information
            tokens = takeCommand.nextLine().split(DELIMITER);
            regionWidth = Integer.parseInt(tokens[0]);
            tokens = takeCommand.nextLine().split(DELIMITER);
            regionHeight = Integer.parseInt(tokens[0]);

            //Initialize the space region
            regionInfo = new Space (regionWidth, regionHeight, Contents.stars);


            // read in the drone starting information
            tokens = takeCommand.nextLine().split(DELIMITER);
            this.numberOfDrones = Integer.parseInt(tokens[0]);

            for (k = 0; k < numberOfDrones; k++) {
                tokens = takeCommand.nextLine().split(DELIMITER);

                droneX = Integer.parseInt(tokens[0]);
                droneY = Integer.parseInt(tokens[1]);
                droneDirection = Directions.valueOf(tokens[2]) ;
                droneStrategy = Integer.parseInt(tokens[3]);
                Drone d = new Drone(k, new Square(droneX, droneY));
                d.setDroneDirection(droneDirection);
                d.setStrategy(droneStrategy);


                droneFleet.put(k, d);


                // explore the stars at the initial location
                regionInfo.setSquareContent(d.getLocation(), Contents.drone);
                spaceKnowledge.put(d.getLocation(), Contents.drone);
            }

            //add initial drone knowledge to the drones
            for (k = 0; k < numberOfDrones; k++) {
                droneFleet.get(k).setSpaceKnowledge(spaceKnowledge);
            }

            // read in the sun information and update the space region
            tokens = takeCommand.nextLine().split(DELIMITER);
            this.numOfSuns = Integer.parseInt(tokens[0]);
            for (k = 0; k < numOfSuns; k++) {
                tokens = takeCommand.nextLine().split(DELIMITER);

                Square sun = new Square(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
                regionInfo.setSquareContent(sun, Contents.sun);

            }

            tokens = takeCommand.nextLine().split(DELIMITER);
            turnLimit = Integer.parseInt(tokens[0]);

            takeCommand.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
        }
    }

    public void exploreSpace(){
        //Drones start exploring the space region using strategy 1 or 0
        //System.out.println("Turn Number: " + turnsCompleted); // Diagnostic purpose

        for (int i=0; i< numberOfDrones;i++) {
            if (!isSimulationCompleted()) {
                if (droneFleet.get(i).getStrategy() == 0) {
                    generateRandomDroneActions(i);
                } else if (droneFleet.get(i).getStrategy() == 1) {
                    requestDroneActions(i);
                }
            }
        }
        turnsCompleted+=1;
    }


    // Generate a random drone action (Strategy 0)
    public void generateRandomDroneActions(Integer i) {
        int moveRandomChoice, thrustRandomChoice, steerRandomChoice;
        randGenerator = new Random();
        if (droneFleet.get(i).getStatus()) {
            moveRandomChoice = randGenerator.nextInt(100);
            if (moveRandomChoice < 5) {
                // do nothing
                droneFleet.get(i).setDroneAction(DroneActions.pass);
                printDroneAction("d" + String.valueOf(i) + "," + "pass"+"\n"+"ok");
            } else if (moveRandomChoice < 20) {
                // check your surroundings
                droneFleet.get(i).setDroneAction(DroneActions.scan);
                String results = generateScanResults(droneFleet.get(i).getLocation());
                droneFleet.get(i).setSpaceKnowledge(spaceKnowledge);

                printDroneAction("d" + String.valueOf(i) + "," + "scan");
                printDroneAction(results);

                //add additional drone knowledge to the drones
                for (int k = 0; k < numberOfDrones; k++) {
                    droneFleet.get(k).setSpaceKnowledge(spaceKnowledge);
                }

            } else if (moveRandomChoice < 50) {
                // change direction
                droneFleet.get(i).setDroneAction(DroneActions.steer);

                // determine a new direction
                ArrayList<Directions> dronedDirList = new ArrayList<>();
                dronedDirList.add(Directions.north);
                dronedDirList.add(Directions.northeast);
                dronedDirList.add(Directions.east);
                dronedDirList.add(Directions.southeast);
                dronedDirList.add(Directions.south);
                dronedDirList.add(Directions.southwest);
                dronedDirList.add(Directions.west);
                dronedDirList.add(Directions.northwest);
                //dronedDirList.add(Directions.unknown); //unknown is for diagnostic purpose

                steerRandomChoice = randGenerator.nextInt(8);

                if(dronedDirList.get(steerRandomChoice)==null){
                    printDroneAction("d" + String.valueOf(i) + "," + "steer" + "," + droneFleet.get(i).getDroneDirection() + "\n" + "action_not_recognized");
                }
                else {
                    droneFleet.get(i).setDroneDirection(dronedDirList.get(steerRandomChoice));
                    printDroneAction("d" + String.valueOf(i) + "," + "steer" + "," + droneFleet.get(i).getDroneDirection() + "\n" + "ok");
                }


            } else {
                // thrust forward
                droneFleet.get(i).setDroneAction(DroneActions.thrust);
                thrustRandomChoice = randGenerator.nextInt(3);
                droneFleet.get(i).setThrustDistance(thrustRandomChoice+1);

                if((droneFleet.get(i).getThrustDistance()>3)||(droneFleet.get(i).getThrustDistance()<=0)){
                    printDroneAction("d" + String.valueOf(i) + "," + "thrust" + "," +droneFleet.get(i).getThrustDistance()+"\n"+"action_not_recognized");
                }
                else {
                    String results = validateThrustAction(i, droneFleet.get(i).getLocation(), droneFleet.get(i).getThrustDistance(), droneFleet.get(i).getDroneDirection());
                    printDroneAction("d" + String.valueOf(i) + "," + "thrust" + "," + results);

                    //add additional drone knowledge to the drones
                    for (int k = 0; k < numberOfDrones; k++) {
                        droneFleet.get(k).setSpaceKnowledge(spaceKnowledge);
                    }
                }
            }
        }
    }


    // Request a random drone action (Strategy 1)
    public void requestDroneActions(Integer i){
        if(this.turnsCompleted>0) {
            droneFleet.get(i).actionGenerator();
            action = droneFleet.get(i).getDroneAction();
        }else{
            action = DroneActions.scan;
        }

        if (action == DroneActions.pass) {
            printDroneAction("d" + String.valueOf(i) + "," + "pass"+"\n"+"ok");

        } else if (action == DroneActions.steer) {
            //Directions dir = droneFleet.get(i).getDroneDirection();
            if(droneFleet.get(i).getDroneDirection()==null) {
                printDroneAction("d" + String.valueOf(i) + "," + "steer" + "," + droneFleet.get(i).getDroneDirection() + "\n" + "action_not_recognized");
            }
            else {
                printDroneAction("d" + String.valueOf(i) + "," + "steer" + "," + droneFleet.get(i).getDroneDirection() + "\n" + "ok");
            }

        } else if (action == DroneActions.scan) {
            String results = generateScanResults(droneFleet.get(i).getLocation());
            droneFleet.get(i).setSpaceKnowledge(spaceKnowledge);

            printDroneAction("d" + String.valueOf(i) + "," + "scan");
            printDroneAction(results);

            //add additional drone knowledge to the other drones
            for (int k = 0; k < numberOfDrones; k++) {
                droneFleet.get(k).setSpaceKnowledge(spaceKnowledge);
            }

        } else if (action == DroneActions.thrust) {
            String results = validateThrustAction(i, droneFleet.get(i).getLocation(), droneFleet.get(i).getThrustDistance(), droneFleet.get(i).getDroneDirection());

            if((droneFleet.get(i).getThrustDistance()<1)||(droneFleet.get(i).getThrustDistance()>3)) {
                printDroneAction("d" + String.valueOf(i) + "," + "thrust" + "," +droneFleet.get(i).getThrustDistance()+"\n"+"action_not_recognized");
            }
            else {
                printDroneAction("d" + String.valueOf(i) + "," + "thrust" + "," + results);

                //add additional drone knowledge to the drones
                for (int k = 0; k < numberOfDrones; k++) {
                    droneFleet.get(k).setSpaceKnowledge(spaceKnowledge);
                }
            }
        }
        else {
            System.out.println("d" + String.valueOf(i)+","+droneFleet.get(i).getDroneAction()+"\n"+"action_not_recognized");
        }
       // turnsCompleted+=1;
    }

    //Determine Scan results and update the drone knowledge
    public String generateScanResults(Square S){
        String str="";

        Point p1 = new Point (S.x, S.y);
        Point north = Directions.north.getDirectionPoint(Directions.north);
        Point northeast = Directions.northeast.getDirectionPoint(Directions.northeast);
        Point east = Directions.east.getDirectionPoint(Directions.east);
        Point southeast = Directions.southeast.getDirectionPoint(Directions.southeast);
        Point south = Directions.south.getDirectionPoint(Directions.south);
        Point southwest = Directions.southwest.getDirectionPoint(Directions.southwest);
        Point west = Directions.west.getDirectionPoint(Directions.west);
        Point northwest = Directions.northwest.getDirectionPoint(Directions.northwest);

        Square northSquare = new Square(p1.x+north.x, p1.y+north.y);
        str = str + regionInfo.getSquareContent(northSquare);
        spaceKnowledge.put(northSquare, regionInfo.getSquareContent(northSquare));

        Square northeastSquare = new Square(p1.x+northeast.x, p1.y+northeast.y);
        str = str + "," + regionInfo.getSquareContent(northeastSquare);
        spaceKnowledge.put(northeastSquare, regionInfo.getSquareContent(northeastSquare));

        Square eastSquare = new Square(p1.x+east.x, p1.y+east.y);
        str = str + "," + regionInfo.getSquareContent(eastSquare);
        spaceKnowledge.put(eastSquare, regionInfo.getSquareContent(eastSquare));

        Square southeastSquare = new Square(p1.x+southeast.x, p1.y+southeast.y);
        str = str + "," + regionInfo.getSquareContent(southeastSquare);
        spaceKnowledge.put(southeastSquare, regionInfo.getSquareContent(southeastSquare));

        Square southSquare = new Square(p1.x+south.x, p1.y+south.y);
        str = str + "," + regionInfo.getSquareContent(southSquare);
        spaceKnowledge.put(southSquare, regionInfo.getSquareContent(southSquare));

        Square southwestSquare = new Square(p1.x+southwest.x, p1.y+southwest.y);
        str = str + "," + regionInfo.getSquareContent(southwestSquare);
        spaceKnowledge.put(southwestSquare, regionInfo.getSquareContent(southwestSquare));

        Square westSquare = new Square(p1.x+west.x, p1.y+west.y);
        str = str + "," + regionInfo.getSquareContent(westSquare);
        spaceKnowledge.put(westSquare, regionInfo.getSquareContent(westSquare));

        Square northwestSquare = new Square(p1.x+northwest.x, p1.y+northwest.y);
        str = str + "," + regionInfo.getSquareContent(northwestSquare);
        spaceKnowledge.put(northwestSquare, regionInfo.getSquareContent(northwestSquare));

        return str;
    }

    // Determine if the thrust operation is valid (leads to a barrier or crash)
    public String validateThrustAction(Integer droneId, Square S, Integer distance, Directions dir){
        String str="";

        Integer thrustDistance=0;

        Point p2 = dir.getDirectionPoint(dir);
        Square sOrigin = droneFleet.get(droneId).getLocation();

        for(int i=1; i<=distance;i++){
            int x=sOrigin.x+i*p2.x;
            int y=sOrigin.y+i*p2.y;

            Square sNext= new Square(x,y);

            if(regionInfo.getSquareContent(sNext)==Contents.sun) {
                droneFleet.get(droneId).setStatus(false);
                regionInfo.setSquareContent(sOrigin, Contents.empty);
                spaceKnowledge.put(sOrigin, Contents.empty);
                spaceKnowledge.put(sNext, Contents.sun);

                if (i>1){
                    Square sCurrent= new Square(sOrigin.x+(i-1)*p2.x, sOrigin.y+(i-1)*p2.y);
                    regionInfo.setSquareContent(sCurrent, Contents.empty);
                    spaceKnowledge.put(sCurrent, Contents.empty);
                }

                thrustDistance=distance;
                return str+String.valueOf(thrustDistance)+"\n"+"crash";
            }

            else if(regionInfo.getSquareContent(sNext)==Contents.drone) {
                droneFleet.get(droneId).setStatus(false);
                regionInfo.setSquareContent(sOrigin, Contents.empty);
                spaceKnowledge.put(sOrigin, Contents.empty);
                //if (!spaceKnowledge.containsKey(S)) {
                spaceKnowledge.put(sNext, Contents.empty);
                if (i>1){
                    Square sCurrent= new Square(sOrigin.x+(i-1)*p2.x, sOrigin.y+(i-1)*p2.y);
                    regionInfo.setSquareContent(sCurrent, Contents.empty);
                    spaceKnowledge.put(sCurrent, Contents.empty);
                }
                //}

                //Find the second drone that crashed
                for (int j=0; j<numberOfDrones; j++) {

                    if (droneFleet.get(j).getLocation()==sNext){
                        droneFleet.get(j).setStatus(false);
                    }
                }

                thrustDistance=distance;
                return str+String.valueOf(thrustDistance)+"\n"+"crash";
            }

            else if(regionInfo.getSquareContent(sNext)==Contents.barrier){
                thrustDistance=distance;


                    regionInfo.setSquareContent(sOrigin, Contents.empty);
                    regionInfo.setSquareContent(sNext, Contents.barrier);
                    spaceKnowledge.put(sOrigin, Contents.empty);
                    spaceKnowledge.put(sNext, Contents.barrier);
                if(i>1){
                    Square sCurrent= new Square(sOrigin.x+(i-1)*p2.x, sOrigin.y+(i-1)*p2.y);
                    droneFleet.get(droneId).setLocation(sCurrent);
                    regionInfo.setSquareContent(sCurrent, Contents.drone);
                    spaceKnowledge.put(sCurrent, Contents.drone);

                }
                return str+String.valueOf(thrustDistance)+"\n"+"ok";
            }
            else{
                thrustDistance=distance;
                Square sCurrent= new Square(sOrigin.x+(i-1)*p2.x, sOrigin.y+(i-1)*p2.y);

                droneFleet.get(droneId).setLocation(sNext);
                regionInfo.setSquareContent(sOrigin, Contents.empty);
                regionInfo.setSquareContent(sCurrent, Contents.empty);
                regionInfo.setSquareContent(sNext, Contents.drone);
                spaceKnowledge.put(sOrigin, Contents.empty);
                spaceKnowledge.put(sCurrent, Contents.empty);
                spaceKnowledge.put(sNext, Contents.drone);
            }
        }

        return str+String.valueOf(thrustDistance)+"\n"+"ok";
    }


    public boolean isSimulationCompleted(){
        //Determine number of crashed drones
        Integer numOfCrashedDrones=0;
        for (int i = 0; i < numberOfDrones; i++) {
            if(!droneFleet.get(i).getStatus()){
                numOfCrashedDrones+=1;
            }
        }

        if((this.turnLimit-this.turnsCompleted==0)||(this.numberOfDrones-numOfCrashedDrones==0)||(regionInfo.isSpaceExplored())){
            //System.out.println("Turn Completed: "+ turnsCompleted);
            //System.out.println("Total Drones: "+ numberOfDrones);
            //System.out.println("Drones Crashed: "+ numOfCrashedDrones);
            //System.out.println("Explored? "+ regionInfo.isSpaceExplored());
            return true;
        }

        return false;
    }

    //Print current drone action on the terminal
    public void printDroneAction (String actionResult){
        System.out.println(actionResult);
    }


    // Print final report after the simulation is halted/completed
    public void finaReport (){
        for (Contents value : spaceKnowledge.values()) {
            if((value==Contents.drone)||(value==Contents.empty))
                numOfExploredSquares+=1;
        }

        String report=String.valueOf(this.regionHeight*this.regionWidth)+","+
                String.valueOf(this.regionHeight*this.regionWidth-this.numOfSuns)+
                ","+numOfExploredSquares+","+turnsCompleted;

        System.out.println(report);
    }
}
