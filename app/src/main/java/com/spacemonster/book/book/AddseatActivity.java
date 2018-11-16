package com.spacemonster.book.book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.spacemonster.book.book.Network.Insert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

// 데모 페이지-자리 등록
public class AddseatActivity extends AppCompatActivity {

    private Intent addintent;
    private String userID;
    ArrayAdapter<String> addadapter;
    ArrayAdapter<String> seatNameadpater;
    @BindView(R.id.addlist_Btn1)Button addBtn;
    @BindView(R.id.addlist_Spinner)Spinner addSpinner;
    @BindView(R.id.addlist_Layout)LinearLayout addLayout;
    @BindView(R.id.addlist_Spinner1)Spinner seatName;
    private String[] seatlistName = {"특1인실","1인실","지정석","자유석"};
    //특1인실
    private String[]  seatList1 ={"10","20","21"};
    //1인실
    private String[]  seatList2 = {"6","7","8","9","22","23","24","25","26"};
    //지정석
    private String[]  seatList3 ={"1","2","3","4","5",
                                 "11","12","13","14","15",
                                 "16","17","18","19","27","28","29"};
    //자유석
    private String[] seatList4 = {"30","31","32","33","34","35","36","37","38","39",
                                 "40","41","42","43","44","45","46","47","48","49",
                                 "50","51","52","53","54","55","56","57","58","59",
                                 "60","61","62","63","64","65","66","67","68","69",
                                 "70","71","72","73","74","75","76","77","78","79"};
    private String addlistTxt;
    private String addSeatNameTxt;
    private String addDate1;
    private String addDate2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addseat);

        addintent = getIntent();
        userID =addintent.getStringExtra("ID");

        ButterKnife.bind(this);

        seatNameadpater = new ArrayAdapter<String>(AddseatActivity.this, R.layout.support_simple_spinner_dropdown_item, seatlistName);
        seatName.setAdapter(seatNameadpater);
        seatName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    //특1인실 선택시
                    case 0:
                        seat1();
                        break;
                        //1인실 선택시
                    case 1:
                        seat2();
                        break;
                        //지정석 선택시
                    case 2:
                        seat3();
                        break;
                        //자유석 선택시
                    case 3:
                        seat4();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                addDate1 = sdf.format(new Date());
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                addDate2 = sdf2.format(new Date());
                addSeatNameTxt = seatName.getSelectedItem().toString();
                addlistTxt = addSpinner.getSelectedItem().toString();
                seatIn();
                finish();
            }
        });
        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void seatIn(){
        Insert insert_addseat = new Insert();
        insert_addseat.execute(userID,"거제점",addSeatNameTxt,addlistTxt,addDate1,addDate2,"입실");
    }

    private void seat1(){
        addadapter = new ArrayAdapter<String>(AddseatActivity.this, R.layout.support_simple_spinner_dropdown_item, seatList1);
        addSpinner.setAdapter(addadapter);
    }
    private void seat2(){
        addadapter = new ArrayAdapter<String>(AddseatActivity.this, R.layout.support_simple_spinner_dropdown_item, seatList2);
        addSpinner.setAdapter(addadapter);
    }
    private void seat3(){
        addadapter = new ArrayAdapter<String>(AddseatActivity.this, R.layout.support_simple_spinner_dropdown_item, seatList3);
        addSpinner.setAdapter(addadapter);
    }
    private void seat4(){
        addadapter = new ArrayAdapter<String>(AddseatActivity.this, R.layout.support_simple_spinner_dropdown_item, seatList4);
        addSpinner.setAdapter(addadapter);
    }
}
