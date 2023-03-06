package com.example.moviium;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity{
    Button btnRegister1, btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        btnRegister1 = (Button) findViewById(R.id.btnLoginReg);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnRegister1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLoginIntentFromLoginBtn = new Intent(getApplicationContext(), login.class);
                startActivity(goToLoginIntentFromLoginBtn);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //write code to validate data??
                //write code to add register details to database/arraylist

                Intent goToLoginIntentFromRegisterBtn = new Intent(getApplicationContext(), login.class);
                startActivity(goToLoginIntentFromRegisterBtn);
            }
        });

    }
}
