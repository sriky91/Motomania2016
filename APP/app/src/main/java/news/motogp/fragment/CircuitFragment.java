package news.motogp.fragment;

import java.util.Calendar;

import news.motogp.activity.ResultActivity;
import news.motogp.R;
import news.motogp.network.JsonDownloadResults;
import news.motogp.utils.CircuitFragmentArrayAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class CircuitFragment extends Fragment{
	
	LayoutInflater inflater;
	protected FrameLayout webViewPlaceholder;
	protected WebView webView;
	protected Boolean isResult=false;
	View layout;
	protected View toRound;
	protected int nInvisible;
	protected boolean jsonIsDownload;
	int positionClick;
	TableLayout country_table;
	
	private int nRace;
	
	private final int N_TOT_RACE = 18;
	
	private boolean[] boolFlagRaceHeld;
	private String[] textData;
	private String[] textCircuit;
	private String[] textCity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater=inflater;
		System.gc();
		layout = inflater.inflate(R.layout.layout_article, container, false);
		layout.setDrawingCacheEnabled(false);
		jsonIsDownload=false;
		
		boolFlagRaceHeld = new boolean[N_TOT_RACE+1];
		boolFlagRaceHeld[0] = false;
		isDidRace();
		
		textData = getResources().getStringArray(R.array.circuit_date); 
		textCircuit = getResources().getStringArray(R.array.circuit_name); 
		textCity = getResources().getStringArray(R.array.circuit_city); 
		
		GridView gridview = (GridView) layout.findViewById(R.id.gridview);
		CircuitFragmentArrayAdapter adapter = new CircuitFragmentArrayAdapter(getActivity(), boolFlagRaceHeld, textData, textCircuit, textCity);
		gridview.setAdapter(adapter);
		
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				initUI(position);
			}
		});

		return layout;
	}

	public void isDidRace() {
		
		Calendar cal = Calendar.getInstance();
		int month=cal.get(Calendar.MONTH)+1;
		double day=cal.get(Calendar.DAY_OF_MONTH);


		double dataRaces[]={3.20,4.03,4.10,4.24,5.08,5.22,6.05,6.26,7.17,8.14,8.21,9.04,9.11,9.25,10.16,10.23,10.30,11.13};
		double data=month+(day/100);
		nInvisible=0;
		for(int i=0;i<18;i++){
			if(data<dataRaces[i]){
				Log.d("dataRaces", "dataRaces:"+dataRaces[i]+" data:"+data);
				boolFlagRaceHeld[i+1] = false;
				++nInvisible;
			}
			else
				boolFlagRaceHeld[i+1] = true;
		}
	}
	
	boolean motoGPResults = true;
 	boolean moto2Results = true;
 	boolean moto3Results = true;
	
	private void openDialog(){
		motoGPResults = true;
	 	moto2Results = true;
	 	moto3Results = true;
	    final Dialog dialog = new Dialog(getActivity());
	    dialog.setTitle(getString(R.string.position_result));
	    dialog.setContentView(R.layout.dialog_result);
	    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
	    final Button btnMotoGP = (Button)dialog.getWindow().findViewById(R.id.motogp);
	    final Button btnMoto2 = (Button)dialog.getWindow().findViewById(R.id.moto2);
	    final Button btnMoto3 = (Button)dialog.getWindow().findViewById(R.id.moto3);
	     
	    if(JsonDownloadResults.motoGP == null || JsonDownloadResults.motoGP.positions[0].length -1 < nRace){
	    	btnMotoGP.setBackgroundResource(R.drawable.custom_btn_orange_off);
	    	motoGPResults = false;
	     }
	    if(JsonDownloadResults.moto2 == null || JsonDownloadResults.moto2.positions[0].length -1 < nRace){
	    	btnMoto2.setBackgroundResource(R.drawable.custom_btn_orange_off);
	    	moto2Results = false;
	     }
	    if(JsonDownloadResults.moto3 == null || JsonDownloadResults.moto3.positions[0].length -1 < nRace){
	    	btnMoto3.setBackgroundResource(R.drawable.custom_btn_orange_off);
	    	moto3Results = false;
	    }
			     
		btnMotoGP.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(motoGPResults){
					clickOnMOtoBtn(dialog, 1);
				}
				else{
					Toast.makeText(getActivity(), getString(R.string.tast_results_are_not), Toast.LENGTH_SHORT).show();
					btnMotoGP.setPressed(true);
				}
			}
		});
		btnMoto2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(moto2Results){
					clickOnMOtoBtn(dialog, 2);
				}
				else{
					Toast.makeText(getActivity(), getString(R.string.tast_results_are_not), Toast.LENGTH_SHORT).show();
					btnMoto2.setPressed(true);
				}
			}
		});
		btnMoto3.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(moto3Results){
					clickOnMOtoBtn(dialog, 3);
				}
				else{
					Toast.makeText(getActivity(), getString(R.string.tast_results_are_not), Toast.LENGTH_SHORT).show();
					btnMoto3.setPressed(true);
				}
			}
		});
		dialog.show();
	}

	private void clickOnMOtoBtn(final Dialog dialog, int nMoto) {
		Intent i = new Intent(getActivity(), ResultActivity.class);
		i.putExtra("NUMBER_OF_MOTO", nMoto);
		i.putExtra("NUMBER_OF_RACE", nRace);
		startActivity(i);
	    dialog.dismiss();
	}
	
	public void initUI(int pos)
	{
//		++pos;
		boolean raceMade;
//		if(Integer.parseInt(tag.substring(4))<=18-nInvisible){
			raceMade= pos<=JsonDownloadResults.getNraces()-1;
			if(/*isOnline()*/ (JsonDownloadResults.getisDownloaded(getActivity()) || jsonIsDownload==true) && raceMade){
//				if(Integer.parseInt(tag.substring(4))<=JsonDownloadResults.getNraces()-1){
					isResult=true;
					
//					inflater.inflate(R.layout.position, null);
//					layout=inflater.inflate(R.layout.position, null);
					
					
					nRace=pos;
					
						
					openDialog();
					
//				}
			}
			else{
				String txt=getString(R.string.toast_results_in_downloading);
				Log.d("results", "pos: "+pos+ " nInvisible: "+nInvisible+ "condizione: "+ (pos<(18-nInvisible)));
				if(!isOnline()){
					txt=getString(R.string.toast_no_internet);
				}
				if(pos>(18-nInvisible)){
					txt=getString(R.string.tast_results_race_is_not_made);
				}
				else if(JsonDownloadResults.getInDownoload() && isOnline()){
					txt=getString(R.string.toast_results_in_downloading);
				}
				else if(!raceMade && isOnline()){
					txt=getString(R.string.tast_results_are_not);
				}
				
				
				Toast t=Toast.makeText(getActivity(), txt, Toast.LENGTH_SHORT);
				t.show();
				JsonDownloadResults.getJson(getActivity());
//				
			}
//		}
//		else{
//			Toast t=Toast.makeText(getActivity(), getString(R.string.tast_results_race_is_not_made), Toast.LENGTH_SHORT);
//			t.show();
//		}
	}
	
	public int getnRace() {
		return nRace;
	}
	
//	private void unbindDrawables(View view) {
//        if (view.getBackground() != null) {
////        view.getBackground().setCallback(null);
//        	view.getDrawable().setCallback(null);
//        }
//        if (view instanceof ViewGroup) {
//            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
//            unbindDrawables(((ViewGroup) view).getChildAt(i));
//            }
//        ((ViewGroup) view).removeAllViews();
//        view.setBackgroundResource(0);
//        }
// }
	
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