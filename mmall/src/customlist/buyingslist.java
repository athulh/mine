package customlist;



import com.alpine.mmall.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class buyingslist extends BaseAdapter {
	
	
	String[] comp;
	int[] price,selrmtrx;
	boolean[][] options;
	private Activity activity;
	  private static LayoutInflater inflater=null;
	
	public  buyingslist(Activity a,String[]company,int[] pric,boolean[][] option, int[] sellerprdctmtrxid ) {
		
		this.activity=a;
		this.options=option;
		this.comp=company;
		this.price=pric;
		this.selrmtrx=sellerprdctmtrxid;
		
	    inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    
		
	}
	
	

	public int getCount() {
		// TODO Auto-generated method stub
		
		return price.length;
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
    public static class ViewHold{
        public TextView textsel;
        public TextView textp;
        public Button sop;
        public Button cod;
        public Button op;
        
    }

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	    View vi=convertView;
        ViewHold holder;
        if(convertView==null){
            vi = inflater.inflate(R.layout.buycas, null);
            holder=new ViewHold();
            holder.textsel=(TextView)vi.findViewById(R.id.tvv1);
            holder.textp=(TextView)vi.findViewById(R.id.tvvcd);
            holder.cod=(Button)vi.findViewById(R.id.cod);
            holder.sop=(Button)vi.findViewById(R.id.sp);
            holder.op=(Button)vi.findViewById(R.id.op);
            
            
            vi.setTag(holder);
        }
        else
            holder=(ViewHold)vi.getTag();
        
        holder.textsel.setText(comp[position]);
        holder.textp.setText("Rs  "+String.valueOf(price[position])+"/-");
        holder.cod.setTag(String.valueOf(selrmtrx[position]));
        Log.e("", String.valueOf(selrmtrx[position]));
        holder.op.setTag(String.valueOf(selrmtrx[position]));
        holder.sop.setTag(String.valueOf(selrmtrx[position]));
        if (!options[position][0]) {
			holder.cod.setVisibility(View.INVISIBLE);
			 
		}
        
        if (!options[position][1]) {
        	holder.op.setVisibility(View.INVISIBLE);
        	
		}
         if (!options[position][2]) {
        	 holder.sop.setVisibility(View.INVISIBLE);
        	 
	    }
      
        
        return vi;
	}

}
