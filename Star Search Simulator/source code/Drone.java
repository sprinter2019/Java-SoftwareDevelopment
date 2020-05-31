import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Drone {
    int id;

    private Integer strategy=0;
    private Integer thrustDistance;
    private Square location;
    private boolean isAlive = true;
    private Directions nextDir = Directions.north;
    private Directions lastDir;
    private DroneActions lastAction;
    private DroneActions nextAction = DroneActions.pass;
    private HashMap<Square, Contents> spaceKnowledge = new HashMap<>();
    boolean isThrust=false;
    boolean isSteer=false;
    boolean isScan=false;
    boolean isPass=false;

    private static Random randGenerator = new Random();


    public Drone(int id, Square S) {
        this.id = id;
        this.location = S;
    }

    public void setLocation(Square S) {
        this.location = S;
    }

    public void setStrategy (Integer i){
        this.strategy=i;
    }

    public Integer getStrategy(){
        return this.strategy;
    }


    public Square getLocation() {
        return location;
    }

    public void setDroneDirection(Directions dir) {
        this.nextDir = dir;
    }

    public Directions getDroneDirection() {
        return nextDir;
    }

    public void setDroneAction(DroneActions action) {
        this.nextAction = action;
    }

    //public void setNextAction(DroneActions action){ this.nextAction=action;}

    public DroneActions getDroneAction() {
        return nextAction;
    }

    public void setStatus(boolean status) {
        this.isAlive = status;
    }

    public boolean getStatus() {
        return isAlive;
    }

    public Integer getThrustDistance() {
        return this.thrustDistance;
    }

    public void setThrustDistance(int distance) {
        this.thrustDistance = distance;
    }

    public void setSpaceKnowledge(HashMap<Square, Contents> spaceKnowledge) {
        this.spaceKnowledge = spaceKnowledge;
    }

    //Determine Drone's next action
    public void actionGenerator() {
        this.isThrust=false;
        this.isSteer=false;
        this.isScan=false;
        this.isPass=false;

        if (this.isAlive) {
            int numOfEmptySquares = 0;
            this.thrustDistance = 0;
            boolean isUnknownSquare=false;
            int x = nextDir.getDirectionPoint(nextDir).x;
            int y = nextDir.getDirectionPoint(nextDir).y;

            // Scan all the squares in the drone's current direction (max length = 3) and determine if a thrust operation is possible
            for(int i=1;i<=3;i++){
                Square S= new Square(i*x+location.x, i*y+location.y);
                if (spaceKnowledge.containsKey(S)) {

                    if((spaceKnowledge.get(S)==Contents.drone)||(spaceKnowledge.get(S)==Contents.sun)||(spaceKnowledge.get(S)==Contents.barrier)) break;
                    else{
                        if(spaceKnowledge.get(S)==Contents.stars) {
                            this.thrustDistance = i;

                        }
                        else if (spaceKnowledge.get(S)==Contents.empty) {
                            numOfEmptySquares+=1;
                        }
                    }
                }
                else if (i==1){
                    isUnknownSquare=true;
                }
            }
            //System.out.println("thrustDistance: "+thrustDistance);

            if(thrustDistance>0){
                isThrust=true;
                this.lastAction=this.nextAction;
                this.nextAction=DroneActions.thrust;
            }

            else if(isUnknownSquare){
                //System.out.println("No thrust location but unknown square available!");
                isScan=true;
                this.lastAction=this.nextAction;
                this.nextAction=DroneActions.scan;
            }

            if(!isThrust && !isScan) {

                //Scan around the current location to determine possible steer directions
                //set steer-scan distance
                int i=1;

                //Look for an unexplored square near the drone location
                validateSteer(i, Contents.stars);

                if(!isSteer){
                    //Look for an explored square near the drone location
                    validateSteer(i, Contents.empty);

                    if((lastDir==nextDir)&&(lastAction==DroneActions.steer)){
                        this.isSteer=false;
                        this.isThrust=true;
                        this.thrustDistance=numOfEmptySquares;
                        this.lastAction=this.nextAction;
                        this.nextAction=DroneActions.thrust;
                    }
                }

            }

            //a Pass operation will be avoided if any other valid action is possible
            else if(!isThrust && !isScan && !isSteer){
                isPass=true;
                this.lastAction = this.nextAction;
                this.nextAction = DroneActions.pass;
            }

        }
    }

    //Determine possible steer directions
    void validateSteer(Integer i, Contents content){

        ArrayList<Directions> dirList = new ArrayList<>();

        Point p1 = new Point(location.x, location.y);
        Point north = Directions.north.getDirectionPoint(Directions.north);
        Point northeast = Directions.northeast.getDirectionPoint(Directions.northeast);
        Point east = Directions.east.getDirectionPoint(Directions.east);
        Point southeast = Directions.southeast.getDirectionPoint(Directions.southeast);
        Point south = Directions.south.getDirectionPoint(Directions.south);
        Point southwest = Directions.southwest.getDirectionPoint(Directions.southwest);
        Point west = Directions.west.getDirectionPoint(Directions.west);
        Point northwest = Directions.northwest.getDirectionPoint(Directions.northwest);

        if (spaceKnowledge.get(new Square(p1.x + i * north.x, p1.y + i * north.y)) == content) {
            isSteer=true;
            dirList.add(Directions.north);
        }  if (spaceKnowledge.get(new Square(p1.x + i * northeast.x, p1.y + i * northeast.y)) == content) {
            isSteer=true;
            dirList.add(Directions.northeast);
        }  if (spaceKnowledge.get(new Square(p1.x + i * east.x, p1.y + i * east.y)) == content) {
            isSteer=true;
            dirList.add(Directions.east);
        }  if (spaceKnowledge.get(new Square(p1.x + i * southeast.x, p1.y + i * southeast.y)) == content) {
            isSteer=true;
            dirList.add(Directions.southeast);
        }  if (spaceKnowledge.get(new Square(p1.x + i * south.x, p1.y + i * south.y)) == content) {
            isSteer=true;
            dirList.add(Directions.south);
        }  if (spaceKnowledge.get(new Square(p1.x + i * southwest.x, p1.y + i * southwest.y)) == content) {
            isSteer=true;
            dirList.add(Directions.southwest);
        }  if (spaceKnowledge.get(new Square(p1.x + i * west.x, p1.y + i * west.y)) == content) {
            isSteer=true;
            dirList.add(Directions.west);
        }  if (spaceKnowledge.get(new Square(p1.x + i * northwest.x, p1.y + i * northwest.y)) == content) {
            isSteer=true;
            dirList.add(Directions.northwest);
        }

        if(isSteer){
            //choose randomly from the available list of valid steer directions
            int maxLimit=dirList.size();

            this.lastDir=this.nextDir;
            this.lastAction = this.nextAction;
            this.nextAction = DroneActions.steer;
            setDroneDirection(dirList.get(randGenerator.nextInt(maxLimit)));
        }
    }

}