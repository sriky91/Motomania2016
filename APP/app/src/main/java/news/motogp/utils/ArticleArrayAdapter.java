package news.motogp.utils;

import news.motogp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticleArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] where;
    private final String[] when;
    private final String[] images;

    private DrawableManager dm;

    public ArticleArrayAdapter(Context context, String[] where, String[] when, String[] images) {
        super(context, R.layout.norml_article, where);
        this.context = context;
        this.where = where;
        this.when = when;
        this.images = images;
        dm = DrawableManager.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.norml_article, parent, false);
        TextView tvWhere = (TextView) rowView.findViewById(R.id.tvWhere);
        TextView tvWhen = (TextView) rowView.findViewById(R.id.tvWhen);

        ImageView ivRace = (ImageView) rowView.findViewById(R.id.ivRace);
        tvWhere.setText(where[position]);
        tvWhen.setText(when[position]);
        dm.fetchDrawableOnThread("http://motogp2014.altervista.org/images/" + images[position], ivRace);
        return rowView;
    }
} 