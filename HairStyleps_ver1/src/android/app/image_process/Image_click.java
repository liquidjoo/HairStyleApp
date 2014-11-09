package android.app.image_process;

import java.util.HashMap;








import android.app.Activity;
import android.app.comment.CommentActivity;
import android.app.hairstyle.Activity_SNSboard;
import android.app.hairstyle.R;
import android.app.networkimage.VolleySingleton;
import android.app.zoom.PhotoViewAttacher;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class Image_click extends Activity {
	
	
	PhotoViewAttacher mAttacher;
	private ImageLoader mImageLoader;
	
	private String url,name,id,email,gender,position;
	private String c_id,c_name,c_gender;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.click_list_item);
		mImageLoader = VolleySingleton.getInstance(Image_click.this).getImageLoader();
		Intent intent = getIntent();
		url = intent.getExtras().getString("url");
		name = intent.getExtras().getString("name");
		id = intent.getExtras().getString("id");
		email = intent.getExtras().getString("email");
		gender = intent.getExtras().getString("gender");
		position = intent.getExtras().getString("position");
		
		c_id = intent.getExtras().getString("c_id");
		c_name = intent.getExtras().getString("c_name");
		c_gender = intent.getExtras().getString("c_gender");
		
		
		TextView user_name = (TextView)findViewById(R.id.c_user_name);
		TextView user_gender = (TextView)findViewById(R.id.c_user_gender);
		TextView user_email = (TextView)findViewById(R.id.c_user_email);
		NetworkImageView netimg = (NetworkImageView)findViewById(R.id.c_hair_image);
		NetworkImageView pv = (NetworkImageView)findViewById(R.id.c_profile_image);
		Button comment_button = (Button)findViewById(R.id.comment_button);
		
		user_name.setText(name);
		user_gender.setText(gender);
		user_email.setText(email);
		netimg.setImageUrl(url, mImageLoader);		
		pv.setImageUrl("https://graph.facebook.com/"+id+"/picture", mImageLoader);
		comment_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Image_click.this,CommentActivity.class);				
				intent.putExtra("url", url);
				intent.putExtra("c_id", c_id);
				intent.putExtra("c_name",c_name);				
				intent.putExtra("c_gender", c_gender);				
				startActivity(intent);
			}
		});
		
		
		//Zoom Library 사용 할시!  위의 문장들은 네트워크 이미지뷰를 쓴다고 선언한것입니다.
		//mAttacher = new PhotoViewAttacher(netimg);
		
		
		
		


	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_click, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
