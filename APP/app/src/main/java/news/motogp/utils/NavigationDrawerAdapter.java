package news.motogp.utils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import news.motogp.R;

/**
 * Created by sriky on 17/03/16.
 */
public class NavigationDrawerAdapter extends ArrayAdapter<String> {

    private String[] arrayMenuVoice;
    private Context context;

    public NavigationDrawerAdapter(Context context, String[] arrayMenuVoice) {
        super(context, android.R.layout.simple_list_item_1, arrayMenuVoice);
        this.context = context;
        this.arrayMenuVoice = arrayMenuVoice;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v  = inflater.inflate(R.layout.norml_navigation_drawer, parent, false);
        TextView tt = (TextView) v.findViewById(R.id.tvTitle);
        tt.setText(arrayMenuVoice[position]);
        return v;
    }

}
