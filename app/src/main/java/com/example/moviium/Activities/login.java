package com.example.moviium.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moviium.R;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    Button btnLogin, btnRegister;
    EditText editUsername, editPassword;
    FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsername = findViewById(R.id.editTxtEmail);
        editPassword = findViewById(R.id.editTxtPassword);
        myAuth = FirebaseAuth.getInstance();

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editUsername.getText().toString().trim();
                String pass = editPassword.getText().toString().trim();

                if(email.isEmpty()) {
                    editUsername.setError("Email cannot be empty!");
                    editUsername.requestFocus();
                    return;
                }


                if(pass.isEmpty()) {
                    editPassword.setError("Password cannot be empty!");
                    editPassword.requestFocus();
                    return;
                }

                myAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        startActivity(new Intent(login.this, HomePage.class));
                    }
                    else {
                        Toast.makeText(login.this, "Invalid Email or Password!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

}