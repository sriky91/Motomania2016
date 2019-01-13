package news.motogp.network;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Map.Entry;

import android.os.Handler;

public class ThreadHandler {
	
	private static final String TAG = "ThreadHandler";
	
	private static final String GROUP_DEFAULT = "ThreadHandler";
//	private static final String GROUP_PHOTOS = "Photos";
	private static Handler mainHandler;
	private static boolean isQueueRunning = false;
	private static Queue<Runnable> queue = new LinkedList<Runnable>();
	
	public static void initializeMainHandler(){
		mainHandler = new Handler();
	}
	
	public static void runOnMainThread(Runnable r){
		mainHandler.post(r);
	}
	
	public static void addRunnable(Runnable r){
		queue.add(r);
		if (!isQueueRunning){
			runQueue();
		}
	}
	
	private static void runQueue(){
		isQueueRunning = true;
		
		start(new Runnable() {
			
			@Override
			public void run() {
				Iterator it = queue.iterator();
				while ( it.hasNext()){
					it.next();
					queue.poll().run();
				}
				isQueueRunning = false;
			}
		});
	}
	
//	private static Hashtable<String, Vector<AsyncTaskEx>> taskGroups = new Hashtable<String, Vector<AsyncTaskEx>>();
	private static Hashtable<String, ThreadGroup> groups = new Hashtable<String, ThreadGroup>();
	private static UncaughtExceptionHandler exHandler = new UncaughtExceptionHandler() {
		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
//			Log.w(TAG, "Uncaught exception: "+ex.getMessage());
			ex.printStackTrace();
		}
	};	
	
//	public static void addTask(AsyncTaskEx r) {
//		addTask(GROUP_DEFAULT, r);
//	}
	
	/*public static void addTask(String group, AsyncTaskEx r) {
		if (!taskGroups.containsKey(group)) {
			taskGroups.put(group, new Vector<AsyncTaskEx>());
		}
		taskGroups.get(group).add(r);
	}*/
	
	public static void start(Runnable r) {
		start(GROUP_DEFAULT, r);
	}
	
	public static void start(String group, Runnable r) {
		if (!groups.containsKey(group)) {
			groups.put(group, new ThreadGroup(group));
		}
		Thread t = new Thread(groups.get(group), r);
		t.setUncaughtExceptionHandler(exHandler);
		t.start();
	}
	
	public static void stopAll() {
		stopAll(null);
	}
	
	public static void stopAll(String group) {
		if (group == null) {
			for (Entry<String, ThreadGroup> t : groups.entrySet()) {
				t.getValue().interrupt();
			}
//			for (Entry<String, Vector<AsyncTaskEx>> t : taskGroups.entrySet()) {
//				stopTasks(t.getValue());
//			}
		} else {
			if (groups.containsKey(group)) {
				groups.get(group).interrupt();
			}
//			if (taskGroups.containsKey(group)) {
//				stopTasks(taskGroups.get(group));
//			}
		}
	}
	
//	private static void stopTasks(Vector<AsyncTaskEx> tasks) {
//		for (int i = 0; i < tasks.size(); i++) {
//			tasks.get(i).cancel(true);
//		}
//		tasks.clear();
//	}

}
