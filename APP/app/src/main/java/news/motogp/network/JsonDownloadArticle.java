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
import android.util.Log;

public class JsonDownloadArticle {
	
	private String[] city;
	private String[] data;
	private String[] image;
	private String[] title;
	private String[] text;
	private int nRaces;
	private JSONObject race;
	
	public ArrayList<String[]> getArticle(Context context){
		final ArrayList<String[]> dataArticle=new ArrayList<String[]>();
		String result = null ;
		try {
		    HttpClient client = new DefaultHttpClient();  
		    String getURL = "http://motogp2014.altervista.org/"+ context.getResources().getString(R.string.json_article);
		    HttpGet get = new HttpGet(getURL);
		    HttpResponse responseGet = client.execute(get);  
		    HttpEntity resEntityGet = responseGet.getEntity();  
		    result= EntityUtils.toString(resEntityGet);
			JSONObject json = null;
			json = new JSONObject(result);
					
			JSONArray cityJson = json.getJSONArray("city");
			JSONArray dataJson = json.getJSONArray("data");
			JSONArray imageJson = json.getJSONArray("image");
			JSONArray titleJson = json.getJSONArray("title");
			JSONArray textJson = json.getJSONArray("text");
					
			city=new String[cityJson.length()];
			data=new String[cityJson.length()];
			image=new String[cityJson.length()];
			title=new String[cityJson.length()];
			text=new String[cityJson.length()];
					
			for (int x = 0; x < cityJson.length(); x++) {
					city[x]= cityJson.getString(x);
					data[x] = dataJson.getString(x);
					image[x] = imageJson.getString(x);
//					title[x]=titleJson.getString(x);
					text[x]=textJson.getString(x);
			}
			
//			city = JsonDownload.city;
//			data = JsonDownload.data;
//			image= JsonDownload.image;
//			texts= JsonDownload.text;
			
			dataArticle.add(city);
			dataArticle.add(data);
			dataArticle.add(image);
			dataArticle.add(title);
			dataArticle.add(text);
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
				
		return dataArticle;
		
	}
}
