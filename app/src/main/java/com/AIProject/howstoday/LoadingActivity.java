package com.AIProject.howstoday;



import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.HashMap;

public class LoadingActivity extends Activity {

    private Bitmap userIMG;
    private String UUID;
    private AdView adView;
    private TextView txt_loading;
    private String text = "잠시만 기다려 주세요.";
    private String resultValue = null;

    public static HttpMethod httpMethod;

    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            if(resultValue != null) {
                AcceptIMGActivity.goToOtherActivity(LoadingActivity.this, ResultActivity.class, new HashMap<String,String>(){
                    {
                       put("resultValue", resultValue);
                       Log.d("resultValue---", resultValue);
                    }
                });
            }
            else{
                Error.errorAlert("오류 발생", LoadingActivity.this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoadingActivity.this.finish();
                    }
                });
            }
        }
    };

    Runnable interConn = new Runnable() {
        @Override
        public void run() {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    resultValue = httpMethod.requestToServer("/GUI",userIMG);
                    if(resultValue == "서버오류"){
                        Error.errorAlert("서버 오류", LoadingActivity.this, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                LoadingActivity.this.finish();
                            }
                        });
                    }
                    else if(resultValue == "timeout"){
                        Error.errorAlert("연결 오류", LoadingActivity.this, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                LoadingActivity.this.finish();
                            }
                        });
                    }
                    else handler.postDelayed(r,100);

                }
            });
            t.start();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_loading);

        //UUID = getIntent().getStringExtra("UUID");

        SharedPreferences sp = getSharedPreferences("userImage", MODE_PRIVATE);
        String img_str = sp.getString("img", "");
        userIMG = ImgHandler.stringToBitmap(img_str); //sharedpreferences를 이용하여 이미지를 가져옴.

        httpMethod = new HttpMethod(LoginActivity.uid);

        txt_loading = findViewById(R.id.txt_loading);
        changeText();

        //이 로딩액티비티에서 이미지에 대한 처리.

        //ad관련 코드
        MobileAds.initialize(this, "ca-app-pub-6496448515336559~4615370032");

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }

    @Override
    protected void onResume() {
        super.onResume();
       handler.postDelayed(interConn, 5000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(r);
        handler.removeCallbacks(interConn);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        return;
    }

    public void changeText(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.interrupted())
                    try{
                        Thread.sleep(700);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               if(text.length() == 15)
                                   text = text.substring(0,12);
                                txt_loading.setText(text);
                                text=text+".";
                            }
                        });
                    }catch(InterruptedException e){

                    }
            }
    }).start();
    }




}
