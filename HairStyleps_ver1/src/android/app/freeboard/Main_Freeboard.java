package android.app.freeboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.hairstyle.R;
import android.app.service.ServiceHandler;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


/**
 * Custom ListView Example
 * http://croute.me/413
 * 
 * @author croute
 * @since 2011.05.04
 */
public class Main_Freeboard extends Activity implements OnClickListener
{
	View view;
	private String email;
	private ArrayList<BoardInfo> mBoardList;
	ArrayList<HashMap<String, String>> contactList;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView();R.layout.custom_list_view_example_activity
		setContentView(R.layout.board_custom_list_view_example_activity2);
		Button bSetListView = (Button)findViewById(R.id.writeBtn2);
		Button bRefresh = (Button)findViewById(R.id.custom_list_view_example_activity_b_refresh2);
		
		bSetListView.setOnClickListener(this);
		bRefresh.setOnClickListener(this);
		
		mBoardList = new ArrayList<BoardInfo>();
		new GetContacts().execute();
	}
	
	//GetContacts 
	public class GetContacts extends AsyncTask<Void, Void, Void> {

		private static final String TAG_FREEBOARD = "board";
		private ProgressDialog pDialog;
		JSONArray products = null;
		private BoardInfo it;
		String url="http://54.64.53.133/board/board_json.php";
		
		private String TAG_EMAIL = "email";
		private String TAG_COMMENT = "comment";
		private String TAG_TIME = "time";
		private String TAG_INDEX = "index";
		List<BoardInfo> its;
		private ListView listview;
	    @Override
	    protected void onPreExecute() {
	       super.onPreExecute();
	       // showing progress dialog
	       
	       pDialog = new ProgressDialog(Main_Freeboard.this);
	       pDialog.setMessage("Please wait...");
	       pDialog.setCancelable(false);
	       it = new BoardInfo();
	    	contactList = new ArrayList<HashMap<String, String>>();
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


	               JSONObject jsonObj = new JSONObject(jsonStr);

	               // Getting JSON Array node
	               products = jsonObj.getJSONArray(TAG_FREEBOARD);

	             // looping through All Contacts

	             for (int i = products.length()-1; i > -1; i--) {
	                JSONObject p = products.getJSONObject(i);
	                
	                it.setIndex(p.getInt(TAG_INDEX));
	                it.setEmail(p.getString(TAG_EMAIL));
	                it.setComment(p.getString(TAG_COMMENT));
	                it.setTime(p.getString(TAG_TIME));

	                 

	                // tmp hashmap for single contact
	                HashMap<String, String> contact = new HashMap<String, String>();

	                // adding each child node to HashMap key => value
	                
	                contact.put(TAG_INDEX, Integer.toString(it.getIndex()));
	                contact.put(TAG_EMAIL, it.getEmail());
	                contact.put(TAG_COMMENT, it.getComment());
	                contact.put(TAG_TIME, it.getTime());

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
	       listview = (ListView)findViewById(R.id.custom_list_view_example_activity_lv_list2);
	       if (pDialog.isShowing())
	          pDialog.dismiss();

	       its = new ArrayList<BoardInfo>();
	       
	       CustomAdapter Boarditadapt = new CustomAdapter(Main_Freeboard.this, R.layout.board_list_view_row, contactList);
	       Log.e("----------------", "--------------------------------");
	       listview.setAdapter(Boarditadapt);	       
	       listview.setOnItemClickListener(mItemClickListener);
	    }

	 }
	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		
		case R.id.writeBtn2:
			write();
			break;
			
		// refresh ��ư Ŭ��
		case R.id.custom_list_view_example_activity_b_refresh2:
			new GetContacts().execute();
			Toast.makeText(Main_Freeboard.this, "���ΰ�ħ ����", Toast.LENGTH_SHORT).show();
			break;
			
		}
	}

	private void write() {
		Intent in = new Intent(Main_Freeboard.this.getApplicationContext(), Write.class);
		in.putExtra("email", email);
		startActivity(in);
		
	}
	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long l_position) {
        	HashMap<String, String> it = contactList.get(position);
			
			Bundle extras = new Bundle();
			extras.putString("comment", it.get("comment"));
			extras.putString("email", it.get("email"));
			extras.putString("time", it.get("time"));
			extras.putString("index", it.get("index"));
			Intent intent = new Intent(Main_Freeboard.this.getApplicationContext(), ViewBoard.class);
			intent.putExtras(extras);
			startActivity(intent);
			
        }
    };
}