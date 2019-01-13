package news.motogp.fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.crypto.spec.OAEPParameterSpec;

import news.motogp.activity.MainActivity;
import news.motogp.activity.OrariActivity;
import news.motogp.R;
import news.motogp.network.ThreadHandler;
import news.motogp.utils.LastHourArrayAdapter;
import news.motogp.utils.OrariArrayAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

public class OrariFragment extends Fragment{

	private View layout;
	private ListView listView; 

	private String[] data ;
	private String[] city ;
	
	private int[][] timeStamp;
	
	private int openRace = -1;
	private int nDay;
    private int nInvisible;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		layout = inflater.inflate(R.layout.fragment_orari, container, false);
		listView= (ListView) layout.findViewById(R.id.listViewOrari);
		
		city = getResources().getStringArray(R.array.cities);
		data = getResources().getStringArray(R.array.circuit_date);


		final OrariArrayAdapter adapter = new OrariArrayAdapter(getActivity(), city, data);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				showTime(position);
				Intent i = new Intent(getActivity(), OrariActivity.class);
				i.putExtra("NUMBER_OF_EVENT", position);
				startActivity(i);
			}
		});
		
		if(openRace != -1){
			Log.d("Orarifragment", "###NUMBER_OF_EVENT2: "+ openRace +" NUMBER_OF_DAY: "+nDay);
			Intent i = new Intent(getActivity(), OrariActivity.class);
			i.putExtra("NUMBER_OF_EVENT", openRace);
			i.putExtra("NUMBER_OF_DAY", nDay);
			openRace = -1;
			nDay = -1;
			startActivity(i);
		}

        listView.setSelection(4);


		return layout;
		
	}
	
	public void setOpenRace(int nRace, int nDay){
		Log.d("Orarifragment", "###NUMBER_OF_EVENT1: "+ openRace +" NUMBER_OF_DAY: "+nDay);
		openRace = nRace;
		this.nDay = nDay;
	}

    public void isDidRace() {

        Calendar cal = Calendar.getInstance();
        int month=cal.get(Calendar.MONTH)+1;
        double day=cal.get(Calendar.DAY_OF_MONTH);


        double dataRaces[]={3.20,4.03,4.10,4.24,5.08,5.22,6.05,6.26,7.17,8.14,8.21,9.04,9.11,9.25,10.16,10.23,10.30,11.13};
        double data=month+(day/100);
        nInvisible=0;
        for(int i=0;i<18;i++){
            if(data<dataRaces[i]){
                Log.d("dataRaces", "dataRaces:"+dataRaces[i]+" data:"+data);
                ++nInvisible;
            }
        }
    }
	
//	private void showTime(int position){
//		
//		
//		Date localTime = new Date() ;
//		
//		
//		DateFormat converter = new SimpleDateFormat("HH:mm");
//	    
//	     converter.setTimeZone(TimeZone.getDefault());
//	     
//	     int year = 2015;
//	     int mounth = 2;
//	     int day = 26;
//	     int hour = 15;
//	     int minute = 0;
//	     
//	     GregorianCalendar calendar = new GregorianCalendar(year, mounth, day, hour, minute);
//	     calendar.setTimeZone(TimeZone.getTimeZone("GMT+0"));
//	    
//	}
	
}
