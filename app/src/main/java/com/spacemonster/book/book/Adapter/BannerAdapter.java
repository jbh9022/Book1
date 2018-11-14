package com.spacemonster.book.book.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.spacemonster.book.book.R;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private int pos;
    private int[] img ={R.drawable.mark1, R.drawable.banner01, R.drawable.banner02};
    ArrayList<Object> arrayList = new ArrayList<>();
    public BannerAdapter(Context context) {
        this.context = context;
        for(pos =0; pos<img.length; pos++){
            arrayList.add(img[pos]);
        }
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_banner, null);
        ImageView banner = (ImageView) view.findViewById(R.id.banner_Img);


        if(position == 0){
            banner.setImageResource((Integer) arrayList.get(position));
            banner.setScaleType(ImageView.ScaleType.CENTER);
        }
        else {
            banner.setImageResource((Integer) arrayList.get(position));
            banner.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);

        //배너 클릭시-웹뷰랑 연결 예정
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "배너 페이지", Toast.LENGTH_SHORT).show();
            }
        });
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
