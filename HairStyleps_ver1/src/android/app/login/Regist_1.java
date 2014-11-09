package android.app.login;



import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import com.android.volley.toolbox.ImageLoader;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Regist_1 extends Activity {

	ArrayList<HashMap<String, String>> contactList;
	private EditText Edit_name, Edit_email, Edit_gender;
	
	private Button loginbtn;
	private ImageView lv;
	private String id, name, gender, user_name, user_gender,pro_picture;
	String emails,genders,names;
	public LoginService_d logsv = new LoginService_d();
	String male="male",female="female";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		
		

		Edit_name = (EditText) findViewById(R.id.editname);
		Edit_email = (EditText) findViewById(R.id.editEmail);
		Edit_gender = (EditText) findViewById(R.id.editgender);
		loginbtn = (Button) findViewById(R.id.loginbutton);
		lv = (ImageView)findViewById(R.id.profile_picture);		

		/*//액티비티 종료
		Fb_login loginAct =(Fb_login)Fb_login.loginAct;
		loginAct.finish();*/
		

		loginbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				logsv.setUrl("http://54.64.53.133/info/regist_manage02.php");
				if(Edit_name.getText().toString().equals("")||Edit_name.getText().toString().equals(null)){
					Toast.makeText(getApplicationContext(), "UserName을 입력해주세요", Toast.LENGTH_SHORT).show();
				}else if(Edit_email.getText().toString().equals("")||Edit_email.getText().toString().equals(null)||!(Edit_email.getText().toString().contains("@"))){
					Toast.makeText(getApplicationContext(), "Eamil을 제대로입력해주세요", Toast.LENGTH_SHORT).show();
				}else if(!(Edit_gender.getText().toString().equals(male)||Edit_gender.getText().toString().equals(female))){
					Toast.makeText(getApplicationContext(), "female 혹은 male로 입력해주세요", Toast.LENGTH_SHORT).show();
				}else{
					SharedPreferences pref_name = getSharedPreferences("pref_name", MODE_PRIVATE);
					SharedPreferences.Editor editor_name = pref_name.edit();
					editor_name.putString("name", Edit_name.getText().toString());
					editor_name.commit();
					

					SharedPreferences pref_email = getSharedPreferences("pref_email", MODE_PRIVATE);
					SharedPreferences.Editor editor_email = pref_email.edit();
					editor_email.putString("email", Edit_email.getText().toString());
					editor_email.commit();

					SharedPreferences pref_gender = getSharedPreferences("pref_gender", MODE_PRIVATE);
					SharedPreferences.Editor editor_gender = pref_gender.edit();
					editor_gender.putString("gender", Edit_gender.getText().toString());
					editor_gender.commit();
					
					Regist_Login(Edit_name.getText().toString(), Edit_email.getText().toString(), Edit_gender.getText().toString(), user_name);
					Intent intent2 = new Intent(Regist_1.this,Background.class);
					intent2.putExtra("email", Edit_email.toString());
					startActivity(intent2);
					finish();
				}

			}
		});

	}

	public void Regist_Login(String name, String email,
			String gender, String user_name) {
		
		SharedPreferences pref_email = getSharedPreferences(
				"pref_email", MODE_PRIVATE);
		SharedPreferences.Editor editor_email = pref_email.edit();
		editor_email.putString("email", email);
		editor_email.commit();
		logsv.execute(name, email, gender, user_name);
		// AsyscTask... 만들어서..값 넘기기..
	}

}
