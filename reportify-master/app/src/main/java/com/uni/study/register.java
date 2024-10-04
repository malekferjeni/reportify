package com.uni.study;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {
    private Button reg;
    private EditText nom;
    private EditText email;
    private EditText pass;
    private EditText phone;
    private EditText niveau;
    private EditText type;
    private TextView login;
    String ty;
    String ni;
    public FirebaseAuth mAuth;
    public ProgressBar progressBar;
    public String[] NivItems = {"1","2","3"};
    public String[] TypeItems = {"Professor","Student"};
    AutoCompleteTextView autoCompleteTextViewNiv;
    AutoCompleteTextView autoCompleteTextViewType;
    ArrayAdapter<String> arrayAdapterNiv;
    ArrayAdapter<String> arrayAdapterType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        progressBar = findViewById(R.id.progressBar);
        reg = findViewById(R.id.btn);
        nom = findViewById(R.id.name);
        email = findViewById(R.id.email1);
        pass = findViewById(R.id.psswd);
        phone = findViewById(R.id.number);
        login = findViewById(R.id.loginWarp);

        autoCompleteTextViewNiv = findViewById(R.id.niv);
        autoCompleteTextViewType = findViewById(R.id.type1);
        arrayAdapterNiv = new ArrayAdapter<String>(this, R.layout.list_item, NivItems);
        arrayAdapterType = new ArrayAdapter<String>(this, R.layout.list_item, TypeItems);
        autoCompleteTextViewNiv.setAdapter(arrayAdapterNiv);
        autoCompleteTextViewType.setAdapter(arrayAdapterType);

        autoCompleteTextViewNiv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ni = parent.getItemAtPosition(position).toString();
                Toast.makeText(register.this, "Grade "+ ni + " selected!", Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextViewType.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ty = parent.getItemAtPosition(position).toString();
                Toast.makeText(register.this, "Type "+ ty + " selected!", Toast.LENGTH_SHORT).show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(register.this, login.class);
                startActivity(i);
                finish();
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String ema = email.getText().toString().trim();
                String password = pass.getText().toString().trim();
                String nam = nom.getText().toString().trim();
                String ph = phone.getText().toString().trim();

                mAuth = FirebaseAuth.getInstance();

                if (TextUtils.isEmpty(ema)) {
                    Toast.makeText(register.this, "Enter an E-mail", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(register.this, "Password field is empty", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(nam)) {
                    Toast.makeText(register.this, "Enter your full name", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(ph)) {
                    Toast.makeText(register.this, "Enter your phone number", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(ni)) {
                    Toast.makeText(register.this, "Enter your grade", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(ty)) {
                    Toast.makeText(register.this, "Enter your field of study", Toast.LENGTH_SHORT).show();
                }

                mAuth.createUserWithEmailAndPassword(ema, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(register.this, "Account created!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(register.this, "Account creation failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                });
            }
        });
    }


}
