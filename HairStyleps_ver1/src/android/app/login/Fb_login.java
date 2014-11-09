package android.app.login;

import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionDefaultAudience;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;

import android.app.Activity;
import android.app.hairstyle.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Fb_login extends Activity {

	private String TAG = "Fb_login";
	private String id, name, gender;	
	public static Activity loginAct;

	//

	private boolean bProgressLogin = false;
	private Bundle mSavedInstanceState = null;

	private Session.StatusCallback statusCallback = new SessionStatusCallback();

	private class SessionStatusCallback implements Session.StatusCallback {

		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			if (state == SessionState.OPENED
					|| state == SessionState.OPENED_TOKEN_UPDATED) {
				// login
				checkFacebookLogin();
				statuslog();

			} else if (state == SessionState.CLOSED)
				;// logout

			else if (state == SessionState.CLOSED_LOGIN_FAILED) {
				// a lot of error
				bProgressLogin = false;
			}
		}

		protected boolean isSessionOpened() {
			Session session = Session.getActiveSession();
			return (session != null && session.isOpened());
		}

		
		
		// check facebookLogin에서 퍼미션 권한을 설정합니다.
		private void checkFacebookLogin() {
			Session session = Session.getActiveSession();
			if (session != null && bProgressLogin) {
				boolean logined = session.isOpened();
				if (logined) {
					String PERMISSION = "publish_actions";
					if (session.getPermissions().contains(PERMISSION)) {
						bProgressLogin = false;
						Log.d(TAG, "gggggggggggg");
						// login success

					} else
						session.requestNewPublishPermissions(new Session.NewPermissionsRequest(
								Fb_login.this, PERMISSION));
				} else {

					// login 시도
					if (!session.isOpened() && !session.isClosed())
						session.openForRead(new Session.OpenRequest(
								Fb_login.this).setCallback(statusCallback)
								.setDefaultAudience(
										SessionDefaultAudience.FRIENDS));
					else
						session.openActiveSession(Fb_login.this, true,
								statusCallback);
				}
			}

		}

		// statuslog 에서 onCompleted를 실행하게 되면 GraphUser에서 유저정보를 받아 올 수 있습니다.
		private void statuslog() {
			final Session session = Session.getActiveSession();
			// Get current logged in user information
			Request meRequest = Request.newMeRequest(session,
					new Request.GraphUserCallback() {
						@Override
						public void onCompleted(GraphUser user,
								Response response) {
							
							name = user.getName().toString();

							id = user.getId().toString();

							gender = user.getProperty("gender").toString();

							SharedPreferences pref_name = getSharedPreferences("pref_name", MODE_PRIVATE);
							SharedPreferences.Editor editor_name = pref_name.edit();
							editor_name.putString("name", name);
							editor_name.commit();
							

							SharedPreferences pref_id = getSharedPreferences("pref_id", MODE_PRIVATE);
							SharedPreferences.Editor editor_id = pref_id.edit();
							editor_id.putString("id", id);
							editor_id.commit();

							SharedPreferences pref_gender = getSharedPreferences("pref_gender", MODE_PRIVATE);
							SharedPreferences.Editor editor_gender = pref_gender.edit();
							editor_gender.putString("gender", gender);
							editor_gender.commit();

							FacebookRequestError error = response.getError();

							if (error != null) {
								Log.e("error", error.toString());
							} else if (session == Session.getActiveSession()) {
								// user.getInnerJSONObject() 사용
							}
							Intent intent = new Intent(Fb_login.this,
									Regist.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
							intent.putExtra("user_name", pref_name.getString("name", ""));
							intent.putExtra("user_gender",pref_gender.getString("gender", ""));
							intent.putExtra("id", id);
							intent.putExtra("name", name);
							intent.putExtra("gender", gender);
							startActivity(intent);
						}

					});
			/*
			 * Bundle params = new Bundle(); params.putString("fields",
			 * "name,picture,locale,gender"); meRequest.setParameters(params);
			 */

			meRequest.executeAsync();

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork()
					.penaltyLog().build());
		}
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fb_login);
				
		loginAct = Fb_login.this;

		mSavedInstanceState = savedInstanceState;

		Session.openActiveSession(Fb_login.this, false, statusCallback);

		LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
		authButton.setSessionStatusCallback(statusCallback);
		authButton.setOnErrorListener(new OnErrorListener() {

			@Override
			public void onError(FacebookException error) {
				Log.i(TAG, "Error" + error.getMessage());

			}
		});

		Button loginbtn = (Button) findViewById(R.id.reg_login);
		loginbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Fb_login.this, Regist_1.class);
				startActivity(intent);

			}
		});

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// Session.getActiveSession().onActivityResult(this, requestCode,
		// resultCode, data);

		boolean processed = false;
		if (Session.getActiveSession() != null) {
			processed = Session.getActiveSession().onActivityResult(this,
					requestCode, resultCode, data);
		}
		if (!processed) {

		}
	}

	public void onStart() {
		super.onStart();
		if (Session.getActiveSession() != null)
			Session.getActiveSession().addCallback(statusCallback);
		Log.d("Fb_login", "---------------------------------onstart");

	}

	public void onStop() {
		super.onStop();
		if (Session.getActiveSession() != null)
			Session.getActiveSession().removeCallback(statusCallback);
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (Session.getActiveSession() != null) {
			Session session = Session.getActiveSession();
			Session.saveSession(session, outState);

		}

	}

}
