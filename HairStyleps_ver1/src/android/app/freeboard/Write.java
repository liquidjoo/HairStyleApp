package android.app.freeboard;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.hairstyle.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Write extends Activity{
	EditText text;
	Button button;
	TextView view,id;
	int size=0;
	String Userid="�ٺ�";
	private String email;
@Override
protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.board_write);
		
		SharedPreferences pref_email = getSharedPreferences("pref_email", MODE_PRIVATE);
		email = pref_email.getString("email", "");
		
		System.out.println(email);
		id = (TextView)findViewById(R.id.id);
		button = (Button)findViewById(R.id.button1);
		text = (EditText)findViewById(R.id.editText1);
		view = (TextView)findViewById(R.id.textlength);
		id.setText(email);
		text.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				size = s.length();
				
				if(size>200){
						Toast.makeText(getApplicationContext(), "200자를 초과했습니다.", Toast.LENGTH_SHORT).show();
				}else{
					view.setText(String.valueOf(s.length())+"/200");
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				s.toString();
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Calendar calendar = Calendar.getInstance();
				java.util.Date date = calendar.getTime();
				SendComment sp = new SendComment(text.getText().toString(),Userid,new SimpleDateFormat("yyyyMMddHHmmss").format(date),email);
				sp.execute();
				finish();
				
			}
		});
	}
}
