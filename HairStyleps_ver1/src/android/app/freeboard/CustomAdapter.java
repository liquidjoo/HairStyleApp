package android.app.freeboard;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.hairstyle.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter{
   
   private Activity activity;
   private LayoutInflater inflater;
   ArrayList<HashMap<String, String>> adapterlist;
   int list_item;
   
   public CustomAdapter(){
      
   }
   
   public CustomAdapter(Activity activity2, int list,
         ArrayList<HashMap<String, String>> contactList) {
      // TODO Auto-generated constructor stub
      this.activity = activity2;
      list_item = list;
      this.adapterlist=contactList;
      Log.i("adapter","aaaaaaaa");
   }
   

   @Override
   public int getCount() {
      // TODO Auto-generated method stub
      return adapterlist.size();
   }

   @Override
   public Object getItem(int position) {
      // TODO Auto-generated method stub
      return adapterlist.get(position);
   }

   @Override
   public long getItemId(int position) {
      // TODO Auto-generated method stub
      return position;
   }

   @Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	   if(inflater ==null)
	         inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	      
	      if(convertView ==null){
	         convertView = inflater.inflate(list_item, null);         
	      }
		
			ImageView ivThumbnail = (ImageView) convertView.findViewById(R.id.list_view_row_profile_thumbnail);
			TextView tvScreenName = (TextView) convertView.findViewById(R.id.list_view_row_user_screen_name);
			TextView tvCreatedAt = (TextView) convertView.findViewById(R.id.list_view_row_create_at);
			TextView tvText = (TextView) convertView.findViewById(R.id.list_view_row_text);
			Log.d("------------------------", "getView");

			HashMap<String, String> it = adapterlist.get(position);
			tvText.setText(it.get("comment"));
			tvScreenName.setText(it.get("email"));
			tvCreatedAt.setText(it.get("time"));
		

		return convertView;
	}  

}