package com.AIProject.howstoday;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import static com.AIProject.howstoday.AcceptIMGActivity.AccepIMGActivity;

public class ResultActivity extends AppCompatActivity {


    private TextView txt_score;


    private String resultValue;
    private Bitmap userIMG;
    private ImageButton btn_home;
    private Button btn_evalme;
    private Button btn_evalother;

    private String score = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        AccepIMGActivity.finish();
        initComponent();
        SharedPreferences sp = getSharedPreferences("userImage", MODE_PRIVATE);
        String img_str = sp.getString("img", "");
        userIMG = ImgHandler.stringToBitmap(img_str);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcceptIMGActivity.goToOtherActivity(ResultActivity.this, AcceptIMGActivity.class, null);
            }
        });

        btn_evalother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcceptIMGActivity.goToOtherActivity(ResultActivity.this, UserBoardActivity.class, null);
            }
        });



        resultValue = getIntent().getStringExtra("resultValue"); //결과 값.

        ImageView resultVIew = (ImageView)findViewById(R.id.View_result);

        resultVIew.setImageBitmap(userIMG);

        txt_score = findViewById(R.id.txt_score);
        setResultView(resultValue);

    }

    public void onBtnOnUpload(View v){
        Intent intent = new Intent(getApplicationContext(), UpLoadActivity.class);
        startActivityForResult(intent, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 0){
            if(resultCode == RESULT_OK){

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String time = sdf.format(System.currentTimeMillis());

                BoardItem bi = new BoardItem(LoginActivity.uid, data.getStringExtra("title"), data.getStringExtra("desc"),time,
                        ImgHandler.bitmapToString(userIMG), resultValue);

                DBController.writeBoardItemToDB(bi, new Consumer<Object>() {
                    @Override
                    public void accept(Object o) {
                        AcceptIMGActivity.goToOtherActivity(ResultActivity.this, UserBoardActivity.class, null);
                    }
                });




            }
        }
    }

    public void initComponent(){

        btn_home = findViewById(R.id.btn_home);
        btn_evalme = findViewById(R.id.btn_evalme);
        btn_evalother = findViewById(R.id.btn_evaloth);

    }

    public void setResultView(String resultValue){
        //double ds = Double.parseDouble(resultValue);
        score = resultValue;
        txt_score.setText(score);

    }
}
