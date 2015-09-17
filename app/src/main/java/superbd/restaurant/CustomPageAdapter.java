package superbd.restaurant;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by lenovo on 8/18/2015.
 */
public class CustomPageAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    int[] mResources = {R.drawable.background_viewpager1, R.drawable.background_viewpager, R.drawable.background_viewpager, R.drawable.background_viewpager};

    public CustomPageAdapter(Context context)
    {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position)
    {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        WebView webView = (WebView) itemView.findViewById(R.id.gifView);
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.loadUrl("file:///android_asset/gif2splash.gif");
        //final ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView1);
        //imageView.setImageResource(mResources[position]);

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((LinearLayout)object);
    }
}
