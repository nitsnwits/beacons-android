package cmpe295.sjsu.edu.salesman.views;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cmpe295.sjsu.edu.salesman.R;

/**
 * Created by Rucha on 7/10/15.
 */
public class ProductViewHolder extends RecyclerView.ViewHolder{

    public TextView titleText;
    public TextView contentText;
    public CardView card;
    public ImageView productImage;

    public ProductViewHolder(View itemView) {
        super(itemView);
        titleText = (TextView) itemView.findViewById(R.id.productName);
        contentText = (TextView) itemView.findViewById(R.id.productValue);
        productImage = (ImageView) itemView.findViewById(R.id.productImage);
        card = (CardView) itemView;


    }

}
