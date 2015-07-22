package cmpe295.sjsu.edu.salesman.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cmpe295.sjsu.edu.salesman.R;
import cmpe295.sjsu.edu.salesman.pojo.Product;
import cmpe295.sjsu.edu.salesman.utils.ImageLoadTask;

/**
 * Created by Rucha on 7/10/15.
 */
public class ProductDetailsFragment extends Fragment {

    private String productId;
    private Product product;
    private ArrayList<Product> recommendationList;

    public ArrayList<Product> getRecommendationList() {
        return recommendationList;
    }

    public void setRecommendationList(ArrayList<Product> recommendationList) {
        this.recommendationList = recommendationList;
    }

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

        View rootView = inflater.inflate(R.layout.product_details_fragment, container, false);
        getActivity().getActionBar().hide();
        setProductValues(rootView);
        ImageView button = (ImageView) rootView.findViewById(R.id.backBtn);
        // Method will be called on Back button click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return rootView;
    }


    private void setProductValues(View rootView){
        System.out.println("Recommendations in product details::" + recommendationList.size());
        TextView productTitle = (TextView)rootView.findViewById(R.id.productTitle);
        productTitle.setText(product.getName());
        TextView productPrice = (TextView)rootView.findViewById(R.id.productPrice);
        productPrice.setText((Float.toString(product.getPrice())));
        ImageView productImage = (ImageView)rootView.findViewById(R.id.productImage);
        new ImageLoadTask(product.getImage(), productImage).execute();
        TextView productDescription = (TextView) rootView.findViewById(R.id.productDescription);
        productDescription.setText(product.getDescription());
    }
}

