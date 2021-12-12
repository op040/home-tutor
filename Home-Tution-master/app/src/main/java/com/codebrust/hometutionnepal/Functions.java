package com.codebrust.hometutionnepal;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.gmail.samehadar.iosdialog.CamomileSpinner;

public class Functions {
    private static Dialog dialog;

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void dialouge(Context context, String title, String message) {

        if (dialog != null) {
            dialog.dismiss();
        }

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_alert_dialouge);
        dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.d_round_corner_white_bkg));

        TextView header_txt = dialog.findViewById(R.id.header_txt);
        TextView message_txt = dialog.findViewById(R.id.alert_msg_txt);
        Button ok_btn = dialog.findViewById(R.id.ok_btn);
        header_txt.setText(title);
        message_txt.setText(message);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public static void show_loader(Context context, boolean outside_touch, boolean cancleable) {

        if (dialog != null) {
            dialog.dismiss();
        }

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_loader_dialog);
        dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.d_round_corner_white_bkg));

        CamomileSpinner loader = dialog.findViewById(R.id.loader);
        loader.start();

        if (!outside_touch)
            dialog.setCanceledOnTouchOutside(false);

        if (!cancleable)
            dialog.setCancelable(false);

        dialog.show();

    }

    public static void cancel_loader() {
        if (dialog != null) {
            dialog.cancel();
            dialog.dismiss();
        }
    }
}
