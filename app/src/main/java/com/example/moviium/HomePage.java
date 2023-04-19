package com.example.moviium;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class HomePage extends BaseActivity implements HomePageAdapter.OnItemClickListener {

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
        //accessing movie table
        moviesRef = db.collection("Movies");
        //accessing all items in movie table, in list form
        Query query = moviesRef.orderBy("Release Date", Query.Direction.DESCENDING).limit(4);

        //specifying which properties we want (id, image, title)
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryListOfMovies) {

                //iterate through list
                for (QueryDocumentSnapshot document : queryListOfMovies) {
                    Movie movie; // Save the fields
                    String id = document.getId();
                    String image = (String) document.getData().get("Image");
                    String title = (String) document.getData().get("Title");

                    //using constructor from movie class
                    movie = new Movie(image, title, id);

                    listOfMovies.add(movie);
                }

                // customAdapter
                HomePageAdapter adapter = new HomePageAdapter(getApplicationContext(), listOfMovies, HomePage.this);
                lvTopMovies.setAdapter(adapter);
            }
        });



        btnHome = findViewById(R.id.imgBtnHomeHM);
        btnStar = findViewById(R.id.imgBtnStarHM);
        btnProfile = findViewById(R.id.imgBtnProfileHM);

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

    }


    //comes from interface created in adapter
    @Override
    public void onMovieClick(String id) {
        Intent intent = new Intent(this, RatingPage.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }

}