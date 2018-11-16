package com.spacemonster.book.book;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.spacemonster.book.book.Adapter.BannerAdapter;
import com.spacemonster.book.book.Adapter.ViewPager_TextAdapter;
import com.spacemonster.book.book.Dialog.CustomDialog_End;
import com.spacemonster.book.book.Dialog.CustomDialog_Logout;
import com.spacemonster.book.book.Dialog.CustomDialog_info;
import com.spacemonster.book.book.databinding.ActivityMainBinding;


import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {


    public static Activity Main_Activity;
    ActivityMainBinding mainLayout;
    String id;
    private Handler handler = new Handler();
    int phoneWidth;
    int phoneHeigth;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //페이지 바인딩
        mainLayout = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Main_Activity = MainActivity.this;

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");

        //디바이스 사이즈 측정
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        phoneWidth = size.x;
        phoneHeigth=size.y;
        //배너
        BannerAdapter bannerAdapter = new BannerAdapter(MainActivity.this);

        mainLayout.mainBanner.setAdapter(bannerAdapter);
        timer = new Timer();
        timer.scheduleAtFixedRate(new ImgTimer(), 10000, 10000);
        //배너위 dot
        IndicatorChange();

        //공지 뷰
        ViewPager_TextAdapter viewPagerAdapter = new ViewPager_TextAdapter(this, phoneWidth, phoneHeigth);
        mainLayout.mainTextslider.setAdapter(viewPagerAdapter);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("pager", String.valueOf(mainLayout.mainTextslider.getCurrentItem() + 1));
                mainLayout.mainTextslider.setCurrentItem(mainLayout.mainTextslider.getCurrentItem() + 1,true);
                handler.postDelayed(this, 7000);
            }
        },7000);

    //로그인!!!!
        if(id == null || id.equals("")){
            //로그인전
            mainLayout.mainUserTxt.setText("");
            mainLayout.mainLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent inTent_login = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(inTent_login);
                }
            });
            //회원정보
            mainLayout.mainImg2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "로그인후 이용해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                }
            });
            mainLayout.mainImg4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "로그인후 이용해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            //로그인 이후
            mainLayout.mainUserTxt.setText(id + " 님");
//            userText2.setText(" 환영합니다.");
            mainLayout.mainLoginTxt.setText("로그아웃");
            mainLayout.mainLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    로그아웃 다이얼로그 넣기
                  CustomDialog_Logout customDialog = new CustomDialog_Logout(MainActivity.this);
                  customDialog.callDialog();
                }
            });
            //회원정보
            mainLayout.mainImg2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String condate = sdf.format(new Date());

                    Intent intent_User = new Intent(MainActivity.this, UserInfoActivity.class);
                    intent_User.putExtra("ID", id);
                    intent_User.putExtra("Time", condate);

                    startActivity(intent_User);
                }
            });
            mainLayout.mainImg4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_addlist = new Intent(MainActivity.this, AddseatActivity.class);
                    intent_addlist.putExtra("ID", id);
                    startActivity(intent_addlist);
                }
            });
        }

        //좌석정보
        mainLayout.mainImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_Seat = new Intent(MainActivity.this, SeatingChartActivity.class);
                intent_Seat.putExtra("ID", id);
                startActivity(intent_Seat);
            }
        });

        //이용
        mainLayout.mainImg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               CustomDialog_info dialog = new CustomDialog_info(MainActivity.this, phoneWidth, phoneHeigth);
               dialog.Callinfo();

//                Intent intent_Inform = new Intent(MainActivity.this, InformationActivity.class);
//                startActivity(intent_Inform);
            }
        });

    }
//뒤로가기 버튼
    @Override
    public void onBackPressed() {
        CustomDialog_End customDialogEnd = new CustomDialog_End(MainActivity.this);
        customDialogEnd.callDialog();
    }

    //dot 구성
    public void IndicatorChange(){
        mainLayout.mainBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mainLayout.mainIndincatorView.setSelected(mainLayout.mainBanner.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //타이머-배너 자동 전환
    public class ImgTimer extends TimerTask {

        @Override
        public void run() {
          MainActivity.this.runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  Log.i("pager", String.valueOf(mainLayout.mainBanner.getCurrentItem() + 1));
                  mainLayout.mainBanner.setCurrentItem(mainLayout.mainBanner.getCurrentItem() + 1, true);
              }
          });
        }
    }

    }
