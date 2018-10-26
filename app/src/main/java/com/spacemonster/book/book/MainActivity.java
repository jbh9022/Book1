package com.spacemonster.book.book;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import pyxis.uzuki.live.rollingbanner.RollingBanner;
import pyxis.uzuki.live.rollingbanner.RollingViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private String[] txtRes = new String[]{"Purple", "Light Blue", "Cyan", "Teal", "Green"};
    private int[] colorRes = new int[]{0xff9C27B0, 0xff03A9F4, 0xff00BCD4, 0xff009688, 0xff4CAF50};

    private LinearLayout loginLayout;
    private TextView loginTxt;
    private TextView userText;
    private TextView mainText1;
    ///btn1>이용안내, btn2> 좌석정보, btn3>회원정보
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private String textset[] = {"(공지) Test 공지 입니다. Test 공지 입니다. Test 공지 입니다. Test 공지 입니다. "};
    public static Activity Main_Activity;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Main_Activity = MainActivity.this;

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        //1>좌석, 2>회원, 3>이용, 4>카페
        img1 = (ImageView) findViewById(R.id.main_img1);
        img2 = (ImageView) findViewById(R.id.main_img2);
        img3 = (ImageView) findViewById(R.id.main_img3);
        img4 = (ImageView) findViewById(R.id.main_img4);

        loginLayout = (LinearLayout) findViewById(R.id.main_login);
        loginTxt = (TextView)findViewById(R.id.main_loginTxt);
        userText = (TextView)findViewById(R.id.main_userTxt);
        mainText1 = (TextView)findViewById(R.id.main_text1);

        mainText1.setSelected(true);
        mainText1.setText(textset[0]);

    //로그인!!!!
        if(id == null || id.equals("")){
            //로그인전
            userText.setText("");
            loginLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent inTent_login = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(inTent_login);
                }
            });
            //회원정보
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "로그인후 이용해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            //로그인 이후
            userText.setText(id + " 님");
//            userText2.setText(" 환영합니다.");
            loginTxt.setText("로그아웃");
            loginLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    로그아웃 다이얼로그 넣기
                  CustomDialog customDialog = new CustomDialog(MainActivity.this);
                  customDialog.callDialog();
                }
            });
            //회원정보
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String condate = sdf.format(new Date());

                    Intent intent_User = new Intent(MainActivity.this, UserInfoActivity.class);
                    intent_User.putExtra("ID", id);
                    intent_User.putExtra("Time", condate);

                    startActivity(intent_User);
                }
            });
        }

        //롤링배너
        RollingBanner rollingBanner = findViewById(R.id.banner);

        SampleAdapter adapter = new SampleAdapter(new ArrayList<>(Arrays.asList(txtRes)));
        rollingBanner.setAdapter(adapter);

        //좌석정보
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_Seat = new Intent(MainActivity.this, SeatingChartActivity.class);
                intent_Seat.putExtra("ID", id);
                startActivity(intent_Seat);
            }
        });

        //이용
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_Inform = new Intent(MainActivity.this, InformationActivity.class);
                startActivity(intent_Inform);
            }
        });
        //카페>추후
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        CustomDialogEnd customDialogEnd = new CustomDialogEnd(MainActivity.this);
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
//            LinearLayout container = view.findViewById(R.id.container);
            TextView txtText = view.findViewById(R.id.main_Text);
            String txt = getItem(i);
            int index = getItemList().indexOf(txt);
            txtText.setText(txt);
            container.setBackgroundColor(colorRes[index]);
            return view;
        }

    }


    }
