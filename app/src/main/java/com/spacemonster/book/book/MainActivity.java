package com.spacemonster.book.book;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spacemonster.book.book.Adapter.ViewPager_TextAdapter;
import com.spacemonster.book.book.Dialog.CustomDialog_End;
import com.spacemonster.book.book.Dialog.CustomDialog_Logout;
import com.spacemonster.book.book.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import pyxis.uzuki.live.rollingbanner.RollingBanner;
import pyxis.uzuki.live.rollingbanner.RollingViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private String[] txtRes = new String[]{"Purple", "Light Blue", "Cyan", "Teal", "Green"};
    private int[] colorRes = new int[]{0xff9C27B0, 0xff03A9F4, 0xff00BCD4, 0xff009688, 0xff4CAF50};

//    @BindView(R.id.main_login) LinearLayout loginLayout;
//    @BindView(R.id.main_loginTxt) TextView loginTxt;
//    @BindView(R.id.main_userTxt) TextView userText;
//    ///btn1>이용안내, btn2> 좌석정보, btn3>회원정보
//    @BindView(R.id.main_img1) ImageView img1;
//    @BindView(R.id.main_img2) ImageView img2;
//    @BindView(R.id.main_img3) ImageView img3;
//    @BindView(R.id.main_img4) ImageView img4;
//
//    @BindView(R.id.main_Text) TextView txtText;
//    @BindView(R.id.main_Textslider) ViewPager main_Viewpager;
    private String textset[] = {"(공지) Test 공지 입니다. Test 공지 입니다. Test 공지 입니다. Test 공지 입니다. "};

    public static Activity Main_Activity;
    ActivityMainBinding mainLayout;
    String id;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        ButterKnife.bind(this);

        Main_Activity = MainActivity.this;

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");

        //공지 뷰
        ViewPager_TextAdapter viewPagerAdapter = new ViewPager_TextAdapter(this);
        mainLayout.mainTextslider.setAdapter(viewPagerAdapter);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("pager", String.valueOf(mainLayout.mainTextslider.getCurrentItem() + 1));
                mainLayout.mainTextslider.setCurrentItem(mainLayout.mainTextslider.getCurrentItem() + 1);
                handler.postDelayed(this, 5000);
            }
        },5000);

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

        //롤링배너
        RollingBanner rollingBanner = findViewById(R.id.banner);

        SampleAdapter adapter = new SampleAdapter(new ArrayList<>(Arrays.asList(txtRes)));
        rollingBanner.setAdapter(adapter);

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
                Intent intent_Inform = new Intent(MainActivity.this, InformationActivity.class);
                startActivity(intent_Inform);
            }
        });

    }
//뒤로가기 버튼
    @Override
    public void onBackPressed() {
        CustomDialog_End customDialogEnd = new CustomDialog_End(MainActivity.this);
        customDialogEnd.callDialog();
    }

    //배너 어댑터
    public class SampleAdapter extends RollingViewPagerAdapter<String> {
        public SampleAdapter(ArrayList<String> itemList) {
            super(itemList);
        }

        @NotNull
        @Override
        public View getView(int i, String s) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, null, false);
            FrameLayout container = view.findViewById(R.id.main_container);

            String txt = getItem(i);
            int index = getItemList().indexOf(txt);
            mainLayout.mainText.setText(txt);
            container.setBackgroundColor(colorRes[index]);
            return view;
        }

    }

    }
