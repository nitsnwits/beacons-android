package cmpe295.sjsu.edu.salesman.pojo;

/**
 * Created by Rucha on 7/10/15.
 */
public class Product {

    private String name;
    private String hexValue;
    private int intValue;
    private int resourceId;

    public Product(String name, String hexValue, int intValue,int resourceId) {
        this.name = name;
        this.hexValue = hexValue;
        this.intValue = intValue;
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public String getHexValue() {
        return hexValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public int getResourceId() {
        return resourceId;
    }

}
