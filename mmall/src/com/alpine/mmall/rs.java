package com.alpine.mmall;





import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;

import android.util.Log;

public class rs extends Service {
	String counter;
	NotificationManager nm;
	String lat = null,longit = null,prov = null,tim = null,id;
	boolean last=false;


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return myRemoteServiceStub;
	}
	private ath.Stub myRemoteServiceStub = new ath.Stub() {
		public String getCounter() throws RemoteException {
			return counter;
		}
	};
/*	public int onStartCommand(Intent intent, int flags, int startId){
		return startId;
		
	}*/
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(getClass().getSimpleName(),"onCreate()");
		nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	
	}
/*	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		myTask mtsk=new myTask();
		mtsk.execute("");
			Log.d(getClass().getSimpleName(), "onStart()");

	}*/
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
	//String loc=	intent.getStringExtra("location_details");
	lat=intent.getStringExtra("lat");
	longit=intent.getStringExtra("longit");
	tim=intent.getStringExtra("tim");
	prov=intent.getStringExtra("prov");
	last=intent.getBooleanExtra("last", false);
	   SharedPreferences app_preferences = 	PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
id =String.valueOf(app_preferences.getLong("userid",0));
		myTask mtsk=new myTask();
		if (isNetworkAvailable()) {
			mtsk.execute("");
		}else {
			Log.e("no n/w connection in service", ": no conection dude!");
		}
		
			Log.d(getClass().getSimpleName(), "onStart()");
		return super.onStartCommand(intent, flags, startId);
	}

	class myTask extends AsyncTask<String, integer, String>{

	

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String s=fetchadd();
		//String sax=fetchadd();
		return s;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		
			handleresp(result);
			 stopService(new Intent(rs.this,rs.class));
	
	}

	
	
	
	}
	@SuppressWarnings("deprecation")
	private void handleresp(String result)  {
		try {
			
	
		// TODO Auto-generated method stub
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = result;
        
        JSONObject jsbb=new JSONObject(result);
        if (jsbb.getBoolean("isSucceeded")) {
        	  JSONObject jbb=jsbb.getJSONObject("result");
        	  

              // Set the icon, scrolling text and timestamp
              Notification notification = new Notification(R.drawable.ic_launcher, jbb.getString("AdvertiseMessage"),
                     System.currentTimeMillis());
              
              
              
              
              
          //   NotificationCompat.Builder nma=new NotificationCompat.Builder(getApplicationContext());
              
           //   nma.setContentText(text);
          //    nma.setSmallIcon(R.drawable.ic_launcher);
              
             Intent i= new Intent(this, MainActivity.class);
             i.putExtra("id", result);
              // The PendingIntent to launch our activity if the user selects this notification
              PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                     i , 0);

              // Set the info for the views that show in the notification panel.
         //   nma.addAction(R.drawable.ic_launcher, "", contentIntent);
              notification.setLatestEventInfo(this, jbb.getString("AdvertiseTitle"),
            		  jbb.getString("AdvertiseMessage"), contentIntent);

              // Send the notification.
              // We use a layout id because it is a unique number.  We use it later to cancel.
          //   NotificationCompat nn=nma.build();
              nm.notify(result, 54321, notification);
              
			
		}
		} catch (Exception e) {
			// TODO: handle exception
			 stopService(new Intent(rs.this,rs.class));
		}
        
		
			
		
	}
	
	public String fetchadd() {
		// TODO Auto-generated method stub
		String rtrn = null;
		try {
		HttpClient fad=new DefaultHttpClient();
		 HttpPost httppost = new HttpPost("http://test.theminimall.com/Android.asmx/GetAdvertise");///// url please
		 
		    List<NameValuePair> param = new ArrayList<NameValuePair>();                
            param.add(new BasicNameValuePair("Latitude", lat));
            param.add(new BasicNameValuePair("Longitude", longit));
            param.add(new BasicNameValuePair("CustomerId", id));
            param.add(new BasicNameValuePair("Provider", prov));
            param.add(new BasicNameValuePair("LastLocation", String.valueOf(last)));
            param.add(new BasicNameValuePair("Time", tim));
                        
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(param,HTTP.UTF_8);
		
		httppost.setEntity(ent);
	       HttpResponse response;
	              
               
     
	

			
				response = fad.execute(httppost);
	

		

			   HttpEntity entity = response.getEntity();
	    	    InputStream is = entity.getContent();
	    	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    		        StringBuilder sb = new StringBuilder();
	    	 	        String line = null;
	    	           while ((line = reader.readLine()) != null) {
	    	  	            sb.append(line + "\n");
	    	   	        }
	    	          is.close();
	    	           rtrn=sb.toString();
	    	          Log.e("", sb.toString());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			   

	return rtrn;
		
		
	}

	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
	
}

