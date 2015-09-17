package superbd.restaurant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import android.content.pm.Signature;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


public class login extends ActionBarActivity {
    Button login;
    EditText username, password;
    ActionBar actionBar;
    private CallbackManager callbackManager;
    private LoginButton fbLoginButton;
    Bundle parameters = new Bundle();
    String mAccessToken,TOKEN;
    ArrayList<FriendInfo> friendsId;
    RestInteraction restInteraction;
    String user_id;
    SharedPreferences sharedPreferences;
    private static final String url = "http://demo10.mindinfoservices.com/superb/login.php?";
    ProgressDialog pDialog;
    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    String user_email;
    Profile profile;

    String get_password, get_username;
    TextView or_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        //you need this method to be used only once to configure your key path in your app console at developers.facebook.com/apps
        getFbKeyHash("superbd.restaurant");

        setContentView(R.layout.activity_login);

        restInteraction = RestInteraction.getInstance(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        fbLoginButton = (LoginButton) findViewById(R.id.loginwithfb);
        fbLoginButton.setReadPermissions("user_friends");
        fbLoginButton.setReadPermissions("email");
        fbLoginButton.setReadPermissions("public_profile");
        fbLoginButton.setReadPermissions("user_birthday");
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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
                Toast.makeText(login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(login.this,restaurant_chooser.class);
                startActivity(i);
            }

            @Override
            public void onCancel() {
                Toast.makeText(login.this, "Login cancelled by user!", Toast.LENGTH_SHORT).show();
                System.out.println("Facebook login failed!");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(login.this, "Login failed due to some error", Toast.LENGTH_SHORT).show();
            }
        });

        //trying to get all the friend's ids
//        try
//        {
//            parameters.putString("format","json");
//            parameters.putString(TOKEN,mAccessToken);
//
//            String url = "https://graph.facebook.com/me/friends";
//            String response =
//        }

        init();

        login = (Button)findViewById(R.id.login);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        or_text = (TextView) findViewById(R.id.or_text);

        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/HelveticaNeue-Light.otf");
        //username.setTypeface(face);
        password.setTypeface(face);
        or_text.setTypeface(face);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_password = password.getText().toString();
                get_username = username.getText().toString();

                if(get_password.equals("") || get_username.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please enter both username and password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    new regUser().execute();
                }
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

    public class regUser extends AsyncTask<String, String, String> {
        JSONObject jsonObject;

        public void onPreExecute() {
            super.onPreExecute();

            //show progress dialog box
            pDialog = new ProgressDialog(login.this);
            pDialog.setMessage("Please wait");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... param) {



            //for get requests

            params.add(new BasicNameValuePair("password", get_password));
            params.add(new BasicNameValuePair("username",get_username));



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

            return restInteraction.registerUser(url + "username=" + get_username + "&password=" + get_password );

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //dismiss the dialog box
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (result.equals("1")) {
                Toast.makeText(getApplicationContext(), "Successfully registered!", Toast.LENGTH_SHORT).show();
                sharedPreferences.edit().putBoolean("loggedin",true).commit();
                sharedPreferences.edit().putString("username",get_username).commit();
                Intent i = new Intent(login.this, restaurant_chooser.class);
                startActivity(i);
                finish();
            } else
                Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int reqCode, int resCode, Intent i)
    {
        callbackManager.onActivityResult(reqCode, resCode, i);
    }

    // custom action bar
    private void init() {
        View actionv = LayoutInflater.from(this).inflate(
                R.layout.login_custom_actionbar, null);
        actionBar = getSupportActionBar();


        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setHomeButtonEnabled(false);


        // setting custom view
        actionBar.setCustomView(actionv);
        actionBar.setDisplayOptions(android.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);

        //adding functionality to back button
       ImageView back = (ImageView) actionv.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
