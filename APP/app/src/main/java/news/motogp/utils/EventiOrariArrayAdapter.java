package news.motogp.utils;

import news.motogp.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventiOrariArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private String[] categoria;
    private String[] tipologia;
    private String[] orario;


    public EventiOrariArrayAdapter(Context context, String[] categoria, String[] tipologia, String[] orario) {
        super(context, R.layout.norml_orari, categoria);
        this.context = context;
        this.categoria = categoria;
        this.tipologia = tipologia;
        this.orario = orario;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.norml_orari, parent, false);
        TextView tvCategoria = (TextView) rowView.findViewById(R.id.tv_categoria);
        TextView tvTipologia = (TextView) rowView.findViewById(R.id.tv_tipologia);
        TextView tvOrario = (TextView) rowView.findViewById(R.id.tv_orario);

        tvCategoria.setText(categoria[position]);
        tvTipologia.setText(tipologia[position]);
        tvOrario.setText(orario[position]);
        return rowView;
    }
}