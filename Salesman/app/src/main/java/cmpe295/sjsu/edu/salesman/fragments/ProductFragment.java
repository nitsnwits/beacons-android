package cmpe295.sjsu.edu.salesman.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.EditText;
import android.widget.SearchView;

import java.util.ArrayList;

import cmpe295.sjsu.edu.salesman.ProductActivity;
import cmpe295.sjsu.edu.salesman.R;
import cmpe295.sjsu.edu.salesman.adapters.ProductCardAdapter;
import cmpe295.sjsu.edu.salesman.pojo.Product;

/**
 * Created by Rucha on 7/10/15.
 */
public class ProductFragment extends Fragment implements ProductCardAdapter.OnItemClickListener{

    //get the text from serach edittext
    private EditText searchET;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        System.out.println("I am Product Fragment");
        View rootView = inflater.inflate(R.layout.fragment_products, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerList);
        getActivity().getActionBar().show();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(new ProductCardAdapter(generateProducts(), this));
        //get the search text from edittext
        searchET = (EditText) rootView.findViewById(R.id.searchET);
        return rootView;
    }



    private ArrayList<Product> generateProducts() {
        ArrayList<Product> products = new ArrayList<>();
//        products.add(new Product("Hotel Grand Cotton Duvet Set", "$29.99", Color.parseColor("#d32f2f"), R.drawable.offer1));
//        products.add(new Product("JCPenny Portraits", "$19.99", Color.parseColor("#ff4081"), R.drawable.offer2));
//        products.add(new Product("Sun glasses", "$9.99", Color.parseColor("#7b1fa2"), R.drawable.offer3));
//        products.add(new Product("Eyelash Extensions", "$50", Color.parseColor("#536dfe"), R.drawable.offer4));
//        products.add(new Product("Down Alternative Blankets", "$19.99", Color.parseColor("#388e3c"), R.drawable.offer5));
//        products.add(new Product("3 - Piece Printed Quilt Sets", "$19.99", Color.parseColor("#ff5722"), R.drawable.offer6));
        return products;
    }

    @Override
    public void onClick(View view, int position) {
        selectProduct(position);
    }

    private void selectProduct(int position) {

        ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.content_frame, productDetailsFragment);
        ft.commit();
    }

//    public void getProducts(View view){
//
//        String query = searchET.getText().toString();
//        System.out.println("User entered to search for::" + query);
//
//    }
    
}
