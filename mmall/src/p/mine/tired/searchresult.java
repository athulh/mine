package p.mine.tired;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import mat.customlist.athul.LazyAdapter;

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

import com.alpine.mmall.Home;
import com.alpine.mmall.Prdct_dtls;
import com.alpine.mmall.R;





import android.R.integer;
import android.os.AsyncTask;
import android.os.Bundle;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class searchresult extends ListActivity {
	String ss,catnam;
	int catid;
	
	
	
	//int[] prdctid;
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		trimCache(this);
		super.onDestroy();
	}
ProgressDialog pDlg;
Context mContext;


	private LinkedList<String> mListItems,mListItems2;
	private LinkedList<Integer> prdctids;
	//String [] images= {"http://theminimall.com/images/update.png"};

	// The data to be displayed in the ListView
	
	
	int aaa= 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Intent searched=getIntent();
   ss=  searched.getStringExtra("searchqry");
   catid=searched.getIntExtra("catid", 0);
   catnam=searched.getStringExtra("catnam");
   Log.e("cat id:", String.valueOf(catid));
        
        mContext=this;
        
        mListItems = new LinkedList<String>();
        mListItems2 = new LinkedList<String>();
        prdctids = new LinkedList<Integer>();
        
       // mListItems2.addAll(Arrays.asList(images));
	//	mListItems.addAll(Arrays.asList(mNames));
		//
		LazyAdapter adap=new LazyAdapter(searchresult.this,mListItems,mListItems2);

	//	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
			//	android.R.layout.simple_list_item_1, mListItems);
		
		

		setListAdapter(adap);
		
		
		new LoadDataTask().execute(0);

		// set a listener to be invoked when the list reaches the end
		
	
		((LoadMoreListView) getListView()).setOnLoadMoreListener(new p.mine.tired.LoadMoreListView.OnLoadMoreListener() {
					public void onLoadMore() {
						// Do the work to load more items at the end of list
						// here
						new LoadDataTask().execute(0);
					}
				});
	
		
	}
    
    
    
    
    
    
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
    	
    	Toast.makeText(getApplication(), String.valueOf(position), Toast.LENGTH_LONG).show();
		Intent in=new Intent(searchresult.this,Prdct_dtls.class);
		in.putExtra("id", prdctids.get(position));
		startActivity(in);
		super.onListItemClick(l, v, position, id);
	}







	private void showProgressDialog() {
        
        pDlg   = new ProgressDialog(mContext);
        //  pDlg.setMessage(processMessage);
         // pDlg.setProgressDrawable(mContext.getWallpaper());
          pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
          pDlg.setCancelable(false);
          pDlg.show();

      }
	private class LoadDataTask extends AsyncTask<Integer, Void, Void> {
		
Boolean pd=false;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (aaa==0) {
				showProgressDialog();
				pd=true;
			}
		}

		protected Void doInBackground(Integer... params) {
			int ath=params[0];

			if (isCancelled()) {
				return null;
			}
String rslts = null;
			// Simulates a background task
			
			
			
			
			
		/*	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			for (int i = 0; i < mNames.length; i++)
				
			{	
				
				mListItems.add(mNames[i]);
				
			
			}
			*/
			try {
				
			/*	
				HttpClient httpclient = new DefaultHttpClient();
				
		    	        HttpPost httppost = new HttpPost("http://theminimall.com/Android.asmx/GetTop10ProductByCategory");
		    	       HttpResponse response;
		    	       List<NameValuePair> param = new ArrayList<NameValuePair>();    
		    	       param.add(new BasicNameValuePair("CategoryId", "10"));
		               param.add(new BasicNameValuePair("ProductId", String.valueOf(aaa))); 
		       //    */
		   // /*	
		        HttpClient httpclient = new DefaultHttpClient();
		    	        HttpPost httppost = new HttpPost("http://www.theminimall.com/Android.asmx/SearchProduct");
		    	       HttpResponse response;
		    	       List<NameValuePair> param = new ArrayList<NameValuePair>();
		    	       param.add(new BasicNameValuePair("SearchQuery", ss));
		    	       param.add(new BasicNameValuePair("CategoryName", catnam));
		    	   
						
					
		    	       param.add(new BasicNameValuePair("CategoryId",String.valueOf(catid)));
		    	       Log.e("", String.valueOf(aaa));
		               param.add(new BasicNameValuePair("ProductId", String.valueOf(aaa)));
		               
		           //     */
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
				    	        rslts=  sb.toString();
				    	    
				    	    
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		         
			
			try {
				JSONObject jso = new JSONObject(rslts);
				Boolean sucess =jso.getBoolean("isSucceeded");
				if (sucess) {
					
					JSONArray ja =jso.getJSONArray("result");
					
					
				/*	new String[ja.length()];
					prdctnam=new String[ja.length()];
					prdctid=new int[ja.length()];*/
					
					//prs=new int[ja.length()];
					
					if (ja.length()<1) {
						((LoadMoreListView) getListView()).bbcd=false;
					}else{
					for (int i = 0; i < ja.length(); i++) {
						JSONObject jobj=ja.getJSONObject(i);
						mListItems.add(jobj.getString("ProductName"));
					//	imagename[i]=
						mListItems2.add("http://www.theminimall.com/"+jobj.getString("ImageName"));
					//	prdctid[i]=jobj.getInt("ProductId");
						
						prdctids.add(jobj.getInt("ProductId"));
						aaa=jobj.getInt("ProductId");
						
					}}}}catch(Exception e){}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			//mListItems.add("Added after load more");
			
			if (pd) {
				pDlg.dismiss();
				pd=false;
			}

			// We need notify the adapter that the data have been changed
			((BaseAdapter) getListAdapter()).notifyDataSetChanged();

			// Call onLoadMoreComplete when the LoadMore task, has finished
			((LoadMoreListView) getListView()).onLoadMoreComplete();

			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			// Notify the loading more operation has finished
			((LoadMoreListView) getListView()).onLoadMoreComplete();
		}

	
	}



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    public static void trimCache(Context context) {
        try {
           File dir = context.getCacheDir();
           if (dir != null && dir.isDirectory()) {
              deleteDir(dir);
           }
        } catch (Exception e) {
           // TODO: handle exception
        }
     }

     public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
           String[] children = dir.list();
           for (int i = 0; i < children.length; i++) {
              boolean success = deleteDir(new File(dir, children[i]));
              if (!success) {
                 return false;
              }
           }
        }

        // The directory is now empty so delete it
        return dir.delete();
     }
}
