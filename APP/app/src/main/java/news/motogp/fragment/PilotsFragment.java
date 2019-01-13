package news.motogp.fragment;

import news.motogp.activity.PilotsActivity;
import news.motogp.R;
import news.motogp.utils.ArticleArrayAdapter;
import news.motogp.utils.PilotsFragmentArrayAdapter;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;

public class PilotsFragment extends Fragment {
    public static int nRace;

    LayoutInflater inflater;
    protected FrameLayout webViewPlaceholder;
    protected WebView webView;
    protected Boolean isResult = false;
    View layout;
    protected View toRound;
    protected int nInvisible;
    protected boolean jsonIsDownload;
    int positionClick;
    TableLayout country_table;

    private GridView gridview;
    private static final String[] images = {
            "lorenzo;flag3",
            "rossi;flag5",
            "marquez;flag3",
            "pedrosa;flag3",
            "iannone;flag5",
            "dovizioso;flag5",
            "espargaro_aleix;flag3",
            "vinales;flag3",
            "smith;flag11",
            "espargaro_pol;flag3",
            "crutchlow;flag11",
            "petrucci;flag5",
            "redding;flag11",
            "barbera;flag3",
            "baz;flag4",
            "miller;flag15",
            "rabat;falag3", // RABAT
            "bautista;flag3",
            "bradl;flag8",
            "laverty;b21",
            "hernandez;b19",
    };


    public static String getImages(int n) {
        return images[n];
    }

//	public static OtherPilots newInstance(int index) {
//		OtherPilots f = new OtherPilots();
//
//		Bundle args = new Bundle();
//		args.putInt("index", index);
//		f.setArguments(args);
//
//		return f;
//	}

    //	@Override
//    public void onResume() {
//
//        super.onResume();
//
//        this.tracker.set(Fields.SCREEN_NAME, getClass().getSimpleName());
//        this.tracker.send( MapBuilder.createAppView().build() );
//    }
//	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        System.gc();

        layout = inflater.inflate(R.layout.layout_article, container, false);
        layout.setDrawingCacheEnabled(false);
        gridview = (GridView) layout.findViewById(R.id.gridview);
        PilotsFragmentArrayAdapter adapter = new PilotsFragmentArrayAdapter(getActivity(), images);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(getActivity(), PilotsActivity.class);
                String[] split = images[position].split(";");
                i.putExtra("NUMBER_OF_PILOT", position);
                i.putExtra("IMAGE_OF_PILOT", split[0]);
                startActivity(i);
            }
        });

        return layout;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        layout.clearFocus();
        layout.invalidate();
        layout.destroyDrawingCache();

    }
//	private void recycleImagesFromView(View view) {
//        if(view instanceof ImageView)
//        {
//            Drawable drawable = ((ImageView)view).getDrawable();
//            if(drawable instanceof BitmapDrawable)
//            {
//                BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
//                bitmapDrawable.getBitmap().recycle();
//            }
//        }
//
//        if (view instanceof ViewGroup) {
//            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
//                recycleImagesFromView(((ViewGroup) view).getChildAt(i));
//            }
//        }
//    }

}
