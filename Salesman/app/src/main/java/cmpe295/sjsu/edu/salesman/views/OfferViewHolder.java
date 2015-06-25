package cmpe295.sjsu.edu.salesman.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cmpe295.sjsu.edu.salesman.R;
import cmpe295.sjsu.edu.salesman.fragments.OfferDetailsFragment;

/**
 * Created by jijhaver on 6/24/15.
 */
public class OfferViewHolder extends RecyclerView.ViewHolder{

    public TextView titleText;
    public TextView contentText;
    public CardView card;
    public ImageView offerImage;

    public OfferViewHolder(View itemView) {
        super(itemView);
        titleText = (TextView) itemView.findViewById(R.id.name);
        contentText = (TextView) itemView.findViewById(R.id.hexValue);
        offerImage = (ImageView) itemView.findViewById(R.id.offerImage);
        card = (CardView) itemView;


    }


}
