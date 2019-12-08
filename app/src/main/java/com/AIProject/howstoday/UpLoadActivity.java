package com.AIProject.howstoday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class UpLoadActivity extends Activity {

    private EditText et_title;
    private EditText et_desc;
    private Button btn_uploadcancel;
    private Button btn_upload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_up_load);

        initComponent();

    }

    public void onUpload(View v){
        Intent intent = new Intent();
        intent.putExtra("title", et_title.getText().toString().trim());
        intent.putExtra("desc", et_desc.getText().toString().trim());

        setResult(RESULT_OK, intent);
        finish();
    }

    public void onCancel(View v){
        finish();
    }


    public void initComponent(){
        et_title = findViewById(R.id.et_title);
        et_desc = findViewById(R.id.et_desc);
        btn_uploadcancel = findViewById(R.id.btn_uploadcancel);
        btn_upload = findViewById(R.id.btn_upload);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
}
