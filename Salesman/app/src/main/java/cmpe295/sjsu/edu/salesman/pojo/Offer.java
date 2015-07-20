package cmpe295.sjsu.edu.salesman.pojo;

import android.graphics.Bitmap;

/**
 * Created by jijhaver on 6/24/15.
 */
public class Offer {

    private String name;
    private String originalPrice;
    private String url;
    private String offerId;
    private String offerDescription;
    private String discount;
    private String offerPrice;


    public Offer(String name, String originalPrice, String url, String offerId, String offerDescription, String discount,String offerPrice) {
        this.name = name;
        this.originalPrice = originalPrice;

        this.url = url;
        this.offerId = offerId;
        this.offerDescription = offerDescription;
        this.discount = discount;
        this.offerPrice = offerPrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }



    public String getName() {
        return name;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }


    public Offer(String discount) {
        this.discount = discount;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }



}
