package cmpe295.sjsu.edu.salesman.algorithm;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cmpe295.sjsu.edu.salesman.pojo.Point;

/**
 * Created by jijhaver on 6/20/15.
 */
public class Constants {

    public static  final int BEACON_COUNT = 4;

    // ipod - 8602
    // mint - 16545
    // sky blue - 19249
    // dark blue - 60209
    public static final int BEACON_1 = 19249;
    public static final int BEACON_2 = 8602;
    public static final int BEACON_3 = 60209;
    public static final int BEACON_4 = 16545;
    public static final Integer[] BEACON_ARRAY = {BEACON_1, BEACON_2, BEACON_3, BEACON_4};

    public static final Point BEACON_1_POINT = new Point(0.32f, 0.25f);
    public static final Point BEACON_2_POINT = new Point(0.32f, 0.75f);
    public static final Point BEACON_3_POINT = new Point(0.68f, 0.75f);
    public static final Point BEACON_4_POINT = new Point(0.68f, 0.25f);

    public static final Map<Integer, Point> beaconPointMap = configureBeaconPointMap();

    private static Map<Integer, Point> configureBeaconPointMap() {

        Map<Integer,Point> beaconMap = new HashMap<>();
        beaconMap.put(BEACON_1, BEACON_1_POINT);
        beaconMap.put(BEACON_2, BEACON_2_POINT);
        beaconMap.put(BEACON_3, BEACON_3_POINT);
        beaconMap.put(BEACON_4, BEACON_4_POINT);

        return  beaconMap;
    }




}
