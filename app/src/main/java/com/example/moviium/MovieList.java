package com.example.moviium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.moviium.CustomAdapters.HomePageAdapter;
import com.example.moviium.CustomAdapters.PlanToWatchAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MovieList extends BaseActivity {
    ImageButton btnHome, btnFav, btnProfile;

    FirebaseFirestore db = FirebaseFirestore.getInstance(); // database
    CollectionReference watchListRef; // tables

    ArrayList<Movie> listOfMovies = new ArrayList<>();
    ListView lvPlanToWatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        btnHome = findViewById(R.id.imgBtnHomeML);
        btnProfile = findViewById(R.id.imgBtnEditProfileML);
        btnFav = findViewById(R.id.imgBtnStarML);
        lvPlanToWatch = findViewById(R.id.lvPlantowatch);

        watchListRef = db.collection("Watchlist");
        Query query = watchListRef;

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryListOfMovies) {
                for (QueryDocumentSnapshot document : queryListOfMovies) {
                    Movie movie; // Save the fields
                    String id = document.getId();
                    //String image = (String) document.getData().get("User_ID");
                    String title = (String) document.getData().get("Movie_ID");

                    //movie = new Movie(image, title, id);
//                    movie.setMovieImage(image);

                    movie = new Movie();
                    movie.setMovieTitle(title);
                    listOfMovies.add(movie);
                }

                // customAdapter
                PlanToWatchAdapter adapter = new PlanToWatchAdapter(getApplicationContext(), listOfMovies);
                lvPlanToWatch.setAdapter(adapter);
            }
        });





        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
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

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MovieList.class);
                startActivity(intent);
            }
        });
    }
}