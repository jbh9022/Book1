package com.spacemonster.book.book;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class CustomDialog {
    private Context context;

    public CustomDialog(Context context) {
        this.context = context;
    }

    public void callDialog(){
       final Dialog dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.custom_logoutdialog);

        dlg.show();
        final Button okbtn = (Button) dlg.findViewById(R.id.logout_okButton);
        final Button cancelbtn = (Button) dlg.findViewById(R.id.logout_cancelButton);


        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)MainActivity.Main_Activity;
                mainActivity.finish();
              Intent intent = new Intent(context, MainActivity.class);
                mainActivity.startActivity(intent);
                mainActivity.overridePendingTransition(0, 0);
                dlg.dismiss();
            }
        });
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();
                dlg.dismiss();
            }
        });

    }

}
