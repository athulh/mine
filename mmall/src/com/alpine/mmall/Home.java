package com.alpine.mmall;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import p.mine.tired.searchresult;






import customlist.ImageLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity {
	String[] imagename,prdctnam,prs;
	int[] prdctid;
	ImageView im1;
	String port;
	int[] img=new int[]{R.id.ImageView01,R.id.ImageView02,R.id.ImageView03,R.id.ImageView04,R.id.ImageView05,R.id.ImageView06};
	int[] tv=new int[]{R.id.hmscrntv_port1,R.id.hmscrntv_port2,R.id.hmscrntv_port3,R.id.hmscrntv_port4,R.id.hmscrntv_port5,R.id.hmscrntv_port6};
	
	int[] imgl=new int[]{R.id.lImageView01,R.id.lImageView02,R.id.limageView03,R.id.limageView04,R.id.limageView05,R.id.limageView06};
	int[] tvl=new int[]{R.id.hmscrn_landtv1,R.id.hmscrn_landtv2,R.id.hmscrn_landtv3,R.id.hmscrn_landtv4,R.id.hmscrn_landtv5,R.id.hmscrn_landtv6};
	
	
	ImageLoader imageLoader;
	Boolean sucess;
	 private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
		
	port=getString(R.bool.portr);
	
	activity=this;
		
		 imageLoader=new ImageLoader(getApplicationContext());
		 Intent in=getIntent();
		String dt= in.getStringExtra("data");
		
	
	
			handlresp(dt);
	//GetProductByProductId(Int64 ProductId)
		 
		/* fetchdata fd=new fetchdata();
		 fd.execute("");*/
			
 //-----------------------------------------------------------------------------------
		    
			
			final EditText et=(EditText)findViewById(R.id.search_et_content1);
	        ImageButton ib=(ImageButton)findViewById(R.id.search_btn1);
	        ib.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
				
					String searchqry=et.getText().toString();
					Intent in = new Intent(Home.this,searchresult.class);
					in.putExtra("searchqry", searchqry);
					in.putExtra("catnam", "");
					in.putExtra("catid", 0);
					startActivity(in);
					et.setText("");
					RelativeLayout rl=(RelativeLayout)findViewById(R.id.searchnow);
			    	rl.setVisibility(View.GONE);
			    	LinearLayout ll=(LinearLayout)findViewById(R.id.normlt);
			    	ll.setVisibility(View.VISIBLE);
					
				}
			});
	      
    }
    
    public void search(View v) {
		/*Intent in =new Intent(Home.this,Mainsearch.class);
		startActivity(in);*/
    	LinearLayout ll=(LinearLayout)findViewById(R.id.normlt);
    	ll.setVisibility(View.GONE);
    	RelativeLayout rl=(RelativeLayout)findViewById(R.id.searchnow);
    	rl.setVisibility(View.VISIBLE);
	}
    
    public void ohgoback(View v) {
    	RelativeLayout rl=(RelativeLayout)findViewById(R.id.searchnow);
    	rl.setVisibility(View.GONE);
    	LinearLayout ll=(LinearLayout)findViewById(R.id.normlt);
    	ll.setVisibility(View.VISIBLE);
	}
    public void allcat(View v) {
  		Intent in =new Intent(Home.this,Allcatogoriesview.class);
  		in.putExtra("catid", 0);
  		startActivity(in);
  	}
    //gokart
    public void gokart(View v) {
  		Intent in =new Intent(Home.this,Mycart.class);
  		//in.putExtra("catid", 0);
  		startActivity(in);
  	}
//useracc
    public void useracc(View v) {
  		Intent in =new Intent(Home.this,Userdetails.class);
  		//in.putExtra("catid", 0);
  		startActivity(in);
  	}
    //-----------------------------------------------------------------------------------
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }

	public void handlresp(String result) {
		// TODO Auto-generated method stub
	
		
		
		try {
			JSONObject jso = new JSONObject(result);
			sucess =jso.getBoolean("isSucceeded");
			if (sucess) {
				
				JSONArray ja =jso.getJSONArray("result");
				
				
				imagename=new String[ja.length()];
				prdctnam=new String[ja.length()];
				prdctid=new int[ja.length()];
				prs=new String[ja.length()];
				
				
				
				for (int i = 0; i < ja.length(); i++) {
					JSONObject jobj=ja.getJSONObject(i);
					imagename[i]=jobj.getString("ImageName");
					prdctid[i]=jobj.getInt("ProductId");
					prdctnam[i]=jobj.getString("ProductName");
					prs[i]=jobj.getString("Price");
				}
				
				
		
				if (port !="true") {
				//int	j=6;
					for (int i = 0; i < 6; i++) {
						
						im1=(ImageView)findViewById(img[i]);
						
						TextView tvv=(TextView)findViewById(tvl[i]);
						tvv.setText(prdctnam[i]);
						//new DownloadImageTask((ImageView)  im1).execute("http://www.theminimall.com/"+imagename[i]);
						im1.setTag("http://www.theminimall.com/"+imagename[i]);
				        imageLoader.DisplayImage("http://www.theminimall.com/"+imagename[i], activity, im1);
				      //  j--;
					}
				} else {
				//int	j=6;
					for (int i = 5; i >=0; i--) {
						
						im1=(ImageView)findViewById(imgl[i]);
						TextView tvv=(TextView)findViewById(tv[i]);
						tvv.setText(prdctnam[i]);
						
						im1.setTag("http://www.theminimall.com/"+imagename[i]);
				        imageLoader.DisplayImage("http://www.theminimall.com/"+imagename[i], activity, im1);
					}

				}
			
			
				
			} else {
				Toast.makeText(Home.this, "error", Toast.LENGTH_LONG).show();

			}
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	 public void prdctdtls(View v){
		 

	      
	        

		 
		// ((ImageView) v).setColorFilter(0xFFFF0000, Mode.OVERLAY);
		 for (int i = 0; i < 6; i++) {
			// Log.e("dd", String.valueOf(i));
			 if (img[i]==v.getId()) {
				
				Log.e("id", String.valueOf(prdctid[i]));
				Intent in=new Intent(Home.this,Prdct_dtls.class);
				in.putExtra("id", prdctid[i]);
				startActivity(in);
				break;
				
			}
			 if (imgl[i]==v.getId()) {
				 Log.e("id", String.valueOf(prdctid[i]));
				 Intent in=new Intent(Home.this,Prdct_dtls.class);
					in.putExtra("id", prdctid[i]);
					startActivity(in);
				break;
			}
			
			
		}
	 }


}
  class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
        /*    InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(new PatchInputStream(in));*/
            
            HttpGet httpRequest = new HttpGet(urldisplay);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = (HttpResponse) httpclient
                                .execute(httpRequest);
            HttpEntity entity = response.getEntity();
            BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(entity);
            InputStream is = bufferedHttpEntity.getContent();
           // Drawable d = Drawable.createFromStream(is, "");
            mIcon11=BitmapFactory.decodeStream(is);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }
    

/*    @Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		showProgressDialog();
	}*/

	protected void onPostExecute(Bitmap result) {
		//pDlg.dismiss();
        bmImage.setImageBitmap(result);
    }}
 class PatchInputStream extends FilterInputStream {
	  public PatchInputStream(InputStream in) {
	    super(in);
	  }
	  public long skip(long n) throws IOException {
	    long m = 0L;
	    while (m < n) {
	      long _m = in.skip(n-m);
	      if (_m == 0L) break;
	      m += _m;
	    }
	    return m;
	  }
	}

