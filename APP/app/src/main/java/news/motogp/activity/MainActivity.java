package news.motogp.activity;

import news.motogp.alarm.AlarmReceiver;
import news.motogp.fragment.ArticleFragment;
import news.motogp.fragment.CircuitFragment;
import news.motogp.fragment.LastHourFragment;
import news.motogp.fragment.OrariFragment;
import news.motogp.fragment.PilotsFragment;
import news.motogp.motomania.NavigationDrawerFragment;
import news.motogp.R;
import news.motogp.network.JsonDownloadResults;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	private ArticleFragment fragmentArticle;
	private LastHourFragment fragmentLastHour;
	private CircuitFragment fragmentCircuit;
	private OrariFragment fragmentOrari;
	private PilotsFragment fragmentPilots;
	
	private int nFragmentVisible = 0;

	private boolean exit = false;
	
	private PendingIntent pendingIntent;
	boolean switchOn = true; 
	private AlarmReceiver alarm;
	
	private static final String NOTIFICATON_KEY = "notificationPref";
	private static final String NOTIFICATON_KEY_MIN_BEFORE = "notificationPrefMinBefore";
	private boolean [] notificationActivation;
	SharedPreferences.Editor editor;

	private String notification;

	private int nDay;
	private int nRace;

	private int minBefore;
	private String[] titleDrawer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent i = getIntent();
	    notification = i.getStringExtra("NOTIFICATION");
	    Log.d("MainActivity", "notification: "+notification);
	    
	    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
	    editor = sharedPref.edit();

		titleDrawer = getResources().getStringArray(R.array.titles_navigation_bar);
		ArrayList<String> ciao;


//	    editor.putBoolean(NOTIFICATON_KEY, true);
	    
	    notificationActivation = new boolean[7];

	    for(int nNotify=0; nNotify<notificationActivation.length; nNotify++){
	    	if(nNotify!=6){
	    		notificationActivation[nNotify] = sharedPref.getBoolean(NOTIFICATON_KEY+nNotify, true);
	    	}
	    	else{
	    		notificationActivation[nNotify] = sharedPref.getBoolean(NOTIFICATON_KEY+nNotify, true);
	    	}
	    }
	    
	    minBefore = sharedPref.getInt(NOTIFICATON_KEY_MIN_BEFORE, 1);
	    
	    alarm = new AlarmReceiver();
	    if(notificationActivation[0]){
			new Thread(new Runnable() { 
				public void run(){        
	        		alarm.setAlarm(getApplicationContext(), notificationActivation, minBefore);
	            }
	        }).start();
	    }
	    
	    Log.d("MainActivity", "MAIN_savedInstanceState: "+ savedInstanceState +"nFragmentVisible: "+nFragmentVisible);

		if(savedInstanceState != null){
			if( savedInstanceState.containsKey("ARTICLE_FRAGMENT"))
				fragmentArticle = (ArticleFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ARTICLE_FRAGMENT");
			else
				fragmentArticle = new ArticleFragment();
			if( savedInstanceState.containsKey("LASTHOUR_FRAGMENT"))
				fragmentLastHour = (LastHourFragment) getSupportFragmentManager().getFragment(savedInstanceState, "LASTHOUR_FRAGMENT");
			else
				fragmentLastHour = new LastHourFragment();
			if( savedInstanceState.containsKey("CIRCUIT_FRAGMENT"))
				fragmentCircuit = (CircuitFragment) getSupportFragmentManager().getFragment(savedInstanceState, "CIRCUIT_FRAGMENT");
			else
				fragmentCircuit = new CircuitFragment();
			if( savedInstanceState.containsKey("ORARI_FRAGMENT"))
				fragmentOrari = (OrariFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ORARI_FRAGMENT");
			else
				fragmentOrari = new OrariFragment();
			if( savedInstanceState.containsKey("PILOTS_FRAGMENT"))
				fragmentPilots = (PilotsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "PILOTS_FRAGMENT");
			else
				fragmentPilots = new PilotsFragment();
		}
		else{
			JsonDownloadResults.getJson(this);
			fragmentArticle = new ArticleFragment();
			fragmentLastHour = new LastHourFragment();
			fragmentCircuit = new CircuitFragment();
			fragmentOrari = new OrariFragment();
			fragmentPilots = new PilotsFragment();
		}
//		fragment = (ArticleFragment) Fragment.instantiate(this, ArticleFragment.class.getName(), savedInstanceState);


		setContentView(R.layout.activity_main);
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		
		if(notification != null && notification.equals("TimesRaces")){
			nRace = i.getIntExtra("NOTIFICATION_N_RACE", -1);
			nDay = i.getIntExtra("NOTIFICATION_N_DAY", -1);
			Log.d("Orarifragment", "###NUMBER_OF_EVENT-1: "+ nRace +" NUMBER_OF_DAY: "+nDay);
			Log.d("MAIN", "nDay: "+ nDay);
			if(titleDrawer[nFragmentVisible].equals(getString(R.string.title_section3))){
				onNavigationDrawerItemSelected(3);
			}
		}
		
//		else{
//			fragment = new ArticleFragment();
//		}
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		if(position == 0 && notification != null && nRace == -1){
			return;
		}
		else if(titleDrawer[position].equals(getString(R.string.title_section4)) && notification != null && notification.equals("TimesRaces") && nRace != -1){
			Log.d("Orarifragment", "###NUMBER_OF_EVENT-1: "+ nRace +" NUMBER_OF_DAY: "+nDay);
			fragmentOrari.setOpenRace(nRace, nDay);
			nRace = -1;
			nDay = 0;
			position= 3;
			notification = null;
	    }

		if(titleDrawer[position].equals(getString(R.string.title_section1))) {
			nFragmentVisible = 0;
			fragmentManager.beginTransaction().replace(R.id.container, fragmentArticle).commit();
		}
		else if(titleDrawer[position].equals(getString(R.string.title_section2))) {
			nFragmentVisible = position;
			fragmentManager.beginTransaction().replace(R.id.container, fragmentLastHour).commit();
		}
		else if(titleDrawer[position].equals(getString(R.string.title_section3))) {
			nFragmentVisible = position;
			fragmentManager.beginTransaction().replace(R.id.container, fragmentCircuit).commit();
		}
		else if(titleDrawer[position].equals(getString(R.string.title_section4))) {
			nFragmentVisible = position;
			fragmentManager.beginTransaction().replace(R.id.container, fragmentOrari).commit();
		}
		else if(titleDrawer[position].equals(getString(R.string.title_section5))) {
			nFragmentVisible = position;
			fragmentManager.beginTransaction().replace(R.id.container, fragmentPilots).commit();
		}
		
//		fragmentManager
//				.beginTransaction()
//				.replace(R.id.container,
//						PlaceholderFragment.newInstance(position + 1)).commit();
	}

	private int checkPosition(String title){
		for(int i=0; i<titleDrawer.length; i++){
			if(titleDrawer.equals(title)){
				return i;
			}
		}
		return 0;
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen() && titleDrawer[nFragmentVisible].equals(getString(R.string.title_section4))/*mTitle.equals(getString(R.string.title_section4))*/) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);

		//Save the fragment's instance

	    if(titleDrawer[nFragmentVisible].equals(getString(R.string.title_section1)))
	    	getSupportFragmentManager().putFragment(outState, "ARTICLE_FRAGMENT", fragmentArticle);
	    else if(titleDrawer[nFragmentVisible].equals(getString(R.string.title_section2)))
			getSupportFragmentManager().putFragment(outState, "LASTHOUR_FRAGMENT", fragmentLastHour);
	    else if(titleDrawer[nFragmentVisible].equals(getString(R.string.title_section3)))
			getSupportFragmentManager().putFragment(outState, "CIRCUIT_FRAGMENT", fragmentCircuit);
	    else if(titleDrawer[nFragmentVisible].equals(getString(R.string.title_section4)))
			getSupportFragmentManager().putFragment(outState, "ORARI_FRAGMENT", fragmentOrari);
	    else if(titleDrawer[nFragmentVisible].equals(getString(R.string.title_section5)))
			getSupportFragmentManager().putFragment(outState, "PILOTS_FRAGMENT", fragmentPilots);

	}
	
	private CheckBox cbMotoGP;
	private CheckBox cbMoto2;
	private CheckBox cbMoto3;
	private RadioButton rbOnlyRace;
	private RadioButton rbAllEvents;
	private ImageView ivSwitchNotify;
	private ImageView ivSwitchVibration;
	private ImageView ivSwitchSound;
	private Spinner spinnerTimeBefore;
	private LinearLayout llSettings;
	private boolean switchOnVibration;
	private boolean switchOnSound;

	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {

			LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	View v= inflater.inflate(R.layout.dialog_orari_setting, null,false);
	    	AlertDialog.Builder b= new AlertDialog.Builder(this);
	    	b.setView(v);
	    	
	    	cbMotoGP = (CheckBox) v.findViewById(R.id.cb_motoGP);
	    	cbMoto2 = (CheckBox) v.findViewById(R.id.cb_moto2);
	    	cbMoto3 = (CheckBox) v.findViewById(R.id.cb_moto3);
	    	rbOnlyRace = (RadioButton) v.findViewById(R.id.rb_only_race);
	    	rbAllEvents = (RadioButton) v.findViewById(R.id.rb_all_events);
	    	ivSwitchNotify = (ImageView) v.findViewById(R.id.iv_switch_notify);
	    	spinnerTimeBefore = (Spinner) v.findViewById(R.id.spinner_time_before);
	    	ivSwitchVibration = (ImageView) v.findViewById(R.id.iv_switch_vibration);
	    	ivSwitchSound = (ImageView) v.findViewById(R.id.iv_switch_sound);
	    	
	    	llSettings = (LinearLayout) v.findViewById(R.id.ll_settings);
	    	
//	    	ivSwitchNotify.setImageResource(R.drawable.switch_on);
	    	if(notificationActivation[5]){
	    		ivSwitchVibration.setImageResource(R.drawable.switch_on_yellow);
	    		switchOnVibration = true;
	    	}
	    	else{
	    		ivSwitchVibration.setImageResource(R.drawable.switch_off);
	    		switchOnVibration = false;
	    	}
	    	if(notificationActivation[6]){
	    		ivSwitchSound.setImageResource(R.drawable.switch_on_yellow);
	    		switchOnSound = true;
	    		
	    	}
	    	else{
	    		ivSwitchSound.setImageResource(R.drawable.switch_off);
	    		switchOnSound = false;
	    	}
	    	ArrayAdapter<CharSequence> speedAdapter = ArrayAdapter
                    .createFromResource(this, R.array.time_before, R.layout.spinner_item);
	    	spinnerTimeBefore.setAdapter(speedAdapter);
	    	spinnerTimeBefore.setSelection(minBefore);
	    	
	    	if(!notificationActivation[0]){
	    		setSwitchOff();
	    	}
	    	else{
	    		setSwitchTrue();
	    		cbMotoGP.setChecked(notificationActivation[1]);
	    		cbMoto2.setChecked(notificationActivation[2]);
	    		cbMoto3.setChecked(notificationActivation[3]);
	    		rbOnlyRace.setChecked(notificationActivation[4]);
	    		if(!notificationActivation[4]){
	    			rbOnlyRace.setChecked(false);
	    			rbAllEvents.setChecked(true);
	    		}
	    	}
	    	
	    	ivSwitchNotify.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(switchOn){
						setSwitchOff();
					}
					else{
						setSwitchTrue();
						cbMotoGP.setChecked(true);
						cbMoto2.setChecked(true);
						cbMoto3.setChecked(true);
						rbAllEvents.setChecked(true);
						rbOnlyRace.setChecked(true);
					}
				}

			});
	    	
	    	ivSwitchVibration.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(switchOnVibration){
						switchOnVibration = false;
						ivSwitchVibration.setImageResource(R.drawable.switch_off);
						
					}
					else{
						switchOnVibration = true;
						ivSwitchVibration.setImageResource(R.drawable.switch_on_yellow);
					}
				}
	    	});
	    	
	    	ivSwitchSound.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(switchOnSound){
						switchOnSound = false;
						ivSwitchSound.setImageResource(R.drawable.switch_off);
						
					}
					else{
						switchOnSound = true;
						ivSwitchSound.setImageResource(R.drawable.switch_on_yellow);
					}
				}
	    	});
//	    	TextView t= (TextView) v.findViewById(R.id.text);
//	    	t.setText("ciao");
	    	
	    	AlertDialog a=b.create();
	    	a.show();
	    	
	    	a.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					notificationActivation[0] = switchOn;
					notificationActivation[1] = cbMotoGP.isChecked();
					notificationActivation[2] = cbMoto2.isChecked();
					notificationActivation[3] = cbMoto3.isChecked();
					notificationActivation[4] = rbOnlyRace.isChecked();
					
					notificationActivation[5] = switchOnVibration;
					notificationActivation[6] = switchOnSound;
					
					minBefore = spinnerTimeBefore.getSelectedItemPosition();
					
					Log.d("MAIN", "switchOn: " + switchOn);
					
					new Thread(new Runnable() { 
						public void run(){   
							if(!switchOn){
								alarm.cancelAlarm(getApplicationContext());
							}
							else{
								alarm.setAlarm(getApplicationContext(), notificationActivation, minBefore);
							}
						}
			        }).start();
					for(int nNotify=0; nNotify<notificationActivation.length; nNotify++){
				    	editor.putBoolean(NOTIFICATON_KEY+nNotify, notificationActivation[nNotify]);
					}
					editor.putInt(NOTIFICATON_KEY_MIN_BEFORE, spinnerTimeBefore.getSelectedItemPosition());
					editor.commit();
				}
			});
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void setSwitchOff() {
		ivSwitchNotify.setImageResource(R.drawable.switch_off);
		switchOn = false;
//		cbMotoGP.setEnabled(false);
//		cbMoto2.setEnabled(false);
//		cbMoto3.setEnabled(false);
//		rbOnlyRace.setEnabled(false);
//		rbAllEvents.setEnabled(false);
		cbMotoGP.setChecked(false);
		cbMoto2.setChecked(false);
		cbMoto3.setChecked(false);
		rbOnlyRace.setChecked(false);
		rbAllEvents.setChecked(false);
		disableEnableControls(false, llSettings);
		ivSwitchVibration.setImageResource(R.drawable.switch_off);
		ivSwitchSound.setImageResource(R.drawable.switch_off);
	}

	private void disableEnableControls(boolean enable, ViewGroup vg){
	    for (int i = 0; i < vg.getChildCount(); i++){
	       View child = vg.getChildAt(i);
	       child.setEnabled(enable);
	       if (child instanceof ViewGroup){ 
	          disableEnableControls(enable, (ViewGroup)child);
	       }
	    }
	}
	
	public void setSwitchTrue() {
		ivSwitchNotify.setImageResource(R.drawable.switch_on_yellow);
		switchOn = true;
//		cbMotoGP.setEnabled(true);
//		cbMoto2.setEnabled(true);
//		cbMoto3.setEnabled(true);
//		rbOnlyRace.setEnabled(true);
//		rbAllEvents.setEnabled(true);
		disableEnableControls(true, llSettings);
		ivSwitchVibration.setImageResource(R.drawable.switch_on_yellow);
		ivSwitchSound.setImageResource(R.drawable.switch_off);
	}

	
	@Override
	public void onBackPressed() {
		if (exit){
			super.onBackPressed();
			this.finish();
		 }
	     else{
	        Toast.makeText(this, getString(R.string.toast_press_back_to_exit),
	        Toast.LENGTH_SHORT).show();
	        exit = true;
	        new Handler().postDelayed(new Runnable() {
	        @Override
	        	public void run() {
	                    exit = false;
	                }
	            }, 3 * 1000);
	        }
	}

	public void initUI(View v)
	{
		fragmentCircuit.initUI(-1);
	}
	
}
