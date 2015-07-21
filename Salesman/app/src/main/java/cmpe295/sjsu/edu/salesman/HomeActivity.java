package cmpe295.sjsu.edu.salesman;
//Hi

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import cmpe295.sjsu.edu.salesman.adapters.NavDrawerAdapter;
import cmpe295.sjsu.edu.salesman.fragments.AboutSalesmanFragment;
import cmpe295.sjsu.edu.salesman.fragments.OfferFragment;
import cmpe295.sjsu.edu.salesman.fragments.ProductFragment;
import cmpe295.sjsu.edu.salesman.fragments.SettingsFragment;
import cmpe295.sjsu.edu.salesman.fragments.StoreMapFragment;


public class HomeActivity extends Activity  implements NavDrawerAdapter.OnItemClickListener{


    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mMenuTitles;



    SharedPreferences sharedpreferences ;

    // Beacon
    private StoreMapFragment storeMapFragment;
    //private static BeaconManager beaconManager;
    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final int REQUEST_ENABLE_BT = 1234;

    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);

    /* public static BeaconManager getBeaconManager(){
        return beaconManager;
    } */

    public StoreMapFragment getStoreMapFragment(){
        return storeMapFragment;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedpreferences = getBaseContext().getSharedPreferences("salesmanPreference", 0);



        mMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (RecyclerView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the bg_strikethrough content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // improve performance by indicating the list if fixed size.
        mDrawerList.setHasFixedSize(true);
        mDrawerList.setLayoutManager(new LinearLayoutManager(this));

        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new NavDrawerAdapter(mMenuTitles, this));
        // enable ActionBar app icon to behave as action to toggle nav drawer

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Initialize beacons
        // Configure BeaconManager.
        //beaconManager = new BeaconManager(this);
        storeMapFragment = new StoreMapFragment();

        if (savedInstanceState == null) {
            selectItem(0);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        String userId = sharedpreferences.getString("userId","default");
        String accessToken = sharedpreferences.getString("accessToken","default");
        Log.d("SalesmanPref:userId" , userId);
        Log.d("SalesmanPref:aT" , accessToken);
        return true;
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
            case R.id.action_settings:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /* The click listener for RecyclerView in the navigation drawer */
    @Override
    public void onClick(View view, int position) {
        selectItem(position);
    }



    private void selectItem(int position) {
        // update the bg_strikethrough content by replacing fragments

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new OfferFragment();
                break;
            case 1:
                fragment = new ProductFragment();
                break;
            case 2:
                fragment = new StoreMapFragment();
                break;
            case 3:
                fragment = new SettingsFragment();
                break;
            case 4:
                fragment = new AboutSalesmanFragment();
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();

            // update selected item title, then close the drawer
            setTitle(mMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        else{
            // error in creating fragment
            Log.e("HomeActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;

        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStart(){
        super.onStart();
        // Check if device supports Bluetooth Low Energy.
        /*if (!beaconManager.hasBluetooth()) {
            Toast.makeText(this, "Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
            return;
        }

        // If Bluetooth is not enabled, let user enable it.

        if (!beaconManager.isBluetoothEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }else {
           connectToService();
        }
        */
    }

    @Override
    protected void onStop(){
        /*try{
            beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
        }
        catch (RemoteException e){
            Log.d(TAG, "Error while stopping ranging", e);
        }*/
        super.onStop();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                connectToService();
            }else {
                Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_LONG).show();
                //getActionBar().setSubtitle("Bluetooth not enabled");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void connectToService(){
        /*beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
                } catch (RemoteException e) {
                    Toast.makeText(HomeActivity.this, "Cannot start ranging, something terrible happended", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Cannot start ranging", e);
                }
            }
        });
        */
    }


    public void openDrawer(){
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }




}
