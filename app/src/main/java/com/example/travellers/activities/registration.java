package com.example.travellers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travellers.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registration extends AppCompatActivity {
    private EditText name, email, password,address,phone;
    private Button sign_up;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        sign_up = findViewById(R.id.signup);
        address=findViewById(R.id.address);
        phone=findViewById(R.id.phone);

        auth=FirebaseAuth.getInstance();

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // getSupportActionBar().hide();
                
                String username = name.getText().toString();
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                String userAddress = address.getText().toString();
                String userPhone = phone.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    name.setError("Field cannot be empty!");
                    return;
                }
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

                if (TextUtils.isEmpty(userAddress)) {
                    address.setError("Field cannot be empty!");
                    return;
                }

                if (TextUtils.isEmpty(userPhone)) {
                    phone.setError("Field cannot be empty!");
                    return;
                }

                auth.createUserWithEmailAndPassword(userEmail,userPassword)
                        .addOnCompleteListener(registration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull  Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(registration.this,"Registration successful!",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(registration.this,Home.class));
                                    name.setText("");
                                    email.setText("");
                                    password.setText("");
                                    address.setText("");
                                    phone.setText("");
                                }

                                else{
                                    Toast.makeText(registration.this,"Registration failed!"+task.getException(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });
    }
}