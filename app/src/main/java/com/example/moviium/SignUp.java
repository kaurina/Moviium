package com.example.moviium;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity{
    Button btnLogin, btnRegister;
    EditText username, password;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        btnLogin = (Button) findViewById(R.id.btnLoginReg);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        username = (EditText) findViewById(R.id.emailInput);
        password = (EditText) findViewById(R.id.passwordInput);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLoginIntent = new Intent(getApplicationContext(), login.class);
                startActivity(goToLoginIntent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (email.isEmpty()){
                    username.setError("Cannot be empty, please enter your email");
                    username.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    username.setError("Enter a valid email address");
                    username.requestFocus();
                    return;
                }

                if (pass.isEmpty()){
                    password.setError("Oops! Enter your password!");
                    password.requestFocus();
                    return;
                }

                if(pass.length() < 8 || pass.length() > 30){
                    password.setError("The length should be between 8 to 30 characters");
                    password.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUp.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this, HomePage.class));
                        } else {
                            Toast.makeText(SignUp.this, "Not successfully registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
//                            String user_id = current_user.getUid();
//                            String device_token = FirebaseInstanceId.getInstance().getToken();

//                            dbObject = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
//                            dbObject.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    if(dataSnapshot.exists()){
//                                      Toast.makeText(SignUp.this, "This user already exists", Toast.LENGTH_SHORT).show();
//                                    }
                
            }
        });
    }
}
