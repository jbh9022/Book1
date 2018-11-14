package com.spacemonster.book.book.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.spacemonster.book.book.MainActivity;
import com.spacemonster.book.book.R;

public class CustomDialog_End {
    private Context context;

    public CustomDialog_End(Context context) {
        this.context = context;
    }

    public void callDialog(){
       final Dialog dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.custom_enddialog);

        dlg.show();
        final Button okbtn = (Button) dlg.findViewById(R.id.end_okButton);
        final Button cancelbtn = (Button) dlg.findViewById(R.id.end_cancelButton);


        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)MainActivity.Main_Activity;
                mainActivity.finish();
                dlg.dismiss();
            }
        });
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();
                dlg.dismiss();
            }
        });

    }

}
