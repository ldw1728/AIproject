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

public class LoginActivity extends AppCompatActivity {

    public static String uid;
    private Button btn_login;
    private Button btn_signup;
    private EditText et_loginid;
    private EditText et_loginpw;

    private FirebaseAuth af;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponent();
        af = FirebaseAuth.getInstance();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = et_loginid.getText().toString().trim();
                String pw = et_loginpw.getText().toString().trim();
                Toast.makeText(LoginActivity.this, "로그인 중 입니다.", Toast.LENGTH_LONG).show();
                af.signInWithEmailAndPassword(email, pw).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            uid = af.getUid();
                            Intent intent = new Intent(getApplicationContext(), AcceptIMGActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else Toast.makeText(LoginActivity.this, "로그인 에러", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }

    public void initComponent(){
        btn_signup = findViewById(R.id.btn_signup);
        btn_login = findViewById(R.id.btn_login);
        et_loginid = findViewById(R.id.et_loginid);
        et_loginpw = findViewById(R.id.et_loginpw);
    }
}
