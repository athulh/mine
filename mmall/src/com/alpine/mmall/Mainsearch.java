package com.alpine.mmall;

import p.mine.tired.searchresult;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class Mainsearch extends Activity {
	  EditText et;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsearch);
        et=(EditText)findViewById(R.id.search_et_content);
        ImageButton ib=(ImageButton)findViewById(R.id.search_btn);
        ib.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String searchqry=et.getText().toString();
				Intent in = new Intent(Mainsearch.this,searchresult.class);
				in.putExtra("searchqry", searchqry);
				in.putExtra("catnam", "");
				in.putExtra("catid", 0);
				startActivity(in);
				finish();
				
			}
		});
      
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_mainsearch, menu);
        return true;
    }
}
