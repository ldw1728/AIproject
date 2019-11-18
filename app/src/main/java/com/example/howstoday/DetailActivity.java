package com.example.howstoday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private BoardItem item;
    private TextView title;
    private TextView desc;
    private TextView userEmail;
    private TextView date;
    private TextView evalCount;
    private ImageView img;
    private Button btn_evalbtn;
    private RatingBar rb_eval;

    boolean evalClick = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        item = (BoardItem) getIntent().getSerializableExtra("item");
        rb_eval.setIsIndicator(false);
        userEmail.setText(item.getEmail());
        title.setText(item.getTitle());
        img.setImageBitmap(ImgHandler.stringToBitmap(item.getUserIMG()));
        desc.setText(item.getDesc());
        date.setText(item.getDate());
        evalCount.setText(String.valueOf(item.getEvalCount()));




        btn_evalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rb_eval.getRating() != 0){
                    if(evalClick == false){
                        ArrayList<String> list = item.getRecommendedList();

                        for(String users : list){

                            if(users.equals(LoginActivity.uid)){
                                Toast.makeText(getApplicationContext(), "평가는 한번만 가능합니다", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }

                        float rating = rb_eval.getRating();
                        Log.d("getRating----", String.valueOf(rb_eval.getRating()));
                       float score =  Float.valueOf(item.getScore());
                        Log.d("Score----", item.getScore());
                       item.setScore(String.valueOf((score*(float)item.getEvalCount()+rating)/((float)item.getEvalCount()+1.0)));
                        Log.d("getEvalCount----", String.valueOf(item.getEvalCount()));
                       Log.d("finalScore----", item.getScore());
                       item.setEvalCount(item.getEvalCount()+1);
                        evalCount.setText(String.valueOf(item.getEvalCount()));
                        list.add(LoginActivity.uid);
                       item.setRecommendedList(list);

                       DBController.updateChild(item);
                        Toast.makeText(getApplicationContext(),"별점이 정상적으로 반영되었습니다.", Toast.LENGTH_LONG);
                        evalClick = true;
                    }
                    else Toast.makeText(getApplicationContext(), "평가는 한번만 가능합니다", Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(getApplicationContext(), "별점을 설정해주세요", Toast.LENGTH_LONG).show();

            }
        });

    }

    public void init(){
        title = findViewById(R.id.tv_title);
        img = findViewById(R.id.imageView);
        desc = findViewById(R.id.desc);
        userEmail = findViewById(R.id.tv_userEmail);
        date = findViewById(R.id.tv_uploadDate);
        btn_evalbtn = findViewById(R.id.btn_evalbtn);
        rb_eval = findViewById(R.id.ratingBar_eval);
        evalCount = findViewById(R.id.tv_evalUser);
    }
}
