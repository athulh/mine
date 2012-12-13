package com.alpine.mmall;

import java.util.Calendar;

import com.commonsware.cwac.locpoll.LocationPoller;
import com.commonsware.cwac.locpoll.LocationPollerParameter;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MyScheduleReceiver extends BroadcastReceiver {
	

	// Restart service every 30 min
	private static final long REPEAT_TIME = 30*1000*4;//1800000 ;

	@Override
	public void onReceive(Context context, Intent intent) {
		
	     /* ConnectivityManager connectivityManager =     (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE ); 
          NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo(); 
          NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE ); 
          if ( activeNetInfo != null ) 
          { 
          //  Toast.makeText( context, "Active Network Type : "+ activeNetInfo.getTypeName(), Toast.LENGTH_SHORT ).show(); 
          } 
          if( mobNetInfo != null ) 
          { 
           // Toast.makeText( context, "Mobile Network Type : "+ mobNetInfo.getTypeName(), Toast.LENGTH_SHORT ).show(); 
          } */
		
		
          boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
        //  String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
                      boolean isFailover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);
          
         // NetworkInfo currentNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
        //  NetworkInfo otherNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);
                      
  if (!noConnectivity) {
	  
	  
		AlarmManager service = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, LocationPoller.class);
		
		Bundle bundle = new Bundle();
		LocationPollerParameter parameter = new LocationPollerParameter(bundle);
		parameter.setIntentToBroadcastOnCompletion(new Intent(context, MyStartServiceReceiver.class));
		// try GPS and fall back to NETWORK_PROVIDER
		parameter.setProviders(new String[] {LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER});
		parameter.setTimeout(60000);
		i.putExtras(bundle);
		
		
		PendingIntent pending = PendingIntent.getBroadcast(context, 54321, i,
				PendingIntent.FLAG_CANCEL_CURRENT);
		Calendar cal = Calendar.getInstance();
		// Start 30 seconds after boot completed
		service.cancel(pending);
		cal.add(Calendar.SECOND, 30);
		//
		// Fetch every 30 seconds
		// InexactRepeating allows Android to optimize the energy consumption
		service.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				cal.getTimeInMillis(), REPEAT_TIME, pending);

		// service.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
		// REPEAT_TIME, pending);
		Log.e("", "in alarm setting");
	
} else {
	Intent i = new Intent(context, LocationPoller.class);
	
	PendingIntent pending = PendingIntent.getBroadcast(context, 54321, i,
			PendingIntent.FLAG_CANCEL_CURRENT);
	AlarmManager am = (AlarmManager)context. getSystemService(Context.ALARM_SERVICE);

	am.cancel(pending);
	Log.e("", "in alarm removal");

}

   
		
	

	}
}