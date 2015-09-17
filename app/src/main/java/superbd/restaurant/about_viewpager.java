package superbd.restaurant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;


public class about_viewpager extends ActionBarActivity {
    CustomPageAdapter mCustomPageAdapter;
    AutoScrollViewPager mViewPager;
    Button login,signup;
    CirclePageIndicator mIndicator;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_viewpager);

        context  = this;
        mCustomPageAdapter = new CustomPageAdapter(this);
        mViewPager = (AutoScrollViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPageAdapter);
        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);

        mViewPager.setInterval(5000);
        mViewPager.startAutoScroll();



        //Viewpaer indicator
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.isCentered();
        mIndicator.setViewPager(mViewPager);


        //checking for internet connection

        cd = new ConnectionDetector(context);
        isInternetPresent = cd.isConnectingToInternet();

        if(isInternetPresent == true)
        {
            //navigate to next activity
        }
        else
        {
            //showAlertDialog(getApplicationContext(),"No Internet Connectivity","Please switch on your internet connection",false);
            Toast.makeText(getApplicationContext(), "Please swictch on", Toast.LENGTH_SHORT).show();
        }



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(about_viewpager.this, login.class);
                startActivity(i);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(about_viewpager.this,signup.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_viewpager, menu);
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
    public void redirect()
    {
        Intent i = new Intent(about_viewpager.this, login.class);
        startActivity(i);
    }
}
