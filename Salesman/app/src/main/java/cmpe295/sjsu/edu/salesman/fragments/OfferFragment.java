package cmpe295.sjsu.edu.salesman.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cmpe295.sjsu.edu.salesman.MyApplication;
import cmpe295.sjsu.edu.salesman.R;
import cmpe295.sjsu.edu.salesman.RestClient;
import cmpe295.sjsu.edu.salesman.RestError;
import cmpe295.sjsu.edu.salesman.adapters.OfferCardAdapter;
import cmpe295.sjsu.edu.salesman.pojo.Offer;
import cmpe295.sjsu.edu.salesman.pojo.OfferResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by jijhaver on 6/22/15.
 */
public class OfferFragment extends Fragment implements OfferCardAdapter.OnItemClickListener{

    ArrayList<Offer> offers;
    private OfferResponse offerDetails;
    private Activity activity;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        offers = MyApplication.getOffers();
        activity = this.getActivity();
        View rootView = inflater.inflate(R.layout.fragment_offers, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerList);
        getActivity().getActionBar().show();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        if (offers.isEmpty()) {
            generateOffers();
        }else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run(){
                    setOffersInList(offers);
                }
            });
        }
        return rootView;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<Offer> generateOffers() {


        RestClient.get().getOffers(new Callback<ArrayList<OfferResponse>>() {
            @Override
            public void success(ArrayList<OfferResponse> offerResponses, Response response) {
                System.out.println("-----Inside getOffers success--------" + offerResponses.size());
                for (OfferResponse offerResponse : offerResponses) {



                   // offers.add(new Offer(offerResponse.getProduct().getName(), Double.toString(offerResponse.getProduct().getPrice()), Color.parseColor("#d32f2f"), offerResponse.getProduct().getImage()));
                    offers.add(new Offer(offerResponse.getProduct().getName(), Float.toString(offerResponse.getProduct().getPrice()), offerResponse.getProduct().getImage(), offerResponse.getOfferId(), offerResponse.getProduct().getDescription(), offerResponse.getDiscount(), Float.toString(offerResponse.getOfferPrice()),offerResponse.getCategory().getxCoord(),offerResponse.getCategory().getyCoord()));
                }

                // Note that results are not delivered on UI thread.
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setOffersInList(offers);
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("------------Inside Failure-----------");
                RestError body = (RestError) error.getBodyAs(RestError.class);
                //dynamic error handling
                if (body.errorCode == 400) {
                    Toast.makeText(getActivity().getApplicationContext(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                if (body.errorCode == 401) {
                    Toast.makeText(getActivity().getApplicationContext(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                if (body.errorCode == 404) {
                    Toast.makeText(getActivity().getApplicationContext(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                if (body.errorCode == 500) {
                    Toast.makeText(getActivity().getApplicationContext(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                if (body.errorCode == 503) {
                    Toast.makeText(getActivity().getApplicationContext(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        return offers;
    }

    private void setOffersInList(ArrayList<Offer> offers) {
        recyclerView.setAdapter(new OfferCardAdapter(offers, this));
    }


    @Override
    public void onClick(View view, int position) {
        selectOffer(position);
    }

    private void selectOffer(int position) {

        OfferDetailsFragment offerDetailsFragment = new OfferDetailsFragment();
        Offer selectedOffer = offers.get(position);
        System.out.println("Selected Offer______"+selectedOffer.getOfferId());
        offerDetailsFragment.setOffer(selectedOffer);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.content_frame, offerDetailsFragment);
        ft.commit();
    }

}
