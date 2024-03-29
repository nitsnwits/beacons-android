package cmpe295.sjsu.edu.salesman.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cmpe295.sjsu.edu.salesman.ProductActivity;
import cmpe295.sjsu.edu.salesman.R;
import cmpe295.sjsu.edu.salesman.RestClient;
import cmpe295.sjsu.edu.salesman.RestError;
import cmpe295.sjsu.edu.salesman.adapters.ProductCardAdapter;
import cmpe295.sjsu.edu.salesman.pojo.Product;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Rucha on 7/10/15.
 */
public class ProductFragment extends Fragment implements ProductCardAdapter.OnItemClickListener{

    //get the text from serach edittext
    private EditText searchET;
    SharedPreferences userSharedpreferences ;
    private Activity activity;
    String userId;
    String accessToken;
    final List<Product> productList = new ArrayList<Product>();
    RecyclerView recyclerView;
    final ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();

    //adding part for the recommendations
    //ArrayList<Product> recommendationList= new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Get the access token for user
        activity = this.getActivity();
        userSharedpreferences = this.getActivity().getSharedPreferences("userPrefs", 0);
        userId = userSharedpreferences.getString("userId","default");
        accessToken = userSharedpreferences.getString("accessToken", "default");
        final List<Product> products = new ArrayList<Product>();
        System.out.println("I am Product Fragment");
        View rootView = inflater.inflate(R.layout.fragment_products, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerList);
        getActivity().getActionBar().show();

        //get the search text from edittext
        searchET = (EditText) rootView.findViewById(R.id.searchET);

        ImageButton searchProductButton = (ImageButton) rootView.findViewById(R.id.searchProductButton);
        // Method will be called on Back button click
        searchProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProducts();
            }
        });

        System.out.println("Product list size is ::" + productList.size());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(new ProductCardAdapter(generateProducts(),this));

        return rootView;
    }


    private List<Product> generateProducts() {
      //  System.out.println("I am in generate Products");
        System.out.println(productList.size());
        return productList;
    }

    @Override
    public void onClick(View view, int position) {
        selectProduct(position);
    }

    private void selectProduct(int position) {
        Product selectedProduct = productList.get(position);
        System.out.println("Selected product______" + selectedProduct.getProductId());
        productDetailsFragment.setProduct(selectedProduct);

        //Get the recommendations here
       // getRecommendations(selectedProduct.getProductId());

       // System.out.println("Recommendation size ::" + recommendationList.size());
       // productDetailsFragment.setRecommendationList(recommendationList);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.content_frame, productDetailsFragment);
        ft.commit();
    }



    public List<Product> getProducts(){
        final ProductCardAdapter.OnItemClickListener mListener = null;
        productList.clear();//so that previous products are not present on the screen
        String query = searchET.getText().toString();
        System.out.println("User entered to search for::" + query);
        System.out.println("User access Token is ::" + accessToken);
        final ProductCardAdapter.OnItemClickListener finalMListener = mListener;
        RestClient.get().searchProduct(accessToken, query, new Callback<ArrayList<Product>>() {
            @Override
            public void success(ArrayList<Product> products, Response response) {

                for (Product product : products) {
                    // Toast.makeText(getActivity(), product.getName(), Toast.LENGTH_SHORT).show();
                    productList.add(product);
                }
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(llm);
                // Note that results are not delivered on UI thread.
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setProductsInList(productList);
                    }
                });
            }


            @Override
            public void failure(RetrofitError error) {
                // Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
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
     return productList;
    }

    private void setProductsInList(List<Product> productList) {
        recyclerView.setAdapter(new ProductCardAdapter(productList, this));
    }

//    public void getRecommendations(String productId){
//
//        RestClient.get().getRecommendations(productId, new Callback<ArrayList<Product>>() {
//            @Override
//            public void success(ArrayList<Product> products, Response response) {
//                System.out.println("I am in success");
//                System.out.println(products.size());
//                for(Product product : products ){
//                    recommendationList.add(product);
//                }
//
//                System.out.println("Recommendations for you are ::" + recommendationList.size());
//                for(Product p : recommendationList){
//                    System.out.println("You can buy::" + p.getName());
//                }
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        productDetailsFragment.setRecommendationList(recommendationList);
//                    }
//                });
//
//
//
//
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                RestError body = (RestError) error.getBodyAs(RestError.class);
//                //dynamic error handling
//                if (body.errorCode == 400) {
//                    Toast.makeText(getActivity(), body.getErrorMessage(),
//                            Toast.LENGTH_SHORT).show();
//                }
//                if (body.errorCode == 401) {
//                    Toast.makeText(getActivity(), body.getErrorMessage(),
//                            Toast.LENGTH_SHORT).show();
//                }
//                if (body.errorCode == 404) {
//                    Toast.makeText(getActivity(), body.getErrorMessage(),
//                            Toast.LENGTH_SHORT).show();
//                }
//                if (body.errorCode == 500) {
//                    Toast.makeText(getActivity(), body.getErrorMessage(),
//                            Toast.LENGTH_SHORT).show();
//                }
//                if (body.errorCode == 503) {
//                    Toast.makeText(getActivity(), body.getErrorMessage(),
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//
//    }


}
