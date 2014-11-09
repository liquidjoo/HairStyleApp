package com.vladimir.pinterestlistview.adapters;

import java.io.IOException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vladimir.pinterestlistview.ItemsActivity;
import android.app.hairstyle.R;

public class Sibal extends BaseAdapter {

	Context context; 
    LayoutInflater inflater;
    int layoutResourceId;
    float imageWidth;
   // float imageHeight;
    
    JSONObject p;
  
    ImageView itemImage;
    NetworkImageView aa;
    JSONArray products;
    int jsonarraynum;
    int num=1;
    
    Bitmap resized;
    static Bitmap bitmap;
    ItemHolder holder;
    
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    //NetworkImageView avatar;
    private static final String TAG_IMAGEURL = "image_url";
    
    static int checknum = 0;
	
	public Sibal(Context context, int layoutResourceId, JSONArray products, JSONObject p) {
		super();
		// TODO Auto-generated constructor stub
		this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.p = p;
        this.products = products;
        jsonarraynum = checknum;
        checknum++;
        if(checknum == 2) checknum = 0;
        float width = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();
        float margin = (int)convertDpToPixel(10f, (Activity)context);
        // two images, three margins of 10dips
		imageWidth = ((width - (3 * margin)) / 2);
		
		Log.i("Sibal","create");
		mImageLoader = VolleySingleton.getInstance(this.context).getImageLoader();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return products.length();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		try {
			return products.get(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		FrameLayout row = (FrameLayout) convertView;
    
        Log.i("position",""+position);
        Object item = getItem(position);
        
        Log.i("getView","dddddddddddddddddd");
		if (row == null) {
			holder = new ItemHolder();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = (FrameLayout) inflater.inflate(layoutResourceId, parent, false);
            //itemImage = (ImageView)row.findViewById(R.id.item_image);
            aa = (NetworkImageView)row.findViewById(R.id.item_image);
			//holder.itemImage = itemImage;
            holder.netimage = aa;
		} else {
			holder = (ItemHolder) row.getTag();
		}

			
			
			try {
				if(jsonarraynum < products.length()){
					JSONObject p = products.getJSONObject(jsonarraynum);
				String url = p.getString(TAG_IMAGEURL);
				Log.i("try","1");
				NetworkImageView avatar = (NetworkImageView) row.findViewById(R.id.item_image);
				Log.i("try","2");
				avatar.setImageUrl(url,mImageLoader);
				Log.i("try","3");
				}
				jsonarraynum = jsonarraynum + 2;;
				/*float i = ((float) imageWidth) / ((float) bitmap.getWidth());
				float imageHeight = i * (bitmap.getHeight());
				FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) itemImage.getLayoutParams();
				params.height = (int) imageHeight;
				params.width = (int) imageWidth;
				itemImage.setLayoutParams(params);
				holder.itemImage.setImageBitmap(resized);*/
				return row;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
		/*NetworkImageView avatar = (NetworkImageView) convertView.findViewById(R.id.item_image);
		avatar.setImageUrl("",mImageLoader);
		float i = ((float) imageWidth) / ((float) bitmap.getWidth());
		float imageHeight = i * (bitmap.getHeight());
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) itemImage.getLayoutParams();
		params.height = (int) imageHeight;
		params.width = (int) imageWidth;
		itemImage.setLayoutParams(params);
		holder.itemImage.setImageBitmap(resized);*/
        return null;
	}
	
	// resize the image proportionately so it fits the entire space
			/*private void setImageBitmap(JSONArray item, ImageView imageView){
				//Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), item);
				try {
					if(jsonarraynum < products.length()){
						JSONObject p = products.getJSONObject(jsonarraynum);
						String url = p.getString(TAG_IMAGEURL);
						new back().execute(url);
					jsonarraynum++;
					Log.i("Jsonparsing","aaaaaaaaaaaaaaaaaaa");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				float i = ((float) imageWidth) / ((float) bitmap.getWidth());
				float imageHeight = i * (bitmap.getHeight());
				FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) imageView.getLayoutParams();
				params.height = (int) imageHeight;
				params.width = (int) imageWidth;
				imageView.setLayoutParams(params);
				
				//imageView.setImageResource(item);
			}*/
			
			public static float convertDpToPixel(float dp, Context context){
			    Resources resources = context.getResources();
			    DisplayMetrics metrics = resources.getDisplayMetrics();
			    float px = dp * (metrics.densityDpi/160f);
			    return px;
			}

			 public static class ItemHolder
			    {
			    	ImageView itemImage;
			    	NetworkImageView netimage;
			    	
			    }
			/*
			private class back extends AsyncTask<String, Void,Bitmap>{
		        
				 
				 
				@Override
				protected Bitmap doInBackground(String... urls) {
		            // TODO Auto-generated method stub
		            try{
		                URL myFileUrl = new URL(urls[0]);
		                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
		                conn.setDoInput(true);
		                conn.connect();
		                
		               // BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
		                InputStream is = conn.getInputStream();
		                
		                bitmap = BitmapFactory.decodeStream(is);*/
		               /* BitmapFactory.Options options = new BitmapFactory.Options();
		                options.inSampleSize = 4;
		                resized = Bitmap.createScaledBitmap(bitmap, (int)imageWidth, (int)imageHeight, true);
		                if(bitmap == null) Log.i("sibvla","dfadas");*/
		                //holder.itemImage.setImageBitmap(bitmap);
		           /* }catch(IOException e){
		                e.printStackTrace();
		            }
		            return bitmap;
		        }
		        
		        protected void onPostExecute(Bitmap img){
		        	Log.i("ansk","aaaaaaaaaaaaa");
		        	//holder.itemImage.setImageBitmap(bitmap);
		        	bitmap.recycle();
		        }

				
		        
		    }*/
	
}
