package android.app.comment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.ImageLoader;

import android.app.Activity;
import android.app.hairstyle.R;
import android.app.hairstyle.R.layout;
import android.app.networkimage.VolleySingleton;
import android.app.service.ServiceHandler;
import android.content.Intent;
import android.graphics.Paint.Join;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class CommentActivity extends Activity {


	
	private ImageLoader mImageLoader;	
	private String url,name,id,email,gender;
	
	private int index_count=0;
	
	
	private Button submitbtn,like_btn;
	private EditText commentEdit;
	CommentServiceHandler csv = new CommentServiceHandler();
	CommentServiceHandler usv = new CommentServiceHandler();
	
	private static final String TAG_ID ="id";
	private static final String TAG_HAIR_URL ="hair_url";
	private static final String TAG_COMMENT = "comment";
	private static final String TAG_LIKE_COUNT ="like_count";
	
	ArrayList<HashMap<String, String>> contactList;
	ArrayList<HashMap<String, String>> samevalue_list;
	ListView listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		
		contactList = new ArrayList<HashMap<String, String>>();
		samevalue_list = new ArrayList<HashMap<String,String>>();
		
		commentEdit = (EditText)findViewById(R.id.edit_comment);
		submitbtn = (Button)findViewById(R.id.submit_button);
		like_btn = (Button)findViewById(R.id.Like_button);
		
		mImageLoader = VolleySingleton.getInstance(CommentActivity.this).getImageLoader();
		
		Intent intent = getIntent();		
		url = intent.getExtras().getString("url");
		name = intent.getExtras().getString("c_name");
		id = intent.getExtras().getString("c_id");		
		gender = intent.getExtras().getString("c_gender");
		Log.d("c_idididididi", id);
		
		
		new GetComments().execute();
		
		
		
		
		submitbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				csv.setUrl("http://54.64.53.133/comment/commentInsert.php");
				csv.setHair_url(url);
				csv.setId(id);
				csv.setComment(commentEdit.getText().toString());				
				csv.execute();
				Toast ts =Toast.makeText(getApplicationContext(), "댓글 달기 완료", 100);
				ts.show();
				commentEdit.setText(null);
				
			}
		});
		
		like_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				usv.setUrl("http://54.64.53.133/comment/commentUpdate.php");
				usv.setHair_url(url);
				usv.setId(id);
				usv.execute();
				Toast ts1 =Toast.makeText(getApplicationContext(), "좋아욤", 100);
				ts1.show();
				
			}
		});
	}
	
	private class GetComments extends AsyncTask<Void, Void, Void>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		};
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			ServiceHandler service = new ServiceHandler();
			
			String jsonStr = service.makeServiceCall("http://54.64.53.133/comment/commentJSON.php", ServiceHandler.POST);
			
			Log.d("Response", ">" + jsonStr);
			
			
			if(jsonStr != null){
				try{
					JSONArray json_array = new JSONArray(jsonStr);
					
					
					for(int i=0; i<json_array.length();i++){
						JSONObject c = json_array.getJSONObject(i);
						
						HashMap<String, String> contact = new HashMap<String, String>();
						
						contact.put(TAG_ID, c.getString(TAG_ID));
						contact.put(TAG_HAIR_URL, c.getString(TAG_HAIR_URL));
						contact.put(TAG_COMMENT, c.getString(TAG_COMMENT));
						contact.put(TAG_LIKE_COUNT, c.getString(TAG_LIKE_COUNT));
						
						contactList.add(contact);						
					}
					
					
					
					
					Log.i("count aaaa", Integer.toString(index_count));
					
				}catch(JSONException e){
					e.printStackTrace();
					
				}
				
			} else {
				Log.e("servicehandler", "couldn't get any data from the url");
			}			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result){
			listview = (ListView)findViewById(R.id.list_comment);
			
			//Collections.reverse(samevalue_list);
			for(int i=0; i<contactList.size();i++){
				if(url.equals(contactList.get(i).get(TAG_HAIR_URL))){
					Log.d("check ddd", contactList.get(i).get(TAG_HAIR_URL));
					HashMap<String, String> same_value = new HashMap<String, String>();					
					same_value.put(TAG_ID, contactList.get(i).get(TAG_ID));
					same_value.put(TAG_HAIR_URL, contactList.get(i).get(TAG_HAIR_URL));
					same_value.put(TAG_COMMENT, contactList.get(i).get(TAG_COMMENT));
					same_value.put(TAG_LIKE_COUNT, contactList.get(i).get(TAG_LIKE_COUNT));
					Log.d("check ddd", same_value.get(TAG_ID));
					Log.d("check ddd", same_value.get(TAG_HAIR_URL));
					Log.d("check ddd", same_value.get(TAG_COMMENT));
					Log.d("check ddd", same_value.get(TAG_LIKE_COUNT));
					samevalue_list.add(same_value);
					
				}
			}
			
			
			CommentAdapter commentadapter = new CommentAdapter(CommentActivity.this,R.layout.comment_inflater,samevalue_list,url,index_count);
			//index_count=0;
			listview.setAdapter(commentadapter);
		}
		
		
		
	}
}
