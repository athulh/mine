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

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class Changeshippingadress extends Activity {
	String adid;
	int casehere;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeshippingadress);
        Intent in =getIntent();
        String ss=in.getStringExtra("adressid");
        againanasync agsc=new againanasync(againanasync.case1);
        agsc.execute("http://test.theminimall.com/Android.asmx/GetAddressById",ss);
        
    }
    
    
    public void changemybldyshipr(View v) {
    //	hideKeyboard();
    	EditText pinc=(EditText)findViewById(R.id.editspad_et_pincode);
    	if (pinc.length()<6) {
			Toast.makeText(getApplicationContext(), "please enter a valid pincode", Toast.LENGTH_LONG).show();
		} else {
			againanasync agsc=new againanasync(againanasync.case2);
	        agsc.execute("http://test.theminimall.com/Android.asmx/UpdateAddress",adid);

		}
	}

//1438 special city id
    
    private class againanasync extends AsyncTask<String	, Integer	, String	>{
String resString="";
public static final int case1=1;
public static final int case2=2;
int mycase;
public againanasync(int case12) {
	// TODO Auto-generated constructor stub
	this.mycase=case12;
	casehere=case12;
}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
String URL=params[0];
 adid=params[1];
			try {
		    	 HttpClient httpclient = new DefaultHttpClient();
		    	        HttpPost httppost = new HttpPost(URL);
		    	       HttpResponse response;
		    	       
		    	 
		             //  params.add(new BasicNameValuePair("PassWord", p));                    
		               UrlEncodedFormEntity ent = new UrlEncodedFormEntity(param(),HTTP.UTF_8);
				
		               
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
				    	          Log.e("here", sb.toString());
				    	          resString=sb.toString();
				    	    
				    	    
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		         
		    	
			return resString;
		}
		private List<? extends NameValuePair> param() {
			List<NameValuePair> param = new ArrayList<NameValuePair>(); 
			// TODO Auto-generated method stub
			switch (mycase) {
			case 1:
				               
	           param.add(new BasicNameValuePair("AddressId", adid));
				
				break;
			case 2:
				String adress,state,city,pincode;
			
				EditText addrs=(EditText)findViewById(R.id.editspad_et_adrss);
				EditText stat=(EditText)findViewById(R.id.editspad_et_state);
				EditText cit=(EditText)findViewById(R.id.editspad_et_city);
				EditText pinc=(EditText)findViewById(R.id.editspad_et_pincode);
				adress=addrs.getText().toString();
				state=stat.getText().toString();
				city=cit.getText().toString();
				pincode=pinc.getText().toString();
				
				
	           param.add(new BasicNameValuePair("AddressId", adid));
	           param.add(new BasicNameValuePair("CityId", "1438"));
	           param.add(new BasicNameValuePair("Street1",adress));
	           param.add(new BasicNameValuePair("Street2", city+","+state));
	           param.add(new BasicNameValuePair("PinCode", pincode));
				break;

			default:
				break;
			}
		      
 	       
			return param;
		}
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			handleit(result);
		}
		
		
		
		}

	public void handleit(String result) {
		// TODO Auto-generated method stub
		switch (casehere) {
		case 1:
			EditText addrs=(EditText)findViewById(R.id.editspad_et_adrss);
			EditText stat=(EditText)findViewById(R.id.editspad_et_state);
			EditText cit=(EditText)findViewById(R.id.editspad_et_city);
			EditText pinc=(EditText)findViewById(R.id.editspad_et_pincode);
			
			try {
				JSONObject jso=new JSONObject(result);
				JSONObject jj=jso.getJSONObject("result");
				if (jj.getInt("CityId")==1438) {
					addrs.setText(jj.getString("Street1"));
					String ath=jj.getString("Street2");
					
				String[]ad=	ath.split(",");
				cit.setText(ad[0]);
				stat.setText(ad[1]);
				pinc.setText(jj.getString("PinCode"));
				} else {

				}
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
case 2:
	
			try {
				JSONObject jso=new JSONObject(result);
				if (jso.getBoolean("isSucceeded")) {
					Toast.makeText(getApplicationContext(), "adress edited sucessfully", Toast.LENGTH_LONG).show();
					
					
				} else {
					Toast.makeText(getApplicationContext(), "error"+jso.getString("result"), Toast.LENGTH_LONG).show();
					
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		default:
			break;
		}
		
		
		
		
	}
    	
    	
    	
    
	  private void hideKeyboard() {
		  
	        InputMethodManager inputManager = (InputMethodManager) Changeshippingadress.this
	                .getSystemService(Context.INPUT_METHOD_SERVICE);
	 
	        inputManager.hideSoftInputFromWindow(
	                Changeshippingadress.this.getCurrentFocus()
	                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	    }
	     
    
}
