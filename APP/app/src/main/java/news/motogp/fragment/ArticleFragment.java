package news.motogp.fragment;

import java.util.ArrayList;

import com.google.android.gms.analytics.GoogleAnalytics;

import news.motogp.activity.ArticleSingleActivity;
import news.motogp.R;
import news.motogp.network.JsonDownloadArticle;
import news.motogp.network.ThreadHandler;
import news.motogp.utils.ArticleArrayAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ArticleFragment extends Fragment {
	
	private static final String DATA_KEY = "Data";
	private static final String TEXT_KEY = "Texts";
	private static final String IMAGE_KEY = "Image";
	private static final String CITY_KEY = "City";
	
	
	private View layout;
	// private View schedule;
	private GridView gridview;
	private RelativeLayout rlDownloading;
	private RelativeLayout rlInternetOff;
	
	private String[] texts = null;
	private String[] image = null;
	private String[] data  = null;
	private String[] city  = null;
	
	
	private ArticleArrayAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layout = inflater.inflate(R.layout.layout_article, container, false);
		layout.setDrawingCacheEnabled(false);
		gridview= (GridView) layout.findViewById(R.id.gridview);
		rlDownloading = (RelativeLayout) layout.findViewById(R.id.rl_downloading);
		rlInternetOff = (RelativeLayout) layout.findViewById(R.id.rl_internet_off);
		
		Log.d("ArticleArrayAdapter", "savedInstanceState"+ savedInstanceState);
		
		if (savedInstanceState != null && savedInstanceState.getStringArray(TEXT_KEY)!=null) {
			texts = savedInstanceState.getStringArray(TEXT_KEY);
			image = savedInstanceState.getStringArray(IMAGE_KEY);
			data = savedInstanceState.getStringArray(DATA_KEY);
			city = savedInstanceState.getStringArray(CITY_KEY);

			adapter = new ArticleArrayAdapter(getActivity(), city, data, image);
			
			ThreadHandler.runOnMainThread(new Runnable() {
				
				@Override
				public void run(){
					gridview.setAdapter(adapter);
				}
			});
			
		}
		else{
		
//			final ProgressDialog progressDialog;
//			progressDialog = new ProgressDialog(getActivity());
//			progressDialog.setCancelable(true);
//			progressDialog.setMessage(getString(R.string.progressdialog_article));
//			progressDialog.show();
			
			rlDownloading.setVisibility(View.VISIBLE);
			
			ThreadHandler.initializeMainHandler();
			
			ThreadHandler.start("FragmentMenu", new Runnable() {
				@Override
				public void run() {
					JsonDownloadArticle jsondownloadArticle= new JsonDownloadArticle();
					ArrayList<String[]> dataArticle = jsondownloadArticle.getArticle(getActivity());
					city = dataArticle.get(0);
					data = dataArticle.get(1);
					image= dataArticle.get(2);
					texts= dataArticle.get(4);
					
					Log.d("ArticleFragment", "texts "+texts);
					
					nenter+=1;
			
					adapter = new ArticleArrayAdapter(getActivity(), city, data, image);
					
					ThreadHandler.runOnMainThread(new Runnable() {
						
						@Override
						public void run(){
							gridview.setAdapter(adapter);
							rlDownloading.setVisibility(View.GONE);
//							progressDialog.dismiss();
						}
					});
							
				}
				
			});
			
			if(!isOnline()){
				
				String txt=getString(R.string.toast_no_internet);
				Toast t=Toast.makeText(getActivity(), txt, Toast.LENGTH_LONG);
				t.show();
//				progressDialog.dismiss();
				rlDownloading.setVisibility(View.GONE);
				rlInternetOff.setVisibility(View.VISIBLE);
				
			}
			
		}
		gridview.setOnItemClickListener(new OnItemClickListener()
		{
		    @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
		    { 
		    	Intent i = new Intent(getActivity(), ArticleSingleActivity.class);
		    	i.putExtra("ARTICLE", texts[position]);
		    	i.putExtra("IMAGE", image[position]);
				startActivity(i);
		    }
		});
		return layout;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);    
	    outState.putStringArray(TEXT_KEY, texts);
	    outState.putStringArray(IMAGE_KEY, image);
	    outState.putStringArray(DATA_KEY, data);
	    outState.putStringArray(CITY_KEY, city);
	}
	
	int nenter=0;

	@Override
    public void onDestroy() {
        super.onDestroy();
        layout.clearFocus();
        layout.buildDrawingCache();
        layout.invalidate();
    }
	
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}	
	
}
