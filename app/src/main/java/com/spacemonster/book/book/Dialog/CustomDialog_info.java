package com.spacemonster.book.book.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.spacemonster.book.book.R;

public class CustomDialog_info {
    private Context context;
    private int phoneWidth;
    private int phoneHeigth;

    public CustomDialog_info(Context context, int phoneWidth, int phoneHeigth) {
        this.context = context;
        this.phoneWidth = phoneWidth;
        this.phoneHeigth = phoneHeigth;
    }
    public void Callinfo(){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_infodialog);
        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.copyFrom(dialog.getWindow().getAttributes());
        wm.width= (int) (phoneWidth *0.9f);
        wm.height= (int)(phoneHeigth *0.9f);
        dialog.getWindow().setAttributes(wm);
        dialog.show();

        final ImageView cancelButton = (ImageView) dialog.findViewById(R.id.custom_infoCancelBtn);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
}
