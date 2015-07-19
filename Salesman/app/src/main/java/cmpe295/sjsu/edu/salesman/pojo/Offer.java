package cmpe295.sjsu.edu.salesman.pojo;

import android.graphics.Bitmap;

/**
 * Created by jijhaver on 6/24/15.
 */
public class Offer {

    private String name;
    private String price;
    private int intValue;
    private String url;




    public Offer(String name, String price, int intValue, String url) {
        this.name = name;
        this.price = price;
        this.intValue = intValue;
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    public String getName() {
        return name;
    }


    public int getIntValue() {
        return intValue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
