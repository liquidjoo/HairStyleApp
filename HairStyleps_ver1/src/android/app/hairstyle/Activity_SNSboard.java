package android.app.hairstyle;

import android.app.Activity;




import android.app.adapter.itemadpater;
import android.app.category.Category;
import android.app.image_process.Image_click;
import android.app.profile.Profile;
import android.app.service.ServiceHandler;
import android.app.service.item;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vladimir.pinterestlistview.ItemsActivity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.service.ServiceHandler;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_SNSboard extends Activity {

	private final int REQ_CODE_GALLERY = 100; 
	private GridView gridview;
	private String c_id,c_name,c_gender;
	Button button;

	// URL to get contacts JSON
	private static String url = "http://54.64.53.133/image/main_image.php";

	// JSON Node names
	private static final String TAG_HAIRAPP = "main";
	private static final String TAG_USER_ID = "user_id";
	private static final String TAG_IMAGEURL = "hair_image";
	private static final String TAG_EMAIL = "user_email";
	private static final String TAG_NAME = "user_name";
	private static final String TAG_GENDER = "user_gender";
	private static final String TAG_LENGTH = "length";
	private static final String TAG_color = "color";
	private static final String TAG_PERM = "perm";

	// contacts JSONArray
	JSONArray products = null;

	item it;

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> contactList;

	// Bitmap
	ImageView img;
	Bitmap bitmap;
	ProgressDialog pDialog;

	// get//set

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gridview);

		it = new item();

		contactList = new ArrayList<HashMap<String, String>>();
		
		Intent intent = getIntent();		
		c_id = intent.getExtras().getString("id");
		c_name = intent.getExtras().getString("name");
		c_gender = intent.getExtras().getString("gender");
		
		
		
		
		button = (Button) findViewById(R.id.grid_plus_button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, REQ_CODE_GALLERY);
			}
		});
		/* ListView lv = ; */
		Log.d("Aaaa", "ddd");
		new GetContacts().execute();

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == REQ_CODE_GALLERY){
			if(resultCode == RESULT_OK){
				Uri uri = data.getData();
				try {
					Bitmap selPhoto = Images.Media.getBitmap(getContentResolver(), uri);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String path = getPathFromUri(uri);
				Cursor c = getContentResolver().query(Uri.parse(uri.toString()), null,null,null,null);
			    c.moveToNext();
			    String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
				Intent in = new Intent(Activity_SNSboard.this, Category.class);
				in.putExtra("uri", path);
				in.putExtra("path", absolutePath);
				startActivity(in);
			}
		}
	}
	public String getPathFromUri(Uri uri){
		Cursor cursor = getContentResolver().query(uri, null, null, null, null );
		cursor.moveToNext(); 
		String path = cursor.getString( cursor.getColumnIndex(MediaStore.MediaColumns.DATA) );
		cursor.close();


		return path;
		}
	

	private class GetContacts extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// showing progress dialog
			pDialog = new ProgressDialog(Activity_SNSboard.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			// pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST);
			Log.d("Aaaa", url);
			Log.d("Response: ", ">" + jsonStr);

			if (jsonStr != null) {
				try {

					JSONArray json = new JSONArray(jsonStr);

					// Getting JSON Array node
					//products = jsonObj.getJSONArray("");

					// looping through All Contacts

					for (int i = 0; i < json.length(); i++) {
						JSONObject p = json.getJSONObject(i);

						it.setHiar_image(p.getString(TAG_IMAGEURL));
						it.setUser_id(p.getString(TAG_USER_ID));
						it.setUser_gender(p.getString(TAG_GENDER));
						it.setLength(p.getString(TAG_LENGTH));
						it.setColor(p.getString(TAG_color));
						it.setPerm(p.getString(TAG_PERM));
						it.setUser_name(p.getString(TAG_NAME));
						it.setUser_email(p.getString(TAG_EMAIL));

						// Object가 나눠져있을때
						/*
						 * // Phone node is JSON Object JSONObject phone =
						 * c.getJSONObject(TAG_PHONE); String mobile =
						 * phone.getString(TAG_PHONE_MOBILE); String home =
						 * phone.getString(TAG_PHONE_HOME); String office =
						 * phone.getString(TAG_PHONE_OFFICE);
						 */

						// tmp hashmap for single contact
						HashMap<String, String> contact = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						contact.put(TAG_IMAGEURL, p.getString(TAG_IMAGEURL));
						contact.put(TAG_USER_ID, p.getString(TAG_USER_ID));
						contact.put(TAG_GENDER, p.getString(TAG_GENDER));
						contact.put(TAG_LENGTH, p.getString(TAG_LENGTH));
						contact.put(TAG_color, p.getString(TAG_color));
						contact.put(TAG_PERM, p.getString(TAG_PERM));
						contact.put(TAG_NAME, p.getString(TAG_NAME));
						contact.put(TAG_EMAIL, p.getString(TAG_EMAIL));

						contactList.add(contact);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			gridview = (GridView) findViewById(R.id.gridView1);

			if (pDialog.isShowing())
				pDialog.dismiss();

			/*
			 * ListAdapter adapter = new SimpleAdapter(MainActivity.this,
			 * contactList,R.layout.list_item, new String[]
			 * {TAG_NAME,TAG_GENDER,TAG_LENGTH,TAG_IMAGEURL},new int[]
			 * {R.id.user_name
			 * ,R.id.user_gender,R.id.user_hair_length,R.id.hair_image} );
			 * 
			 * setListAdapter(adapter);
			 */
			Collections.reverse(contactList);
			itemadpater itadapt = new itemadpater(Activity_SNSboard.this,
					R.layout.activity_inflater_item, contactList);
			Log.e("----------------", "--------------------------------");
			gridview.setAdapter(itadapt);
			
			itadapt.notifyDataSetChanged();			
			gridview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					//parent.getAdapter().getItem(position);
					
					Intent intent = new Intent(Activity_SNSboard.this,
							Image_click.class);
					intent.putExtra("url", contactList.get(position).get(TAG_IMAGEURL));
					intent.putExtra("id", contactList.get(position).get(TAG_USER_ID));
					intent.putExtra("name",contactList.get(position).get(TAG_NAME));
					intent.putExtra("email", contactList.get(position).get(TAG_EMAIL));
					intent.putExtra("gender", contactList.get(position).get(TAG_GENDER));
					intent.putExtra("c_id", c_id);
					Log.d("c_idididididi", c_id);
					intent.putExtra("c_name", c_name);
					intent.putExtra("c_gender", c_gender);
					
					startActivity(intent);

				}
			});

		}
		

	}

}
