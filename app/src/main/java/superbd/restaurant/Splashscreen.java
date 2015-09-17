package superbd.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


public class Splashscreen extends ActionBarActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 4000;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //setting the shared preference
        //sharedPreferences.edit().putBoolean("loggedin",false);
        new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                    Intent i = new Intent(Splashscreen.this, about_viewpager.class);
                    startActivity(i);



                //Intent i = new Intent(Splashscreen.this, about_viewpager.class);
                //startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
