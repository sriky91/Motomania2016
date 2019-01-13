package news.motogp.utils;

import news.motogp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CircuitFragmentArrayAdapter extends ArrayAdapter<String> {

    private final String IMAGES_FLAG = "flag";
    private final String IMAGES_RIDE = "ride";

    private boolean[] boolFlagRaceHeld;
    private String[] textData;
    private String[] textCircuit;
    private String[] textCity;
    private Context context;

    public CircuitFragmentArrayAdapter(
            Context context,
            boolean[] imagesFlagRaceHeld,
            String[] textData,
            String[] textCircuit,
            String[] textCity) {
        super(context, R.layout.norml_article, textData);
        this.context = context;
        this.boolFlagRaceHeld = imagesFlagRaceHeld;
        this.textData = textData;
        this.textCircuit = textCircuit;
        this.textCity = textCity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.norml_circuit, parent, false);
        ImageView ivFlag = (ImageView) rowView.findViewById(R.id.ivFlag);
        ImageView ivCircuit = (ImageView) rowView.findViewById(R.id.ivCircuit);
        ImageView ivFlagRaceHeld = (ImageView) rowView.findViewById(R.id.ivFlagRaceHeld);

        TextView tvDate = (TextView) rowView.findViewById(R.id.tvDate);
        TextView tvCircuit = (TextView) rowView.findViewById(R.id.tvCircuit);
        TextView tvCity = (TextView) rowView.findViewById(R.id.tvCity);

        if (position == 0) {
            ivCircuit.setImageResource(R.drawable.mondiale);

            ivFlag.setVisibility(View.GONE);
            ivFlagRaceHeld.setVisibility(View.GONE);
            tvDate.setVisibility(View.GONE);
            tvCircuit.setVisibility(View.GONE);
            tvCity.setVisibility(View.GONE);

        } else {
            int flagResource = context.getResources().getIdentifier(IMAGES_FLAG + (position - 1), "drawable", context.getPackageName());
            int circuitResource = context.getResources().getIdentifier(IMAGES_RIDE + (position - 1), "drawable", context.getPackageName());
            ivFlag.setImageResource(flagResource);
            ivCircuit.setImageResource(circuitResource);
            if (!boolFlagRaceHeld[position]) {
                ivFlagRaceHeld.setVisibility(View.GONE);
            }
//		    if(position == 17){
//		    	LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//		    	lp.setMargins(0, top, 0, 0);
//		    	imageView.setLayoutParams(lp);
//		    }
            tvCircuit.setText(textCircuit[position - 1]);
            tvCity.setText(textCity[position - 1]);
            tvDate.setText(textData[position]);
        }
        return rowView;
    }
}
