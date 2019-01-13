package news.motogp.utils;

import news.motogp.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class OrariArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] when;
    private String[] text;


    public OrariArrayAdapter(Context context, String[] text, String[] when) {
        super(context, R.layout.norml_article, text);
        this.context = context;
        this.when = when;
        this.text = text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fragment_adapter_orari, parent, false);
        TextView tvMounth = (TextView) rowView.findViewById(R.id.tv_mounth);
        TextView tvDay = (TextView) rowView.findViewById(R.id.tv_day);
        TextView tvCity = (TextView) rowView.findViewById(R.id.tv_city);
        String[] data = when[position].split(" ");
        tvDay.setText(data[0]);
        tvMounth.setText(data[1]);
        tvCity.setText(text[position]);
        return rowView;
    }
}
