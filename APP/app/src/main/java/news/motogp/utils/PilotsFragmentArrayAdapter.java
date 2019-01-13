package news.motogp.utils;

import news.motogp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class PilotsFragmentArrayAdapter extends ArrayAdapter<String> {

    private String[] urls;
    private Context context;

    public PilotsFragmentArrayAdapter(Context context, String[] urls) {
        super(context, R.layout.norml_article, urls);
        this.context = context;
        this.urls = urls;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.norml_pilots_fragment, parent, false);
        ImageView ivPilot = (ImageView) rowView.findViewById(R.id.imageViewPilot);
        ImageView ifFlag = (ImageView) rowView.findViewById(R.id.imageViewFlag);

        String[] urlsSplit = urls[position].split(";");
        int pilotResource = context.getResources().getIdentifier(urlsSplit[0], "drawable", context.getPackageName());
        int flagResource = context.getResources().getIdentifier(urlsSplit[1], "drawable", context.getPackageName());
        ivPilot.setImageResource(pilotResource);
        ifFlag.setImageResource(flagResource);

        return rowView;
    }
}
