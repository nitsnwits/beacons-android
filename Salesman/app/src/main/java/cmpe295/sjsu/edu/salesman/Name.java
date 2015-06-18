package cmpe295.sjsu.edu.salesman;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rucha on 6/17/15.
 */
public class Name {
    @SerializedName("first")
    @Expose
    public String first;

    @SerializedName("last")
    @Expose
    public String last;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
