package com.example.moviium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

public class HomePage extends BaseActivity {

    ImageButton btnHome, btnStar, btnProfile, btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        btnHome = findViewById(R.id.imgBtnHomeHM);
        btnStar = findViewById(R.id.imgBtnStarHM);
        btnProfile = findViewById(R.id.imgBtnProfileHM);
        btnSignOut = findViewById(R.id.imgBtnLogout);

        btnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToFav = new Intent(getApplicationContext(), MovieList.class);
                startActivity(intentToFav);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToProfile = new Intent(getApplicationContext(), Profile.class);
                startActivity(intentToProfile);
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getCurrentUser();
                mAuth.signOut();
                //Toast.makeText(HomePage.this, "", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomePage.this, login.class));
            }
        });

    }
}