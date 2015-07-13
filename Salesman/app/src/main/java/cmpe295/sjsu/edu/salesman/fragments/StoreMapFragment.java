package cmpe295.sjsu.edu.salesman.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.qozix.tileview.TileView;
import com.qozix.tileview.markers.MarkerEventListener;

import java.util.List;

import cmpe295.sjsu.edu.salesman.HomeActivity;
import cmpe295.sjsu.edu.salesman.R;
import cmpe295.sjsu.edu.salesman.algorithm.Constants;
import cmpe295.sjsu.edu.salesman.algorithm.LocationAlgorithm;
import cmpe295.sjsu.edu.salesman.algorithm.Utility;
import cmpe295.sjsu.edu.salesman.pojo.Point;

/**
 * Created by jijhaver on 6/22/15.
 */


public class StoreMapFragment extends Fragment  {

    private  TileView tileView;
    private Context context;
    private Activity activity;
    private static final String TAG = StoreMapFragment.class.getSimpleName();

    private static final int REQUEST_ENABLE_BT = 1234;
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);

    private BeaconManager beaconManager;
    private ImageView userLocationView;
    private ImageView pointLocationView;
    private LocationAlgorithm locationAlgorithm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = container.getContext();
        activity = this.getActivity();
        tileView =  new TileView(context);
        // size of original image at 100% scale
        tileView.setSize(6220, 11060);

        // detail levels
        //tileView.addDetailLevel(1.000f, "tiles_1/floor/1000/%col%_%row%.jpg", "samples_1/floor_plan.jpg");
        tileView.addDetailLevel(0.500f, "tiles_1/floor/500/%col%_%row%.png", "samples_1/retailmap.png");
        tileView.addDetailLevel(0.250f, "tiles_1/floor/250/%col%_%row%.png", "samples_1/retailmap.png");
        tileView.addDetailLevel(0.125f, "tiles_1/floor/125/%col%_%row%.jpg", "samples_1/retailmap.png");

        // let's use 0-1 positioning...
        tileView.defineRelativeBounds(0, 0, 1, 1);
        // add a marker listener
        tileView.addMarkerEventListener(markerEventListener);

        // add some beacons...
        //addBeacon(0.25, 0.25);
        //addBeacon(0.25, 0.75);
        //addBeacon(0.75, 0.25);
        //addBeacon(0.75, 0.75);

        // center markers along both axes
        tileView.setMarkerAnchorPoints(-0.5f, -0.5f);


        // scale it down to manageable size
        tileView.setScale(0.25);

        getActivity().getActionBar().hide();
       // setContentView(rootView);

        userLocationView = new ImageView( context );
        userLocationView.setImageResource(R.drawable.beacon);

        pointLocationView = new ImageView(context );
        pointLocationView.setImageResource(R.drawable.marker_icon );

        // Configure BeaconManager.
        beaconManager = HomeActivity.getBeaconManager();
        locationAlgorithm = new LocationAlgorithm();
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
                locationAlgorithm.addBeaconRange(beacons);
                final Point userLocation = locationAlgorithm.getUserLocation();

                // Note that results are not delivered on UI thread.
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setUserLocation(userLocation);
                    }
                });
            }
        });


        return tileView;
    }

    private void addBeacon( double x, double y ) {
        ImageView imageView = new ImageView(context );
        imageView.setImageResource(R.drawable.beacon );
        getTileView().addMarker(imageView, x, y );
    }

    private void addPathPoint( double x, double y ) {
        ImageView imageView = new ImageView( context );
        imageView.setImageResource(R.drawable.beacon );
        getTileView().addMarker(imageView, x, y );
    }

    public void addLocationMarker(double x, double y ) {
        getTileView().removeMarker(pointLocationView);
        getTileView().addMarker(pointLocationView, x, y );
    }

    private void setUserLocation(Point userLocationPoint){
        if (getTileView() != null && userLocationPoint != null) {
            getTileView().removeMarker(userLocationView);

            getTileView().addMarker(userLocationView, userLocationPoint.getX(), userLocationPoint.getY());
            // getTileView().moveTo(userLocationPoint.x, userLocationPoint.y);
        }
    }

    private void setUserLocation(Beacon beacon)
    {
        getTileView().removeMarker(userLocationView);
        Point userLocationPoint = Constants.beaconPointMap.get(beacon.getMinor());
        if (userLocationPoint != null) {
            getTileView().addMarker(userLocationView, userLocationPoint.getX(), userLocationPoint.getY());
            // getTileView().moveTo(userLocationPoint.x, userLocationPoint.y);
        }

    }

    private MarkerEventListener markerEventListener = new MarkerEventListener() {
        @Override
        public void onMarkerTap( View v, int x, int y ) {
            int beaconId = 3;//new Random().nextInt(3);
            Toast.makeText(context.getApplicationContext(), "You tapped a pin: " + String.valueOf(x) + "," + String.valueOf(y) + "! BeaconId: " + String.valueOf(beaconId), Toast.LENGTH_LONG).show();
            Point destinationPoint = Constants.beaconPointMap.get(Constants.BEACON_ARRAY[beaconId]);
            List<List<Point>> pathList = locationAlgorithm.getAllPathsFromCurrentLocationTo(destinationPoint);
            int[] colorList = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
            int i = 0;
            for(List<Point> path : pathList){
                String pathString = Utility.ListToString(path);
                Paint pathPaint = getTileView().getPathPaint();
                pathPaint.setColor(colorList[i++]);
                pathPaint.setShadowLayer(4, 2, 2, 0x66000000);
                pathPaint.setPathEffect(new CornerPathEffect(5));
                getTileView().drawPath(Utility.getDoubleList(path));
                Log.i(TAG, "Path: " + pathString.toString());
                break;

            }
        }
    };


    @Override
    public void onPause() {
        super.onPause();
        tileView.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        tileView.resume();

        // Set the location marker to previous set location

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tileView.destroy();
        tileView = null;
    }

    public TileView getTileView(){
        return tileView;
    }

    /**
     * This is a convenience method to moveToAndCenter after layout (which won't happen if called directly in onCreate
     * see https://github.com/moagrius/TileView/wiki/FAQ
     */
    public void frameTo( final double x, final double y ) {
        getTileView().post(new Runnable() {
            @Override
            public void run() {
                getTileView().moveToAndCenter(x, y);
            }
        });
    }
}


