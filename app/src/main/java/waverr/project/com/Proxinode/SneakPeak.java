package waverr.project.com.Proxinode;

/**
 * Created by Nikhil on 8/14/2015.
 */
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.app.Activity;
import android.os.Bundle;

public class SneakPeak extends Activity {

    ViewPager viewPager;
    MyPagerAdapter myPagerAdapter;
    RelativeLayout topLevelLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.sneakpeak);

        viewPager = (ViewPager)findViewById(R.id.myviewpager);
        myPagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(myPagerAdapter);
        topLevelLayout = (RelativeLayout)findViewById(R.id.top_layout);
        if (isFirstTime()) {
            topLevelLayout.setVisibility(View.INVISIBLE);
        }

    }

    private class MyPagerAdapter extends PagerAdapter{

        int NumberOfPages = 4;

        int[] res = {
                R.drawable.ba,
                R.drawable.bm,
                R.drawable.bb,
                R.drawable.bh};
        int[] backgroundcolor = {
                0xFF101010,
                0xFF202020,
                0xFF303030,
                0xFF404040};

        @Override
        public int getCount() {
            return NumberOfPages;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {




            ImageView imageView = new ImageView(SneakPeak.this);
            imageView.setImageResource(res[position]);
            LayoutParams imageParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(imageParams);

            LinearLayout layout = new LinearLayout(SneakPeak.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            LayoutParams layoutParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
            layout.setBackgroundColor(backgroundcolor[position]);
            layout.setLayoutParams(layoutParams);

            layout.addView(imageView);

            final int page = position;

            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout)object);
        }

    }

    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", false);
            editor.commit();
            topLevelLayout.setVisibility(View.VISIBLE);
            topLevelLayout.setOnTouchListener(new View.OnTouchListener(){

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    topLevelLayout.setVisibility(View.INVISIBLE);
                    return false;
                }

            });


        }
        return ranBefore;

    }

}