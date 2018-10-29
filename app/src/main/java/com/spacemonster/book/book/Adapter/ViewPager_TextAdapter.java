package com.spacemonster.book.book.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spacemonster.book.book.R;

public class ViewPager_TextAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private String[] textSet = {"(공지) Test 공지 입니다. Test 공지 입니다."
    ,"(공지) Test 공지 2 입니다. Test 공지 2 입니다."};

    public ViewPager_TextAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return textSet.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_main_textslider, null);
        TextView textView = (TextView)view.findViewById(R.id.main_customText);
        textView.setText(textSet[position]);
//        textView.setSelected(true);
        ViewPager vp = (ViewPager)container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
