package superbd.restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by lenovo on 8/28/2015.
 */
public class GridViewAdapter extends BaseAdapter {

    int[] keyword_images;
    Context context;
    private static LayoutInflater inflater;

    public GridViewAdapter(int[] keyword_images, Context context) {
        this.keyword_images = keyword_images;
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return keyword_images.length;
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
        {
            convertView = inflater.inflate(R.layout.gridview_item,null);
        }

        ImageView images = (ImageView) convertView.findViewById(R.id.images_gridview);
        images.setImageResource(keyword_images[position]);

        return convertView;
    }
}
