package com.spacemonster.book.book;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class InformationActivity extends AppCompatActivity {

//    private Button inform_btn;
private LinearLayout inform_Layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
//        inform_btn = (Button)findViewById(R.id.inform_btn1);
        inform_Layout = (LinearLayout)findViewById(R.id.inform_layout);


        inform_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
