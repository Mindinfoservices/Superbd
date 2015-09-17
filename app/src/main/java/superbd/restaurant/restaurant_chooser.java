package superbd.restaurant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Calendar;


public class restaurant_chooser extends ActionBarActivity {

    //for navigation drawer
    private Toolbar toolbar;
    String[] titles = {"Home","Discover","Reservations","Favorites","Settings"};
    int[] icons = {R.drawable.ic_home,R.drawable.ic_search,R.drawable.ic_reservation,R.drawable.ic_fav,R.drawable.ic_settings,};
    String name, email;
    int pic;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;

    //for activity
    Context context;
    ActionBar actionBar;

    //declaring widgets
    Button select_city,continue_button;
    ImageButton invite_friends;
    Button increase_date, decrease_date,increase_time, decrease_time, increase_people, decrease_people;
    ImageView city_image;
    TextView today,current_date,current_hour,current_minute, people_count;

    //objects
    final Calendar calendar = Calendar.getInstance();
    ProgressDialog pDialog;
    RestInteraction restInteraction;
    SharedPreferences sharedPreferences;

    //data for sending
    String date, time, people;
    String[] city_ids;

    //url string for server connectivity
    private static final String url_for_city_image = "http://demo10.mindinfoservices.com/superb/call-city.php?";
    private static final String url_for_available_restaurants = "http://demo10.mindinfoservices.com/superb/restobycityid.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_chooser);


        //object initialization
        context = this;
        restInteraction = RestInteraction.getInstance(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //finding the widgets
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        continue_button = (Button) findViewById(R.id.continue_button);
        increase_date = (Button) findViewById(R.id.increase_date);
        decrease_date = (Button) findViewById(R.id.decrease_date);
        increase_time = (Button) findViewById(R.id.increase_time);
        decrease_time = (Button) findViewById(R.id.decrease_time);
        select_city = (Button) findViewById(R.id.select_city);
        invite_friends = (ImageButton) findViewById(R.id.invite_friends);
        increase_people = (Button) findViewById(R.id.increase_people);
        decrease_people = (Button) findViewById(R.id.decrease_people);


        today = (TextView) findViewById(R.id.today);
        current_date = (TextView) findViewById(R.id.current_date);
        current_hour = (TextView) findViewById(R.id.current_hour);
        current_minute = (TextView) findViewById(R.id.current_minute);
        people_count = (TextView) findViewById(R.id.people_count);

        city_image = (ImageView) findViewById(R.id.city_image);

         //Setting current_date on textView
        String today_date = String.valueOf((calendar.get(Calendar.DATE)));
        String today_month = String.valueOf((calendar.get(Calendar.MONTH)) + 1);
        if(today_month.equals("1"))
            today_month = "January";
        else if(today_month.equals("2"))
            today_month = "February";
        else if(today_month.equals("3"))
            today_month = "March";
        else if(today_month.equals("4"))
            today_month = "April";
        else if(today_month.equals("5"))
            today_month = "May";
        else if(today_month.equals("6"))
            today_month = "June";
        else if(today_month.equals("7"))
            today_month = "July";
        else if(today_month.equals("8"))
            today_month = "August";
        else if(today_month.equals("9"))
            today_month = "September";
        else if(today_month.equals("10"))
            today_month = "October";
        else if(today_month.equals("11"))
            today_month = "November";
        else if(today_month.equals("12"))
            today_month = "December";
        String today_year = String.valueOf((calendar.get(Calendar.YEAR)));
        String complete_date = today_date + ", " + today_month + ", " + today_year;
        current_date.setText(complete_date);

        //setting the current time on textview
        int now_hour = calendar.get(Calendar.HOUR);
        int now_minute = calendar.get(Calendar.MINUTE);
        if(now_minute != 30 || now_minute != 00)
            now_minute = 30;
        current_hour.setText(String.valueOf(now_hour));
        current_minute.setText(String.valueOf(now_minute));

        //loading the city image
        //new getCityImage().execute();

        //initializing the navigation drawer
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_navigation);

        mRecyclerView.setHasFixedSize(true);
        name = sharedPreferences.getString("username","Guest");
        email = "";
        pic = R.drawable.profile1;
        adapter = new MyAdapter(titles, icons, name, email, pic, context);
        mRecyclerView.setAdapter(adapter);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        //toggling with action bar
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
        final GestureDetector mGestureDetector = new GestureDetector(restaurant_chooser.this, new GestureDetector.SimpleOnGestureListener(){
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
                         Intent i = new Intent(restaurant_chooser.this,about_viewpager.class);
                         startActivity(i);
                         break;

                     case 2:
                         i = new Intent(restaurant_chooser.this,keyword_search.class);
                         //startActivity(i);
                         Toast.makeText(getApplicationContext(),"Yet to Come!!",Toast.LENGTH_SHORT).show();
                         break;

                     case 3:
                         i = new Intent(restaurant_chooser.this,Reservation_list.class);
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

     //handling the click listeners of button

    select_city.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             city.selected_cities.clear();
             Intent i = new Intent(restaurant_chooser.this,choose_city.class);
             startActivity(i);
        }
     });

    continue_button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             new getAvailableRestaurants().execute();

        }
     });

    increase_date.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String get_date = current_date.getText().toString();
            today.setText("Tomorrow");
            String tomorrow_date = String.valueOf((calendar.get(Calendar.DATE)) + 1);
            String today_month = String.valueOf((calendar.get(Calendar.MONTH)) + 1);
            if(today_month.equals("1"))
                today_month = "January";
            else if(today_month.equals("2"))
                today_month = "February";
            else if(today_month.equals("3"))
                today_month = "March";
            else if(today_month.equals("4"))
                today_month = "April";
            else if(today_month.equals("5"))
                today_month = "May";
            else if(today_month.equals("6"))
                today_month = "June";
            else if(today_month.equals("7"))
                today_month = "July";
            else if(today_month.equals("8"))
                today_month = "August";
            else if(today_month.equals("9"))
                today_month = "September";
            else if(today_month.equals("10"))
                today_month = "October";
            else if(today_month.equals("11"))
                today_month = "November";
            else if(today_month.equals("12"))
                today_month = "December";
            String today_year = String.valueOf((calendar.get(Calendar.YEAR)));
            String complete_date = tomorrow_date + ", " + today_month + ", " + today_year;
            current_date.setText(complete_date);
                   }
    });

     decrease_date.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String current = current_date.getText().toString();
             String today_date = String.valueOf((calendar.get(Calendar.DATE)));
             String today_month = String.valueOf((calendar.get(Calendar.MONTH)) + 1);
             if(today_month.equals("1"))
                 today_month = "January";
             else if(today_month.equals("2"))
                 today_month = "February";
             else if(today_month.equals("3"))
                 today_month = "March";
             else if(today_month.equals("4"))
                 today_month = "April";
             else if(today_month.equals("5"))
                 today_month = "May";
             else if(today_month.equals("6"))
                 today_month = "June";
             else if(today_month.equals("7"))
                 today_month = "July";
             else if(today_month.equals("8"))
                 today_month = "August";
             else if(today_month.equals("9"))
                 today_month = "September";
             else if(today_month.equals("10"))
                 today_month = "October";
             else if(today_month.equals("11"))
                 today_month = "November";
             else if(today_month.equals("12"))
                 today_month = "December";
             String today_year = String.valueOf((calendar.get(Calendar.YEAR)));
             String complete_date = today_date + ", " + today_month + ", " + today_year;
             if(complete_date.equals(current) || (today.getText().toString().equals("Today")))
             {

             }
             else
             {
                 current_date.setText(complete_date);
                 today.setText("Today");
             }
         }
     });

     increase_time.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             int get_hour = Integer.parseInt(current_hour.getText().toString());
             int get_minute = Integer.parseInt(current_minute.getText().toString());

             if(get_hour == 24) {
                 current_hour.setText("01");
                 current_minute.setText("00");
             }
             else {
                 if (get_minute == 30) {
                     current_hour.setText(String.valueOf(get_hour + 1));
                     current_minute.setText("00");
                 } else if (get_minute == 00) {

                     current_hour.setText(String.valueOf(get_hour));
                     current_minute.setText("30");
                 }
             }
         }
     });

        decrease_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int get_hour = Integer.parseInt(current_hour.getText().toString());
                int get_minute = Integer.parseInt(current_minute.getText().toString());
                if(get_hour == 1 && get_minute == 00)
                {

                }
                else {

                    if (get_minute == 30) {
                        current_hour.setText(String.valueOf((get_hour)));
                        current_minute.setText("00");
                    } else if (get_minute == 00) {
                        current_hour.setText(String.valueOf((get_hour) - 1));
                        current_minute.setText("30");
                    }
                }
            }
        });

        //handling increase people button
        increase_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_people = Integer.parseInt(people_count.getText().toString());
                people_count.setText(String.valueOf(current_people + 1));
            }
        });

        //handling decrease people button
        decrease_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_people = Integer.parseInt(people_count.getText().toString());
                if(current_people == 1)
                    Toast.makeText(getApplicationContext(),"At least 1 person is required",Toast.LENGTH_SHORT).show();
                else
                    people_count.setText(String.valueOf(current_people - 1));
            }
        });
        //handling on click listener of invite friends
        invite_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInvitePage();

            }

        });

    }

//    @Override
//    protected void onResume() {
//        String selected_city_id = "";
//
//        if(city.selected_cities.size() > 0)
//        {
//            String selected_cities_names = "";
//
//
//                selected_cities_names = city.selected_cities.get(0).getCity_name();
//                selected_city_id = city.selected_cities.get(0).getCity_id();
//
//
//            select_city.setText(selected_cities_names);
//
//        }
//        else
//        {
//            select_city.setText("Norrebro");
//            selected_city_id = "9";
//        }
//        //new getCityImage().execute();
//        super.onRestart();
//    }

    public void showInvitePage()
    {
        String appLinkUrl, previewImageUrl;

        appLinkUrl = "https://www.superb.com/myapplink";
        previewImageUrl = "https://www.mydomain.com/my_invite_image.jpg";

        if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)

                    .build();
            AppInviteDialog.show(this, content);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurant_chooser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        super.onResume();

        new getCityImage().execute();

//        date = current_date.getText().toString();
//        String hour = current_hour.getText().toString();
//        String minute = current_minute.getText().toString();
//        time = hour + minute;
//        city_ids = new String[city.selected_cities.size()];
//        for (int i = 0; i < city.selected_cities.size(); i++) {
//            city_ids[i] = city.selected_cities.get(i).getCity_id();
//        }
//        people = people_count.getText().toString();
//
//        new getAvailableRestaurants().execute();

    }

    public class getCityImage extends AsyncTask<String, String, String> {
        JSONObject jsonObject;

        public void onPreExecute() {
            super.onPreExecute();

            //show progress dialog box
            pDialog = new ProgressDialog(restaurant_chooser.this);
            pDialog.setMessage("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... param) {

            String selected_city_id = "";

            if(city.selected_cities.size() > 0)
            {

                selected_city_id = city.selected_cities.get(0).getCity_id();

            }
            else
            {

                selected_city_id = "9";
            }
            return restInteraction.get_city_image (url_for_city_image + "city_id=" + selected_city_id);

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //dismiss the dialog box
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (!(result.equals("0"))) {
                //setting image from server
                Picasso.with(context).load(result).placeholder(R.drawable.city_item1).into(city_image);

                String selected_city_id = "";

                if(city.selected_cities.size() > 0)
                {
                    String selected_cities_names = "";
                    selected_cities_names = city.selected_cities.get(0).getCity_name();
                    selected_city_id = city.selected_cities.get(0).getCity_id();
                    //Toast.makeText(context,"Selected city is: " + selected_cities_names, Toast.LENGTH_SHORT).show();
                    select_city.setText(selected_cities_names);
                }
                else
                {
                    select_city.setText("Norrebro");
                    selected_city_id = "9";
                }

                //setting placeholder image if image from server cannot be set
                //Picasso.with(context).load("http://demo10.mindinfoservices.com/superb/uploads/n.jpg").placeholder(R.drawable.background_login).error(R.drawable.splash).into(city_image);

                //setting the city name on the button

            } else
                Toast.makeText(getApplicationContext(), "Unable to load image", Toast.LENGTH_SHORT).show();

        }
    }

    public class getAvailableRestaurants extends AsyncTask<String, String, String> {
        JSONObject jsonObject;

        public void onPreExecute() {
            super.onPreExecute();

            //show progress dialog box
            pDialog = new ProgressDialog(restaurant_chooser.this);
            pDialog.setMessage("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... param) {

            String selected_city_id = "";

            if(city.selected_cities.size() > 0){
                selected_city_id = city.selected_cities.get(0).getCity_id();
            }
            else{
                selected_city_id = "9";
            }
            return restInteraction.get_available_restaurants(url_for_available_restaurants + "city_id=" + selected_city_id);

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //dismiss the dialog box
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (!(result.equals("0"))) {
                Intent i = new Intent(restaurant_chooser.this,available_restaurants.class);
                i.putExtra("From_Activity","restaurant_chooser");
                startActivity(i);
            } else
                Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();

        }
    }
}
