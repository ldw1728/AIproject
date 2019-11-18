package com.example.howstoday;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

public class Error {

    static void errorAlert(final String msg, final Activity activity, final DialogInterface.OnClickListener listener){
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                android.app.AlertDialog.Builder errorAlert = new AlertDialog.Builder(activity);

                errorAlert.setMessage(msg).setCancelable(false).setPositiveButton("확인",
                        listener);
                errorAlert.show();
            }
        },0);

    }
}
