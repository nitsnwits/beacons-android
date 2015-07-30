package cmpe295.sjsu.edu.salesman.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cmpe295.sjsu.edu.salesman.HomeActivity;
import cmpe295.sjsu.edu.salesman.R;
import cmpe295.sjsu.edu.salesman.RestClient;
import cmpe295.sjsu.edu.salesman.RestError;
import cmpe295.sjsu.edu.salesman.pojo.Category;
import cmpe295.sjsu.edu.salesman.pojo.Point;
import cmpe295.sjsu.edu.salesman.pojo.Product;
import cmpe295.sjsu.edu.salesman.utils.ImageLoadTask;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Rucha on 7/10/15.
 */
public class ProductDetailsFragment extends Fragment {

    private String productId;
    private Product product;
    static final ArrayList<Product> recommendationList = new ArrayList<>();
    View rootView;

//    public ArrayList<Product> getRecommendationList() {
//        return recommendationList;
//    }
//
//    public void setRecommendationList(ArrayList<Product> recommendationList) {
//        System.out.println("I am in setRecom");
//        this.recommendationList = recommendationList;
//    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.product_details_fragment, container, false);
        getActivity().getActionBar().hide();

        setProductValues(rootView);
        ImageView button = (ImageView) rootView.findViewById(R.id.backBtn);
        Button mapBtn = (Button) rootView.findViewById(R.id.mapButton);
        // Method will be called on Back button click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToStoreMap();
            }
        });
        return rootView;
    }

    private void navigateToStoreMap() {

        StoreMapFragment mapFragment = ((HomeActivity)(this.getActivity())).getStoreMapFragment();
        Category category = new Category();
        category.setCategoryId(product.getCategoryId());
        mapFragment.setPoiPoint(new Point(category.getxCoord(), category.getyCoord()));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.content_frame, mapFragment);
        ft.commit();

    }

    private void setProductValues(View rootView){

        TextView productTitle = (TextView)rootView.findViewById(R.id.productTitle);
        productTitle.setText(product.getName());
        TextView productPrice = (TextView)rootView.findViewById(R.id.productPrice);
        productPrice.setText((Float.toString(product.getPrice())));
        ImageView productImage = (ImageView)rootView.findViewById(R.id.productImage);
        new ImageLoadTask(product.getImage(), productImage).execute();
        TextView productDescription = (TextView) rootView.findViewById(R.id.productDescription);
        productDescription.setText(product.getDescription());
        getRecommendations(product.getProductId());

         System.out.println("Size is::" + recommendationList.size());
    }

    public void getRecommendations(String productId){

        RestClient.get().getRecommendations(productId, new Callback<ArrayList<Product>>() {
            @Override
            public void success(ArrayList<Product> products, Response response) {
                ScrollView sv = new ScrollView(rootView.getContext());
                LinearLayout ll = (LinearLayout)rootView.findViewById(R.id.recommendations);
                ll.setVerticalScrollBarEnabled(true);
                ll.setOrientation(LinearLayout.VERTICAL);

                System.out.println("I am in success");
                System.out.println(products.size());
                for (Product product : products) {
                    recommendationList.add(product);
                }

                System.out.println("Recommendations for you are ::" + recommendationList.size());
                for (Product p : recommendationList) {
                    System.out.println("You can buy::" + p.getName());
                    TextView tv = new TextView(rootView.getContext());
                    tv.setText(p.getName());
                    ll.addView(tv);
                }




            }

            @Override
            public void failure(RetrofitError error) {
                RestError body = (RestError) error.getBodyAs(RestError.class);
                //dynamic error handling
                if (body.errorCode == 400) {
                    Toast.makeText(getActivity(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                if (body.errorCode == 401) {
                    Toast.makeText(getActivity(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                if (body.errorCode == 404) {
                    Toast.makeText(getActivity(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                if (body.errorCode == 500) {
                    Toast.makeText(getActivity(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                if (body.errorCode == 503) {
                    Toast.makeText(getActivity(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}

