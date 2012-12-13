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
import org.json.JSONObject;

import customlist.ImageLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class Prdct_dtls extends Activity {
	 private Activity activity;
	 ImageLoader imageLoader;
	 ImageView im1;
	 WebView webv;
		int sellerid;
		int id;
		
		ProgressDialog pDlg;
		Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prdct_dtls);
    	activity=this;
    
    	mContext=this;
    	webv=(WebView)findViewById(R.id.prdct_dtls_webv);
    	webv.getSettings().setJavaScriptEnabled(true);
    	//webv.setBackgroundColor(Color.parseColor("#424244"));
    //	webv.getSettings().setc
		im1=(ImageView)findViewById(R.id.prdct_dtls_img1);
		 imageLoader=new ImageLoader(getApplicationContext());
        
        Intent in=getIntent();
	 id= in.getIntExtra("id",1);
		fetchdata fd=new fetchdata();
		 fd.execute(id);
		 TextView tt=(TextView)findViewById(R.id.prdct_dtls_sidehdr);
		 
		 showProgressDialog();
		 
		 tt.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inte=new Intent(Prdct_dtls.this,Sellerdetails.class);
				inte.putExtra("sellerid", sellerid);
				
				startActivity(inte);
		       
			}
		});
		 
		 
		
    }
    public void  buynow(View v) {
		Intent ii =new Intent(Prdct_dtls.this,Buydetails.class);
		ii.putExtra("id", id);
		ii.putExtra("buynow", true);
		startActivity(ii);
	}
    public void adtocart(View v) {
    	Intent ii =new Intent(Prdct_dtls.this,Buydetails.class);
		ii.putExtra("id", id);
		ii.putExtra("buynow", false);
		startActivity(ii);
	}

	private void showProgressDialog() {
        
        pDlg   = new ProgressDialog(mContext);
        //  pDlg.setMessage(processMessage);
         // pDlg.setProgressDrawable(mContext.getWallpaper());
          pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
          pDlg.setCancelable(false);
          pDlg.show();

      }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_prdct_dtls, menu);
        return true;
    }
    public void handleresp(String resp){
    	String imagename;
    	String dcf;
		
		try {
			JSONObject jso = new JSONObject(resp);
		Boolean	sucess =jso.getBoolean("isSucceeded");
			if (sucess) {
				
				JSONObject ja =jso.getJSONObject("result");
				
				 
	/*			String[] imagename=new String[ja.length()];
				String[]	prdctnam=new String[ja.length()];
			int[]	prdctid=new int[ja.length()];
			int[]	prs=new int[ja.length()];*/
				
				
				
				
					imagename=ja.getString("ImageName");
					sellerid=ja.getInt("SellerId");
					im1.setTag("http://www.theminimall.com/"+imagename);
			        imageLoader.DisplayImage("http://www.theminimall.com/"+imagename, activity, im1);
			     
					dcf =ja.getString("DescriptionFileName");
					String price=ja.getString("Price");
					String prdctnam=ja.getString("ProductName");
					TextView tv=(TextView)findViewById(R.id.prdct_dtls_tv_head);
					tv.setText(prdctnam);
					
					
					TextView tv2=(TextView)findViewById(R.id.prdct_dtls_sidehdr);
					tv2.setText(Html.fromHtml("<br><font color=\"#00aeef\">Price :</font><font color=\"red\">Rs. "+price+"</font></br><br><font color=\"#00aeef\">Sold By :</font></span><span><font color=\"red\">"+ja.getString("ComapanyName")+"</font></br>"));
					
					
//					TextView tv3=(TextView)findViewById(R.id.prdct_dtls_sidehdr2);
//					tv3.setText(ja.getString("SellerName"));
					webv.loadUrl("http://www.theminimall.com/"+dcf);
					//webv.loadUrl("javascript:void(document.body.style.color=\"blue\")");
			
				pDlg.dismiss();
    	
			}	
    	
    }catch (Exception e) {
		// TODO: handle exception
	}}
    

	private class fetchdata extends AsyncTask<Integer , Integer, String>{

		protected String doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			int id=params[0];
			String res = "";
			
			try {
		    	 HttpClient httpclient = new DefaultHttpClient();
		    	        HttpPost httppost = new HttpPost("http://theminimall.com/Android.asmx/GetProductByProductId");
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
		
	
		
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		handleresp(result);
	}
	}}
    	


