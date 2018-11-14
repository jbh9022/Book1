package com.spacemonster.book.book.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spacemonster.book.book.Dialog.CustomDialog_Notice;
import com.spacemonster.book.book.R;

public class ViewPager_TextAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private String[] textSet = {"  (공지) Test 공지 입니다. Test 공지 입니다."
                            ,"  (공지) Test 공지 2 입니다. Test 공지 2 입니다."};
    private int pos = 0;
    private int phoneWidth;
    private int phoneHeigth;
    public ViewPager_TextAdapter(Context context, int phoneWidth, int phoneHeigth) {
        this.context = context;
        this.phoneWidth = phoneWidth;
        this.phoneHeigth = phoneHeigth;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = layoutInflater.inflate(R.layout.custom_textlayout, null);

        TextView text = new TextView(context);
        ((ViewPager)container).addView(text);
        text.setText(textSet[pos]);
        text.setTextColor(Color.WHITE);
        text.setGravity(Gravity.CENTER_VERTICAL);
        if(pos>= textSet.length - 1)
            pos = 0;
        else
            ++pos;
        Log.i("posittion", pos+"");
        //웹뷰랑 연결
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "toast", Toast.LENGTH_SHORT).show();
                CustomDialog_Notice notice = new CustomDialog_Notice(context, phoneWidth, phoneHeigth);
                notice.CallDialog_Notice();
            }
        });
        return text;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
