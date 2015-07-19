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
import cmpe295.sjsu.edu.salesman.views.OfferViewHolder;
import cmpe295.sjsu.edu.salesman.views.ProductViewHolder;

import static com.google.api.client.http.AbstractInputStreamContent.copy;

/**
 * Created by Rucha on 7/10/15.
 */
public class ProductCardAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private static final int IO_BUFFER_SIZE = 4*1024;
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
        productViewHolder.contentText.setText(product.getDescription());
try{
        in = new BufferedInputStream(new URL(product.getImage()).openStream(), IO_BUFFER_SIZE);
        final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
        out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
        copy(in, out);
        out.flush();

        final byte[] data = dataStream.toByteArray();
        BitmapFactory.Options options = new BitmapFactory.Options();

        bmp = BitmapFactory.decodeByteArray(data, 0, data.length,options);
        productViewHolder.productImage.setImageBitmap(bmp);
        } catch (IOException e) {
                     System.out.println("Could not load Bitmap from: " + product.getImage());
        }
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