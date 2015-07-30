package cmpe295.sjsu.edu.salesman;

import android.app.Application;
import android.app.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import cmpe295.sjsu.edu.salesman.algorithm.Constants;
import cmpe295.sjsu.edu.salesman.algorithm.LocationAlgorithm;
import cmpe295.sjsu.edu.salesman.pojo.Offer;
import cmpe295.sjsu.edu.salesman.pojo.OfferResponse;
import cmpe295.sjsu.edu.salesman.pojo.Point;
import cmpe295.sjsu.edu.salesman.utils.ForegroundCheckTask;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by jijhaver on 7/12/15.
 */
public class MyApplication extends Application {

    private static BeaconManager beaconManager;
    private NotificationManager notificationManager;
    private static final int NOTIFICATION_ID = 123;
    private static final String TAG = MyApplication.class.getSimpleName();
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", "B9407F30-F5F8-466E-AFF9-25556B57FE6D", 6472, 19249);
    private boolean enteredRegion;
    private  boolean foregroud;
    private LocationAlgorithm locationAlgorithm;
    private static ArrayList<Offer> offers = new ArrayList<>();
    public static BeaconManager getBeaconManager(){
        return beaconManager;
    }

    private HashSet<String> beaconSet = new HashSet<>();

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        initializeBeaconManager();
    }

    private void initializeBeaconManager() {
        System.out.println("-------initializeBeaconManager----------");
        beaconManager = new BeaconManager(this);
        beaconManager.setBackgroundScanPeriod(5000, 10000);
        if (!beaconManager.hasBluetooth()) {
            Toast.makeText(this, "Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
            return;
        }

        // If Bluetooth is not enabled, let user enable it.

        if (!beaconManager.isBluetoothEnabled()) {
            //Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }else {
            connectToService();
        }




        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
                try {
                    foregroud = new ForegroundCheckTask().execute(getApplicationContext()).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!foregroud) {
                    locationAlgorithm = new LocationAlgorithm();
                    locationAlgorithm.addBeaconRange(beacons);
                    final Point userLocation = locationAlgorithm.getUserLocation();
                    String beaconId = Constants.beaconIdMap.get(userLocation);
                    System.out.println("Beacon discovered _______" + beaconId);
                    if (!beaconSet.contains(beaconId)) {
                        beaconSet.add(beaconId);
                        getNearByOffers(beaconId);
                    }
                }

            }
        });
    }

    private void getNearByOffers(final String beaconId) {
        RestClient.get().getOffersByBeacon(beaconId, new Callback<ArrayList<OfferResponse>>() {
            @Override
            public void success(ArrayList<OfferResponse> offerResponses, Response response) {

                String notificationStr = null;
                System.out.println("No of offers____" + offerResponses.size());
                try {
                    if (offerResponses.size() != 0) {
                        notificationStr = "Welcome to " + offerResponses.get(0).getCategory().getName() + " Section : ";
                        notificationStr += offerResponses.size() + " offers are going on.";
                    } else {
                        notificationStr = "Welcome to " + Constants.beaconOfferMap.get(beaconId) + " section";
                        notificationStr += offerResponses.size() + " offers are going on.";
                    }
                    postNotification(notificationStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("------------Inside Failure-----------");

            }
        });
    }

    private void postNotification(String msg) {

        System.out.println("-----------Post Notification---------------");



        Intent resultIntent = new Intent(this, HomeActivity.class);

        // Because clicking the notification opens a new ("special") activity, there's
        // no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );



        Notification notification = new Notification.Builder(MyApplication.this)
                .setSmallIcon(R.drawable.s_icon)
                .setContentTitle("Salesman")
                .setContentText(msg)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .build();

        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        notificationManager.notify(NOTIFICATION_ID, notification);





    }

    private void connectToService(){
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
                } catch (RemoteException e) {
                    Toast.makeText(MyApplication.this, "Cannot start ranging, something terrible happended", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Cannot start ranging", e);
                }
            }
        });
    }


    public static ArrayList<Offer> getOffers() {
        return offers;
    }


}

