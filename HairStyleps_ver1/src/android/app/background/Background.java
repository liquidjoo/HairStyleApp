package android.app.background;

import com.vladimir.pinterestlistview.ItemsActivity;

import android.app.Activity;
import android.app.freeboard.FreeBoard;
import android.app.freeboard.Main_Freeboard;
import android.app.hairstyle.Activity_SNSboard;
import android.app.hairstyle.MainActivity;
import android.app.hairstyle.R;
import android.app.hairstyle.R.layout;
import android.app.tab.FragActivty;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

public class Background extends Activity {

	
	private String id,name,gender;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_background);
		
		
		Button sbutton = (Button)findViewById(R.id.shopbutton);
		Button gbutton = (Button)findViewById(R.id.gallerybutton);
		Button bbutton = (Button)findViewById(R.id.boardbutton);
		
		Intent intent = getIntent();		
		id = intent.getExtras().getString("id");
		name = intent.getExtras().getString("name");
		gender = intent.getExtras().getString("gender");
		
		
		
		sbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Background.this,FragActivty.class);
				intent.putExtra("id", id);
				intent.putExtra("name", name);
				intent.putExtra("gender", gender);
				startActivity(intent);
				
			}
		});
		
		gbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Background.this,Activity_SNSboard.class);
				intent.putExtra("id", id);
				Log.d("c_idididididi", id);
				intent.putExtra("name", name);
				intent.putExtra("gender", gender);
				startActivity(intent);
				
			}
		});
		
		bbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Background.this,Main_Freeboard.class);
				intent.putExtra("id", id);
				intent.putExtra("name", name);
				intent.putExtra("gender", gender);
				startActivity(intent);
			}
		});
	}
}
