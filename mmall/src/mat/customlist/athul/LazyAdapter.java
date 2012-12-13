package mat.customlist.athul;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.alpine.mmall.R;






import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private LinkedList<String> data;
    private LinkedList<String> dat;
    int ath;
    
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter(Activity a, LinkedList<String>save,LinkedList<String> images) {
        activity = a;
       // Toast.makeText(LazyAdapter.this, "try again", Toast.LENGTH_LONG).show();
       // for (int i = 0; i < mylist.size(); i++) {	
      // dat.add(mylist.get(i).get("photopath"));
       
  	  //data[i]=mylist.get(i).get("photopath");
       //}
     
        dat=save;
        data=images;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

  

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public static class ViewHolder{
        public TextView text;
        public ImageView image;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        ViewHolder holder;
        if(convertView==null){
            vi = inflater.inflate(R.layout.item, null);
            holder=new ViewHolder();
            holder.text=(TextView)vi.findViewById(R.id.text);;
            holder.image=(ImageView)vi.findViewById(R.id.image);
            vi.setTag(holder);
        }
        else
            holder=(ViewHolder)vi.getTag();
        
        holder.text.setText(dat.get(position));
       holder.image.setTag(data.get(position));
       imageLoader.DisplayImage(data.get(position), activity, holder.image);
        
        return vi;
        
    }

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
	
		super.notifyDataSetChanged();
	}



	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}
}