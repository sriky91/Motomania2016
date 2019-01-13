package news.motogp.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * This BroadcastReceiver automatically (re)starts the alarm when the device is
 * rebooted. This receiver is set to be disabled (android:enabled="false") in the
 * application's manifest file. When the user sets the alarm, the receiver is enabled.
 * When the user cancels the alarm, the receiver is disabled, so that rebooting the
 * device will not trigger this receiver.
 */
// BEGIN_INCLUDE(autostart)
public class BootReceiver extends BroadcastReceiver {
    AlarmReceiver alarm = new AlarmReceiver();
    private final String NOTIFICATON_KEY = "notificationPref";
    private final String NOTIFICATON_KEY_MIN_BEFORE = "notificationPrefMinBefore";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
        	SharedPreferences sharedPref = context.getSharedPreferences("myPrefs", 
                    Context.MODE_PRIVATE);
        	boolean[] notificationActivation = new boolean[7];
        	
        	for(int nNotify=0; nNotify<notificationActivation.length; nNotify++){
        		if(nNotify!=6){
    	    		notificationActivation[nNotify] = sharedPref.getBoolean(NOTIFICATON_KEY+nNotify, true);
    	    	}
    	    	else{
    	    		notificationActivation[nNotify] = sharedPref.getBoolean(NOTIFICATON_KEY+nNotify, true);
    	    	}
    	    }
        	int minBefore = sharedPref.getInt(NOTIFICATON_KEY_MIN_BEFORE, 1);
        	
            alarm.setAlarm(context, notificationActivation, minBefore);
        }
    }
}


