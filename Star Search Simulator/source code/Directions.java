import java.awt.*;

public enum Directions {
    //unknown is only for diagnostic purpose
    north, northeast, east, southeast, south, southwest, west, northwest, unknown;


    public Point getDirectionPoint(Directions dir){
        //Determine directions using unique co-ordinate manipulators
        Point P;
        switch(dir){
            case northeast:
                P = new Point(1,1);
                break;
            case east:
                P = new Point(1,0);
                break;
            case southeast:
                P = new Point(1,-1);
                break;
            case south:
                P = new Point(0,-1);
                break;
            case southwest:
                P = new Point(-1,-1);
                break;
            case west:
                P = new Point(-1,0);
                break;
            case northwest:
                P = new Point(-1,1);
                break;
            //default is north
            default:
                P = new Point(0,1);
                break;
        }
        return P;
    }
}
