package superbd.restaurant;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class available_restaurants extends ActionBarActivity {
    ListView list_available_restaurant;
    String[] restaurant_name,restaurant_location, restaurant_image;
    available_restaurants_adapter my_adapter;
    Context context;
    private Toolbar toolbar;
    String[] titles = {"Home","Discover","Reservations","Favorites","Settings"};
    int[] icons = {R.drawable.ic_home,R.drawable.ic_search,R.drawable.ic_reservation,R.drawable.ic_fav,R.drawable.ic_settings,};

    String name = "Twinkle Mishra";
    String email = "twinklemishra679@gmail.com";
    int pic = R.drawable.profile;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    ActionBar actionBar;
    Intent i;

    String url_for_image = "http://demo10.mindinfoservices.com/superb/uploads/";
    String previous_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_restaurants);

        context = this;
        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        list_available_restaurant = (ListView) findViewById(R.id.list_available_restaurants);

        i = getIntent();
        previous_activity = i.getStringExtra("From_Activity");

        if(previous_activity.equals("restaurant_chooser"))
        {
            //get the number of available restaurants
            int no_of_restaurants = city.available_restaurants.size();

            //Specify the size of arrays
            restaurant_name = new String[no_of_restaurants];
            restaurant_image = new String[no_of_restaurants];

            //run a loop for extracting the values from model class
            for(int i = 0; i < no_of_restaurants; i++)
            {
                restaurant_name[i] = city.available_restaurants.get(0).getRestaurant_name();
                restaurant_image[i] = url_for_image + city.available_restaurants.get(0).getRestaurant_image();

            }

            //keep the location as default
            restaurant_location = new String[]{"TORSGADE 8, Norreport","TORSGADE 8, Norreport","TORSGADE 8, Norreport","TORSGADE 8, Norreport"};

            //call the adapter constructor
            my_adapter = new available_restaurants_adapter(this,restaurant_name,restaurant_location,restaurant_image);

            //set the adapter
            list_available_restaurant.setAdapter(my_adapter);

        }
        else if(previous_activity.equals("keyword_search"))
        {
            //get the number of available restaurants
            int no_of_restaurants = city.available_restaurant_in_category.size();

            //Specify the size of arrays
            restaurant_name = new String[no_of_restaurants];
            restaurant_image = new String[no_of_restaurants];

            //run a loop for extracting the values from model class
            for(int i = 0; i < no_of_restaurants; i++)
            {
                restaurant_name[i] = city.available_restaurant_in_category.get(0).getRestaurant_name();
                restaurant_image[i] = url_for_image + city.available_restaurant_in_category.get(0).getRestaurant_image();
            }

            //keep the location as default
            restaurant_location = new String[]{"TORSGADE 8, Norreport","TORSGADE 8, Norreport","TORSGADE 8, Norreport","TORSGADE 8, Norreport"};

            //call the adapter constructor
            my_adapter = new available_restaurants_adapter(this,restaurant_name,restaurant_location,restaurant_image);

            //set the adapter
            list_available_restaurant.setAdapter(my_adapter);
        }

        list_available_restaurant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i  = new Intent(available_restaurants.this,table_booked.class);
                startActivity(i);
            }
        });

        setSupportActionBar(toolbar);
        //View custom_view = getLayoutInflater().inflate(R.layout.choose_city_custom_action_bar,null);
        //toolbar.addView(custom_view);
        toolbar.setNavigationIcon(R.drawable.ic_navigation);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        adapter = new MyAdapter(titles, icons, name, email, pic, context);
        mRecyclerView.setAdapter(adapter);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        drawer = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open_drawer, R.string.close_drawer)
        {
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        final GestureDetector mGestureDetector = new GestureDetector(available_restaurants.this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }
        });


        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());

                if(child != null && mGestureDetector.onTouchEvent(motionEvent))
                {
                    drawer.closeDrawers();

                    switch (recyclerView.getChildPosition(child)){
                        case 1:
                            Intent i = new Intent(available_restaurants.this,about_viewpager.class);
                            startActivity(i);
                            break;

                        case 2:
                            i = new Intent(available_restaurants.this,keyword_search.class);
                            //startActivity(i);
                            Toast.makeText(getApplicationContext(),"Yet to Come!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 3:
                            i = new Intent(available_restaurants.this,Reservation_list.class);
                            //startActivity(i);
                            Toast.makeText(getApplicationContext(),"Yet to Come!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 4:
                            Toast.makeText(getApplicationContext(),"Yet to Come!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 5:
                            Toast.makeText(getApplication(),"Yet to Come!!",Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
    }
}
