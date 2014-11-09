package android.app.tab;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.android.common.view.SlidingTabLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListFragment;
import android.app.hairstyle.MainActivity;
import android.app.hairstyle.R;
import android.app.hairstyle.R.layout;
import android.app.profile.Profile;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.freeboard.*;

@SuppressLint("DefaultLocale")
public class FragActivty extends FragmentActivity {

	// private static final String[] CONTENT = new String[] { "미용실 정보", "디자이너",
	// "사진", "게시판"};
	SectionsPagerAdapter mSectionsPagerAdapter;
	private SlidingTabLayout mSlidingTabLayout;
	ViewPager mViewPager;
	private String email, pro_picture,id,name,gender;
	private NetworkImageView niv;
	TextView tgender,temail,tname;
	private String c_id,c_name,c_gender;
	

	private ImageLoader mImageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frag_activty);
		SharedPreferences pref_profile = getSharedPreferences("pref_profile",MODE_PRIVATE);
		id = pref_profile.getString("pro_id", "");		
		SharedPreferences pref_email = getSharedPreferences("pref_email", MODE_PRIVATE);
		email = pref_email.getString("email", "");		
		SharedPreferences pref_name =getSharedPreferences("pref_name", MODE_PRIVATE);
		name = pref_name.getString("name", "");
		SharedPreferences pref_gender =getSharedPreferences("pref_gender", MODE_PRIVATE);
		gender = pref_gender.getString("gender", "");
		
		
		tgender =(TextView)findViewById(R.id.profile_gender);
		temail =(TextView)findViewById(R.id.profile_email);
		tname =(TextView)findViewById(R.id.profile_name);
		
		tgender.setText(gender);
		temail.setText(email);
		tname.setText(name);
		
		
		Intent intent = getIntent();		
		c_id = intent.getExtras().getString("id");
		c_name = intent.getExtras().getString("name");
		c_gender = intent.getExtras().getString("gender");
		
		
		
		
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getApplicationContext(), getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// email = send_email();
		
		niv = (NetworkImageView)findViewById(R.id.ppro);

		pro_picture = "https://graph.facebook.com/"+id+"/picture";

		//niv.setImageUrl(pro_picture, mImageLoader);

		mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
		mSlidingTabLayout.setViewPager(mViewPager);
	}/*
	 * public String send_email(){ Intent intent = getIntent(); email=
	 * intent.getExtras().getString(email); return email; }
	 */

	@SuppressLint("DefaultLocale")
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		Context mContext;

		public SectionsPagerAdapter(Context context, FragmentManager fm) {
			// TODO Auto-generated constructor stub
			super(fm);
			mContext = context;
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			switch (position) {
			case 0:
				return new Profile();
			case 1:
				return new MainActivity(c_id,c_name,c_gender);
			case 2:
				return new FreeBoard();

			}
			return null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}

		@SuppressLint("DefaultLocale")
		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "미용실 정보";
			case 1:
				return "SNS보드";
			case 2:
				return "자유게시판";

			}
			return null;
		}
	}

}
