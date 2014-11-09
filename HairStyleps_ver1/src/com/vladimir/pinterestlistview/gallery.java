package com.vladimir.pinterestlistview;

import android.app.Activity;
import android.app.hairstyle.R;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class gallery extends Activity{
	//image
		final int REQUEST_CODE_IMAGE = 1234;
		Bitmap bitmap;
	
		ImageView imageview;
		
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		
		imageview = (ImageView)findViewById(R.id.image);
		Drawable drawable = getResources().getDrawable(R.drawable.cnw_2);
		
		imageview.setImageDrawable(drawable);
		
		imageview.setPadding(3, 3, 3, 3);
		imageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				final Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		    	startActivityForResult(intent, REQUEST_CODE_IMAGE);
				
			}
		});
	}
	
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
			Drawable result = new BitmapDrawable(bitmap);
			
			imageview.setImageDrawable(result);
		}
	}
	
	
}
