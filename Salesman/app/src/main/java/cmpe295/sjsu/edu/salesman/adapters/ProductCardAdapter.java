package cmpe295.sjsu.edu.salesman.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cmpe295.sjsu.edu.salesman.R;
import cmpe295.sjsu.edu.salesman.pojo.Offer;
import cmpe295.sjsu.edu.salesman.pojo.Product;
import cmpe295.sjsu.edu.salesman.views.OfferViewHolder;
import cmpe295.sjsu.edu.salesman.views.ProductViewHolder;

/**
 * Created by Rucha on 7/10/15.
 */
public class ProductCardAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private List<Product> products;
    private OnItemClickListener mListener;

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
        productViewHolder.contentText.setText(product.getHexValue());
        productViewHolder.productImage.setImageResource(product.getResourceId());


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
