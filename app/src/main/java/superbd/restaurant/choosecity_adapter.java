package superbd.restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Twinkle on 8/21/2015.
 */
public class choosecity_adapter extends BaseAdapter {


    Context context;
    String[] city_names;
    String[] city_id;
    LayoutInflater layoutInflater;

    public choosecity_adapter(Context context, String[] city_names, String[] city_id) {
        this.context = context;
        this.city_names = city_names;
        this.city_id = city_id;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return city_names.length;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;


        if (view == null) {
            view = layoutInflater.inflate(R.layout.choosecity_list_item, parent, false);
        }
        TextView names = (TextView) view.findViewById(R.id.city_name);
        final ImageView selected = (ImageView) view.findViewById(R.id.city_selected);
        //names.setText("Twinkle");


        names.setText(city_names[position]);

        names.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected.getVisibility() == View.VISIBLE) {
                    selected.setVisibility(View.GONE);
                    city.selected_cities.remove(new city_model(city_id[position], city_names[position]));
                }
                else if(selected.getVisibility() == View.GONE) {
                    selected.setVisibility(View.VISIBLE);
                    city.selected_cities.add(new city_model(city_id[position],city_names[position]));
                }
            }
        });
        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do nothing
            }
        });

        return view;
    }
}
