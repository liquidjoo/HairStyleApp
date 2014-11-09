package android.app.category;

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

import android.app.tab.FragActivty;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

class SendPost extends AsyncTask<Void, Void, String> {
	String gender,hairlength,permanent,color;
	private String email,id;
	boolean res=false;
	String urlString = "http://54.64.53.133/image_data/5.php";
	String boundary = "*****";
	String twoHyphens = "--";
	String lineEnd = "\r\n";
	String absolutePath;
	Bitmap bm;
	FileInputStream mFileInputStream = null;
	URL connectUrl = null;
	Bitmap bitmap;
	private InputStream is;
	Bitmap resized;
	int mWidth, mHeight;
	
	
	
	public SendPost(String g, String h, String p, String c,String path,String email, String id) {
		gender=g;
		hairlength=h;
		permanent=p;
		color=c;
		absolutePath = path;
		this.email = email;
		this.id = id;
	}

	protected String doInBackground(Void... unused) {
		
		Resize(urlString,absolutePath); 
		String content = executeClient();
		
		return content;
	}

	

	public void Resize(String apiUrl, String absolutePath) {
		bitmap = BitmapFactory.decodeFile(absolutePath);
		mWidth= bitmap.getWidth()/2;
		mHeight = bitmap.getHeight()/2;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		Bitmap src = BitmapFactory.decodeFile( absolutePath, options );
		resized = Bitmap.createScaledBitmap( src, mWidth, mHeight, true ); 
		 }


	public String executeClient() {
		ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
		
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		
		resized.compress(Bitmap.CompressFormat.JPEG, 50, bao);
		
		byte[] ba = bao.toByteArray();
		
		//String ba1=Base64.encodeBytes(ba);
		String ba1=Base64.encodeToString(ba, Base64.DEFAULT);
		


		post.add(new BasicNameValuePair("image",ba1));
		post.add(new BasicNameValuePair("id", id));
		post.add(new BasicNameValuePair("email", email));
		post.add(new BasicNameValuePair("gender", gender));
		post.add(new BasicNameValuePair("hairlength", hairlength));
		post.add(new BasicNameValuePair("permanent", permanent));
		post.add(new BasicNameValuePair("color", color));

		//      HttpClient   ü     
		HttpClient client = new DefaultHttpClient();
		
		//   ü            κ ,       ִ ð     
		HttpParams params = client.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 5000);
		
		// Post  ü     
		HttpPost httpPost = new HttpPost("http://54.64.53.133/image_data/upload.php");
		
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post, "UTF-8");
			httpPost.setEntity(entity);
			//client.execute(httpPost);
			
			HttpResponse resp = client.execute(httpPost);
			Log.i("everybody chul??check", EntityUtils.toString(entity)+"");
			Log.i("ddddddddddddd", id+email);
			return EntityUtils.getContentCharSet(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}