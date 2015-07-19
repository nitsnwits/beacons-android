package cmpe295.sjsu.edu.salesman.pojo;

/**
 * Created by Rucha on 7/10/15.
 */
public class Product {

    private String categoryId;
    private long created;
    private String description;
    private String image;
    private String name;
    private double price;
    private String productId;
    private String updated;

    public Product(String categoryId, long created, String description, String image, String name, double price, String productId, String updated) {
        this.categoryId = categoryId;
        this.created = created;
        this.description = description;
        this.image = image;
        this.name = name;
        this.price = price;
        this.productId = productId;
        this.updated = updated;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
