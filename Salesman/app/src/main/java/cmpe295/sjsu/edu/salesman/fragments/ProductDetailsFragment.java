package cmpe295.sjsu.edu.salesman.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cmpe295.sjsu.edu.salesman.R;

/**
 * Created by Rucha on 7/10/15.
 */
public class ProductDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.product_details_fragment, container, false);
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

