package superbd.restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by lenovo on 9/2/2015.
 */
public class Reservation_list_adapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    String[] restaurant_name,restaurant_address,people_count,time;

    public Reservation_list_adapter(String[] restaurant_name,String[] restaurant_address,String[] people_count,String[] time,Context context)
    {
        this.restaurant_name = restaurant_name;
        this.restaurant_address = restaurant_address;
        this.people_count = people_count;
        this.time = time;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return restaurant_name.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = null;

        if(convertView == null)
            convertView = inflater.inflate(R.layout.reservation_list_item,null);
        return convertView;
    }
}
