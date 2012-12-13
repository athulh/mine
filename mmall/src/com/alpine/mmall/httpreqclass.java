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

import android.util.Log;

public class httpreqclass {
	public String[] postData(String u, String p,String url) {
		String asp = null,data = null;
		String[] rtrn=new String[2];
		
    	//Log.e("i entrd here", "");
    	 
           
    	try {
    	 HttpClient httpclient = new DefaultHttpClient(getHttpParams());
    	        HttpPost httppost = new HttpPost(url);
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
	   

}
