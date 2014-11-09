package android.app.comment;

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

public class CommentServiceHandler extends AsyncTask<String, String, String> {

	private String url,hair_url,comment, likecount,id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHair_url() {
		return hair_url;
	}

	public void setHair_url(String hair_url) {
		this.hair_url = hair_url;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getLikecount() {
		return likecount;
	}

	public void setLikecount(String likecount) {
		this.likecount = likecount;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

			params.add(new BasicNameValuePair("url", url));
			params.add(new BasicNameValuePair("id", id));
			params.add(new BasicNameValuePair("hair_url", hair_url));
			params.add(new BasicNameValuePair("comment", comment));
			params.add(new BasicNameValuePair("likecount", likecount));
			

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
