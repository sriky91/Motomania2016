package news.motogp.fragment;

import java.util.ArrayList;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import news.motogp.application.MotomaniaApplication;
import news.motogp.application.MotomaniaApplication.TrackerName;
import news.motogp.R;
import news.motogp.network.JsonDownloadLastHour;
import news.motogp.network.ThreadHandler;
import news.motogp.utils.LastHourArrayAdapter;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class LastHourFragment extends android.support.v4.app.Fragment{
	

	private static final String TEXT_KEY = "TEXT";
	private static final String TITLE_KEY = "TITLE";
	private static final String DATA_KEY = null;
	private View layout;
	
	private GridView gridView;
	private RelativeLayout rlDownloading;
	private RelativeLayout rlInternetOff;
	
	public  String text;
	private String[] texts;
	private String[] title;
	private String[] data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layout = inflater.inflate(R.layout.layout_article, container, false);
		
		gridView= (GridView) layout.findViewById(R.id.gridview);
		rlDownloading = (RelativeLayout) layout.findViewById(R.id.rl_downloading);
		rlInternetOff = (RelativeLayout) layout.findViewById(R.id.rl_internet_off);
		TextView tvDownloading = (TextView) layout.findViewById(R.id.tv_downloading);
		tvDownloading.setText(getText(R.string.progressdialog_lastHour));
		
		if (savedInstanceState != null && savedInstanceState.getStringArray(TEXT_KEY)!=null) {
			texts = savedInstanceState.getStringArray(TEXT_KEY);
			title = savedInstanceState.getStringArray(TITLE_KEY);
			data = savedInstanceState.getStringArray(DATA_KEY);

			final LastHourArrayAdapter adapter = new LastHourArrayAdapter(getActivity(), title, data);
			
			ThreadHandler.runOnMainThread(new Runnable() {
				
				@Override
				public void run(){
					gridView.setAdapter(adapter);
				}
			});
			
		}
		else{
			
//			final ProgressDialog progressDialog;
//			progressDialog = new ProgressDialog(getActivity());
//			progressDialog.setCancelable(true);
//			progressDialog.setMessage(getActivity().getString(R.string.progressdialog_lastHour));
//			progressDialog.show();
			
			rlDownloading.setVisibility(View.VISIBLE);
		
		
	//		tracker = EasyTracker.getInstance(this.getActivity());
			
			 ThreadHandler.start("FragmentMenu", new Runnable() {
				private LastHourArrayAdapter adapter=null;
				
				@Override
				public void run() {
					
					JsonDownloadLastHour jsondownloadLastHour= new JsonDownloadLastHour();
					
					ArrayList<String[]> dataLastHour = jsondownloadLastHour.getLastHour(getActivity());
	//				dataLastHour.getLastHour(getActivity());
					
					data  = dataLastHour.get(0);
					title = dataLastHour.get(1);
					texts = dataLastHour.get(2);
					nenter+=1;
					
					adapter = new LastHourArrayAdapter(getActivity(), title, data);
					  
					ThreadHandler.runOnMainThread(new Runnable() {
						
						@Override
						public void run(){
							 gridView.setAdapter(adapter);
//							 progressDialog.dismiss();
							 rlDownloading.setVisibility(View.GONE);
						}
					});
				}
			});
			
			if(!isOnline()){
				
				String txt=getString(R.string.toast_no_internet);
				Toast t=Toast.makeText(getActivity(), txt, Toast.LENGTH_LONG);
				t.show();
//				progressDialog.dismiss();
				rlInternetOff.setVisibility(View.VISIBLE);
				rlDownloading.setVisibility(View.GONE);
				
			}
			
		}
		gridView.setOnItemClickListener(new OnItemClickListener()
		{
		    @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
		    { 
		    	
//		    	EasyTracker et=EasyTracker.getInstance(getActivity());
//		    	et.send(MapBuilder.createEvent("LastHour_selection", "LastHour",title[position], null).build());
		    	
		    	text=texts[position];
		    	LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    	View v= inflater.inflate(R.layout.last_hour, null,false);
		    	AlertDialog.Builder b= new AlertDialog.Builder(getActivity());
		    	b.setView(v);
		    	
		    	TextView t= (TextView) v.findViewById(R.id.text);
		    	t.setText(text);
		    	
		    	AlertDialog a=b.create();
		    	a.show();
		    	
		    	Tracker tracker = ((MotomaniaApplication) getActivity().getApplication()).getTracker(TrackerName.APP_TRACKER);
		    	tracker.setScreenName(getString(R.string.title_section2) +" "+ text);
		    	tracker.send(new HitBuilders.AppViewBuilder().build());
		    }

		});
		return layout;
	}
	int nenter=0;

	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		
		outState.putStringArray(TEXT_KEY, texts);
	    outState.putStringArray(TITLE_KEY, title);
	    outState.putStringArray(DATA_KEY, data);
	    
	}

}
