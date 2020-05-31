import java.util.HashMap;

//THis class defines the initial Space region and also updates the contents in squares as the simulation progresses
public class Space{
    private HashMap<Square, Contents> spaceRegion = new HashMap<>();
    private Integer xSize;
    private Integer ySize;

    public Space(int xSize, int ySize, Contents content){

        this.xSize=xSize;
        this.ySize=ySize;
        for (int i=0; i<xSize; i++){
            for (int j=0; j<ySize;j++) {
                Square S = new Square(i,j);
                spaceRegion.put(S, content);
            }
        }
    }


    public void setSquareContent(Square S, Contents content){
        spaceRegion.replace(S, content);
    }

    public Contents getSquareContent(Square S){
        if ((S.x>=xSize)||(S.x<0)||(S.y>=ySize)||(S.y<0)){
            return Contents.barrier;
        }
        else {
            return spaceRegion.get(S);
        }
    }

    //This method determines if the Space region is completed explored to determine a possible simulation halt
    public boolean isSpaceExplored(){
        if (spaceRegion.containsValue(Contents.stars)) return false;
        return true;
    }

    /*
    //This method is only for diagnostic purpose
    public void printSpaceContents(){
        for (Contents value : spaceRegion.values()) {
            System.out.println(value);
        }
    }
     */
}
