package android.app.category;

import android.app.Activity;
import android.app.hairstyle.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Category extends Activity{
	ImageView iv;
	String path;
	String url;
	String email;
	String id;
	String name;
	String g,h,p,c;
	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category);
		
		iv = (ImageView) findViewById(R.id.getimage);
		RadioGroup Gender = (RadioGroup) findViewById(R.id.Gender);
		RadioGroup HairLength = (RadioGroup) findViewById(R.id.HairLength);
		RadioGroup Permanent = (RadioGroup) findViewById(R.id.Permanent);
		RadioGroup Color = (RadioGroup) findViewById(R.id.Color);
		Button upload = (Button) findViewById(R.id.Upload);				
		Intent intent = getIntent();
		//url = intent.getParcelableExtra("uri");
		url = intent.getStringExtra("uri");
		path = intent.getStringExtra("path");
		
		SharedPreferences pref_id = getSharedPreferences("pref_id", MODE_PRIVATE);
		id = pref_id.getString("id", "");
		
		SharedPreferences pref_email = getSharedPreferences("pref_email", MODE_PRIVATE);
		email = pref_email.getString("email", "");
		
		SharedPreferences pref_name =getSharedPreferences("pref_name", MODE_PRIVATE);
		name = pref_name.getString("name", "");
		updateImageView();
		
		Log.d("check11111111111111", id);
		Log.d("check22222222222222", email);
		Gender.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton rb = (RadioButton) findViewById(checkedId);
				if(checkedId==R.id.Male){
					g="1";
				}
				else if(checkedId==R.id.Female){
					g="2";
				}
				
			}
		});
		HairLength.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton rb = (RadioButton) findViewById(checkedId);
				if(checkedId==R.id.Short){
					h="1";
				}
				else if(checkedId==R.id.Mid){
					h="2";
				}
				else if(checkedId==R.id.Long){
					h="3";
				}
				
			}
		});
		Permanent.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton rb = (RadioButton) findViewById(checkedId);
				if(checkedId==R.id.Yes){
					p="1";
				}
				else if(checkedId==R.id.No){
					p="2";
				}
				
			}
		});
		Color.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton rb = (RadioButton) findViewById(checkedId);
				if(checkedId==R.id.Yes2){
					c="1";
				}
				else if(checkedId==R.id.No2){
					c="2";
				}
				
			}
		});
		upload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			if(g=="0"||h=="0"||p=="0"||c=="0"){
				Toast.makeText(Category.this, "업로드 완료.", Toast.LENGTH_SHORT).show();
			}else{
				SendPost sp = new SendPost(g,h,p,c,path,email,id);
				sp.execute();
			}
			finish();
				
			}
		});
	}

	private void updateImageView() {
		// TODO Auto-generated method stub
		int degree = ImageUtil.GetExifOrientation(url);  
        Bitmap resizeBitmap = ImageUtil.loadBackgroundBitmap(  
                Category.this, url);  
        Bitmap rotateBitmap = ImageUtil.GetRotatedBitmap(resizeBitmap, degree);
        iv.setImageBitmap(rotateBitmap);  
        //resizeBitmap.recycle();
	}

	@Override
	protected void onDestroy() {
		Drawable d = iv.getDrawable();
		if (d instanceof BitmapDrawable) {
			Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
			bitmap.recycle();
			bitmap = null;
		}
		d.setCallback(null);

		super.onDestroy();
	}
	private Matrix getMatrix() {
		Matrix m = new Matrix();
		return m;
	}
}
