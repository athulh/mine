package com.alpine.mmall;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class Payment extends Activity {
	//Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        WebView wv=(WebView)findViewById(R.id.paymentwebv);
        Intent iin=getIntent();
       String res= iin.getStringExtra("res");
      wv.getSettings().setJavaScriptEnabled(true);
   
        wv.loadUrl("http://payment.theminimall.com/Data.aspx?Value="+res);
        
        
        
    }

 
}
