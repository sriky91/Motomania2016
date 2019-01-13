package news.motogp.activity;

import java.util.HashMap;
import java.util.Locale;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import news.motogp.application.MotomaniaApplication;
import news.motogp.application.MotomaniaApplication.TrackerName;
import news.motogp.R;
import news.motogp.utils.DrawableManager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticleSingleActivity extends Activity implements OnInitListener/*, OnUtteranceCompletedListener*/{
	ImageView img;
	TextToSpeech talker;
	String text;
	private MotomaniaApplication ma;
	private String image;
	private String[] text2sayArray = null;
	private int index = 0;
	private MenuItem playVoiceButton;
	private Drawable playResource;
	private Drawable stopResource;
	private Locale locale;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.article);
        img= (ImageView) findViewById(R.id.imageView1);
        TextView tv= (TextView) findViewById(R.id.text);
        DrawableManager dm = DrawableManager.getInstance();
        Intent intent = getIntent();
        if(intent != null){
        	text = intent.getExtras().getString("ARTICLE");
        	tv.setText (text);
        	image = intent.getExtras().getString("IMAGE");
        	dm.fetchDrawableOnThread("http://motogp2014.altervista.org/images/"+image, img);
        }
        
        locale = new Locale("en");
        
    	Tracker t = ((MotomaniaApplication) getApplication()).getTracker(TrackerName.APP_TRACKER);
    	t.setScreenName(getString(R.string.title_section1) + " " + image);
    	t.send(new HitBuilders.AppViewBuilder().build());
    	
    	playResource = getResources().getDrawable(R.drawable.play);
    	stopResource = getResources().getDrawable(R.drawable.stop);
    
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Drawable d = img.getDrawable();  
		if (d != null) d.setCallback(null);  
		img.setImageDrawable(null);  
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		if(talker == null){
			talker = new TextToSpeech(this, this);
		}
		else{
			playVoiceButton.setIcon(playResource);
			talker.stop();
			talker.shutdown();
			talker = null;
			index = 0;
			text2sayArray = null;
			this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
		
		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
		getMenuInflater().inflate(R.menu.article_voice, menu);
		playVoiceButton = menu.getItem(0);
		return super.onCreateOptionsMenu(menu);
	}
	
	public void say(){
		HashMap<String, String> myHashAlarm = new HashMap<String, String>();
	    myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_MUSIC));
	    myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "SOME MESSAGE");
    	talker.speak(text2sayArray[index], TextToSpeech.QUEUE_FLUSH, myHashAlarm);
    }
	@Override
	public void onInit(int status) {
		if(text2sayArray == null){
			
			if(!getString(R.string.menu_pilots).equals("Piloti")){
				talker.setLanguage(locale);
			}
			
			playVoiceButton.setIcon(stopResource);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			Intent intent = getIntent();
	        if(intent != null)
	        	text = intent.getExtras().getString("ARTICLE");
	        Log.d("tag", "onInit_ "+text);
	        text2sayArray = text.split("\n\n");
	        say();
	        checkTTS();
//	        if(status == TextToSpeech.SUCCESS) {
//	            talker.setOnUtteranceCompletedListener(this);
//	        }
		}
//        if(status == TextToSpeech.SUCCESS) {
//            talker.setOnUtteranceCompletedListener(this);
//        }
        
	}
	
	private void checkTTS(){
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
		    @Override
		    public void run() {
		    	if( talker != null && text2sayArray != null  && !talker.isSpeaking()){
			    	++index;
					if(text2sayArray.length > index){
						say();
						checkTTS();
					}
					else{
						playVoiceButton.setIcon(playResource);
			        	talker.stop();
						talker.shutdown();
						talker = null;
						index = 0;
						text2sayArray = null;
						getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	//					onCreate(null);
			        }
		    	}
		    	else{
		    		checkTTS();
		    	}
		    }
		}, 500);
	}
	
	
//	public void onUtteranceCompleted(String utteranceId) {
//		++index;
//		if(talker != null && text2sayArray.length > index){
//			Log.d("ArticleSingleActivity","IF onUtteranceCompleted: "+ utteranceId); //utteranceId == "SOME MESSAGE"
//			say();
//		}
//		else{
////			playVoiceButton.setIcon(playResource);
//        	Log.d("ArticleSingleActivity","ELSE onUtteranceCompleted: "+ utteranceId);
//        	talker.stop();
//			talker.shutdown();
//			talker = null;
//			index = 0;
//			text2sayArray = null;
////			this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
////			onCreate(null);
//        }
//	}
	
//	public void click(View v){
//		if(talker == null){
//			talker = new TextToSpeech(this, this);
//		}
//		else{
//			talker.stop();
//			talker.shutdown();
//			talker = null;
//			index = 0;
//			text2sayArray = null;
//		}
//	}
	
	@Override
	protected void onPause() {
		if(talker != null){
			talker.stop();
			talker.shutdown();
			talker = null;
			playVoiceButton.setIcon(playResource);
			this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
		super.onPause();
	}
	
	@Override
	public void onStart() {
//		Tracker t = ((MotomaniaApplication) getApplication()).getTracker(
//		    TrackerName.APP_TRACKER);
//		t.setScreenName("Article_selection"+"Article"+ image);
//		t.send(new HitBuilders.AppViewBuilder().build());
		super.onStart();
	}
	
	
	
//	@Override
//    public void onStart() {
//      super.onStart();
//      EasyTracker et=EasyTracker.getInstance(this);
//      et.send(MapBuilder.createEvent("Article_selection", "Article", ArticleFragment.imageUrl, null).build());
//      EasyTracker.getInstance(this).activityStart(this);
//    }
//
//    @Override
//    public void onStop() {
//      super.onStop();
//      EasyTracker.getInstance(this).activityStop(this);
//    }
}
