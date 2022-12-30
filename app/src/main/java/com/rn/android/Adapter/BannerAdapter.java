package com.rn.android.Adapter;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rn.android.R;
import com.rn.android.model.HolidayMaster;


import java.util.List;

/**
 * Created by abc on 9/22/2017.
 */

public class BannerAdapter extends PagerAdapter {
    private List<HolidayMaster> images;
    private LayoutInflater inflater;
    Context context;

    public BannerAdapter(Context context, List<HolidayMaster> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.banner, view, false);

        assert imageLayout != null;
        TextView textView = (TextView) imageLayout
                .findViewById(R.id.banner_text);
        textView.setText("fkgkdgjdjdgdgjgg");
        view.addView(imageLayout, 0);


        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}

