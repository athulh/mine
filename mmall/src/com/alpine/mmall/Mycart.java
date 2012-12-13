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

import customlist.cartlist;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class Mycart extends ListActivity {
	ListView lv;
	long csid;
	String[] ProductName,DeliveryBy;
	int[] Quantity,Price,ShippingCost;
	Activity a;
	ProgressDialog pDlg;
	String adresid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);
        lv=getListView();
        SharedPreferences app_preferences = 	PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        csid =app_preferences.getLong("userid",0);
        
        a=this;
        
        fetchd ff=new fetchd();
        ff.execute();
        
    }
    
    public void checkoutmycart(View v) {
    	
    	
    	abcd abc=new abcd();
    	abc.execute();
    
		
	}
    public void clrcrt(View v) {
    	
    clrmeoo cl=new clrmeoo();
    cl.execute();
		
	}
    private class abcd extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String residentevil="";

			try {
		    	 HttpClient httpclient = new DefaultHttpClient();
		    	        HttpPost httppost = new HttpPost("http://test.theminimall.com/Android.asmx/GetUser");
		    	       HttpResponse response;
		    	       List<NameValuePair> param = new ArrayList<NameValuePair>();                
		               param.add(new BasicNameValuePair("UserId", String.valueOf(csid)));
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
				    	          residentevil=sb.toString();
				    	    
				    	    
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		         
		    	
			return residentevil;
		}
		@Override
		protected void onPostExecute(String result) {
			
			String adress = null;
			
			try {
				JSONObject jso = new JSONObject(result);
				if (!jso.getString("Address2Id").equals(null)) {
					adress=jso.getString("FullAddress2");
					adresid=jso.getString("Address2Id");
					
				} else {
					adress=jso.getString("DefaultFullAddress");
					adresid=jso.getString("DefaultAddressId");

				}
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			AlertDialog.Builder ab=new AlertDialog.Builder(a);
			ab.setTitle("your order will be shiped to:");
			ab.setMessage(adress);
			ab.setPositiveButton("ok", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					chkmeout cm=new chkmeout();
			    	cm.execute();
				}
			});
               ab.setNeutralButton("edit adress", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent in = new Intent(Mycart.this,Changeshippingadress.class);
					in.putExtra("adressid", adresid);
					startActivity(in);
					
				}
			});
               ab.setNegativeButton("cancel", null);
			ab.show();
		}}
    
    private class clrmeoo extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String residentevil="";

			try {
		    	 HttpClient httpclient = new DefaultHttpClient();
		    	        HttpPost httppost = new HttpPost("http://theminimall.com/Android.asmx/ClearCart");
		    	       HttpResponse response;
		    	       List<NameValuePair> param = new ArrayList<NameValuePair>();                
		               param.add(new BasicNameValuePair("CustomerId", String.valueOf(csid)));
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
				    	          residentevil=sb.toString();
				    	    
				    	    
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		         
		    	
			return residentevil;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			AlertDialog.Builder ab=new AlertDialog.Builder(a);
			ab.setMessage("cart cleared click ok to return to home page");
			ab.setNeutralButton("ok", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					a.finish();
				}
			});
			ab.show();
		}
		
    }


	private void showProgressDialog() {
        
        pDlg   = new ProgressDialog(a);
        //  pDlg.setMessage(processMessage);
         // pDlg.setProgressDrawable(mContext.getWallpaper());
          pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
          pDlg.setCancelable(false);
          pDlg.show();

      }
    
    private class chkmeout extends AsyncTask<String, Integer, String>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			showProgressDialog();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String residentevil="";

			try {
		    	 HttpClient httpclient = new DefaultHttpClient();
		    	        HttpPost httppost = new HttpPost("http://theminimall.com/Android.asmx/AddCartOrderAndroid");
		    	       HttpResponse response;
		    	       List<NameValuePair> param = new ArrayList<NameValuePair>();                
		               param.add(new BasicNameValuePair("CustomerId", String.valueOf(csid)));
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
				    	          residentevil=sb.toString();
				    	    
				    	    
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		         
		    	
			return residentevil;
		}

		@Override
		protected void onPostExecute(String result) {
			
			pDlg.dismiss();
			
			// TODO Auto-generated method stub
			try {
				JSONObject jo=new JSONObject(result);
				if (jo.getBoolean("isSucceeded")) {
					String aaa=jo.getString("result");
					if (!aaa.equals("false")) {
						Intent in=new Intent(Mycart.this,Payment.class);
						in.putExtra("res", aaa);
						startActivity(in);
					} else {

					}
					
					a.finish();
					
				}else {
					
				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		}
    	
    
    
    
    private class fetchd extends AsyncTask<String , Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			//String url=params[0];
			String res = "";
			
			try {
		    	 HttpClient httpclient = new DefaultHttpClient();
		    	        HttpPost httppost = new HttpPost("http://theminimall.com/Android.asmx/GetCartAndroid");
		    	       HttpResponse response;
		    	       List<NameValuePair> param = new ArrayList<NameValuePair>();                
		               param.add(new BasicNameValuePair("CustomerId", String.valueOf(csid)));
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
			ProductName=new String[ja.length()];
			DeliveryBy=new String[ja.length()];
			Quantity=new int[ja.length()];
			ShippingCost=new int[ja.length()];
			Price=new int[ja.length()];
			for (int i = 0; i < ja.length(); i++) {
				
				JSONObject jb=ja.getJSONObject(i);
				ProductName[i]=jb.getString("ProductName");
				DeliveryBy[i]=jb.getString("DeliveryBy");
				Quantity[i]=jb.getInt("Quantity");
				ShippingCost[i]=jb.getInt("ShippingCost");
				if (!DeliveryBy[i].equals("Store Pick Up")) {
				//	
					Price[i]=(jb.getInt("Price")+ShippingCost[i])*Quantity[i];
				} else {
					Price[i]=(jb.getInt("Price"))*Quantity[i];

				}
				
				
			}
			
			cartlist cl=new cartlist(a, ProductName, DeliveryBy, Price, Quantity);
			setListAdapter(cl);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

 
}
