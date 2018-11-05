package com.spacemonster.book.book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.spacemonster.book.book.Network.Insert;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddseatActivity extends AppCompatActivity {

    private Intent addintent;
    private String userID;

    @BindView(R.id.addlist_Btn1)Button addBtn;
    @BindView(R.id.addlist_Spinner)Spinner addSpinner;
    @BindView(R.id.addlist_Layout)LinearLayout addLayout;

    private String[] seatList = {"1","2","3","4","5","6","7","8","9","10",
                                "11","12","13","14","15","16","17","18","19","20",
                                "21","22","23","24","25","26","27","28","29","30",
                                "31","32","33","34","35","36","37","38","39","40",
                                "41","42","43","44","45","46","47","48","49","50"};
    private String addlistTxt;
    private String addDate1;
    private String addDate2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addseat);

        addintent = getIntent();
        userID =addintent.getStringExtra("ID");

        ButterKnife.bind(this);

        ArrayAdapter<String> addadapter;
        addadapter = new ArrayAdapter<String>(AddseatActivity.this, R.layout.support_simple_spinner_dropdown_item, seatList);
        addSpinner.setAdapter(addadapter);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                addDate1 = sdf.format(new Date());
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                addDate2 = sdf2.format(new Date());
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
        insert_addseat.execute(userID,addlistTxt,addDate1,addDate2,"입실");
    }
}
