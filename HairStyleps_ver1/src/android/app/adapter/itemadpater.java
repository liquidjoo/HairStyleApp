package android.app.adapter;

import java.io.IOException;



import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import android.app.hairstyle.MainActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.hairstyle.R;
import android.app.image_process.Image_click;
import android.app.networkimage.VolleySingleton;
import android.app.service.LoadImage;
import android.app.service.item;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class itemadpater extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	private ImageLoader mImageLoader;
	ArrayList<HashMap<String, String>> adapterlist;
	int list_item;
	String name,id,email;
	

	public itemadpater() {

	}

	public itemadpater(Activity activity2, int list,
			ArrayList<HashMap<String, String>> contactList) {
		// TODO Auto-generated constructor stub
		this.activity = activity2;
		list_item = list;
		this.adapterlist = contactList;
		Log.i("adapter", "aaaaaaaa");
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = inflater.inflate(list_item, null);
		}
		

		mImageLoader = VolleySingleton.getInstance(activity).getImageLoader();

		TextView user_name = (TextView) convertView
				.findViewById(R.id.user_name);
		TextView user_gender = (TextView) convertView
				.findViewById(R.id.user_gender);
		TextView user_email = (TextView)convertView.findViewById(R.id.user_email);
		
		NetworkImageView pv = (NetworkImageView)convertView.findViewById(R.id.profile_image);

		NetworkImageView iv = (NetworkImageView) convertView
				.findViewById(R.id.main_hair_image);
		iv.setTag(position);
		HashMap<String, String> it = adapterlist.get(position);
		/*user_name.setText(it.get("user_name"));
		user_gender.setText(it.get("user_gender"));
		user_email.setText(it.get("user_email"));*/
		
		iv.setImageUrl(it.get("hair_image"), mImageLoader);
		
		//pv.setImageUrl("https://graph.facebook.com/"+it.get("user_id")+"/picture", mImageLoader);
		

		return convertView;
	}

}
