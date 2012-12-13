package com.alpine.mmall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

import customlist.ImageLoader;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class Sellerdetails extends Activity {
	 private Activity activity;
	 ImageLoader imageLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellerdetails);
        imageLoader=new ImageLoader(getApplicationContext());
        Intent inn=getIntent();
        activity=this;
        fetchdata fd=new fetchdata();
		 fd.execute(inn.getIntExtra("sellerid", 1));
		 
		
       
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sellerdetails, menu);
        return true;
    }*/
    
    
    
    

private class fetchdata extends AsyncTask<Integer , Integer, String>{

	protected String doInBackground(Integer... params) {
		// TODO Auto-generated method stub
		int id=params[0];
		String res = "";
		
		try {
	    	 HttpClient httpclient = new DefaultHttpClient();
	    	        HttpPost httppost = new HttpPost("http://theminimall.com/Android.asmx/GetUser");
	    	       HttpResponse response;
	    	       List<NameValuePair> param = new ArrayList<NameValuePair>();                
	               param.add(new BasicNameValuePair("UserId", String.valueOf(id)));
	                           
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
		JSONObject jso = new JSONObject(result);
		TextView tv=(TextView)findViewById(R.id.seller_details_tv_head);
		tv.setText(jso.getString("CompanyName"));
		String imagename=jso.getString("CompanyLogo");
		Log.e("imagename:", imagename);
		ImageView im1=(ImageView)findViewById(R.id.seller_dtls_img1);
		im1.setTag("http://www.theminimall.com/"+imagename);
        imageLoader.DisplayImage("http://www.theminimall.com/"+imagename, activity, im1);
        TextView tv2=(TextView)findViewById(R.id.seller_dtls_tv_contact);
        tv2.setText(jso.getString("AboutUs"));
        TextView tv3=(TextView)findViewById(R.id.slr_dtls_tvcontactus);
        tv3.setText("Contact us:"+jso.getString("DefaultFullAddress"));
        Log.e("contact", jso.getString("DefaultFullAddress"));
   
		
		
		
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	
}}