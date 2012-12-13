package com.alpine.mmall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class Userdetails extends Activity {
	EditText username,firstname,middlename,lastname;
	Spinner gender;
	DatePicker dp;
	boolean edit=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        username=(EditText)findViewById(R.id.userdtls_username);
        firstname=(EditText)findViewById(R.id.userdtls_firstname);
        middlename=(EditText)findViewById(R.id.userdtls_middlename);
        lastname=(EditText)findViewById(R.id.userdtls_lasname);
        gender=(Spinner)findViewById(R.id.userdtls_gender);
        dp=(DatePicker)findViewById(R.id.userdtls_bd);
        username.setEnabled(false);
        firstname.setEnabled(false);
        middlename.setEnabled(false);
        lastname.setEnabled(false);
        gender.setEnabled(false);
        dp.setEnabled(false);
        SharedPreferences app_preferences = 	PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       long csid =app_preferences.getLong("userid",0);
       fetchdata ffd=new fetchdata();
       ffd.execute(String.valueOf(csid));
        
    }
    public void editud(View v){
    	if (!edit) {
    		 firstname.setEnabled(true);
             middlename.setEnabled(true);
             lastname.setEnabled(true);
             gender.setEnabled(true);
             dp.setEnabled(true);
             edit=true;
		} else {

		}
    	
    }

private class fetchdata extends AsyncTask<String , Integer, String>{

	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		String res = "";
		String id=params[0];
		try {
	    	 HttpClient httpclient = new DefaultHttpClient();
	    	        HttpPost httppost = new HttpPost("http://theminimall.com/Android.asmx/GetUser");
	    	       HttpResponse response;
	    	       List<NameValuePair> param = new ArrayList<NameValuePair>();                
	               param.add(new BasicNameValuePair("UserId", id));
	                           
	               UrlEncodedFormEntity ent = new UrlEncodedFormEntity(param,HTTP.UTF_8);
			
			httppost.setEntity(ent);
				
					response = httpclient.execute(httppost);
					   HttpEntity entity = response.getEntity();
			    	    InputStream is = entity.getContent();
			    	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			    		        StringBuilder sb = new StringBuilder();
			    	 	        String line = null;
			    	           while ((line = reader.readLine()) != null) {
			    	  	            sb.append(line + "\n");
			    	   	        }
			    	          is.close();
			    	          Log.e("", sb.toString());
			    	          res=sb.toString();
			    	    
			    	    
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         
	    	
		return res;
	}
	

	
protected void onPostExecute(String result) {
	// TODO Auto-generated method stub
	handleresp(result);
}




}

public void handleresp(String result) {
	// TODO Auto-generated method stub
	try {
		JSONObject jso=new JSONObject(result);
		username.setText(jso.getString("UserName"));
		
		firstname.setText(jso.getString("FirstName"));
		middlename.setText(jso.getString("MiddleName"));
		lastname.setText(jso.getString("LastName"));
		Date dd=JsonDateToDate(jso.getString("BirthDate"));
		Log.e("", String.valueOf(dd));
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dd);
		int date = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		dp.init(year, month, date, null);
	
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//
}
public static Date JsonDateToDate(String jsonDate)
{
    //  "/Date(1321867151710)/"
    int idx1 = jsonDate.indexOf("(");
    int idx2 = jsonDate.indexOf(")");
    String s = jsonDate.substring(idx1+1, idx2);
    long l = Long.valueOf(s);
    return new Date(l);
}

}
