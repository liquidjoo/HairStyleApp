package com.vladimir.pinterestlistview;

import android.app.Activity;
import android.app.hairstyle.R;
import android.app.zoom.PhotoViewAttacher;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vladimir.pinterestlistview.*;
import com.vladimir.pinterestlistview.adapters.VolleySingleton;



public class FullImageActivity extends Activity {
	private int length;
	private int positions;
	
	private String item;
	private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    //NetworkImageView avatar;
    private static final String TAG_IMAGEURL = "image_url";
    PhotoViewAttacher mAttacher;
	
	/*@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_image);
		
		// get intent data
		Intent i = getIntent();
		
		// Selected image id
		int position = i.getExtras().getInt("id");
		ItemsActivity imageActivity = new ItemsActivity();
				
		ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
		Log.e("position",Integer.toString(position));
		imageView.setImageResource(imageActivity.mThumbIds[position]);
	}*/
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.full_image);

	    Intent i = getIntent();
		
		// Selected image id
	    positions = i.getExtras().getInt("id");
		length = i.getExtras().getInt("length");
		
		item = i.getExtras().getString("item");
		 mImageLoader = VolleySingleton.getInstance(FullImageActivity.this).getImageLoader();
	    /*ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
	    ImagePagerAdapter adapter = new ImagePagerAdapter();
	    viewPager.setAdapter(adapter);*/
		 
		 Log.i("try","1");
			NetworkImageView avatar = (NetworkImageView) findViewById(R.id.item_image2);
			Log.i("try","2");
			Log.i("url",item);
			avatar.setImageUrl(item,mImageLoader);
			mAttacher = new PhotoViewAttacher(avatar);
	   
	    
	  }

	  private class ImagePagerAdapter extends PagerAdapter {
	    
	    ItemsActivity items = new ItemsActivity();
	    
	    
	    @Override
	    public int getCount() {
	      //return mImages.length;
	    	return length;
	    }

	    @Override
	    public boolean isViewFromObject(View view, Object object) {
	      return view == ((NetworkImageView) object);
	    }

	    @Override
	    public Object instantiateItem(ViewGroup container, int position) {
	      position = positions;
	      Context context = FullImageActivity.this;
	     /* ImageView imageView = new ImageView(context);
	      int padding = context.getResources().getDimensionPixelSize(
	          R.dimen.padding_medium);
	      imageView.setPadding(padding, padding, padding, padding);
	      imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	      imageView.setImageResource(items.mThumbIds[positions]);
	      ((ViewPager) container).addView(imageView, 0);
	      if(positions+1 < length){
	    	  if(positions < 5) positions = positions + 5;
	    	  else positions = positions -4;
	      }
	      else positions = 0;
	      */
	      
	      //String url = item;
			Log.i("try","1");
			NetworkImageView avatar = (NetworkImageView) findViewById(R.id.item_image2);
			Log.i("try","2");
			Log.i("url",item);
			avatar.setImageUrl(item,mImageLoader);
			//((ViewPager) container).addView(avatar, 0);
			return avatar;
	      //return imageView;
	    }

	    @Override
	    public void destroyItem(ViewGroup container, int position, Object object) {
	      ((ViewPager) container).removeView((ImageView) object);
	    }
	  }
	

}
