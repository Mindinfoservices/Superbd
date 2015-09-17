package superbd.restaurant;

/**
 * Created by lenovo on 8/26/2015.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class RestInteraction {

        private static RestInteraction _instance;
        private DefaultHttpClient httpclient;
        private HttpPost postRequest;
        private HttpResponse httpResponse;
        private HttpEntity httpEntity;
        Context mContext;
        SharedPreferences sharedPreferences;
        public String userId;
        private ArrayList<NameValuePair> nameValuePairs;
        private HttpGet getRequest;
        private HttpDelete delRequest;
        static InputStream is = null;
        static JSONObject jObj = null;
        static String json = "";


        public RestInteraction(Context ctx) {
            mContext = ctx;
            sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(mContext);
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        }

        public static RestInteraction getInstance(Context ctx) {

            if (_instance == null) {
                _instance = new RestInteraction(ctx);
            }
            return _instance;
        }

        private void initializeHttpClient() {
            httpclient = new DefaultHttpClient();
            nameValuePairs = new ArrayList<NameValuePair>();
        }

        private String sendHttpRequest(String url,List<NameValuePair> nameValuePairs) {
            String jSonStr = null;
            try {
                postRequest = new HttpPost(url);
                postRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs,
                        HTTP.UTF_8));
                httpResponse = httpclient.execute(postRequest);
                httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    jSonStr = EntityUtils.toString(httpEntity);
                    return jSonStr;
                }
            } catch (Exception e) {
                if (e != null) {
                    e.getMessage();
                }
            }
            return jSonStr;
        }

        private String sendHttpGetRequest(String url) {
            String jSonStr = null;
            try {
                httpclient = new DefaultHttpClient();
                getRequest = new HttpGet(url);
                httpResponse = httpclient.execute(getRequest);
                httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    jSonStr = EntityUtils.toString(httpEntity);
                    return jSonStr;
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jSonStr;
        }

        // function get json from url
        // by making HTTP POST or GET mehtod
        public JSONObject makeHttpRequest(String url, String method,
                                          List<NameValuePair> params) {
            // Making HTTP request
            try {


                // check for request method
                if (method == "POST") {
                    // request method is POST
                    // defaultHttpClient
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();
                } else if (method == "GET") {
                    // request method is GET
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                    url += "?" + paramString;
                    HttpGet httpGet = new HttpGet(url);
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                jObj = new JSONObject(json);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            // return JSON String
            return jObj;

        }
//        public String getAllCategory(String url){
//            try{
//                JSONObject jsonObj = new JSONObject(sendHttpGetRequest(url));
//                //Log.d(getTag(), "json = "+jsonObj.toString());
////            if (jsonObj.has("success")) {
////                //Utils.dealModels.clear();
////                if (jsonObj.getString("success").equals("1")){
//
//                {
//                    JSONArray jsonArray = jsonObj.getJSONArray("categories");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject c = jsonArray.getJSONObject(i);
//                        String id = c.getString("cat_id");
//                        String name = c.getString("cat_name");
//                        String imgName = c.getString("img_name");
//                        String visiblity = c.getString("visible");
//
//                        Utils.categoryModels.add(new CategoryModel(id, name, imgName, visiblity));
//
//                    }
//                    return "Successful";
//                }
//                //}
//                //}
//            }
//
//            catch (Exception e) {
//                e.getMessage();
//            }
//            // Log.d(getTag(), "util = " + Utils.dealModels);
//            return "UnSuccessful";
//        }
//
//        public String getAllDeal(String url){
//            try{
//                JSONObject jsonObj = new JSONObject(sendHttpGetRequest(url));
//                //Log.d(getTag(), "json = "+jsonObj.toString());
////            if (jsonObj.has("success")) {
////                //Utils.dealModels.clear();
////                if (jsonObj.getString("success").equals("1")){
//                JSONObject  jsonObject = jsonObj.getJSONObject("menus");
//                {
//                    JSONArray jsonArray = jsonObject.getJSONArray("items");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject c = jsonArray.getJSONObject(i);
//                        String dish = c.getString(TAG_DISH);
//                        String item = c.getString(TAG_ITEM);
//                        String cat = c.getString(TAG_CAT);
//                        String price = c.getString(TAG_PRICE);
//                        String desc = c.getString(TAG_DESC);
//                        String image = c.getString(TAG_IMAGE);
//                        String count = c.getString(TAG_COUNT);
//
//                        ContentValues val = new ContentValues();
//                        val.put("dish", dish);
//                        val.put("item", item);
//                        val.put("cat", cat);
//                        val.put("price", price);
//                        val.put("desc", desc);
//                        val.put("image", image);
//                        val.put("count", count);
//
//                        dbOperations.addMenuItemToDB(item, val);
//                    }
//                    return "Successful";
//                }
//                //}
//                //}
//            }
//
//            catch (Exception e) {
//                e.getMessage();
//            }
//            // Log.d(getTag(), "util = " + Utils.dealModels);
//            return "UnSuccessful";
//        }

        public String registerUser(String url){
            String res = "0";
            try {
                JSONObject jsonObj = new JSONObject(sendHttpGetRequest(url));
                if (jsonObj.getString("success").equals("1")) {
                    sharedPreferences.edit().putString("user_id",jsonObj.getString("user_id")).commit();
                    //sharedPreferences.edit().putString("name",jsonObj.getString(("name_per"))).commit();
                    //sharedPreferences.edit().putString("user_id",jsonObj.getString("user_id")).commit();
                    //sharedPreferences.edit().putString("contact_number",jsonObj.getString("user_id")).commit();

                    res = "1";
                    //}
                    //}
                }else
                    res = "0";
                // }
            }
            catch (Exception e) {
                e.getMessage();
            }
            // Log.d(getTag(), "util = " + Utils.dealModels);
            return res;
        }


    public String get_available_locations(String url)
    {
        String res = "0";
        try {
            JSONArray jsonArray = new JSONArray(sendHttpGetRequest(url));

            String[] checks = new String[city.cityModels.size()];
            int flag = 0;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                String id = c.getString("city_id");
                String name = c.getString("city_name");

                for(int j = 0; j < checks.length; j++) {

                    if (city.cityModels.get(j).getCity_id().equals(id))
                    {
                        //dont add the city because it already exists
                        flag = 1;
                    }
                }
                if(flag == 0)
                    city.cityModels.add(new city_model(id, name));
            }
            int size = city.cityModels.size();
            res = "1";
        }
        catch (Exception e) {
            e.getMessage();
        }

        return res;
    }
    public String get_city_image(String url)
    {
        String res = "0";
        try {
            JSONObject jsonObj = new JSONObject(sendHttpGetRequest(url));
            res = jsonObj.getString("imagepath");
        }
        catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public String get_available_restaurants(String url)
    {
        String res = "0";
        try{
            JSONArray jsonArray = new JSONArray(sendHttpGetRequest(url));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                String id = c.getString("restaurant_id");
                String image = c.getString("restaurant_images");
                String name = c.getString("restaurant_name");
                city.available_restaurants.add(new restaurant_model(id,image,name));
            }
            res = "1";
        }
        catch (Exception e) {
            e.getMessage();
        }

        return res;
    }

    public String get_available_restaurant_in_category(String url)
    {
        String res = "0";
        try{
            JSONArray jsonArray = new JSONArray(sendHttpGetRequest(url));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                String id = c.getString("restaurant_id");
                String image = c.getString("restaurant_images");
                String name = c.getString("restaurant_name");
                city.available_restaurant_in_category.add(new restaurant_category_model(id,image,name));
            }
            res = "1";
        }
        catch (Exception e) {
            e.getMessage();
        }

        return res;
    }


    public String get_facebook_friends(String url)
    {
        String res = "0";
        try
        {
            JSONObject jsonObject = new JSONObject(sendHttpGetRequest(url));


        }catch(Exception e){
        }
        return res;
    }
//        public String getUserFromServer(String url){
//            String res = "0";
//            String name = "", email = "", mob = "", add1 = "", add2 = "", landmark = "", city = "", state= "";
//            try {
//                JSONObject jsonObj = new JSONObject(sendHttpGetRequest(url));
//                if (jsonObj.getString("status").equals("success")) {
//
//                    JSONObject jsonObject = jsonObj.getJSONObject("details");
//                    {
//                        JSONObject jsonOb = jsonObject.getJSONObject("user_info");
//                        //JSONArray jsonArray = jsonObject.getJSONArray("user_info");
//                        //for (int i = 0; i < jsonArray.length(); i++) {
//                        //JSONObject c = jsonArray.getJSONObject(i);
//                        String status = jsonOb.getString(TAG_STATUS);
//                        if (status == "0") {
//                            name = jsonOb.getString(TAG_NAME);
//                            email = jsonOb.getString(TAG_EMAIL);
//                        } else{
//                            name = jsonOb.getString(TAG_NAME);
//                            email = jsonOb.getString(TAG_EMAIL);
//                            mob = jsonOb.getString(TAG_MOB);
//                            add1 = jsonOb.getString(TAG_ADD1);
//                            add2 = jsonOb.getString(TAG_ADD2);
//                            landmark = jsonOb.getString(TAG_LANDMARK);
//                            city = jsonOb.getString(TAG_CITY);
//                            state = jsonOb.getString(TAG_STATE);
//                        }
//                        Utils_user_details.userInfoModelArrayList.add(new userInfoModel(name, email, mob, add1, add2, landmark, city, state));
//                        res = "1";
//
//                    }
//                    return res;
//                    //}
//                    //}
//                }else
//                    res = "0";
//                // }
//            }
//            catch (Exception e) {
//                e.getMessage();
//            }
//            // Log.d(getTag(), "util = " + Utils.dealModels);
//            return res;
//        }
//
//        public String updateUserToServer(String url){
//            String res = "0";
//
//            try {
//                JSONObject jsonObj = new JSONObject(sendHttpGetRequest(url));
//                if (jsonObj.getString("status").equals("success")) {
//
//
//                    res = "1";
//
//
//                    return res;
//                    //}
//                    //}
//                }else
//                    res = "0";
//                // }
//            }
//            catch (Exception e) {
//                e.getMessage();
//            }
//            // Log.d(getTag(), "util = " + Utils.dealModels);
//            return res;
//        }
//
//        public String getMenuCategoryFromServer(String url){
//            String visible = "";
//            try{
//                JSONObject jsonObj = new JSONObject(sendHttpGetRequest(url));
//                //Log.d(getTag(), "json = "+jsonObj.toString());
////            if (jsonObj.has("success")) {
////                //Utils.dealModels.clear();
////                if (jsonObj.getString("success").equals("1")){
//                if(jsonObj.getString("status").equals("success"))
//                {
//                    JSONArray jsonArray = jsonObj.getJSONArray("categories");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject c = jsonArray.getJSONObject(i);
//                        visible = c.getString(TAG_CAT_VISIBLE);
//                        if (visible.equals("1")) {
//                            String cat_id = c.getString(TAG_CAT_ID);
//                            String cat_name = c.getString(TAG_CAT_NAME);
//                            String img_name = c.getString(TAG_IMG_NAME);
//
//                            ContentValues val = new ContentValues();
//                            val.put("cat_id", cat_id);
//                            val.put("cat_name", cat_name);
//                            val.put("cat_image",img_name);
//                            val.put("visible",visible);
//                            Utils.categoryModels.add(new CategoryModel(cat_id,cat_name,img_name,visible));
//                            dbOperations.addMenuCatToDB(cat_id,val);
//                        }
//                    }
//                    return "Successful";
//                }
//                //}
//                //}
//            }
//
//            catch (Exception e) {
//                e.getMessage();
//            }
//            // Log.d(getTag(), "util = " + Utils.dealModels);
//            return "UnSuccessful";
//        }
//
//        public String getMenuItemFromServer(String url){
//            String visible = "";
//            try{
//                JSONObject jsonObj = new JSONObject(sendHttpGetRequest(url));
//                //Log.d(getTag(), "json = "+jsonObj.toString());
////            if (jsonObj.has("success")) {
////                //Utils.dealModels.clear();
////                if (jsonObj.getString("success").equals("1")){
//                if(jsonObj.getString("status").equals("success"))
//                {
//                    JSONArray jsonArray = jsonObj.getJSONArray("categories");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject c = jsonArray.getJSONObject(i);
//                        visible = c.getString(TAG_ITEM_VISIBLE);
//                        if (visible.equals("1")) {
//                            String cat_id = c.getString(TAG_CAT_ID);
//                            String item_id = c.getString(TAG_ITEM_ID);
//                            String img_name = c.getString(TAG_IMG_NAME);
//                            String item_name = c.getString(TAG_ITEM_NAME);
//                            String about = c.getString(TAG_ABOUT);
//                            String price = c.getString(TAG_PRICE);
//                            String type = c.getString(TAG_TYPE);
//
//
//                            ContentValues val = new ContentValues();
//                            val.put("cat_id", cat_id);
//                            val.put("item_id",item_id);
//
//                            val.put("item_name", item_name);
//                            val.put("about",about);
//                            val.put("price",price);
//                            val.put("type",type);
//                            val.put("img_name",img_name);
//                            val.put("visible",visible);
//                            Utils.categoryModels.add(new CategoryModel(cat_id,item_name,img_name,visible));
//                            dbOperations.addMenuItemToDB(item_id,val);
//                        }
//                    }
//                    return "Successful";
//                }
//                //}
//                //}
//            }
//
//            catch (Exception e) {
//                e.getMessage();
//            }
//            // Log.d(getTag(), "util = " + Utils.dealModels);
//            return "UnSuccessful";
//        }

    //}
}
