package news.motogp.activity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import news.motogp.application.MotomaniaApplication;
import news.motogp.application.MotomaniaApplication.TrackerName;
import news.motogp.R;
import news.motogp.network.JsonDownloadResults;
import news.motogp.utils.RaceResultData;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity
{
	protected FrameLayout webViewPlaceholder;
	protected WebView webView;
	protected Boolean isResult=false;
	protected View toRound;
	protected int nInvisible;
	protected boolean jsonIsDownload;
	
	
	int positionClick;
	ListView listView;
	View layout;
	public static Context context;
	TableLayout country_table;
	private int n_Race = 0;
	private int nCategoryMoto;
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//		      Bundle savedInstanceState) {
//		
//		    layout = inflater.inflate(R.layout.position,
//		        container, false);
////		   
////		    fillCountryTable();
//		    setContentView(R.layout.position);
//		    return layout;
//		  }
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.fragment_result);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nCategoryMoto = extras.getInt("NUMBER_OF_MOTO");
            n_Race = extras.getInt("NUMBER_OF_RACE");
        }
        
        country_table=(TableLayout) findViewById(R.id.country_table);
        
//        TODO ciao
//        n_Race = CircuitFragment.nRace;
        RaceResultData raceResultData;
        String category;
        switch (nCategoryMoto) {
			case 1:
				raceResultData = JsonDownloadResults.motoGP;
				category = " MotoGP";
				break;
			case 2:
				raceResultData = JsonDownloadResults.moto2;
				category = " Moto2";
				break;
			case 3:
				raceResultData = JsonDownloadResults.moto3;
				category = " Moto3";
				break;
			default:
				raceResultData = JsonDownloadResults.motoGP;
				category = " MotoGP";
				break;
		}
        
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.position_result) + category);
        
        fillCountryTable(raceResultData);
        
        Tracker t = ((MotomaniaApplication) getApplication()).getTracker(TrackerName.APP_TRACKER);
    	t.setScreenName(getString(R.string.title_section3) + " nubmer of race: "+n_Race);
    	t.send(new HitBuilders.AppViewBuilder().build());
        
//        while(!CountriesList.getisDownloaded(this)){}
//        EasyTracker tracker = EasyTracker.getInstance(this);
//        tracker.set("Pilots", "nubmer of race: "+n_Race);
//        tracker.send(MapBuilder.createAppView().build());
        
    }
//	public static void changeFragment(){
//		((Activity) context).setContentView(R.layout.article);
//	}
//	
	public void fillCountryTable(RaceResultData raceResultData) {
		 
		jsonIsDownload=true;
        TableRow row;
        
        TextView t1, t2, t3, t4, t5;
        
//        ImageView imgBack;
//		imgBack= (ImageView) findViewById(R.id.imgBack);
//        
		
		
//		imgBack.setOnClickListener(new OnClickListener(){
//
//	        public void onClick(View view) {
//	             
////	        	setContentView(R.layout.other);
//	        	isDidRace();
//	        	
//	          }});
        int width=getResources().getDisplayMetrics().widthPixels;
        //Converting to dip unit
        
        /*int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics().widthPixels);
        */
 
        t1=(TextView) findViewById(R.id.tv_1);
        t2=(TextView) findViewById(R.id.tv_2);
        t3=(TextView) findViewById(R.id.tv_3);
        t4=(TextView) findViewById(R.id.tv_4);
        t5=(TextView) findViewById(R.id.tv_5);
        
        if(n_Race > 0){
	        t1.setWidth((10*width)/100);
	        t2.setWidth((35*width)/100);
	        t3.setWidth((12*width)/100);
	        t4.setWidth((20*width)/100);
	        t5.setWidth((23*width)/100);
        }
        else{
	        t1.setWidth((10*width)/100);
	        t2.setWidth((40*width)/100);
	        t3.setWidth((15*width)/100);
	        t4.setWidth((35*width)/100);
	        t5.setVisibility(View.GONE);
        }
        
        for (int current = 0; current < raceResultData.positions.length; current++) {
        	if(raceResultData.positions[current][n_Race]!=null){
 	            row = new TableRow(this);
	           
	            t1 = new TextView(this);
	            t2 = new TextView(this);
	            t3 = new TextView(this);
	            t4 = new TextView(this);
	            
	            t1.setTextColor(0xFF555555);
	            t2.setTextColor(0xFF555555);
	            t3.setTextColor(0xFF555555);
	            t4.setTextColor(0xFF555555);
	            
	            if(!raceResultData.positions[current][n_Race].equals("0"))
	            	t1.setText(raceResultData.positions[current][n_Race]);
	            else{
	            	t1.setText("NC");
	            	t1.setTextColor(Color.RED);
	            }
	            t2.setText(raceResultData.names[current][n_Race]);
	            t3.setText(raceResultData.points[current][n_Race]);
	            t4.setText(raceResultData.marche[current][n_Race]);
	            
	            t1.setTypeface(null, 1);
	            t2.setTypeface(null, 1);
	            t3.setTypeface(null, 1);
	            t4.setTypeface(null, 1);
	            
	            t1.setTextSize(16);
	            t2.setTextSize(16);
	            t3.setTextSize(16);
	            t4.setTextSize(16);
	            
	            t1.setGravity(17);
	            t2.setGravity(17);
	            t3.setGravity(17);
	            t4.setGravity(17);
	            
	            t1.setMinLines(2);
	            
	            row.addView(t1);
	            row.addView(t2);
	            row.addView(t3);
	            row.addView(t4);
	            
	            if(n_Race > 0){
	            	t5 = new TextView(this);
	            	t5.setText(raceResultData.time[current][n_Race]);
	            	t5.setTextColor(0xFF555555);
	            	t5.setTypeface(null, 1);
	            	t5.setTextSize(16);
	            	t5.setGravity(5);
	            	row.addView(t5);
	            	t1.setWidth((10*width)/100);
		            t2.setWidth((35*width)/100);
		            t3.setWidth((10*width)/100);
		            t4.setWidth((20*width)/100);
		            t5.setWidth((23*width)/100);
	            }
	            else{
	            	t1.setWidth((10*width)/100);
		            t2.setWidth((40*width)/100);
		            t3.setWidth((15*width)/100);
		            t4.setWidth((35*width)/100);
	            }
	            
	            row.setGravity(17);
	            
	            if(current %2==0){
	            	row.setBackgroundColor(0xFFE8D9C0);
	            }
	
	            country_table.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	        }
           
            
        }
	}
   
//    @Override
//    public void onStart() {
//      super.onStart();
//      EasyTracker et=EasyTracker.getInstance(this);
//      et.send(MapBuilder.createEvent("Result_selection", "Result", "nubmer of race: "+n_Race, null).build());
//      EasyTracker.getInstance(this).activityStart(this);
//    }
//
//    @Override
//    public void onStop() {
//      super.onStop();
//      EasyTracker.getInstance(this).activityStop(this);
//    }
}
