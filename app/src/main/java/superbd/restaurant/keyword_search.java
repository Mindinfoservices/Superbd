package superbd.restaurant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;


public class keyword_search extends ActionBarActivity {

    GridView gridview_category;
    GridViewAdapter my_adapter;
    int[] images = new int[]{R.drawable.hangout, R.drawable.hangout, R.drawable.bouysnightout, R.drawable.girlsnightout, R.drawable.dating,R.drawable.mummies};
    Context context;
    SharedPreferences recommendations;

    ProgressDialog pDialog;
    RestInteraction restInteraction;
    String tag = "Cheap";
    EditText search_text;

    private static String url_for_category_chooser = "http://demo10.mindinfoservices.com/superb/restobytag.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword_search);
        context = this;
        recommendations = PreferenceManager.getDefaultSharedPreferences(this);

        gridview_category = (GridView) findViewById(R.id.gridview_categories);
        search_text = (EditText) findViewById(R.id.search_text);

        //object initialization
        context = this;
        restInteraction = RestInteraction.getInstance(this);

        my_adapter = new GridViewAdapter(images,context);
        gridview_category.setAdapter(my_adapter);

        //getting search string
        tag = search_text.getText().toString();
        search_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_GO)
                {
                    recommendations.edit().putString("First_Search",tag);
                    new getRestaurants().execute();
                    return true;
                }
                return false;
            }
        });

//        gridview_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                selectItem(position);
//                new getRestaurants().execute();
//
//            }
//        });

    }

    private void selectItem(int position) {


        if(position == 0)
        {
            //family
            tag = "family";
        }
        else if(position == 1)
        {
            //guys night out
            tag = "";
        }
        else if(position == 2)
        {
            //business
            tag = "";
        }
        else if(position == 3)
        {
            //girls night out
            tag = "";
        }
        else if(position == 4)
        {
            //date
            tag = "";
        }
        else if(position == 5)
        {
            //for mummies
            tag = "";
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_keyword_search, menu);
        return true;
    }


    public class getRestaurants extends AsyncTask<String, String, String> {
        JSONObject jsonObject;

        public void onPreExecute() {
            super.onPreExecute();

            //show progress dialog box
            pDialog = new ProgressDialog(keyword_search.this);
            pDialog.setMessage("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... param) {
            return restInteraction.get_available_restaurant_in_category(url_for_category_chooser + "tag=" + tag);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //dismiss the dialog box
            if (pDialog.isShowing())
                pDialog.dismiss();

            if ((result.equals("1"))) {
                Intent i = new Intent(keyword_search.this,available_restaurants.class);
                i.putExtra("From_Activity","keyword_search");
                startActivity(i);
            } else
                Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();

        }
    }
}
