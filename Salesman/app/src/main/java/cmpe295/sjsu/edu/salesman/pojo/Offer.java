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
    private float x;
    private float y;


    public Offer(String name, String originalPrice, String url, String offerId, String offerDescription, String discount,String offerPrice,float x, float y) {
        this.name = name;
        this.originalPrice = originalPrice;

        this.url = url;
        this.offerId = offerId;
        this.offerDescription = offerDescription;
        this.discount = discount;
        this.offerPrice = offerPrice;
        this.x = x;
        this.y = y;
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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }




}
