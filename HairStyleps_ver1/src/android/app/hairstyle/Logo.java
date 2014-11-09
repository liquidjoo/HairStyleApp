package android.app.hairstyle;

import android.app.*;
import android.app.background.Background;
import android.app.login.Fb_login;
import android.app.login.Regist_1;
import android.content.*;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.app.login.Regist_1;

public class Logo extends Activity {
	Handler h;//핸들러 선언
	String TAG = "LOGO";
	String emails,genders,names,user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //인트로화면이므로 타이틀바를 없앤다
        setContentView(R.layout.activity_logo);
        h= new Handler(); //딜래이를 주기 위해 핸들러 생성
        h.postDelayed(mrun, 1500); // 딜레이 ( 런어블 객체는 mrun, 시간 2초)
    }
     
    Runnable mrun = new Runnable(){
        @Override
        public void run(){
        	SharedPreferences pref_id = getSharedPreferences("pref_emails", MODE_PRIVATE);
    		emails = pref_id.getString("emails", "");
    		SharedPreferences pref_gender = getSharedPreferences("pref_gender", MODE_PRIVATE);
    		genders = pref_gender.getString("genders", "");
    		SharedPreferences pref_name = getSharedPreferences("pref_name", MODE_PRIVATE);
    		names = pref_name.getString("names", "");
    		Regist_1 rg = new Regist_1();
    		
    		Log.e(TAG, emails);
    		Log.e(TAG, genders);
    		Log.e(TAG, names);
    		
    		if(!(names.equals("")||names.equals(null))
    				&&(!(emails.equals("")||emails.equals(null)))
    					&&(!(genders.equals("")||genders.equals(null)))
    				){
    			rg.logsv.setUrl("http://54.64.53.133/info/regist_manage02.php");
    			//rg.Regist_Login(names, emails, genders, user_name);
    			Intent intent2 = new Intent(Logo.this,Background.class);
    			intent2.putExtra("email", emails);
    			startActivity(intent2);
    			finish();		
    		}else{
    		
	        Intent i = new Intent(Logo.this, Fb_login.class); //인텐트 생성(현 액티비티, 새로 실행할 액티비티)
	        startActivity(i);
	        finish();
	        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); 
	        //overridePendingTransition 이란 함수를 이용하여 fade in,out 효과를줌. 순서가 중요
    		}
        }
    };
    //인트로 중에 뒤로가기를 누를 경우 핸들러를 끊어버려 아무일 없게 만드는 부분
    //미 설정시 인트로 중 뒤로가기를 누르면 인트로 후에 홈화면이 나옴.
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        h.removeCallbacks(mrun);
    }
}