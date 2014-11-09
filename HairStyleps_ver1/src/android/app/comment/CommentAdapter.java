package android.app.comment;

import java.util.ArrayList;
import java.util.HashMap;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.app.Activity;
import android.app.hairstyle.R;
import android.app.networkimage.VolleySingleton;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class CommentAdapter extends BaseAdapter  {

	private Activity activity;
	private LayoutInflater inflater;
	private ImageLoader mImageLoader;
	ArrayList<HashMap<String, String>> adapterlist;
	int commentInflater;
	String url;
	private int count;
	
	
	public CommentAdapter(CommentActivity commentActivity, int commentInflater,
			ArrayList<HashMap<String, String>> contactList, String url, int index_count) {
		// TODO Auto-generated constructor stub
		
		this.activity = commentActivity;
		this.commentInflater = commentInflater;
		this.adapterlist = contactList;
		this.url = url;		
		Log.d("construct", "zzzzzzzzzzzz");
		this.count = index_count;
		
	}
/*
	public int list_count(){
		for(int i=0;i<adapterlist.size();i++){
			if(url.equals(adapterlist.get(i).get("hair_url"))){
				count++;
			}
			Log.i("count zzzz", Integer.toString(count));
			Log.i("count zzzz", Integer.toString(adapterlist.size()));
		}
		return count;
	}*/

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// 리스트 갯수
		
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
		if(inflater == null)
			inflater =(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
		if(convertView == null){
			convertView = inflater.inflate(commentInflater,parent,false);
		}
		//if(url.equals(adapterlist.get(position).get("hair_url"))){	
		
		mImageLoader = VolleySingleton.getInstance(activity).getImageLoader();
		
		NetworkImageView profile_image = (NetworkImageView)convertView.findViewById(R.id.comment_profile_image);
		//TextView comment_user_name = (TextView)convertView.findViewById(R.id.comment_user_name);
		TextView comment_comment = (TextView)convertView.findViewById(R.id.comment_comment);
		
		profile_image.setImageUrl("https://graph.facebook.com/"+adapterlist.get(position).get("id")+"/picture", mImageLoader);
		comment_comment.setText(adapterlist.get(position).get("comment"));
		
		Log.d("skljdhfaklsdflksj", adapterlist.get(position).get("hair_url"));
	
		
		return convertView;
	}
}
