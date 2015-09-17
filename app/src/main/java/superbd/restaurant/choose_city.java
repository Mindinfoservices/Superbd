package superbd.restaurant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;


public class choose_city extends ActionBarActivity {
    ListView choosecity;
    String[] city_names;
    ArrayList<String> chosen_city;
    choosecity_adapter my_adapter;
    android.support.v7.app.ActionBar actionBar;
    ProgressDialog pDialog;
    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    RestInteraction restInteraction;
    SharedPreferences sharedPreferences;
    private static final String url = "http://demo10.mindinfoservices.com/superb/city.php?";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);

        init();

        context = this;

        restInteraction = RestInteraction.getInstance(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        city_names = new String[]{"Fredericksberg","Indre by","Norrebro","Osterbro"};
        chosen_city = new ArrayList<String>();

        choosecity = (ListView) findViewById(R.id.choosecity);
        choosecity.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //city.cityModels.add(new city_model("9","Fredericksburg"));
        //city.cityModels.add(new city_model("10","Norrebro"));


        new getCities().execute();

//        choosecity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                view = LayoutInflater.from(context).inflate(R.layout.choosecity_list_item, parent, false);
//                TextView names = (TextView) view.findViewById(R.id.city_name);
//                final ImageView selected = (ImageView) view.findViewById(R.id.city_selected);
//
//                //toggling with selected/non-selected tick mark
//                if(selected.getVisibility() == View.VISIBLE)
//                    selected.setVisibility(View.GONE);
//                else if(selected.getVisibility() == View.GONE)
//                    selected.setVisibility(View.VISIBLE);
//
//                String selected_city_id = city.cityModels.get(position).getCity_id();
//                String selected_city_name = city.cityModels.get(position).getCity_name();
//                sharedPreferences.edit().putString("selected_city_id",selected_city_id);
//                sharedPreferences.edit().putString("selected_city_name",selected_city_name);
//                Toast.makeText(getApplicationContext(),"Selected city is: " + selected_city_name, Toast.LENGTH_SHORT).show();
//
//                //choosecity.s(city_names[position]);
//            }
//        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_city, menu);
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

    private void init() {

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        // action.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        //overridePendingTransition(0, 0);

        ViewGroup.LayoutParams layoutparams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View actionv= LayoutInflater.from(this).inflate(R.layout.choose_city_custom_action_bar, null);
        //action.setCustomView(actionv);
        actionBar.setCustomView(actionv);
        actionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView img=(ImageView)actionv.findViewById(R.id.back);
        //back=(Button)actionv.findViewById(R.id.back1);
        img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

               finish();
            }

        });

        actionBar.setDisplayShowCustomEnabled(true);
    }
    public class getCities extends AsyncTask<String, String, String> {
        JSONObject jsonObject;

        public void onPreExecute() {
            super.onPreExecute();

            //show progress dialog box
            pDialog = new ProgressDialog(choose_city.this);
            pDialog.setMessage("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... param) {
            return restInteraction.get_available_locations(url);

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //dismiss the dialog box
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (result.equals("1")) {
                String[] city_name = new String[city.cityModels.size()];
                String[] city_id = new String[city.cityModels.size()];
                for(int i = 0; i < city_name.length; i++)
                {
                    city_name[i] = city.cityModels.get(i).getCity_name();
                    city_id[i] = city.cityModels.get(i).getCity_id();
                }

                my_adapter = new choosecity_adapter(choose_city.this,city_name,city_id);
                choosecity.setAdapter(my_adapter);


            } else {
                Toast.makeText(getApplicationContext(), "Error while retrieving locations", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }


        }
    }
}
