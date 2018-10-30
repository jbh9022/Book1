package com.spacemonster.book.book;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SeatingChartActivity extends AppCompatActivity {

    private String text1;
    private String seatingList[] = {"좌석선택","자유석1", "자유석2"};

    private Button btn1;
    private Button btn2;
    private Spinner spinner;
    private ImageView imageView;
    private LinearLayout seat_Layout;
    private Intent intent_seat1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seating_chart);

        final Intent intent_Seat = getIntent();
        text1 = intent_Seat.getStringExtra("ID");

        //뒤로가기
       seat_Layout = (LinearLayout)findViewById(R.id.seat_Laout) ;
        //확인
        btn2 = (Button)findViewById(R.id.seat_btn2);
        spinner = (Spinner)findViewById(R.id.seat_spinner);
        imageView = (ImageView)findViewById(R.id.seat_img);

        seat_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(SeatingChartActivity.this, R.layout.support_simple_spinner_dropdown_item, seatingList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        imageView.setBackgroundResource(0);
                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(SeatingChartActivity.this, "좌석을 선택하세요", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case 1:
                        imageView.setBackgroundResource(R.drawable.seating1r);
                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(SeatingChartActivity.this, "좌석1 선택", Toast.LENGTH_SHORT).show();
                            }
                        });
                    break;
                    case 2:
                        imageView.setBackgroundResource(R.drawable.seating2r);
                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(SeatingChartActivity.this, "좌석2 선택", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
