package android.app.freeboard;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

class SendComment extends AsyncTask<Void, Void, String> {
	String Text,Userid,Format,Email;
	private InputStream is;

	public SendComment(String text, String userid, String format, String email) {
		Text=text;
		Userid=userid;
		Format=format;
		Email = email;
	}

	protected String doInBackground(Void... unused) {
		String content = executeClient();
		
		return content;
	}

	

	
	// ���� �����ϴ� �κ�
	public String executeClient() {
		ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
		post.add(new BasicNameValuePair("email", Email));
		post.add(new BasicNameValuePair("comment", Text));
		post.add(new BasicNameValuePair("time", Format));		

		// ���� HttpClient ��ü ����
		HttpClient client = new DefaultHttpClient();
		
		// ��ü ���� ���� �κ�, ���� �ִ�ð� ���
		HttpParams params = client.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 5000);
		
		// Post��ü ����
		HttpPost httpPost = new HttpPost("http://54.64.53.133/board/board.php");
		
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post, "UTF-8");
			httpPost.setEntity(entity);
			//client.execute(httpPost);
			
			HttpResponse resp = client.execute(httpPost);
			Log.i("", EntityUtils.toString(entity)+"");
			return EntityUtils.getContentCharSet(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}