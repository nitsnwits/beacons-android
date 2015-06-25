package cmpe295.sjsu.edu.salesman.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cmpe295.sjsu.edu.salesman.R;
import cmpe295.sjsu.edu.salesman.adapters.OfferCardAdapter;
import cmpe295.sjsu.edu.salesman.pojo.Offer;

/**
 * Created by jijhaver on 6/22/15.
 */
public class OfferFragment extends Fragment implements OfferCardAdapter.OnItemClickListener{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_offers, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerList);
        getActivity().getActionBar().show();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(new OfferCardAdapter(generateOffers(),this));
        return rootView;
    }

    private ArrayList<Offer> generateOffers() {
        ArrayList<Offer> offers = new ArrayList<>();
        offers.add(new Offer("Hotel Grand Cotton Duvet Set", "$29.99", Color.parseColor("#d32f2f"),R.drawable.offer1));
        offers.add(new Offer("JCPenny Portraits", "$19.99", Color.parseColor("#ff4081"),R.drawable.offer2));
        offers.add(new Offer("Sun glasses", "$9.99", Color.parseColor("#7b1fa2"),R.drawable.offer3));
        offers.add(new Offer("Eyelash Extensions", "$50", Color.parseColor("#536dfe"), R.drawable.offer4));
        offers.add(new Offer("Down Alternative Blankets", "$19.99", Color.parseColor("#388e3c"), R.drawable.offer5));
        offers.add(new Offer("3 - Piece Printed Quilt Sets", "$19.99", Color.parseColor("#ff5722"), R.drawable.offer6));
        return offers;
    }

    @Override
    public void onClick(View view, int position) {
        selectOffer(position);
    }

    private void selectOffer(int position) {

        OfferDetailsFragment offerDetailsFragment = new OfferDetailsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.content_frame, offerDetailsFragment);
        ft.commit();
    }
}
