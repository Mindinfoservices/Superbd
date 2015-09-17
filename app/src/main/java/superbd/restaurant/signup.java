package superbd.restaurant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class signup extends ActionBarActivity {
    ActionBar actionBar;
    Button signup;
    private CallbackManager callbackManager;
    private LoginButton fbSignupButton;
    String user_id, mAccessToken;
    Profile profile;
    RestInteraction restInteraction;
    private static final String url = "http://demo10.mindinfoservices.com/superb/login.php?";
    private static final String TAG_SUCCESS = "success";
    SharedPreferences sharedPreferences;
    ProgressDialog pDialog;
    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();


    EditText email, username, password, dob, gender;
    String get_email,get_password, get_username, get_dob, get_gender, get_name, get_fbid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        //you need this method to be used only once to configure your key path in your app console at developers.facebook.com/apps
        getFbKeyHash("superbd.restaurant");
        setContentView(R.layout.activity_signup);

        restInteraction = RestInteraction.getInstance(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        init();

        //finding the widgets
        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        dob = (EditText) findViewById(R.id.dob);
        gender = (EditText) findViewById(R.id.gender);
        signup = (Button) findViewById(R.id.signup);

        //setting font
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/HelveticaNeue-Light.otf");
        email.setTypeface(face);
        username.setTypeface(face);
        password.setTypeface(face);
        dob.setTypeface(face);
        gender.setTypeface(face);


        //handling on click listener
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                get_email = email.getText().toString();
                get_password = password.getText().toString();
                get_username = username.getText().toString();
                get_dob = dob.getText().toString();
                get_gender = gender.getText().toString();
                get_name = "";
                get_fbid = "";

                if(get_email.equals("") || (get_username.equals("")) || (get_password.equals("")))
                {
                    Toast.makeText(getApplicationContext(),"Please enter email, username and password",Toast.LENGTH_SHORT).show();
                }
                else {
                    new regUser().execute();
                }
            }
        });
        fbSignupButton = (LoginButton) findViewById(R.id.signupwithfb);
        fbSignupButton.setReadPermissions("user_friends");
        fbSignupButton.setReadPermissions("email");
        fbSignupButton.setReadPermissions("public_profile");
        fbSignupButton.setReadPermissions("user_birthday");
        fbSignupButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("Facebook login Successful!");
                System.out.println("Logged in user Details: ");
                System.out.println("-------------------------");
                System.out.println("User ID: " + loginResult.getAccessToken().getUserId());
                user_id = loginResult.getAccessToken().getUserId();
                profile = Profile.getCurrentProfile();
                String name = profile.getFirstName();
                Toast.makeText(getApplicationContext(),"Hi" + name, Toast.LENGTH_SHORT).show();
                sharedPreferences.edit().putBoolean("loggedin",true);
                sharedPreferences.edit().putString("user_id", user_id);
                mAccessToken = loginResult.getAccessToken().toString();
                System.out.println("Authentication Token : " + loginResult.getAccessToken().getToken());
                Toast.makeText(signup.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(signup.this,restaurant_chooser.class);
                startActivity(i);
            }

            @Override
            public void onCancel() {
                Toast.makeText(signup.this, "Login cancelled by user!", Toast.LENGTH_SHORT).show();
                System.out.println("Facebook login failed!");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(signup.this, "Failed due to some error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getFbKeyHash(String packageName)
    {
        try
        {
            PackageInfo info = getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {

                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("YourKeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.println("YourKeyHash: "+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }catch (PackageManager.NameNotFoundException e)
        {

        }catch(NoSuchAlgorithmException e)
        {

        }
    }

    // custom action bar
    private void init() {
        View actionv = LayoutInflater.from(this).inflate(
                R.layout.signup_custom_actionbar, null);
        actionBar = getSupportActionBar();


        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayOptions(android.app.ActionBar.DISPLAY_SHOW_CUSTOM);

        // setting custom view
        actionBar.setCustomView(actionv);

        //adding functionality to back button
        ImageView back = (ImageView) actionv.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
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

    public class regUser extends AsyncTask<String, String, String> {
        JSONObject jsonObject;

        public void onPreExecute() {
            super.onPreExecute();

            //show progress dialog box
            pDialog = new ProgressDialog(signup.this);
            pDialog.setMessage("Registering");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... param) {





            //for get requests
            params.add(new BasicNameValuePair("email", get_email));
            params.add(new BasicNameValuePair("password", get_password));
            params.add(new BasicNameValuePair("username",get_username));
            params.add(new BasicNameValuePair("dob",get_dob));
            params.add(new BasicNameValuePair("gender",get_gender));
            params.add(new BasicNameValuePair("name",get_name));
            params.add(new BasicNameValuePair("facebook_id",get_fbid));


            //These are for post requests
//            BasicNameValuePair emailBasicNameValuePair = new BasicNameValuePair("paramEmail",get_email);
//            BasicNameValuePair passwordBasicNameValuePair = new BasicNameValuePair("paramPassword",get_password);
//            BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair("paramUsername",get_username);
//            BasicNameValuePair dobBasicNameValuePair = new BasicNameValuePair("paramDob",get_dob);
//            BasicNameValuePair genderBasicNameValuePair = new BasicNameValuePair("paraGender",get_gender);
//            BasicNameValuePair nameBasicNameValuePair = new BasicNameValuePair("paramName",get_name);
//            BasicNameValuePair fbidBasicNameValuePair = new BasicNameValuePair("paramFbid",get_fbid);
//
//            nameValuePairList.add(emailBasicNameValuePair);
//            nameValuePairList.add(passwordBasicNameValuePair);
//            nameValuePairList.add(usernameBasicNameValuePair);
//            nameValuePairList.add(dobBasicNameValuePair);
//            nameValuePairList.add(genderBasicNameValuePair);
//            nameValuePairList.add(nameBasicNameValuePair);
//            nameValuePairList.add(fbidBasicNameValuePair);
            //return restInteraction.registerUser(url,nameValuePairList);

            return restInteraction.registerUser(url + "name=" + get_name + "&email=" + get_email + "&password=" + get_password + "&username=" + get_username + "&dob=" + get_dob + "&gender=" + get_gender + "&facebook_id=" + get_fbid);

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //dismiss the dialog box
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (result.equals("1")) {
                Toast.makeText(getApplicationContext(), "Successfully registered!", Toast.LENGTH_SHORT).show();
                sharedPreferences.edit().putBoolean("register",true).commit();
                sharedPreferences.edit().putBoolean("loggedin",true).commit();
                sharedPreferences.edit().putString("username",get_username).commit();
                Intent i = new Intent(signup.this, restaurant_chooser.class);
                startActivity(i);
                finish();
            } else
                Toast.makeText(getApplicationContext(), "Username or Email already existing! Choose a different one!", Toast.LENGTH_SHORT).show();

        }
    }
}
