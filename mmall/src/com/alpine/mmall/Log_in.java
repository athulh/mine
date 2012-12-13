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
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.alpine.mmall.Home.fetchdata;

import android.R.integer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Log_in extends Activity {
	String layt;
EditText et_uname,et_pass;
String uname,pass;
public long userid;
SharedPreferences app_preferences;
Context mContext;
public String username,password,session;

ProgressDialog pDlg;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        layt=getString(R.bool.lyt);
        
       // ProgressDialog pDlg=new ProgressDialog(this);
        mContext=this;
        
    	Toast.makeText(getApplicationContext(), layt, Toast.LENGTH_LONG).show();
       app_preferences = 	PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userid=app_preferences.getLong("userid",0);
        username=app_preferences.getString("username", "");
        
        password=app_preferences.getString("password", "");
        
        
        session=app_preferences.getString("session", "");

    	if (layt.equals("login")) {
    		et_uname=(EditText)findViewById(R.id.nrm_et_uname);
    		et_pass=(EditText)findViewById(R.id.nrm_et_pass);
    		
		} else if (layt.equals("login_large")) {
			
			et_uname=(EditText)findViewById(R.id.lrg_et_uname);
    		et_pass=(EditText)findViewById(R.id.lrg_et_pass);
			
		} 
		 else if (layt.equals("login_large_p")) {
			 
			 et_uname=(EditText)findViewById(R.id.lrgp_et_uname);
	    		et_pass=(EditText)findViewById(R.id.lrgp_et_pass);
				
			} 
		 else if (layt.equals("login_p")) {
			 
			 et_uname=(EditText)findViewById(R.id.nrmp_et_uname);
	    		et_pass=(EditText)findViewById(R.id.nrmp_et_pass);
				
			} 
		 else if (layt.equals("login_small")) {
			 
				et_uname=(EditText)findViewById(R.id.nrm_et_uname);
	    		et_pass=(EditText)findViewById(R.id.nrm_et_pass);
				
			} 
		
		 
		 else if (layt.equals("login_small_p")) {
			 
			 et_uname=(EditText)findViewById(R.id.smlp_et_uname);
	    		et_pass=(EditText)findViewById(R.id.smlp_et_pass);
				
			} 
		
		 
		 else if (layt.equals("login_xlarge")) {
				et_uname=(EditText)findViewById(R.id.lrg_et_uname);
	    		et_pass=(EditText)findViewById(R.id.lrg_et_pass);
				
			}  else if (layt.equals("login_xlarge_p")) {
				
				et_uname=(EditText)findViewById(R.id.xlrgp_et_uname);
	    		et_pass=(EditText)findViewById(R.id.xlrgp_et_pass);
				
			} 
    /*	et_uname.setText("dilip.mariya@gmail.com");
    	et_pass.setText("vipul123");*/
    	
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log_in, menu);
        return true;
    }
    private void showProgressDialog() {
        
      pDlg   = new ProgressDialog(mContext);
      //  pDlg.setMessage(processMessage);
       // pDlg.setProgressDrawable(mContext.getWallpaper());
        pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDlg.setCancelable(false);
        pDlg.show();

    }

    public void login_pst(View v){
    	
    	
    	uname=et_uname.getText().toString();
    	
    	pass=et_pass.getText().toString();
    
    	task dat=new task();
    	
    	dat.execute(uname,pass);
    	
    	Toast.makeText(getApplicationContext(), uname+":"+pass, Toast.LENGTH_LONG).show();
    	
    }
    class task extends AsyncTask<String, integer, Boolean>{
    	httpreqclass htrq= new httpreqclass();

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			showProgressDialog();
		}

		@Override
		protected Boolean doInBackground(String... params) {
			String usr=params[0];
			String p=params[1];
			String data[]=new String[2];
		String ur=	"http://theminimall.com/Android.asmx/Login";
		
			// TODO Auto-generated method stub
		data=	htrq.postData(usr,p,ur);
		
		boolean	sucess=false;
		try {
			JSONObject jso = new JSONObject(data[0]);
			sucess =jso.getBoolean("isSucceeded");
			if (sucess) {
				  SharedPreferences.Editor editor = app_preferences.edit();
				
				JSONObject jsr=  jso.getJSONObject("result");
				Long a= jsr.getLong("Id");
				editor.putLong("userid", a);
			        editor.putString("username", usr);
			        editor.putString("password", p);
			        editor.putString("session", data[1]);
			        
			     editor.commit(); // Very important
				
			}
			
		}catch (Exception e){
			
		}
			
			return sucess;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result) {
				fetchdata fd=new fetchdata();
				 fd.execute("");
			
			} else {
				Toast.makeText(getApplicationContext(), "invalid uname/pass", Toast.LENGTH_LONG).show();
				 pDlg.dismiss();
			}
			
			
		}
		
		
		
    }
    
    public String[] postData(String u, String p,String url) {
		String asp = null,data = null;
		String[] rtrn=new String[2];
		
    	//Log.e("i entrd here", "");
    	 
           
    	try {
    	 HttpClient httpclient = new DefaultHttpClient(getHttpParams());
    	        HttpPost httppost = new HttpPost();
    	       HttpResponse response;
    	       List<NameValuePair> params = new ArrayList<NameValuePair>();                
               params.add(new BasicNameValuePair("EmailId", u));
               params.add(new BasicNameValuePair("PassWord", p));                    
               UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
		
		httppost.setEntity(ent);
				response = httpclient.execute(httppost);
				Header[] headers;
			 headers = response.getHeaders("Set-Cookie");
				//Header atha= respone.getFirstHeader("PHPSESSID");
				 List<Cookie> cookies = ((AbstractHttpClient) httpclient).getCookieStore().getCookies();
				
				 if (cookies.isEmpty()) {
				    Log.d("TAG","no cookies received");
				 } else {
				    for (int i = 0; i < cookies.size(); i++) {
				       
				    	if(cookies.get(i).getName().contentEquals("ASP.NET_SessionId")) {
				         asp = cookies.get(i).getValue();
				       }
				    }
				    Log.e("this is the cookiee", asp);
				    }  
			
				   

			

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
		    	          rtrn[0]=sb.toString();
		    	          rtrn[1]=asp;
		    	    
		    	    
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return rtrn;

}
	  
	
	
	private HttpParams getHttpParams() {
	        
	        HttpParams htpp = new BasicHttpParams();
	         
	        HttpConnectionParams.setConnectionTimeout(htpp, 3000);
	        HttpConnectionParams.setSoTimeout(htpp, 5000);
	         
	        return htpp;
	    }
	   
	public class fetchdata extends AsyncTask<String , Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=params[0];
			String res = "";
			
			try {
		    	 HttpClient httpclient = new DefaultHttpClient();
		    	        HttpPost httppost = new HttpPost("http://theminimall.com/Android.asmx/GetTopCategoryProduct");
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
		 pDlg.dismiss();
		Log_in.this.finish();
		  Intent mainIntent = new Intent(Log_in.this, Home.class);
		  mainIntent.putExtra("data", result);
		  mainIntent.putExtra("ex", true);
         Log_in.this.startActivity(mainIntent);
		
	}

}
