package android.app.hairstyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle.Control;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.ImageLoader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.adapter.itemadpater;
import android.app.image_process.Image_click;
import android.app.networkimage.VolleySingleton;
import android.app.service.ServiceHandler;
import android.app.service.ServiceHandler_sns;
import android.app.service.item;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends Fragment {

	private List<item> its;
	private ListView listview;
	itemadpater itadapt;
	// URL to get contacts JSON
	private static String url = "http://54.64.53.133/image/main_image.php";
	
	private String c_id,c_name,c_gender;

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

	public MainActivity(String c_id, String c_name, String c_gender) {
		// TODO Auto-generated constructor stub
		this.c_id = c_id;
		this.c_name = c_name;
		this.c_gender = c_gender;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);

		it = new item();

		contactList = new ArrayList<HashMap<String, String>>();

		Log.d("Aaaa", "ddd");
		new GetContacts().execute();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_main, null);
		return view;

	}

	private class GetContacts extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			// showing progress dialog
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			// pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			// Creating service handler class instance
			ServiceHandler_sns sh = new ServiceHandler_sns();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST);
			Log.d("Aaaa", url);
			Log.d("Response: ", ">" + jsonStr);

			if (jsonStr != null) {
				try {
					
					JSONArray json = new JSONArray(jsonStr);

					// Getting JSON Array node
					//products = jsonObj.getJSONArray(TAG_HAIRAPP);
					
					

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
			listview = (ListView) getView().findViewById(android.R.id.list);

			if (pDialog.isShowing())
				pDialog.dismiss();

			its = new ArrayList<item>();

			itadapt = new itemadpater(getActivity(), R.layout.list_item,
					contactList);

			Log.e("----------------", "--------------------------------");
			listview.setAdapter(itadapt);

			itadapt.notifyDataSetChanged();

			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(), Image_click.class);
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
