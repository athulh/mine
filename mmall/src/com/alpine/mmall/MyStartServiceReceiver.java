package com.alpine.mmall;

import com.commonsware.cwac.locpoll.LocationPollerResult;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

public class MyStartServiceReceiver extends BroadcastReceiver {
boolean bbc=false;
	@Override
	public void onReceive(Context context, Intent intent) {
		String lat = null,longit = null,prov = null,tim = null;
		boolean last=false;

	    Bundle b=intent.getExtras();
	      
	      LocationPollerResult locationResult = new LocationPollerResult(b);
	      
	      Location loc=locationResult.getLocation();
	      String msg;

	      if (loc==null) {
	        loc=locationResult.getLastKnownLocation();

	        if (loc==null) {
	          msg=locationResult.getError();
	          bbc=false;
	          Log.e("its here in error","'");
	        }
	        else {
	        	bbc=true;
	          msg="TIMEOUT, lastKnown="+loc.toString();
	          lat=String.valueOf(loc.getLatitude());
	          longit=String.valueOf(loc.getLongitude());
	          prov=String.valueOf(loc.getProvider());
	          tim=String.valueOf(loc.getTime());
	          last=true;
	          Log.e("its here in lastknown","'");
	        }
	      }
	      else {
	        msg=loc.toString();
	        
	          lat=String.valueOf(loc.getLatitude());
	          longit=String.valueOf(loc.getLongitude());
	          prov=String.valueOf(loc.getProvider());
	          tim=String.valueOf(loc.getTime());
	          last=false;
	        bbc=true;
	      }

	      if (msg==null) {
	        msg="Invalid broadcast received!";
	        bbc=false;
	      }
	      if(bbc){
	    	  Log.d("", "in start service");
			Intent service = new Intent(context, rs.class);
			service.putExtra("lat", lat);
			service.putExtra("longit", longit);
			service.putExtra("prov", prov);
			service.putExtra("tim", tim);
			service.putExtra("last", last);
			context.startService(service);}
	Log.e("", msg);
		
		
	}
} 
