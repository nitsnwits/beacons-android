package cmpe295.sjsu.edu.salesman.algorithm;


import java.util.HashMap;
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

    // OFFERS
    public static final Point SPORTING_GOODS_OFFER_POINT = new Point(0.25, 0.1);
    public static final Point AUTO_CARE_OFFER_POINT = new Point(0.25, 0.28);
    public static final Point TOYS_OFFER_POINT = new Point(0.25, 0.45);
    public static final Point HOME_OFFER_POINT = new Point(0.25, 0.6);
    public static final Point APPAREL_OFFER_POINT = new Point(0.25, 0.75);
    public static final Point JEWELRY_OFFER_POINT = new Point(0.25, 0.9);

    public static final Point PETS_OFFER_POINT = new Point(0.38, 0.2);
    public static final Point HEALTH_OFFER_POINT = new Point(0.38, 0.45);
    public static final Point BEAUTY_OFFER_POINT = new Point(0.38, 0.78);
    public static final Point PAPER_CLEANING_OFFER_POINT = new Point(0.62, 0.32);
    public static final Point BOOKS_OFFER_POINT = new Point(0.62, 0.58);
    public static final Point CARDS_OFFER_POINT = new Point(0.62, 0.8);

    public static final Point CRAFTS_OFFER_POINT = new Point(0.75, 0.05);
    public static final Point HOME_OFFICE_OFFER_POINT = new Point(0.75, 0.2);
    public static final Point ELECTRONICS_OFFER_POINT = new Point(0.75, 0.42);
    public static final Point FROZEN_FOODS_OFFER_POINT = new Point(0.75, 0.67);
    public static final Point GROCERY_OFFER_POINT = new Point(0.75, 0.82);
    public static final Point DELI_OFFER_POINT = new Point(0.75, 0.88);
    public static final Point SOME_OFFER_POINT = new Point(0.75, 0.95);

    public static final Point[] OFFER_ARRAY = {
            SPORTING_GOODS_OFFER_POINT,
            AUTO_CARE_OFFER_POINT,
            TOYS_OFFER_POINT,
            HOME_OFFER_POINT,
            APPAREL_OFFER_POINT,
            JEWELRY_OFFER_POINT,
            PETS_OFFER_POINT,
            HEALTH_OFFER_POINT,
            BEAUTY_OFFER_POINT,
            PAPER_CLEANING_OFFER_POINT,
            BOOKS_OFFER_POINT,
            CARDS_OFFER_POINT,
            CRAFTS_OFFER_POINT,
            HOME_OFFICE_OFFER_POINT,
            ELECTRONICS_OFFER_POINT,
            FROZEN_FOODS_OFFER_POINT,
            GROCERY_OFFER_POINT,
            DELI_OFFER_POINT,
            SOME_OFFER_POINT
    };
    public static final Map<String, Point> offerPointMap = configureOfferPointMap();

    public static final Map<String,String> beaconOfferMap = configureBeaconOfferMap();

    private static Map<String, Point> configureOfferPointMap() {
        Map<String, Point> OfferMap = new HashMap<>();
        for (int i = 0; i < OFFER_ARRAY.length; i++) {
            OfferMap.put(OFFER_ARRAY[i].toString(),OFFER_ARRAY[i]);
        }
        return  OfferMap;
    }

    public static final double CLOSEST_DISTANCE_TO_PATH = 0.07;

    public static final Point BEACON_1_POINT = new Point(0.315, 0.25);
    public static final Point BEACON_2_POINT = new Point(0.315, 0.75);
    public static final Point BEACON_3_POINT = new Point(0.685, 0.75);
    public static final Point BEACON_4_POINT = new Point(0.685, 0.25);

    public static final Map<Integer, Point> beaconPointMap = configureBeaconPointMap();

    private static Map<Integer, Point> configureBeaconPointMap() {

        Map<Integer,Point> beaconMap = new HashMap<>();
        beaconMap.put(BEACON_1, BEACON_1_POINT);
        beaconMap.put(BEACON_2, BEACON_2_POINT);
        beaconMap.put(BEACON_3, BEACON_3_POINT);
        beaconMap.put(BEACON_4, BEACON_4_POINT);

        return  beaconMap;
    }

    public static final Map<Point,String> beaconIdMap = configureBeaconIdMap();

    private static Map<Point, String> configureBeaconIdMap() {

        Map<Point,String> beaconMap = new HashMap<>();
        beaconMap.put(BEACON_1_POINT, "35b1784b-5bb8-4da6-83d4-ee6d01300355");
        beaconMap.put(BEACON_2_POINT, "3f8d0c46-6ac0-4b06-8ab3-03ed50afb43a");
        beaconMap.put(BEACON_3_POINT, "a57622bb-d10e-413b-b669-334c6c793bfc");
        beaconMap.put(BEACON_4_POINT, "7ff16ebf-40de-4758-b480-d4345412b6aa");

        return  beaconMap;
    }

    private static Map configureBeaconOfferMap(){
        Map<String,String> beaconOfferMap = new HashMap<>();
        beaconOfferMap.put("35b1784b-5bb8-4da6-83d4-ee6d01300355", "Health");
        beaconOfferMap.put("3f8d0c46-6ac0-4b06-8ab3-03ed50afb43a", "Grocery");
        beaconOfferMap.put("a57622bb-d10e-413b-b669-334c6c793bfc", "Books");
        beaconOfferMap.put("7ff16ebf-40de-4758-b480-d4345412b6aa", "Electronics");

        return  beaconOfferMap;
    }

}
