package customlist;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.alpine.mmall.R;



public class cartlist  extends BaseAdapter {
	
	
	String[] ProductName,DeliveryBy;
	int[] price,Qantity;
	
	private Activity activity;
	  private static LayoutInflater inflater=null;
	
  public cartlist(Activity a,String[]Productname,String[]deliveryBy,int[] pric, int[] Qatity ) {
		
		this.activity=a;
		
		this.ProductName=Productname;
		this.DeliveryBy=deliveryBy;
		this.price=pric;
		this.Qantity=Qatity;
		
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
	
    public static class ViewHolded{
        public TextView textqty;
        public TextView textprc;
        public TextView textmd;
        public TextView textprdct;
      
        
    }

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	    View vi=convertView;
        ViewHolded holder;
        if(convertView==null){
            vi = inflater.inflate(R.layout.cartsview, null);
            holder=new ViewHolded();
            holder.textprdct=(TextView)vi.findViewById(R.id.cartpnam);
            holder.textmd=(TextView)vi.findViewById(R.id.cartdm);
            holder.textqty=(TextView)vi.findViewById(R.id.cartqty);
            holder.textprc=(TextView)vi.findViewById(R.id.carttp);
          
            
            
            vi.setTag(holder);
        }
        else
            holder=(ViewHolded)vi.getTag();
        holder.textprdct.setText(ProductName[position]);
        holder.textmd.setText(DeliveryBy[position]);
        holder.textqty.setText(String.valueOf(Qantity[position]));
        holder.textprc.setText(String.valueOf(price[position]));
        
        
     

      
        
        return vi;
	}

}
