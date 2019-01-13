package news.motogp.alarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import news.motogp.utils.TimesRaces;

/**
 * When the alarm fires, this WakefulBroadcastReceiver receives the broadcast Intent 
 * and then starts the IntentService {@code SampleSchedulingService} to do some work.
 */
@SuppressLint("NewApi")
public class AlarmReceiver extends WakefulBroadcastReceiver {
    // The app's AlarmManager, which provides access to the system alarm services.
    private AlarmManager alarmMgr;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent alarmIntent;
	private ArrayList<PendingIntent> intentArray;
	private AlarmManager[] alarmManager;
	private boolean[] notificationActivation;
	private Thread thread;
  
    @Override
    public void onReceive(Context context, Intent intent) {   
        // BEGIN_INCLUDE(alarm_onreceive)
        /* 
         * If your receiver intent includes extras that need to be passed along to the
         * service, use setComponent() to indicate that the service should handle the
         * receiver's intent. For example:
         * 
         * ComponentName comp = new ComponentName(context.getPackageName(), 
         *      MyService.class.getName());
         *
         * // This intent passed in this call will include the wake lock extra as well as 
         * // the receiver intent contents.
         * startWakefulService(context, (intent.setComponent(comp)));
         * 
         * In this example, we simply create a new intent to deliver to the service.
         * This intent holds an extra identifying the wake lock.
         */
        Intent service = new Intent(context, SchedulingService.class);
        
        String mex = intent.getExtras().getString("MEX");
        service.putExtra("MEX", mex);
        service.putExtra("VIBRATION",intent.getExtras().getBoolean("VIBRATION"));
        service.putExtra("SOUND",intent.getExtras().getBoolean("SOUND"));
        service.putExtra("N_RACE",intent.getExtras().getInt("N_RACE"));
        service.putExtra("N_DAY", intent.getExtras().getInt("N_DAY"));
        service.putExtra("INDEX", intent.getExtras().getInt("INDEX"));
        
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, service);
        // END_INCLUDE(alarm_onreceive)
    }

    // BEGIN_INCLUDE(set_alarm)
    /**
     * Sets a repeating alarm that runs once a day at approximately 8:30 a.m. When the
     * alarm fires, the app broadcasts an Intent to this WakefulBroadcastReceiver.
     * @param context
     * @param notificationActivation 
     * @param minBefore 
     */
    public void setAlarm(final Context context, final boolean[] notificationActivation, final int minBefore) {
    	
//    	if(thread != null && thread.isAlive()){
//    		thread.interrupt();
//    		thread = null;
//    	}
    	if(alarmManager!= null){
    		cancelAlarm(context);
    	}
    	this.notificationActivation = notificationActivation;
    	Log.d("AlarmReceiver", "############## setAlarm!!!");
    	
        TimesRaces timeRaces = TimesRaces.getIstance();
        timeRaces.populate(notificationActivation, minBefore);
        final List<String> typeRace = timeRaces.getTypeRace();
        final List<Integer> nRace = timeRaces.getnRace();
        final List<Integer> nDay = timeRaces.getnDay();

        intentArray = new ArrayList<PendingIntent>();
        final List<Calendar> arrayDate = timeRaces.getDate();
        alarmManager=new AlarmManager[arrayDate.size()];
        
//        thread = new Thread(new Runnable() { 
//			public void run(){        
//				for(int i=0; i<arrayDate.size(); i++){
//		        	
//		        	Intent intent = new Intent(context, AlarmReceiver.class);
//		        	intent.putExtra("MEX", typeRace.get(i));
//		        	intent.putExtra("N_RACE", nRace.get(i));
//		        	intent.putExtra("N_DAY", nDay.get(i));
//		        	intent.putExtra("INDEX", minBefore );
//		        	intent.putExtra("VIBRATION", notificationActivation[5]);
//		        	intent.putExtra("SOUND", notificationActivation[6]);
//		        	alarmIntent = PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//		        	alarmManager[i] = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//		        	if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//		        		alarmManager[i].setExact(AlarmManager.RTC_WAKEUP, arrayDate.get(i).getTimeInMillis(), alarmIntent);
//		    		} else {
//		    			alarmManager[i].set(AlarmManager.RTC_WAKEUP, arrayDate.get(i).getTimeInMillis(), alarmIntent);
//		    		}
//		            
//		            // Enable {@code SampleBootReceiver} to automatically restart the alarm when the
//		            // device is rebooted.
//		            ComponentName receiver = new ComponentName(context, BootReceiver.class);
//		            PackageManager pm = context.getPackageManager();
//
//		            pm.setComponentEnabledSetting(receiver,
//		                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//		                    PackageManager.DONT_KILL_APP);   
//		        	
//		            intentArray.add(alarmIntent);
//		            
//		        }
//            }
//        });
//        thread.start();
        
        for(int i=0; i<arrayDate.size(); i++){
        	
        	Intent intent = new Intent(context, AlarmReceiver.class);
        	intent.putExtra("MEX", typeRace.get(i));
        	intent.putExtra("N_RACE", nRace.get(i));
        	intent.putExtra("N_DAY", nDay.get(i));
        	intent.putExtra("INDEX", minBefore );
        	intent.putExtra("VIBRATION", notificationActivation[5]);
        	intent.putExtra("SOUND", notificationActivation[6]);
        	alarmIntent = PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        	alarmManager[i] = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        	if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
        		alarmManager[i].setExact(AlarmManager.RTC_WAKEUP, arrayDate.get(i).getTimeInMillis(), alarmIntent);
    		} else {
    			alarmManager[i].set(AlarmManager.RTC_WAKEUP, arrayDate.get(i).getTimeInMillis(), alarmIntent);
    		}
            
            // Enable {@code SampleBootReceiver} to automatically restart the alarm when the
            // device is rebooted.
            ComponentName receiver = new ComponentName(context, BootReceiver.class);
            PackageManager pm = context.getPackageManager();

            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);   
        	
            intentArray.add(alarmIntent);
            
        }
        
        /*Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // Set the alarm's trigger time to 8:30 a.m.
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 35);*/
  
        /* 
         * If you don't have precise time requirements, use an inexact repeating alarm
         * the minimize the drain on the device battery.
         * 
         * The call below specifies the alarm type, the trigger time, the interval at
         * which the alarm is fired, and the alarm's associated PendingIntent.
         * It uses the alarm type RTC_WAKEUP ("Real Time Clock" wake up), which wakes up 
         * the device and triggers the alarm according to the time of the device's clock. 
         * 
         * Alternatively, you can use the alarm type ELAPSED_REALTIME_WAKEUP to trigger 
         * an alarm based on how much time has elapsed since the device was booted. This 
         * is the preferred choice if your alarm is based on elapsed time--for example, if 
         * you simply want your alarm to fire every 60 minutes. You only need to use 
         * RTC_WAKEUP if you want your alarm to fire at a particular date/time. Remember 
         * that clock-based time may not translate well to other locales, and that your 
         * app's behavior could be affected by the user changing the device's time setting.
         * 
         * Here are some examples of ELAPSED_REALTIME_WAKEUP:
         * 
         * // Wake up the device to fire a one-time alarm in one minute.
         * alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 
         *         SystemClock.elapsedRealtime() +
         *         60*1000, alarmIntent);
         *        
         * // Wake up the device to fire the alarm in 30 minutes, and every 30 minutes
         * // after that.
         * alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 
         *         AlarmManager.INTERVAL_HALF_HOUR, 
         *         AlarmManager.INTERVAL_HALF_HOUR, alarmIntent);
         */
        
        // Set the alarm to fire at approximately 8:30 a.m., according to the device's
        // clock, and to repeat once a day.
        /*alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,  
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
        */
        
        /*
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
        	alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
		} else {
			alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
		}
        
        // Enable {@code SampleBootReceiver} to automatically restart the alarm when the
        // device is rebooted.
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
                */           
    }
    // END_INCLUDE(set_alarm)

    /**
     * Cancels the alarm.
     * @param context
     */
    // BEGIN_INCLUDE(cancel_alarm)
    public void cancelAlarm(Context context) {
    	
    	Log.d("AlarmReceiver", "############## cancelAlarm!!!");
        cancelAlarms();
        
        // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the 
        // alarm when the device is rebooted.
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
    // END_INCLUDE(cancel_alarm)
    private void cancelAlarms(){
        if(intentArray!= null && intentArray.size()>0){
            for(int i=0; i<intentArray.size(); i++){
            	alarmManager[i].cancel(intentArray.get(i));
            }
            intentArray.clear();
        }
    }
}