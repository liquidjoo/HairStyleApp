package android.app.profile;

import java.util.ArrayList;

import android.app.hairstyle.R;
import android.app.service.ServiceHandler;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;



public class Profile extends ListFragment {
   ArrayList<String> list;
   
   private String url;
   
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
      
      View view = inflater.inflate(R.layout.activity_profile, null, false);
      
      
      
      
      
      
      return view;
      
   }
     @Override
       public void onActivityCreated(Bundle savedInstanceState) {

           super.onActivityCreated(savedInstanceState);

         list = new ArrayList<String>();
         list.add("화미주 헤어샾");
         list.add("신길이네 미용원");
         list.add("이재현 미용실");
         list.add("순수 헤어살롱");
         list.add("더컷 미용실");
         list.add("동네 미용실");
         
         setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list));
           

       }
   

   
   
     private class GetInfo extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			ServiceHandler serv = new ServiceHandler();
			
			serv.makeServiceCall(url, ServiceHandler.POST);
			Log.d("Aaaa", url);
			Log.d("Response: ", ">" + jsonStr);
			
			
			return null;
		}
    	 
    	 
    	 
    	 
     }
     
     
   
}