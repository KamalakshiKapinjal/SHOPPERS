package com.example.travellers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travellers.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_page extends AppCompatActivity {

    private EditText  email, password;
    private Button login;
    private TextView sign_up;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        email = findViewById(R.id.get_email);
        password = findViewById(R.id.get_pass);
        login = findViewById(R.id.login);
        sign_up=findViewById(R.id.textview);

        auth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getSupportActionBar().hide();

                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();


                if (TextUtils.isEmpty(userEmail)) {
                    email.setError("Field cannot be empty!");
                    return;
                }

                if (TextUtils.isEmpty(userPassword)) {
                    password.setError("Field cannot be empty!");
                    return;
                }

                if (userPassword.length() < 6) {
                    password.setError("Password should be at least 6 characters!");
                    return;
                }

                auth.signInWithEmailAndPassword(userEmail,userPassword)
                        .addOnCompleteListener(login_page.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(login_page.this,"Login successful!",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(login_page.this,Home.class));
                                    email.setText("");
                                    password.setText("");
                                }
                                else{
                                    Toast.makeText(login_page.this,"Login failed!"+task.getException(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login_page.this,registration.class));
            }
        });
    }
}