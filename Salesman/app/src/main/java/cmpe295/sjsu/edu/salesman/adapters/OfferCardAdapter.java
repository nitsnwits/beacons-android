package cmpe295.sjsu.edu.salesman.adapters;

import android.graphics.Paint;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cmpe295.sjsu.edu.salesman.R;
import cmpe295.sjsu.edu.salesman.fragments.OfferDetailsFragment;
import cmpe295.sjsu.edu.salesman.pojo.Offer;
import cmpe295.sjsu.edu.salesman.utils.ImageLoadTask;
import cmpe295.sjsu.edu.salesman.views.OfferViewHolder;

/**
 * Created by jijhaver on 6/24/15.
 */
public class OfferCardAdapter extends RecyclerView.Adapter<OfferViewHolder> {

    private List<Offer> offers;
    private OnItemClickListener mListener;

    /**
     * Interface for receiving click events from cells.
     */
    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }

    public OfferCardAdapter(List<Offer> offers,OnItemClickListener listener) {
        this.offers = new ArrayList<Offer>();
        this.mListener = listener;
        this.offers.addAll(offers);
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.offer_card, viewGroup, false);

        return new OfferViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OfferViewHolder offerViewHolder,final int position) {
        Offer offer = offers.get(position);
        offerViewHolder.titleText.setText(offer.getName());
        offerViewHolder.contentText.setText("$" + offer.getOfferPrice());

        TextView t = offerViewHolder.originalPrice;
        t.setText("$" + offer.getOriginalPrice());
        t.setPaintFlags(t.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        //offerViewHolder.offerImage.setImageBitmap(offer.getResourceId());
        new ImageLoadTask(offer.getUrl(), offerViewHolder.offerImage).execute();


        offerViewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return offers.size();
    }
}
