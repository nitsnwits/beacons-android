package cmpe295.sjsu.edu.salesman.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cmpe295.sjsu.edu.salesman.R;
import cmpe295.sjsu.edu.salesman.adapters.OfferCardAdapter;

/**
 * Created by jijhaver on 6/25/15.
 */
public class OfferDetailsFragment  extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.offer_details_fragment, container, false);
        getActivity().getActionBar().hide();
        Button button = (Button) rootView.findViewById(R.id.backBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return rootView;
    }



}
