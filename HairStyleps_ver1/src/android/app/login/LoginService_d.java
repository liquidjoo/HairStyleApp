package android.app.login;





import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class LoginService_d extends AsyncTask<String, String, String> {


private String name,email,gender,url;
private int id;
	
	
	
	


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	
	
	
	@Override
	protected String doInBackground(String... args) {
		// TODO Auto-generated method stub
		try {			
			HttpClient client = new DefaultHttpClient();
			String postURL = url;
			String check;
			Log.d("ddfsfsfs", postURL);
			HttpPost post = new HttpPost(postURL);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			
			
				params.add(new BasicNameValuePair("name",args[0]));
				params.add(new BasicNameValuePair("email",args[1]));
				params.add(new BasicNameValuePair("gender", args[2]));				
					
			
			UrlEncodedFormEntity ent;
			
				ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
				post.setEntity(ent);
				HttpResponse responsePOST = client.execute(post);			
				Log.d("ueiruei", responsePOST.toString());
				HttpEntity resEntity = responsePOST.getEntity();
				if(resEntity !=null){
					Log.i("RESPONSE",EntityUtils.toString(resEntity));
				}
			
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
			
			
	
	}
	
	
	
	
	
	
	
	
}

