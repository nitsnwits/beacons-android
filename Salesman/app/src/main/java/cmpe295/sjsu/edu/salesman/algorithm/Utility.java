package cmpe295.sjsu.edu.salesman.algorithm;

import java.util.ArrayList;
import java.util.List;

import cmpe295.sjsu.edu.salesman.pojo.Offer;
import cmpe295.sjsu.edu.salesman.pojo.Point;

/**
 * Created by jijhaver on 6/24/15.
 */
public class Utility {
    public static List<double[]> getDoubleList(List<Point> path) {
        List<double[]> pointList = new ArrayList<>();
        for(Point point:path){
            double[] pointArray = new double[2];
            pointArray[0] = point.getX();
            pointArray[1] = point.getY();
            pointList.add(pointArray);
        }

        return pointList;
    }

    public static String ListToString(List<Point> path) {
        StringBuilder pathString = new StringBuilder();
        for(Point pathPoint : path){
            pathString.append(String.valueOf(pathPoint.getX()) + "," + String.valueOf(pathPoint.getY() + " -> "));
        }
        return  pathString.toString();
    }

    public static Offer searchOfferByName(ArrayList<Offer> offers, String searchedOfferName) {
        Offer searchedOffer = null;
        for (int i = 0; i < offers.size() ; i++) {
            if(offers.get(i).getName().equals(searchedOfferName)) {
                searchedOffer = offers.get(i);
                break;
            }
        }
        return  searchedOffer;

    }
}
