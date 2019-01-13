package news.motogp.alarm;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import news.motogp.R;
import news.motogp.activity.MainActivity;

/**
 * This {@code IntentService} does the app's actual work.
 * {@code SampleAlarmReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class SchedulingService extends IntentService {
    public SchedulingService() {
        super("SchedulingService");
    }
    
    public static final String TAG = "Scheduling Demo";
    // An ID used to post the notification.
    public static final int NOTIFICATION_ID = 1;
    // The string the app searches for in the Google home page content. If the app finds 
    // the string, it indicates the presence of a doodle.  
    public static final String SEARCH_STRING = "doodle";
    // The Google home page URL from which the app fetches content.
    // You can find a list of other Google domains with possible doodles here:
    // http://en.wikipedia.org/wiki/List_of_Google_domains
    public static final String URL = "http://www.google.com";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
	private int nRace;
	private int nDay;
	
	private boolean vibrationON;
	private boolean soundON;

    @Override
    protected void onHandleIntent(Intent intent) {
        // BEGIN_INCLUDE(service_onhandle)
        // The URL from which to fetch content.
        String urlString = URL;
    
        String result ="";
        
        // Try to connect to the Google homepage and download content.
        /*try {
            result = loadFromNetwork(urlString);
        } catch (IOException e) {
            Log.i(TAG, getString(R.string.connection_error));
        }
    
        // If the app finds the string "doodle" in the Google home page content, it
        // indicates the presence of a doodle. Post a "Doodle Alert" notification.
        if (result.indexOf(SEARCH_STRING) != -1) {
            sendNotification(getString(R.string.doodle_found));
            Log.i(TAG, "Found doodle!!");
        } else {
            sendNotification(getString(R.string.no_doodle));
            Log.i(TAG, "No doodle found. :-(");
        }*/
        // Release the wake lock provided by the BroadcastReceiver.
        
        String mex = intent.getExtras().getString("MEX");
        vibrationON = intent.getExtras().getBoolean("VIBRATION");
        soundON = intent.getExtras().getBoolean("SOUND");
//        service.putExtra("VIBRATION", notificationActivation[5]);
//        service.putExtra("SOUND", notificationActivation[6]);
        nRace = intent.getExtras().getInt("N_RACE");
        nDay = intent.getExtras().getInt("N_DAY");
        int index = intent.getExtras().getInt("INDEX");
        
        String[] timeBeforeArray = getResources().getStringArray(R.array.time_before); 
        
        if( getString(R.string.title_section4).equals("Orari")){
	        String mexNotif = getString(R.string.notify_mex, timeBeforeArray[index]);
	        sendNotification( mexNotif + mex );
        }
        else{
        	String mexNotif = getString(R.string.notify_mex, mex,timeBeforeArray[index]);
        	sendNotification( mexNotif );
        }
        AlarmReceiver.completeWakefulIntent(intent);
        // END_INCLUDE(service_onhandle)
    }
    
    // Post a notification indicating whether a doodle was found.
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
               this.getSystemService(Context.NOTIFICATION_SERVICE);
    
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//            new Intent(this, MainActivity.class), 0);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("NOTIFICATION", "TimesRaces");
        intent.putExtra("NOTIFICATION_N_RACE", nRace);
        intent.putExtra("NOTIFICATION_N_DAY", nDay);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle(getString(R.string.app_name))
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(msg))
        .setDefaults(0/*Notification.DEFAULT_SOUND*/)
        .setAutoCancel(true)
//        .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
//        .setSound(Uri.parse("android.resource://"+ getPackageName() + "/" + R.raw.motogp_sound))
        .setLights(Color.parseColor("#ff9200"/*#E56717*/),1,1) // will blink
        .setContentText(msg);
        
        if(vibrationON)
        	mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        if(soundON)
        	mBuilder.setSound(Uri.parse("android.resource://"+ getPackageName() + "/" + R.raw.motogp_sound));
//        mBuilder.setSound(Uri.parse("android.resource://"+ getPackageName() + "/" + R.raw.motogp_sound));
        
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
 
//
// The methods below this line fetch content from the specified URL and return the
// content as a string.
//
    /** Given a URL string, initiate a fetch operation. */
    private String loadFromNetwork(String urlString) throws IOException {
        InputStream stream = null;
        String str ="";
      
        try {
            stream = downloadUrl(urlString);
            str = readIt(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }      
        }
        return str;
    }

    /**
     * Given a string representation of a URL, sets up a connection and gets
     * an input stream.
     * @param urlString A string representation of a URL.
     * @return An InputStream retrieved from a successful HttpURLConnection.
     * @throws IOException
     */
    private InputStream downloadUrl(String urlString) throws IOException {
    
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Start the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }

    /** 
     * Reads an InputStream and converts it to a String.
     * @param stream InputStream containing HTML from www.google.com.
     * @return String version of InputStream.
     * @throws IOException
     */
    private String readIt(InputStream stream) throws IOException {
      
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        for(String line = reader.readLine(); line != null; line = reader.readLine()) 
            builder.append(line);
        reader.close();
        return builder.toString();
    }
}