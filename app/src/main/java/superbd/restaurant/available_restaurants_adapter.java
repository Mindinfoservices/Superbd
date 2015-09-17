package superbd.restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Twinkle on 8/24/2015.
 */
public class available_restaurants_adapter extends BaseAdapter {


    Context context;
    String[] restaurant_names,restaurant_locations, restaurant_images;
    LayoutInflater layoutInflater;

    public available_restaurants_adapter(Context context, String[] restaurant_name, String[] restaurant_location,String[] restaurant_image) {
        this.context = context;
        this.restaurant_names = restaurant_name;
        this.restaurant_locations = restaurant_location;
        this.restaurant_images = restaurant_image;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return restaurant_names.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;


        if (view == null) {
            view = layoutInflater.inflate(R.layout.available_restaurants_list_item, parent, false);
        }

        TextView restaurant_name = (TextView) view.findViewById(R.id.restaurant_name);
        ImageView restaurant_image = (ImageView) view.findViewById(R.id.restaurant_image);
        TextView restaurant_location = (TextView) view.findViewById(R.id.restaurant_location);

        restaurant_name.setText(restaurant_names[position]);
        restaurant_location.setText(restaurant_locations[position]);
        //restaurant_image.setImageResource(restaurant_images[position]);
        //Picasso.with(context).load(restaurant_images[position]).placeholder(R.drawable.city_item1).into(restaurant_image);
        Picasso.with(context).load("http://demo10.mindinfoservices.com/superb/uploads/resto1.jpg").into(restaurant_image);



        return view;
    }
}
