package superbd.restaurant;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lenovo on 8/17/2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0; //declaring variable to understand which view is being worked on

    //if the view under inflation and population is header or item
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[]; //String array to store the passed titles value from restaurant_choose activity
    private int mIcons[]; //int array to store the passed icons

    private String name;
    private int profile;
    private String email;
    Context context;

    //creating a view holder which extends the Recyclerview view holder

    public static class ViewHolder extends RecyclerView.ViewHolder{
        int Holderid;
        TextView textView;
        ImageView imageView;
        ImageView profile;
        TextView name;
        TextView email;
        Context contxt;

        public ViewHolder(View itemView, int ViewType, Context c) {

            super(itemView);
            contxt = c;
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(contxt,"item clicked is: "+ getPosition(),Toast.LENGTH_SHORT).show();
                }
            });

            //here we set the appropriate view in accordance with the view type as passed

            if(ViewType == TYPE_ITEM)
            {
                textView = (TextView) itemView.findViewById(R.id.rowText);
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                Holderid = 1;
            }
            else
            {
                name = (TextView) itemView.findViewById(R.id.name);
                email = (TextView) itemView.findViewById(R.id.email);
                profile = (ImageView) itemView.findViewById(R.id.circleView);
                Holderid = 0;
            }
        }
    }

    MyAdapter(String Titles[], int Icons[],String Name, String Email, int Profile, Context passContext)
    {
        mNavTitles = Titles;
        mIcons = Icons;
        name = Name;
        email = Email;
        profile = Profile;
        this.context = passContext;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_ITEM){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
            ViewHolder vhItem = new ViewHolder(v,viewType,context);
            return vhItem;
        }
        else if(viewType == TYPE_HEADER)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header,parent,false);
            ViewHolder vhHeader = new ViewHolder(v,viewType,context);
            return vhHeader;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

        if(holder.Holderid == 1)
        {
            holder.textView.setText(mNavTitles[position - 1]);
            holder.imageView.setImageResource(mIcons[position - 1]);
        }
        else
        {
            holder.profile.setImageResource(profile);
            holder.name.setText(name);
            holder.email.setText(email);
        }
    }

    @Override
    public int getItemCount() {
        return mNavTitles.length+1;
    }

    @Override    public int getItemViewType(int position){
        if(isPositionHeader(position))
            return  TYPE_HEADER;
        return TYPE_ITEM;
    }
    private boolean isPositionHeader(int position){ return position == 0; }
}
