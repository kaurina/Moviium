package com.example.moviium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class RatingPage extends BaseActivity {

    ImageButton btnHome, btnFav, btnProfile;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_page);

        btnHome = findViewById(R.id.imgBtnHomeRP);
        btnFav = findViewById(R.id.imgBtnStarRP);
        btnProfile = findViewById(R.id.imgBtnProfileRP);
        title = findViewById(R.id.txtTitleRP);

        Intent intent = getIntent();
        String movieId = intent.getStringExtra("id");

        title.setText(movieId);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MovieList.class);
                startActivity(intent);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
    }
}