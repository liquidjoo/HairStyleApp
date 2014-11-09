package android.app.service;

import java.io.InputStream;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

public class LoadImage extends AsyncTask<String, String, Bitmap>{

	private Context context;
	
	public LoadImage(Context context, ImageView imgv){
		this.context = context;
		img = imgv;
	}
	
	public LoadImage(){
		
	}
	
public LoadImage(ImageView imgv){
		img = imgv;
	}
	// Bitmap
		ImageView img;
		Bitmap bitmap;
		ProgressDialog pDialog;	
	
	@Override
		protected void onPreExecute(){
		super.onPreExecute();
		pDialog = new ProgressDialog(context);
		pDialog.setMessage("Loading Image...");
		//pDialog.show(); // 
	}
	
	@Override
	protected Bitmap doInBackground(String... args) {
		// TODO Auto-generated method stub		
		try{
			bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());
			
		} catch(Exception e){
			e.printStackTrace();
		}			
		return bitmap;
	}
	protected void onPostExecute(Bitmap image){
		if(image != null){		
			img.setImageBitmap(image);
			pDialog.dismiss();
		}else{
			pDialog.dismiss();
			Toast.makeText(context, "Image Does not exist..", Toast.LENGTH_SHORT).show();
		}
	}
	
}