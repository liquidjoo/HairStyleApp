package android.app.shop;

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
import android.app.login.Regist;

public class hair_shop_servicehandler extends AsyncTask<String, String, String> {

	private String phpurl,shop_name,shop_logo,shop_region,shop_phone_number,shop_intro;

	

	public String getPhpurl() {
		return phpurl;
	}



	public void setPhpurl(String phpurl) {
		this.phpurl = phpurl;
	}



	public String getShop_name() {
		return shop_name;
	}



	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}



	public String getShop_logo() {
		return shop_logo;
	}



	public void setShop_logo(String shop_logo) {
		this.shop_logo = shop_logo;
	}



	public String getShop_region() {
		return shop_region;
	}



	public void setShop_region(String shop_region) {
		this.shop_region = shop_region;
	}



	public String getShop_phone_number() {
		return shop_phone_number;
	}



	public void setShop_phone_number(String shop_phone_number) {
		this.shop_phone_number = shop_phone_number;
	}



	public String getShop_intro() {
		return shop_intro;
	}



	public void setShop_intro(String shop_intro) {
		this.shop_intro = shop_intro;
	}



	@Override
	protected String doInBackground(String... args) {
		// TODO Auto-generated method stub
		try {
			HttpClient client = new DefaultHttpClient();
			String postURL = phpurl;
			String check;
			Log.d("ddfsfsfs", postURL);
			HttpPost post = new HttpPost(postURL);

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			//private String phpurl,shop_name,shop_logo,shop_region,shop_phone_number,shop_intro;
			params.add(new BasicNameValuePair("shop_name",shop_name));
			params.add(new BasicNameValuePair("shop_logo",shop_logo));
			params.add(new BasicNameValuePair("shop_region",shop_region));
			params.add(new BasicNameValuePair("shop_phone_number",shop_phone_number));
			params.add(new BasicNameValuePair("shop_intro",shop_intro));
			

			UrlEncodedFormEntity ent;

			ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);
			Log.d("ueiruei", responsePOST.toString());
			HttpEntity resEntity = responsePOST.getEntity();
			if (resEntity != null) {
				Log.i("RESPONSE", EntityUtils.toString(resEntity));
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
