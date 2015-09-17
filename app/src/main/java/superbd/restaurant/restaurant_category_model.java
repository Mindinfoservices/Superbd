package superbd.restaurant;

/**
 * Created by lenovo on 9/8/2015.
 */
public class restaurant_category_model {
    private String restaurant_id, restaurant_image, restaurant_name;

    public restaurant_category_model(String restaurant_id, String restaurant_image, String restaurant_name)
    {
        this.restaurant_id = restaurant_id;
        this.restaurant_image = restaurant_image;
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getRestaurant_image() {
        return restaurant_image;
    }

    public void setRestaurant_image(String restaurant_image) {
        this.restaurant_image = restaurant_image;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }
}
