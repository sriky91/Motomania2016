package news.motogp.utils;

import news.motogp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LastHourArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] when;
    private String[] text;


    public LastHourArrayAdapter(Context context, String[] text, String[] when) {
        super(context, R.layout.norml_article, text);
        this.context = context;
        this.when = when;
        this.text = text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.norml_lasthour, parent, false);
        TextView tvWhere = (TextView) rowView.findViewById(R.id.tvWhere);
        TextView tvWhen = (TextView) rowView.findViewById(R.id.tvWhen);
        tvWhere.setText(text[position]);
        tvWhen.setText(when[position]);
        return rowView;
    }
}
