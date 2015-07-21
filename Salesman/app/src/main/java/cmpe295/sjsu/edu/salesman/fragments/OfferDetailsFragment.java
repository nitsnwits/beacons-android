package cmpe295.sjsu.edu.salesman.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cmpe295.sjsu.edu.salesman.HomeActivity;
import cmpe295.sjsu.edu.salesman.R;
import cmpe295.sjsu.edu.salesman.algorithm.Constants;
import cmpe295.sjsu.edu.salesman.pojo.Offer;
import cmpe295.sjsu.edu.salesman.pojo.OfferResponse;
import cmpe295.sjsu.edu.salesman.pojo.Point;

/**
 * Created by jijhaver on 6/25/15.
 */
public class OfferDetailsFragment  extends Fragment {

    private Map<Integer,Point> offerMap;
    private Offer offer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.offer_details_fragment, container, false);
        getActivity().getActionBar().hide();
        setOfferValues(rootView);
        ImageView button = (ImageView) rootView.findViewById(R.id.backBtn);
        Button navigatebBtn = (Button) rootView.findViewById(R.id.navigateBtn);

        // Method will be called on Back button click
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }

        });


        // Attache clickListener to navigateBtn
        navigatebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToStoreMap();
            }
        });
        return rootView;
    }

    private void setOfferValues(View rootView) {
        TextView offerTitle = (TextView) rootView.findViewById(R.id.offerTitle);
        offerTitle.setText(offer.getName());
        TextView offerDescription = (TextView) rootView.findViewById(R.id.offerDescription);
        offerDescription.setText(offer.getOfferDescription());
        TextView offerPrice = (TextView) rootView.findViewById(R.id.offerPrice);
        offerPrice.setText(offer.getOfferPrice());
        TextView discount = (TextView) rootView.findViewById(R.id.discountValue);
        discount.setText(offer.getDiscount());
        TextView originalPrice = (TextView) rootView.findViewById(R.id.originalPrice);
        originalPrice.setText(offer.getOriginalPrice());
    }

    private void navigateToStoreMap() {

        StoreMapFragment mapFragment = ((HomeActivity)(this.getActivity())).getStoreMapFragment();
        mapFragment.setPoiPoint(new Point(offer.getX(), offer.getY()));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.content_frame, mapFragment);
        ft.commit();

    }


    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Map<Integer, Point> getOfferMap() {
        return offerMap;
    }

    public void setOfferMap(Map<Integer, Point> offerMap) {
        this.offerMap = offerMap;
    }


}
