package cmpe295.sjsu.edu.salesman.pojo;

/**
 * Created by jijhaver on 6/20/15.
 */
public class Point {

    private double x;
    private double y;

    public Point(){

    }


    public Point(double x,double y){
        this.x = x;
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getX()) + "," + String.valueOf(this.getY());
    }




}
