package cmpe295.sjsu.edu.salesman.algorithm;


import com.estimote.sdk.Beacon;
import com.estimote.sdk.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cmpe295.sjsu.edu.salesman.datastructure.PathGraph;
import cmpe295.sjsu.edu.salesman.pojo.Point;

/**
 * Created by jijhaver on 6/20/15.
 */
public class LocationAlgorithm {

    private Map<Integer,BeaconMovingAverage> beaconMovingAverageMap;
    private PathGraph pathGraph;

    public LocationAlgorithm(){
        beaconMovingAverageMap = new HashMap<Integer,BeaconMovingAverage>();
        for (int i = 0; i <Constants.BEACON_COUNT; i++) {
            BeaconMovingAverage beaconMovingAverage = new BeaconMovingAverage();
            beaconMovingAverageMap.put(Constants.BEACON_ARRAY[i], beaconMovingAverage);
        }

        pathGraph  = new PathGraph();
        pathGraph.storePath();

    }

    public void addBeaconRange(List<Beacon> beacons){
        for(Beacon beacon : beacons){
            BeaconMovingAverage beaconMovingAverage = beaconMovingAverageMap.get(beacon.getMinor());
            if (beaconMovingAverage != null){
                beaconMovingAverage.addSample(Utils.computeAccuracy(beacon));
            }
        }
    }

    public Point getUserLocation() {
        TreeMap<Double,Integer> sortedBeaconRangeMap = new TreeMap<>();
        for (Integer beacon : beaconMovingAverageMap.keySet()) {
            sortedBeaconRangeMap.put(beaconMovingAverageMap.get(beacon).getAverage(), beacon);
        }

        // Get the beacon nearest to the user
        Integer nearestBeacon = sortedBeaconRangeMap.firstEntry().getValue();
        Point userLocationPoint = Constants.beaconPointMap.get(nearestBeacon);
        return  userLocationPoint;
    }

    public List<List<Point>>  getAllPathsFromCurrentLocationTo(Point destinationPoint) {
        Point sourcePoint = getUserLocation();
        return pathGraph.getAllPath(sourcePoint, destinationPoint);
    }


}
