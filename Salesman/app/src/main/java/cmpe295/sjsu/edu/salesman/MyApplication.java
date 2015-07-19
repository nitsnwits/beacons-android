package cmpe295.sjsu.edu.salesman;

import android.app.Application;
import android.app.Notification;

import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cmpe295.sjsu.edu.salesman.pojo.Point;
import cmpe295.sjsu.edu.salesman.utils.ForegroundCheckTask;

/**
 * Created by jijhaver on 7/12/15.
 */
public class MyApplication extends Application {

    private BeaconManager beaconManager;
    private NotificationManager notificationManager;
    private static final int NOTIFICATION_ID = 123;
    private static final String TAG = MyApplication.class.getSimpleName();
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", "B9407F30-F5F8-466E-AFF9-25556B57FE6D", 6472, 19249);
    private boolean enteredRegion;
    private  boolean foregroud;




    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        initializeBeaconManager();
    }

    private void initializeBeaconManager() {
        System.out.println("-------initializeBeaconManager----------");
        beaconManager = new BeaconManager(this);
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

        beaconManager.setBackgroundScanPeriod(TimeUnit.SECONDS.toMillis(1), 0);


        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
                System.out.println("Beacon Discovered");
                try {
                    foregroud = new ForegroundCheckTask().execute(getApplicationContext()).get();
                    System.out.println("Foreground______"+foregroud);
                    if(!foregroud & !enteredRegion) {
                        // Call Rest api with for beacon
                        postNotification("Welcome to Electronics Section");
                        enteredRegion = true;
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private void postNotification(String msg) {

        System.out.println("-----------Post Notification---------------");

        Notification notification = new Notification.Builder(MyApplication.this)
                .setSmallIcon(R.drawable.s_icon)
                .setContentTitle("Salesman")
                .setContentText(msg)
                .setAutoCancel(true)
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
                } catch (RemoteException     e) {
                    Toast.makeText(MyApplication.this, "Cannot start ranging, something terrible happended", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Cannot start ranging", e);
                }
            }
        });
    }


}

