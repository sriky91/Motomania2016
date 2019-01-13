package news.motogp.network;

import news.motogp.R;
import news.motogp.utils.RaceResultData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.content.Context;
import android.util.Log;

public class JsonDownloadResults {

	public static RaceResultData motoGP;
	public static RaceResultData moto2;
	public static RaceResultData moto3;
	
	public static int nRaces;
	public static JSONObject race;
	public static Boolean inDownoload = false;
	private static boolean isDownloaded=false;
	private static boolean isStartDownload=false;
	
	private static final int SIZE = 45;
	
	public static void getJson(Context context){
		inDownoload=true;
			AsyncHttpClient client = new AsyncHttpClient();
			client.get( context.getString(R.string.url_results), new AsyncHttpResponseHandler(){
				
				@Override
				public void onSuccess(String result) {
					super.onSuccess(result);
					isStartDownload=true;
					JSONObject json = null;
					
					try{
						json = new JSONObject(result);  
						
						motoGP = extractRaceData(json, "motogp");
						moto2 = extractRaceData(json, "moto2");
						moto3 = extractRaceData(json, "moto3");
						
						inDownoload = false;
						isDownloaded=true;
							
					}catch (Exception e) {
						e.printStackTrace();
					}
					
				}

				public RaceResultData extractRaceData(JSONObject json, String category)
						throws JSONException {
					JSONArray races=json.getJSONArray(category);
					
					RaceResultData motoCategory = new RaceResultData();
					
					
					Log.d("JsonDownloadResults", "races: "+races.toString());
					
//					int size=0;
//					for(int i=0; i<races.length(); i++){
//						if(size)
//						race= races.getJSONObject(0);
//						race.getJSONArray("")
//					}
					
					
//					if(!race.has("Pos.")){
//						return null;
//					}
					
//					JSONArray positionsJson = race.getJSONArray("Pos.");
//					JSONArray namesJson = race.getJSONArray("Rider");
//					JSONArray pointsJson = race.getJSONArray("Points");
//					JSONArray marcheJson = race.getJSONArray("Bike");
					
					JSONArray positionsJson = null ;
					JSONArray namesJson = null ;
					JSONArray pointsJson = null ;
					JSONArray marcheJson = null ;
					JSONArray timeJson = null ;//= race.getJSONArray("Time/Gap");
					
					
					//positions = new String[positionsJson.length()][0];
					if(nRaces < races.length())
						nRaces=races.length();
					motoCategory.positions=new String[SIZE][races.length()];
					motoCategory.names=new String[SIZE][races.length()];
					motoCategory.points=new String[SIZE][races.length()];
					motoCategory.marche=new String[SIZE][races.length()];
					motoCategory.time=new String[SIZE][races.length()];
					for (int y = 0; y < races.length(); y++) {
					
						race= races.getJSONObject(y);
						
						positionsJson = race.getJSONArray("Pos.");
						namesJson = race.getJSONArray("Rider");
						pointsJson = race.getJSONArray("Points");
						marcheJson = race.getJSONArray("Bike");
						if(y>0)
							timeJson = race.getJSONArray("Time/Gap");
						
						for (int x = 0; x < positionsJson.length(); x++) {
							
//							race= races.getJSONObject(0);
							
//							String nameDecode = namesJson.getString(x);
//							byte[] latin1;
//							try {
//								latin1 = nameDecode.getBytes("ISO-8859-1");
//								nameDecode = new String(latin1);
//							} catch (UnsupportedEncodingException e) {
//								e.printStackTrace();
//							}
							
							motoCategory.positions[x][y] = positionsJson.getString(x);
							motoCategory.names[x][y] = namesJson.getString(x);
							motoCategory.points[x][y] = pointsJson.getString(x);
							motoCategory.marche[x][y] = marcheJson.getString(x);
							if(y>0)
								motoCategory.time[x][y] = timeJson.getString(x);
						}
					}
					Log.d("JsonDownloadResults", "motoGP0: "+motoCategory.positions.length);
					return motoCategory;
				}
				
				@Override
				public void onFailure(Throwable arg0, String arg1) {
					super.onFailure(arg0, arg1);
				}
				
			});
				
	}


	public static boolean getisDownloaded(Context context){
		if(!isStartDownload){
			getJson(context);
		}
		return isDownloaded;
	}
	public static int getNraces(){
		return nRaces;
	}
	
	public static Boolean getInDownoload() {
		return inDownoload;
	}
	

}
