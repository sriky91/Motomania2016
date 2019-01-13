package news.motogp.network;

import java.util.ArrayList;

import news.motogp.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

public class JsonDownloadLastHour {

//	static JSONObject race;
	
	
	private String[] data;
	private String[] title;
	private String[] text;
	
public ArrayList<String[]> getLastHour(Context context){
	final ArrayList<String[]> dataArticle=new ArrayList<String[]>();
	String result = null ;
	try {
	    HttpClient client = new DefaultHttpClient();  
	    String getURL = "http://motogp2014.altervista.org/"+context.getResources().getString(R.string.json_lastHour);
	    HttpGet get = new HttpGet(getURL);
	    HttpResponse responseGet = client.execute(get);  
	    HttpEntity resEntityGet = responseGet.getEntity();  
	    	result= EntityUtils.toString(resEntityGet);
			JSONObject json = null;
			
				json = new JSONObject(result);
				
				
				JSONArray dataJson = json.getJSONArray("data");
				JSONArray titleJson = json.getJSONArray("title");
				JSONArray textJson = json.getJSONArray("text");
				
				data=new String[titleJson.length()];
				title=new String[titleJson.length()];
				text=new String[titleJson.length()];
					
				for (int x = 0; x < titleJson.length(); x++) {
						data[x] = dataJson.getString(x);
						title[x]=titleJson.getString(x);
						text[x]=textJson.getString(x);
				}
				
				dataArticle.add(data);
				dataArticle.add(title);
				dataArticle.add(text);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
	return dataArticle;
	
	}

	
}
