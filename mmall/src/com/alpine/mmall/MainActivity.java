package com.alpine.mmall;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.alpine.mmall.Home.fetchdata;



import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public long userid;
	public String username,password,session;
	

	
	private ath remoteService;
	   private final int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wlcm);
        
        
        
        
        SharedPreferences app_preferences = 	PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userid=app_preferences.getLong("userid",0);
        username=app_preferences.getString("username", "");
        
        password=app_preferences.getString("password", "");
       
        
        session=app_preferences.getString("session", "");
   
  
        
//        TextView tv=(TextView)findViewById(R.id.textView1);
//       try {
//		
//
//        Intent intnt =getIntent();
//        String str=intnt.getStringExtra("id");
//        tv.setText("h"+str);
//        
//   	} catch (Exception e) {
//		// TODO: handle exception
//	}
//        tv.setOnClickListener(new View.OnClickListener() {
//			
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//			/*    Toast.makeText(getApplicationContext(), "hai", Toast.LENGTH_LONG).show();
//		        Intent i=new Intent();
//		        i.setClassName("com.alpine.mmall", "com.alpine.mmall.rs");
//		        startService(i);*/
//		    	final long REPEAT_TIME = 1000 * 30 ;
//
//		  
//		    	
//		    		AlarmManager service = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//		    		Intent in = new Intent(getApplicationContext(), MyStartServiceReceiver.class);
//		    		PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), 0, in,
//		    				PendingIntent.FLAG_CANCEL_CURRENT);
//		    		Calendar cal = Calendar.getInstance();
//		    		// Start 30 seconds after boot completed
//		    		cal.add(Calendar.SECOND, 30);
//		    		//
//		    		// Fetch every 30 seconds
//		    		// InexactRepeating allows Android to optimize the energy consumption
//		    		service.setInexactRepeating(AlarmManager.RTC_WAKEUP,
//		    				cal.getTimeInMillis(), REPEAT_TIME, pending);}
//			
//		});
    
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.activity_main, menu);
      

            MenuInflater inflater = getMenuInflater();

            inflater.inflate(R.menu.activity_main, menu);

            return true;

       
    }
    protected void onResume()
    {
        super.onResume();
        if (isNetworkAvailable()) {
			
		
        if (userid==0) {
        	   new Handler().postDelayed(new Runnable()
               {
                   public void run()
                   {
                       //Finish the splash activity so it can't be returned to.
                      MainActivity.this.finish();
                       // Create an Intent that will start the main activity.
                      
                      
                     Intent mainIntent = new Intent(MainActivity.this, Log_in.class);
                      MainActivity.this.startActivity(mainIntent);
                   }
               }, SPLASH_DISPLAY_LENGTH);
		} else {
			 fetchdata fd=new fetchdata();
			 fd.execute("");
			 

		}
        } else {
        	Toast.makeText(getApplicationContext(), "please check your internet connection !", Toast.LENGTH_LONG).show();
finish();
		}
     
        }
    
	public class fetchdata extends AsyncTask<String , Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=params[0];
			String res = "";
			
			try {
		    	 HttpClient httpclient = new DefaultHttpClient();
		    	        HttpGet httppost = new HttpGet("http://theminimall.com/Android.asmx/GetTopCategoryProduct");
		    	       HttpResponse response;
					
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

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			handlresp(result);
		}

		

	}
	public void handlresp(String result) {
		// TODO Auto-generated method stub
		MainActivity.this.finish();
	//	int[][] a =new int[5][5];
		Intent mainIntent = new Intent(MainActivity.this, Home.class);
		mainIntent.putExtra("data", result);
		 mainIntent.putExtra("ex", true);
        MainActivity.this.startActivity(mainIntent);
		
	}
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}

    
}
