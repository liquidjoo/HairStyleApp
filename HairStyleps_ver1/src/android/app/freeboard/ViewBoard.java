package android.app.freeboard;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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

import android.app.Activity;
import android.app.hairstyle.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class ViewBoard extends Activity{
	String email,time,comment,index;
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_viewboard);
         
        // ���� ��Ƽ��Ƽ�κ��� �Ѿ�� �����͸� ������.
        email = getIntent().getStringExtra("email");
        time = getIntent().getStringExtra("time");
        comment = getIntent().getStringExtra("comment");
        index = getIntent().getStringExtra("index");
        
        TextView BoardTime = (TextView)findViewById(R.id.boardtime);
        TextView BoardEmail = (TextView)findViewById(R.id.boardemail);
        TextView BoardComment = (TextView)findViewById(R.id.boardcomment);
        Button delbtn = (Button)findViewById(R.id.delbutton);
        
        BoardEmail.setText(email);
        BoardTime.setText(time);
        BoardComment.setText(comment);
        
        delbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				class SendDel extends AsyncTask<Void, Void, String> {


					protected String doInBackground(Void... unused) {
						String content = executeClient();
						
						return content;
					}

					

					
					// ���� �����ϴ� �κ�
					public String executeClient() {
						ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
						post.add(new BasicNameValuePair("email", email));	
						post.add(new BasicNameValuePair("index", index));
						// ���� HttpClient ��ü ����
						HttpClient client = new DefaultHttpClient();
						
						// ��ü ���� ���� �κ�, ���� �ִ�ð� ���
						HttpParams params = client.getParams();
						HttpConnectionParams.setConnectionTimeout(params, 5000);
						HttpConnectionParams.setSoTimeout(params, 5000);
						
						// Post��ü ����
						HttpPost httpPost = new HttpPost("http://54.64.53.133/board/del.php");
						
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
			}
		});
    }
}
