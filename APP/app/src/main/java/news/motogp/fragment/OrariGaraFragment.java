package news.motogp.fragment;

import news.motogp.R;
import news.motogp.activity.MainActivity;
import news.motogp.utils.EventiOrariArrayAdapter;
import news.motogp.utils.OrariArrayAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


public class OrariGaraFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    
    private String[] categoria;
	private String[] tipologia;
	private String[] orario;

    // newInstance constructor for creating fragment with arguments
    public static OrariGaraFragment newInstance(int page, String title, String[] categoria, String[] tipologia, String[] orario) {
    	OrariGaraFragment fragmentFirst = new OrariGaraFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        args.putStringArray("someCategoria", categoria);
        args.putStringArray("someTipologia", tipologia);
        args.putStringArray("someOrario", orario);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        categoria = getArguments().getStringArray("someCategoria");
        tipologia = getArguments().getStringArray("someTipologia");
        orario = getArguments().getStringArray("someOrario");
    }
    
    String[] test = {
    		"",
    		"",
    		"",
    		"",
    		"",
    		"",
    };
    

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_orari, container, false);
        ListView lvEventi = (ListView) view.findViewById(R.id.lv_eventi);
        
        final EventiOrariArrayAdapter adapter = new EventiOrariArrayAdapter(getActivity(), categoria, tipologia, orario);
        lvEventi.setAdapter(adapter);
        
        
        
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
//        tvLabel.setText(page + " -- " + title);
//        ViewPager vpPager = (ViewPager) view.findViewById(R.id.vpPager);
//        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
//        vpPager.setAdapter(adapterViewPager);
        
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
//        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
//        vpPager.setAdapter(adapterViewPager);
        
        return view;
    }
}

