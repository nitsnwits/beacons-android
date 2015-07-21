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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cmpe295.sjsu.edu.salesman.R;
import cmpe295.sjsu.edu.salesman.RestClient;
import cmpe295.sjsu.edu.salesman.RestError;
import cmpe295.sjsu.edu.salesman.adapters.OfferCardAdapter;
import cmpe295.sjsu.edu.salesman.pojo.Offer;
import cmpe295.sjsu.edu.salesman.pojo.OfferResponse;
import cmpe295.sjsu.edu.salesman.resetPwdResponse;
import cmpe295.sjsu.edu.salesman.utils.ImageLoadTask;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by jijhaver on 6/22/15.
 */
public class OfferFragment extends Fragment implements OfferCardAdapter.OnItemClickListener{

    ArrayList<Offer> offers = new ArrayList<>();
    private Activity activity;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity = this.getActivity();
        View rootView = inflater.inflate(R.layout.fragment_offers, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerList);
        getActivity().getActionBar().show();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        generateOffers();
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

                       // Bitmap bitmap = getBitmapFromURL(offerResponse.getProduct().getImage());
                        /*try {
                            URL url = new URL(offerResponse.getProduct().getImage());

                                HttpGet httpRequest = null;
                                httpRequest = new HttpGet(url.toURI());

                                HttpClient httpclient = new DefaultHttpClient();
                                HttpResponse httpResponse = (HttpResponse) httpclient.execute(httpRequest);

                                HttpEntity entity = httpResponse.getEntity();
                                BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
                                InputStream input = b_entity.getContent();

                                 bitmap = BitmapFactory.decodeStream(input);
                        }
                        catch(Exception ex) {
                            ex.printStackTrace();
                        }
                        */

                    offers.add(new Offer(offerResponse.getProduct().getName(), Double.toString(offerResponse.getProduct().getPrice()), Color.parseColor("#d32f2f"), offerResponse.getProduct().getImage()));
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

       /* offers.add(new Offer("Hotel Grand Cotton Duvet Set", "$29.99", Color.parseColor("#d32f2f"),R.drawable.offer1));
        offers.add(new Offer("JCPenny Portraits", "$19.99", Color.parseColor("#ff4081"),R.drawable.offer2));
        offers.add(new Offer("Sun glasses", "$9.99", Color.parseColor("#7b1fa2"),R.drawable.offer3));
        offers.add(new Offer("Eyelash Extensions", "$50", Color.parseColor("#536dfe"), R.drawable.offer4));
        offers.add(new Offer("Down Alternative Blankets", "$19.99", Color.parseColor("#388e3c"), R.drawable.offer5));
        offers.add(new Offer("3 - Piece Printed Quilt Sets", "$19.99", Color.parseColor("#ff5722"), R.drawable.offer6));*/

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
        offerDetailsFragment.setOfferId("877d2a68-2c24-491a-8a2b-6d1b07704dd0");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.content_frame, offerDetailsFragment);
        ft.commit();
    }

    private void getOfferDetailsByOfferId(String offerId){
        RestClient.get().getOfferDetails(offerId, new Callback<OfferResponse>(){
            @Override
            public void success(OfferResponse offerResponse, Response response) {
                System.out.println("----------Inside getOfferDetails Success");
            }

            @Override
            public void failure(RetrofitError error) {
                RestError body = (RestError) error.getBodyAs(RestError.class);
                //dynamic error handling
                if(body.errorCode==400){
                    Toast.makeText(getActivity().getApplicationContext(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                if(body.errorCode==401){
                    Toast.makeText(getActivity().getApplicationContext(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                if(body.errorCode==404){
                    Toast.makeText(getActivity().getApplicationContext(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                if(body.errorCode==500){
                    Toast.makeText(getActivity().getApplicationContext(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                if(body.errorCode==503){
                    Toast.makeText(getActivity().getApplicationContext(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
