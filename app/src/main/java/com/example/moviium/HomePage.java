package com.example.moviium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moviium.CustomAdapters.HomePageAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BaseActivity {

    ImageButton btnHome, btnStar, btnProfile, btnSignOut;

    FirebaseFirestore db = FirebaseFirestore.getInstance(); // database
    CollectionReference moviesRef; // tables

    ArrayList<Movie> listOfMovies = new ArrayList<>();
    ListView lvTopMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        lvTopMovies = findViewById(R.id.movieListView);

        moviesRef = db.collection("Movies");
        Query query = moviesRef.orderBy("Release Date", Query.Direction.DESCENDING).limit(4);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryListOfMovies) {
                // code
                for (QueryDocumentSnapshot document : queryListOfMovies) {
                    Movie movie; // Save the fields
                    String image = (String) document.getData().get("Image");
                    String title = (String) document.getData().get("Title");

                    movie = new Movie(image, title); // object

                    listOfMovies.add(movie);
                }

                // call function / Adapters..
                // customAdapter
                HomePageAdapter adapter = new HomePageAdapter(getApplicationContext(), listOfMovies);
                lvTopMovies.setAdapter(adapter);
            }
        });



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