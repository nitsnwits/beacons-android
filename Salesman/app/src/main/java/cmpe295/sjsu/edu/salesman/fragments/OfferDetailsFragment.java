package cmpe295.sjsu.edu.salesman.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cmpe295.sjsu.edu.salesman.HomeActivity;
import cmpe295.sjsu.edu.salesman.R;
import cmpe295.sjsu.edu.salesman.pojo.Point;

/**
 * Created by jijhaver on 6/25/15.
 */
public class OfferDetailsFragment  extends Fragment {

    private Map<Integer,Point> offerMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.offer_details_fragment, container, false);
        getActivity().getActionBar().hide();
        Button button = (Button) rootView.findViewById(R.id.backBtn);
        Button navigatebBtn = (Button) rootView.findViewById(R.id.navigateBtn);

        // Method will be called on Back button click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        // Attache clickListener to navigateBtn
        navigatebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               navigateToStoreMap();
            }
        });
        return rootView;
    }

    private void navigateToStoreMap() {

        StoreMapFragment mapFragment = ((HomeActivity)(this.getActivity())).getStoreMapFragment();
        mapFragment.addLocationMarker(4d,3d); //Added by Jinali
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.content_frame, mapFragment);
        ft.commit();

    }

    private void initializeOfferMap(){
        offerMap = new HashMap<>();
        offerMap.put(1,new Point(1d,3d));

    }

    private Point getRandomOfferPoint() {
        int offerId = new Random().nextInt(3);
        return  offerMap.get(offerId);
    }



}
