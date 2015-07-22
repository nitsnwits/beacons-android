package cmpe295.sjsu.edu.salesman.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cmpe295.sjsu.edu.salesman.R;
import cmpe295.sjsu.edu.salesman.pojo.Offer;
import cmpe295.sjsu.edu.salesman.pojo.Product;
import cmpe295.sjsu.edu.salesman.utils.ImageLoadTask;
import cmpe295.sjsu.edu.salesman.views.OfferViewHolder;
import cmpe295.sjsu.edu.salesman.views.ProductViewHolder;

import static com.google.api.client.http.AbstractInputStreamContent.copy;

/**
 * Created by Rucha on 7/10/15.
 */
public class ProductCardAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    //adding for recommendation
    //private ArrayList<Product> recommendationList;

    private List<Product> products;
    private OnItemClickListener mListener;
    Bitmap bmp = null;
    InputStream in = null;
    BufferedOutputStream out = null;

    /**
     * Interface for receiving click events from cells.
     */
    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }

    public ProductCardAdapter(List<Product> products,OnItemClickListener listener) {
        this.products = new ArrayList<Product>();
        this.mListener = listener;
        this.products.addAll(products);
       // this.recommendationList.addAll(recommendationList);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.product_card, viewGroup, false);

        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder productViewHolder,final int position) {
        Product product = products.get(position);
        productViewHolder.titleText.setText(product.getName());
        productViewHolder.contentText.setText(Float.toString(product.getPrice()));

        new ImageLoadTask(product.getImage(), productViewHolder.productImage).execute();

        productViewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view, position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
