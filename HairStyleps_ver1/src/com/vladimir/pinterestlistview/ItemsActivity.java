package com.vladimir.pinterestlistview;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.category.Category;
import android.app.hairstyle.R;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.vladimir.pinterestlistview.adapters.Sibal;

public class ItemsActivity extends Activity {
	
	private ListView listViewLeft;
	private ListView listViewRight;
	
	private Sibal sibaleft;
	private Sibal sibalright;
	
	private final int REQ_CODE_GALLERY = 100; 
	Uri uri;
	Bitmap selPhoto;
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;

	private ActionBarDrawerToggle mDrawerToggle;
	private String[] mMenulist;
	private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    
    private boolean touch = false;
	
	int[] leftViewsHeights;
	int[] rightViewsHeights;
	
	private Button button;

	//Json
	private static String url = "http://54.64.53.133/image/main_image.php";
	
	private static final String TAG_HAIRAPP = "main";
	private static final String TAG_IMAGEURL = "hair_image";
	private static final String TAG_GENDER = "user_gender";
	private static final String TAG_LENGTH = "length";
	private static final String TAG_color = "color";
	private static final String TAG_PERM = "perm";
	
	JSONArray products = null;
	
	ProgressDialog pDialog;	
	JSONObject p;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.items_list);
		
		
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		ActionBar ab = getActionBar();
		//ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
		ab.setDisplayShowTitleEnabled(true);
		ab.setDisplayShowHomeEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);
		
        
		//Ansynctask
        GetContacts get = new GetContacts();
        get.execute();
		
		listViewLeft = (ListView) findViewById(R.id.list_view_left);
		listViewRight = (ListView) findViewById(R.id.list_view_right);
		
		//loadItems();
		
		listViewLeft.setOnTouchListener(touchListener);
		listViewLeft.setOnScrollListener(scrollListener);
		listViewRight.setOnScrollListener(scrollListener);

		//button = (Button) findViewById(R.id.plus_button);

		listViewLeft.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// Sending image id to FullScreenActivity
				try {
					p = products.getJSONObject(position*2);
					Intent i = new Intent(getApplicationContext(), FullImageActivity.class);
					// passing array index
					i.putExtra("id", position*2);
					i.putExtra("length", mThumbIds.length);
					i.putExtra("item", p.getString(TAG_IMAGEURL));
					startActivity(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		listViewRight.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// Sending image id to FullScreenActivity
				if(touch == true){
					touch = false;
					return;
				}
				
				try {
					p = products.getJSONObject(1+position*2);
					Intent i = new Intent(getApplicationContext(), FullImageActivity.class);
					// passing array index
					i.putExtra("id", 1+position*2);
					i.putExtra("length", mThumbIds.length);
					i.putExtra("item", p.getString(TAG_IMAGEURL));
					startActivity(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});


		mTitle = mDrawerTitle = getTitle();
		Log.d(mDrawerTitle+"", mTitle+"");
        mPlanetTitles = getResources().getStringArray(R.array.menu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        
        
        
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
       
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item, mPlanetTitles));
       // mDrawerList.setOnClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        /*getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
*/
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  
                mDrawerLayout,         
                R.drawable.ic_drawer,  
                R.string.drawer_open,  
                R.string.drawer_close  
                ) {
            public void onDrawerClosed(View view) {
               // getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
              //  getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        
        mDrawerLayout.setDrawerListener(mDrawerToggle);

       
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
	}
	

	OnTouchListener touchListener = new OnTouchListener() {					
		boolean dispatched = false;
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (v.equals(listViewLeft) && !dispatched) {
				dispatched = true;
				touch = true;
				listViewRight.dispatchTouchEvent(event);
			} else if (v.equals(listViewRight) && !dispatched) {
				dispatched = true;
				listViewLeft.dispatchTouchEvent(event);
			}
			
			dispatched = false;
			return false;
		}
	};
	
	
	OnScrollListener scrollListener = new OnScrollListener() {
		
		@Override
		public void onScrollStateChanged(AbsListView v, int scrollState) {	
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			
			if (view.getChildAt(0) != null) {
				if (view.equals(listViewLeft) ){
					leftViewsHeights[view.getFirstVisiblePosition()] = view.getChildAt(0).getHeight();
					
					int h = 0;
					for (int i = 0; i < listViewRight.getFirstVisiblePosition(); i++) {
						h += rightViewsHeights[i];
					}
					
					int hi = 0;
					for (int i = 0; i < listViewLeft.getFirstVisiblePosition(); i++) {
						hi += leftViewsHeights[i];
					}
					
					int top = h - hi + view.getChildAt(0).getTop();
					listViewRight.setSelectionFromTop(listViewRight.getFirstVisiblePosition(), top);
				} else if (view.equals(listViewRight)) {
					rightViewsHeights[view.getFirstVisiblePosition()] = view.getChildAt(0).getHeight();
					
					int h = 0;
					for (int i = 0; i < listViewLeft.getFirstVisiblePosition(); i++) {
						h += leftViewsHeights[i];
					}
					
					int hi = 0;
					for (int i = 0; i < listViewRight.getFirstVisiblePosition(); i++) {
						hi += rightViewsHeights[i];
					}
					
					int top = h - hi + view.getChildAt(0).getTop();
					listViewLeft.setSelectionFromTop(listViewLeft.getFirstVisiblePosition(), top);
				}
				
			}
			
		}
	};
	
	
	public Integer[] mThumbIds = {
			/*R.drawable.ic_1, R.drawable.ic_2,
			R.drawable.ic_3, R.drawable.ic_4,
			R.drawable.ic_5, R.drawable.ic_6,
			R.drawable.ic_7, R.drawable.ic_8,
			R.drawable.ic_9, R.drawable.ic_10,
			*/
	};
	
	private void loadItems(){
		
		
		Log.i("products length",""+products.length());
		if(p == null) Log.e("pnull","aaaaaaaaaaaaaaa");
		
		sibaleft = new Sibal(this, R.layout.item, products, p);
		sibalright = new Sibal(this, R.layout.item, products, p);
		sibaleft.notifyDataSetChanged();
		sibalright.notifyDataSetChanged();
		listViewLeft.setAdapter(sibaleft);
		
		
		listViewRight.setAdapter(sibalright);
		
		leftViewsHeights = new int[products.length()];
		rightViewsHeights = new int[products.length()];	
	
	}
	
	
	/////drawer 
	
	  @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.main, menu);
	        return super.onCreateOptionsMenu(menu);
	    }

	     
	    @Override
	    public boolean onPrepareOptionsMenu(Menu menu) {
	        // If the nav drawer is open, hide action items related to the content view
	        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
	        menu.findItem(R.id.menu_search).setVisible(true);
	        return super.onPrepareOptionsMenu(menu);
	    }


	   
	   
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	         
	        switch(item.getItemId()) {
	        case R.id.menu_search:
	        	Intent intent = new Intent(ItemsActivity.this, SearchActivity.class);
				startActivity(intent);

	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }
/*
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && null != data){
			final Uri selectImageUri = data.getData();
			final String[] filePathColumn = {MediaStore.Images.Media.DATA};
			
			final Cursor imageCursor = this.getContentResolver().query(selectImageUri, filePathColumn, null, null, null);
			imageCursor.moveToFirst();
			
			final int columnIndex = imageCursor.getColumnIndex(filePathColumn[0]);
			final String imagePath = imageCursor.getString(columnIndex);
			imageCursor.close();
			
			bitmap = BitmapFactory.decodeFile(imagePath);
		}
	}*/
	
    /* The click listner for ListView in the navigation drawer */
	
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
            Log.d("adaf",position+"d");
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        // update selected item and title, then close the drawer

        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
        Log.d("aa","aaaaa");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    
    /*
    public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            String planet = getResources().getStringArray(R.array.planets_array)[i];

            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                            "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(planet);
            return rootView;
        }
    }
	*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == REQ_CODE_GALLERY){
			if(resultCode == RESULT_OK){
				uri = data.getData();
				try {
					selPhoto = Images.Media.getBitmap(getContentResolver(), uri);
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
				Intent in = new Intent(ItemsActivity.this, Category.class);
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
    
    //Ansyctask
    
private class GetContacts extends AsyncTask<Void, Void, Void> {

		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//showing progress dialog
			pDialog = new ProgressDialog(ItemsActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			//pDialog.show();
		}
		
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			
			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
			Log.d("Aaaa",url);
			Log.d("Response: ",">"+jsonStr);
			
			if(jsonStr != null){
				try{
					
					JSONObject jsonObj = new JSONObject(jsonStr);
					
					// Getting JSON Array node
					products = jsonObj.getJSONArray(TAG_HAIRAPP);
					
					// looping through All Contacts
					
					for(int i=0; i<products.length();i++){
						p = products.getJSONObject(i);
					
						//it.setUser_profile(p.getString(TAG_USERPROFILE));
						p.getString(TAG_IMAGEURL);
						p.getString(TAG_GENDER);
						p.getString(TAG_LENGTH);
						p.getString(TAG_color);
						p.getString(TAG_PERM);
						Log.e(TAG_IMAGEURL, p.getString(TAG_IMAGEURL));
						HashMap<String, String> contact = new HashMap<String, String>();
						
						// adding each child node to HashMap key => value
						
						contact.put(TAG_IMAGEURL,p.getString(TAG_IMAGEURL));		
					}
				} catch(JSONException e){
					e.printStackTrace();
				}
			} else{
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}
			return null;
			
		}
		
		@Override
		protected void onPostExecute(Void result){
			super.onPostExecute(result);	
			if(pDialog.isShowing())
				pDialog.dismiss();
			loadItems();
			
		}		
		
		
	}
	


	
	
}
    
    
    


