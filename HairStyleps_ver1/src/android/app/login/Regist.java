package android.app.login;



import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.app.Activity;
import android.app.background.Background;
import android.app.hairstyle.MainActivity;
import android.app.hairstyle.R;
import android.app.hairstyle.R.id;
import android.app.hairstyle.R.layout;
import android.app.networkimage.VolleySingleton;
import android.app.service.LoadImage;
import android.app.tab.FragActivty;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Regist extends Activity {

	ArrayList<HashMap<String, String>> contactList;
	private EditText Edit_name, Edit_email, Edit_gender;
	private Button loginbtn;
	private NetworkImageView niv;
	
	private ImageLoader mImageLoader;
	private String id, name, gender, user_name, user_gender,pro_picture;
	LoginServiceHandler logsv = new LoginServiceHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		
		mImageLoader = VolleySingleton.getInstance(Regist.this).getImageLoader();

		Edit_name = (EditText) findViewById(R.id.editname);
		Edit_email = (EditText) findViewById(R.id.editEmail);
		Edit_gender = (EditText) findViewById(R.id.editgender);
		loginbtn = (Button) findViewById(R.id.loginbutton);
		niv = (NetworkImageView)findViewById(R.id.profile_picture);
		
		Intent intent = getIntent();
		user_name = intent.getExtras().getString("user_name");
		user_gender = intent.getExtras().getString("user_gender");
		id = intent.getExtras().getString("id");
		name = intent.getExtras().getString("name");
		gender = intent.getExtras().getString("gender");
		pro_picture = "https://graph.facebook.com/"+id+"/picture";
		
		SharedPreferences pref_profile = getSharedPreferences(
				"pref_profile", MODE_PRIVATE);
		SharedPreferences.Editor editor_profile = pref_profile.edit();
		editor_profile.putString("pro_id",id );
		editor_profile.commit();
		
		
		
		Edit_name.setText(user_name);
		Edit_gender.setText(user_gender);
		
		niv.setImageUrl(pro_picture, mImageLoader);
		
		
		Log.d("fjoeoeoeoeoeo", pro_picture);
		
		
		/*//액티비티종료
		Fb_login loginAct =(Fb_login)Fb_login.loginAct;
		loginAct.finish();*/

		loginbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				logsv.setUrl("http://54.64.53.133/info/regist_manage01.php");
				logsv.setId(id);
				logsv.setName(name);
				logsv.setGender(gender);
				Regist_Login(Edit_name, Edit_email, Edit_gender, user_name);
				Intent intent2 = new Intent(Regist.this,Background.class);
				intent2.putExtra("id", id);
				intent2.putExtra("name", name);
				intent2.putExtra("gender", gender);
				startActivity(intent2);

			}
		});

	}

	public void Regist_Login(EditText Edit_name, EditText Edit_email,
			EditText Edit_gender, String user_name) {

		String name = Edit_name.getText().toString();
		String email = Edit_email.getText().toString();
		String gender = Edit_gender.getText().toString();
		SharedPreferences pref_email = getSharedPreferences(
				"pref_email", MODE_PRIVATE);
		SharedPreferences.Editor editor_email = pref_email.edit();
		editor_email.putString("email", email);
		editor_email.commit();
		logsv.execute(name, email, gender, user_name);
		// AsyscTask... 만들어서..값 넘기기..
	}

}
