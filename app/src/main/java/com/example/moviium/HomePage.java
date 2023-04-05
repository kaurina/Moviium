package com.example.moviium;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        moviesRef = db.collection("Movies");
        Query query = moviesRef.orderBy("Release Date", Query.Direction.DESCENDING).limit(4);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryListOfMovies) {

                // code
                for (QueryDocumentSnapshot document : queryListOfMovies) {
                    Movie movie; // Save the fields
                    String id = document.getId();
                    String image = (String) document.getData().get("Image");
                    String title = (String) document.getData().get("Title");

                    movie = new Movie(image, title, id); // object

                    listOfMovies.add(movie);

                    Intent intent = new Intent(HomePage.this, RatingPage.class);
                    intent.putExtra("id", id);

                }



                // call function / Adapters..
                // customAdapter
                HomePageAdapter adapter = new HomePageAdapter(getApplicationContext(), listOfMovies, HomePage.this);
                lvTopMovies.setAdapter(adapter);

                lvTopMovies.setClickable(true);
                lvTopMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Movie entry= (Movie) parent.getAdapter().getItem(position);
                        Intent intent = new Intent(HomePage.this, RatingPage.class);
                        String message = entry.getMovieTitle();
                        intent.putExtra(EXTRA_MESSAGE, message);
                        startActivity(intent);
                    }
                });

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

    @Override
    public void onMovieClick(String id) {
        Intent intent = new Intent(this, RatingPage.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }
}