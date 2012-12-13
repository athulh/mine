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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import customlist.buyingslist;

import android.R.integer;
import android.R.string;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class Buydetails extends ListActivity {
	int[] shippingcst;
	
	ListView lv;
	String compny[];
	int[] pric,sellerprdctmtrxid;
	boolean[][] opt;
	Activity a;
	int id;
	long csid;
	boolean buynow;
	String url;
	boolean onlinep=false;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buydetails);
        
        SharedPreferences app_preferences = 	PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
     csid =app_preferences.getLong("userid",0);
     Log.e("cust id", String.valueOf(csid));
        
        lv=getListView();
        Intent buyersint=getIntent();
        
       id= buyersint.getIntExtra("id", 0);
       buynow=buyersint.getBooleanExtra("buynow", false);
       if (buynow) {
    	   url="http://theminimall.com/Android.asmx/AddOrder";
	} else {
		  url="http://theminimall.com/Android.asmx/AddCartItem";

	}
        
        a=this;
        
        fetchdata fff=new fetchdata();
        fff.execute();
        
        
        
    }
    
    private class fetchdata extends AsyncTask<String , Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			//String url=params[0];
			String res = "";
			
			try {
		    	 HttpClient httpclient = new DefaultHttpClient();
		    	        HttpPost httppost = new HttpPost("http://theminimall.com/Android.asmx/GetSeller");
		    	       HttpResponse response;
		    	       List<NameValuePair> param = new ArrayList<NameValuePair>();                
		               param.add(new BasicNameValuePair("ProductId", String.valueOf(id)));
		             //  params.add(new BasicNameValuePair("PassWord", p));                    
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

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			handlresp(result);
		}


}

	public void handlresp(String result) {
		// TODO Auto-generated method stub
		
		try {
			JSONObject job=new JSONObject(result);
			JSONArray ja=job.getJSONArray("result");
			compny=new String[ja.length()];
			pric=new int[ja.length()];
			shippingcst=new int[ja.length()];
			sellerprdctmtrxid=new int[ja.length()];
	 opt=new boolean[ja.length()][3];
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo=ja.getJSONObject(i);
				compny[i]=jo.getString("CompanyName");
				pric[i]=jo.getInt("Price");
				//shippingcst[i]=jo.getInt("ShippingCost");
				sellerprdctmtrxid[i]=jo.getInt("Id");
				Log.e("bhuf:", String.valueOf(sellerprdctmtrxid[i]));
				opt[i][0]=jo.getBoolean("IsCOD");
				opt[i][1]=jo.getBoolean("IsOP");
				opt[i][2]=jo.getBoolean("IsSPU");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buyingslist bl=new buyingslist(a, compny, pric, opt,sellerprdctmtrxid);
		setListAdapter(bl);
		
		
	}
	
public void spdirect(View v) {
	String ss=String.valueOf(v.getTag());
	adodr ad=new adodr();
	ad.execute(ss,"SPU");
	
}
public void opdirect(View v){
	String ss=v.getTag().toString();
	adodr ad=new adodr();
	ad.execute(ss,"OP");
	onlinep=true;
	
	
}
public void coddirect(View v){
	String ss=v.getTag().toString();
	adodr ad=new adodr();
	ad.execute(ss,"COD");
	
}

private class adodr extends AsyncTask<String, Integer, String>{

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String DeliveryBy=params[1];
		String slrmtrx=params[0];
		String res = "";
		Log.e("djkj", slrmtrx);
		try {
	    	 HttpClient httpclient = new DefaultHttpClient();
	    	        HttpPost httppost = new HttpPost(url);
	    	       HttpResponse response;
	    	       List<NameValuePair> param = new ArrayList<NameValuePair>();                
	               param.add(new BasicNameValuePair("SellerProductMatrixId", slrmtrx));
	               param.add(new BasicNameValuePair("DeliveryBy", DeliveryBy)); 
	               param.add(new BasicNameValuePair("CustomerId", String.valueOf(csid))); 
	               param.add(new BasicNameValuePair("Quantity", "1")); 
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
	         
	    	Log.e("haiiiiiiires", res);
		return res;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		
		JSONObject jb;
		try {
			jb = new JSONObject(result);
	
		
		if (buynow) {
			
			if (onlinep) {
				if (jb.getBoolean("isSucceeded")) {
					String aaa=jb.getString("result");
					if (!aaa.equals("false")) {
						Intent in=new Intent(Buydetails.this,Payment.class);
						in.putExtra("res", aaa);
						startActivity(in);
				} 

				}else {
					

					AlertDialog.Builder ab=new AlertDialog.Builder(a);
					ab.setMessage("error try again");
		            ab.setNeutralButton("ok", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							a.finish();
						}
					});
					ab.show();
					
				}
				
				
				
			} else {

			
			
			AlertDialog.Builder ab=new AlertDialog.Builder(a);
			ab.setMessage("you will recive a confirmation email to your default emailadress shortly");
            ab.setNeutralButton("ok", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					a.finish();
				}
			});
			ab.show();}
		} else {
			if (jb.getBoolean("isSucceeded")) {
				
				AlertDialog.Builder ab=new AlertDialog.Builder(a);
				ab.setMessage("item succesfully added to cart");
				ab.setNeutralButton("ok", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						a.finish();
					}
				});
				ab.show();
			} else {
				AlertDialog.Builder ab=new AlertDialog.Builder(a);
				ab.setMessage("error while adding to cart");
				ab.setNeutralButton("ok", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						a.finish();
					}
				});
				ab.show();
			}
			
		

		}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	


}




}
