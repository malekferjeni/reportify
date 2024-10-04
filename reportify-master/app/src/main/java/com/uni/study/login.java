package com.uni.study;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    private EditText email;
    private EditText psswd;
    private Button log;
    private TextView sign;
    public FirebaseAuth mAuth;
    public ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        psswd = findViewById(R.id.password);
        log = findViewById(R.id.login);
        sign = findViewById(R.id.signupText);
        progressBar = findViewById(R.id.progressBar);
//

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                mAuth = FirebaseAuth.getInstance();
                String ema = String.valueOf(email.getText().toString().trim());
                String password = String.valueOf(psswd.getText().toString().trim());

                if (TextUtils.isEmpty(ema)) {
                    Toast.makeText(login.this, "Enter an E-mail", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(login.this, "Password field is empty", Toast.LENGTH_SHORT).show();
                }


                mAuth.signInWithEmailAndPassword(ema, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(login.this, "Logged In!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), dash.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(login.this, "Login failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(getApplicationContext(), register.class);
                startActivity(i2);
                finish();
            }
        });

    }

    /*

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 =new Intent(login.this, dash.class);
                startActivity(i1);
            }
        }); */

}