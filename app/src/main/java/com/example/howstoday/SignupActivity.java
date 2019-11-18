package com.example.howstoday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {


    private EditText et_signemail;
    private EditText et_signpw;
    private Button btn_ok;
    private Button btn_cancel;
    private FirebaseAuth fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fa = FirebaseAuth.getInstance();

        initComponent();

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_signemail.getText().toString().trim();
                String pw = et_signpw.getText().toString().trim();

                fa.createUserWithEmailAndPassword(email,pw).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()){
                             Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                             startActivity(intent);
                             finish();
                         }
                         else Toast.makeText(SignupActivity.this, "등록 에러", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void initComponent(){
        et_signemail = findViewById(R.id.et_signemail);
        et_signpw = findViewById(R.id.et_signpw);
        btn_ok = findViewById(R.id.btn_ok);
        btn_cancel = findViewById(R.id.btn_cancel);
    }
}
