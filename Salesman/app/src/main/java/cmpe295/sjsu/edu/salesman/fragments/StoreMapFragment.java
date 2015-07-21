package cmpe295.sjsu.edu.salesman.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.qozix.tileview.TileView;
import com.qozix.tileview.markers.MarkerEventListener;
import com.qozix.tileview.paths.DrawablePath;

import java.util.ArrayList;
import java.util.List;

import cmpe295.sjsu.edu.salesman.HomeActivity;
import cmpe295.sjsu.edu.salesman.MyApplication;
import cmpe295.sjsu.edu.salesman.R;
import cmpe295.sjsu.edu.salesman.algorithm.Constants;
import cmpe295.sjsu.edu.salesman.algorithm.LocationAlgorithm;
import cmpe295.sjsu.edu.salesman.algorithm.Utility;
import cmpe295.sjsu.edu.salesman.pojo.Point;

/**
 * Created by jijhaver on 6/22/15.
 */


public class StoreMapFragment extends Fragment  {

    private TileView tileView;
    private Context context;
    private Activity activity;
    private static final String TAG = StoreMapFragment.class.getSimpleName();
    private List<DrawablePath> locationPathList;

    private static final int REQUEST_ENABLE_BT = 1234;
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);

    private BeaconManager beaconManager;
    private ImageView userLocationView;
    private ImageView pointLocationView;
    private LocationAlgorithm locationAlgorithm;
    private RelativeLayout relativeLayout;

    // search box
    private RelativeLayout searchBoxBackground;
    private LinearLayout searchResultLayout;
    private ImageButton menuButton;
    private ImageButton backButton;
    private EditText searchEditText;
    private Point poiPoint;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = container.getContext();
        activity = this.getActivity();
        relativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayout.setLayoutParams(lp);
        tileView =  new TileView(context);
        tileView.setFocusable(true);
        // size of original image at 100% scale
        tileView.setSize(6220, 11060);
        locationPathList = new ArrayList<>();

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

        // add offers
        for (int count = 0; count < Constants.OFFER_ARRAY.length; count++){
            addOffer(Constants.OFFER_ARRAY[count].getX(), Constants.OFFER_ARRAY[count].getY());
        }
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
        beaconManager = MyApplication.getBeaconManager();
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

        if (poiPoint != null){
            addLocationMarker(poiPoint.getX(), poiPoint.getY());
            poiPoint = null;
            frameTo(pointLocationView);
        }
        relativeLayout.addView(tileView);
        addMapIcons(inflater, container);
        return relativeLayout;
    }


    private void addMapIcons(LayoutInflater inflater, ViewGroup container) {

        // Current location button
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        ImageButton currentLocationBtn = new ImageButton(context);
        currentLocationBtn.setFocusable(true);
        currentLocationBtn.setImageResource(R.drawable.current_location);
        currentLocationBtn.setBackgroundColor(Color.TRANSPARENT);
        currentLocationBtn.setOnClickListener(currentLocationClickListener);
        relativeLayout.addView(currentLocationBtn, lp);

        // Search Box
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        View searchView = inflater.inflate(R.layout.searchbox, container, false);
        relativeLayout.addView(searchView, lp1);
        searchBoxBackground = (RelativeLayout) searchView.findViewById(R.id.searchBoxBackground);
        menuButton = (ImageButton) searchView.findViewById(R.id.menuButton);
        menuButton.setOnClickListener(menuBtnClickListener);
        backButton = (ImageButton) searchView.findViewById(R.id.backButton);
        backButton.setOnClickListener(backBtnClickListener);

        searchEditText = (EditText) searchView.findViewById(R.id.searchEditText);
        searchEditText.clearFocus();
        //searchEditText.setOnFocusChangeListener(searchFocusChangeListener);
        searchResultLayout = (LinearLayout) searchView.findViewById(R.id.search_result);
        ListView poiListView = (ListView) searchView.findViewById(R.id.poi_list_view);
        poiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchResultLayout.setVisibility(View.INVISIBLE);
                menuButton.setVisibility(View.VISIBLE);
                backButton.setVisibility(View.INVISIBLE);
                searchBoxBackground.setBackgroundColor(Color.TRANSPARENT);
            }
        });

        ArrayList<String> listItems=new ArrayList<String>();
        listItems.add(" Offers");
        listItems.add(" Sale");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, listItems);
        poiListView.setAdapter(adapter);


        searchEditText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                searchResultLayout.setVisibility(View.VISIBLE);
                menuButton.setVisibility(View.INVISIBLE);
                backButton.setVisibility(View.VISIBLE);
                searchBoxBackground.setBackgroundColor(Color.WHITE);
            }

        });

        //Being inside the box and pressing a key
        searchEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //If the event is a key-down event on the "enter" button
                //If enter is pressed while inside the textbox
                /*if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                }
                return false;
                */

                return true;
            }
        });
    }

    private void addOffer(double x, double y )
    {
        ImageView imageView = new ImageView(context);
        imageView.setTag(String.valueOf(x)+","+String.valueOf(y));
        imageView.setImageResource(R.drawable.offer);
        getTileView().addMarker(imageView, x, y);
    }

    private void addBeacon( double x, double y ) {
        ImageView imageView = new ImageView(context );
        imageView.setImageResource(R.drawable.beacon);
        getTileView().addMarker(imageView, x, y );
    }


    public void addLocationMarker(double x, double y ) {
        getTileView().removeMarker(pointLocationView);
        getTileView().addMarker(pointLocationView, x, y - 0.02);
    }

    private void setUserLocation(Point userLocationPoint){
        if (getTileView() != null && userLocationPoint != null) {
            getTileView().removeMarker(userLocationView);
            getTileView().addMarker(userLocationView, userLocationPoint.getX(), userLocationPoint.getY());
        }
    }

    private View.OnClickListener menuBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((HomeActivity)activity).openDrawer();
        }
    };
    private View.OnClickListener backBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            searchResultLayout.setVisibility(View.INVISIBLE);
            menuButton.setVisibility(View.VISIBLE);
            backButton.setVisibility(View.INVISIBLE);
            searchEditText.clearComposingText();
            searchBoxBackground.setBackgroundColor(Color.TRANSPARENT);
        }
    };


    private View.OnClickListener currentLocationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Point userLocation = locationAlgorithm.getUserLocation();
            if(userLocation != null)
                frameTo(userLocationView);
        }
    };

    private MarkerEventListener markerEventListener = new MarkerEventListener() {
        @Override
        public void onMarkerTap( View v, int x, int y ) {
            //Toast.makeText(context.getApplicationContext(), "You tapped a pin: " + String.valueOf(x) + "," + String.valueOf(y) + "!", Toast.LENGTH_LONG).show();
            if(v.getTag() != null && v.getTag() instanceof String) {
                Point destinationPoint = Constants.offerPointMap.get(v.getTag());
                if(destinationPoint != null) {
                    List<List<Point>> pathList = locationAlgorithm.getAllPathsFromCurrentLocationTo(destinationPoint);
                    int[] colorList = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
                    int i = 0;
                    for (int j = 0; j < locationPathList.size(); j++) {
                        getTileView().removePath(locationPathList.get(j));
                    }
                    for (List<Point> path : pathList) {
                        String pathString = Utility.ListToString(path);
                        Paint pathPaint = new Paint(getTileView().getPathPaint()); //getTileView().getPathPaint();
                        pathPaint.setColor(colorList[i++]);
                        pathPaint.setShadowLayer(4, 2, 2, 0x66000000);
                        pathPaint.setPathEffect(new CornerPathEffect(5));
                        DrawablePath locationPath = getTileView().drawPath(Utility.getDoubleList(path), pathPaint);
                        if(locationPath != null)
                            locationPathList.add(locationPath);
                       // Log.i(TAG, "Path: " + pathString.toString());
                        // Place a marker
                        addLocationMarker(path.get(path.size()-1).getX(), path.get(path.size()-1).getY());
                        //break;

                    }
                }
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

    public Point getPoiPoint() {
        return poiPoint;
    }

    public void setPoiPoint(Point poiPoint) {
        this.poiPoint = poiPoint;
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

    public void frameTo( final View marker ) {
        getTileView().post(new Runnable() {
            @Override
            public void run() {
                getTileView().moveToMarker(marker, true);
            }
        });
    }


}


