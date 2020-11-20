package com.demyzo.firebasenotification;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class InitialiseCredential {


    private static Context mActivity;
    private String  TokenORTopices = "";
    private  String  Title = "";
    private String  Body = "";
    private String  FCMServer_key = "";
    private  String  Data_1 ;
    private  String  Data_2 ;
    private  String  Data_3 ;
    private  String  Data_4 ;
    private  String  Data_5 ;
    private  String  Data_6 ;


    private  String  token ;
    private  int  method ;


    private AsyncTaskExample asyncTask;
    private String URl = "https://fcm.googleapis.com/fcm/send";


    public static InitialiseCredential initialize(Context activity) {

        return new InitialiseCredential(activity);
    }
    private InitialiseCredential(Context activity) {
        this.mActivity = activity;
    }
    public  InitialiseCredential setTokenORTopices(String TokenORTopices) {
        this.TokenORTopices = TokenORTopices;
        return this;
    }
    public  InitialiseCredential setFCMServer_key(String fcmServer_key) {
        this.FCMServer_key = fcmServer_key;
        return this;
    }
    public InitialiseCredential setprimaryDataKey(String Title , String Body) {
        this.Title = Title;
        this.Body = Body;
        return  this;
    }

    public  InitialiseCredential setdefaultDataKey(String value_1 , String value_2 , String value_3 , String value_4 , String value_5 , String value_6) {
        this.Data_1 = value_1;
        this.Data_2 = value_2;
        this.Data_3 = value_3;
        this.Data_4 = value_4;
        this.Data_5 = value_5;
        this.Data_6 = value_6;
        return  this;
    }

    public InitialiseCredential setmethod(int method ) {
        this.method = method;
        return this;
    }
    private int getmethod() {
        return  method;
    }







    @SuppressLint("StaticFieldLeak")
    public void build(){
        if (!TextUtils.isEmpty(TokenORTopices)){
            if (!TextUtils.isEmpty(FCMServer_key)){
                if (getmethod()==1){
                    token = "/token/" + TokenORTopices;
                }else  if (getmethod()==2){
                    token = "/topics/" + TokenORTopices;
                }
                asyncTask=new AsyncTaskExample();
                asyncTask.execute();
            }
        }

    }

    private  class  AsyncTaskExample extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection connection = null;
            try {
                //Open a new URL connection
                connection = (HttpURLConnection) new URL(URl).openConnection();
                //Defines a HTTP request type
                connection.setRequestMethod("POST");
                //Sets headers: Content-Type, Authorization
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("authorization","key="+FCMServer_key);
                //Add POST data in JSON format
                JSONObject json = new JSONObject();
                try {
                    JSONObject notificationObj = new JSONObject();
                    notificationObj.put("title",Title);
                    notificationObj.put("body",Body);

                    JSONObject data = new JSONObject();
                    data.put("value_1", Data_1);
                    data.put("value_2", Data_2);
                    data.put("value_3", Data_3);
                    data.put("value_4", Data_4);
                    data.put("value_5", Data_5);
                    data.put("value_6", Data_6);


                    json.put("to",token);
                    json.put("notification",notificationObj);
                    json.put("data",data);
                } catch (JSONException e) { e.printStackTrace(); }
                //Create a writer object and make the request
                OutputStreamWriter outputStream = new OutputStreamWriter(connection.getOutputStream());
                outputStream.write(json.toString());
                outputStream.flush();
                outputStream.close();
                //Get the Response code for the request
                return String.valueOf(connection.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() { }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

}
