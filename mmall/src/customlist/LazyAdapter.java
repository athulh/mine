package customlist;

import java.util.ArrayList;
import java.util.HashMap;


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
    private String[] data;
    private ArrayList<HashMap<String, String>> dat;
    
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> save,String[] images) {
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

    public int getCount() {
        return data.length;
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
		return parent;
 /*      View vi=convertView;
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
        
        holder.text.setText(dat.get(position).get("NAME"));
        holder.image.setTag(data[position]);
        imageLoader.DisplayImage(data[position], activity, holder.image);
        
        return vi;*/
        
    }
}