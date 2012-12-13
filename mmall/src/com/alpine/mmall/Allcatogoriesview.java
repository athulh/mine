package com.alpine.mmall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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

import p.mine.tired.searchresult;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ClipData.Item;
import android.content.pm.FeatureInfo;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Allcatogoriesview extends ListActivity {
	ProgressDialog pDlg;
	Context mContext;
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		if (catchild[position]) {
			Intent in=new Intent(Allcatogoriesview.this,Allcatogoriesview.class);
			in.putExtra("catid", catogaryid[position]);
			startActivity(in);
			//Allcatogoriesview.this.finish();
			
			
		} else {
			Intent in = new Intent(Allcatogoriesview.this,searchresult.class);
			in.putExtra("searchqry", "");
			in.putExtra("catid", catogaryid[position]);
			in.putExtra("catnam", catagoryname[position]);
			startActivity(in);
			finish();
		}
		
		
		
		super.onListItemClick(l, v, position, id);
	}

	String[] catagoryname;
	
	int[] catogaryid;
	ListView lv;
	boolean[] catchild;
	int cat;
	
	 private ArrayAdapter<String> listAdapter ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allcatogoriesview);
        Intent icat=getIntent();
        cat=icat.getIntExtra("catid", 0);
        
        mContext=this;
        
        lv=getListView();
        
	fetchdata fdd=new fetchdata();
	fdd.execute("");
        
    }

	private void showProgressDialog() {
        
        pDlg   = new ProgressDialog(mContext);
        //  pDlg.setMessage(processMessage);
         // pDlg.setProgressDrawable(mContext.getWallpaper());
          pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
          pDlg.setCancelable(false);
          pDlg.show();

      }

    private class fetchdata extends AsyncTask<String , Integer, String>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			showProgressDialog();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=params[0];
			String res = "";
			
			try {
		    	 HttpClient httpclient = new DefaultHttpClient();
		    	        HttpPost httppost = new HttpPost("http://www.theminimall.com/Android.asmx/GetCategoryAndroid");
		    	       HttpResponse response;
		    	       
		    	       List<NameValuePair> param = new ArrayList<NameValuePair>();                
		               param.add(new BasicNameValuePair("CategoryId", String.valueOf(cat)));
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
			pDlg.dismiss();
		}

		


		
		


}

	public void handlresp(String result) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsobj=new JSONObject(result);
			
			JSONArray jaa=jsobj.getJSONArray("result");
			catagoryname=new String[jaa.length()];
			catogaryid=new int[jaa.length()];
			catchild=new boolean[jaa.length()];
			for (int i = 0; i < jaa.length(); i++) {
				JSONObject js=jaa.getJSONObject(i);
				catagoryname[i]=js.getString("CategoryName");
				catogaryid[i]=js.getInt("Id");
				catchild[i]=js.getBoolean("IsChildCategory");
			
				
			}
			
			
			ArrayList<String> cat = new ArrayList<String>();
			cat.addAll(Arrays.asList(catagoryname));
			
			listAdapter=new ArrayAdapter<String>(this,R.layout.abldylistagain,R.id.textforlist,catagoryname);
			lv.setAdapter(listAdapter);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
