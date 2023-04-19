package com.example.moviium;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.moviium.CustomAdapters.PlanToWatchAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MovieList extends BaseActivity {
    ImageButton btnHome, btnFav, btnProfile;

    FirebaseFirestore db = FirebaseFirestore.getInstance(); // database
    CollectionReference watchListRef, moviesRef, ratingRef; // tables

    ArrayList<Movie> listOfMovies = new ArrayList<>();
    ListView lvPlanToWatch;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

    ArrayList<String> listOfMovieIds = new ArrayList<>();
    ArrayList<String> listOfRatings = new ArrayList<>();

    // Test
    TextView txtPlanToWatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        btnHome = findViewById(R.id.imgBtnHomeML);
        btnProfile = findViewById(R.id.imgBtnEditProfileML);
        btnFav = findViewById(R.id.imgBtnStarML);
        lvPlanToWatch = findViewById(R.id.lvPlantowatch);



        watchListRef = db.collection("Watchlist");
        //Query queryWatchlist = watchListRef;
        Query queryWatchlist = watchListRef.whereEqualTo("User_ID", uid);

        queryWatchlist.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryListOfMovies) {
                for (QueryDocumentSnapshot document : queryListOfMovies) {

                    String id = (String) document.getData().get("Movie_ID"); // fetch ids from the table
                    listOfMovieIds.add(id); // add ids to list

                }
            }
        });

        moviesRef = db.collection("Movies");
        for(String id : listOfMovieIds){
            DocumentReference queryMovies = moviesRef.document(id);

            queryMovies.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {

                    }
                }
            });
        }

//        moviesRef = db.collection("Movies");
//        Query queryMovies = moviesRef.whereIn(FieldPath.documentId(), listOfMovieIds);
//        queryMovies.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
//                    Movie movie;
//                    String id = document.getId();
//                    String image = (String) document.getData().get("Image");
//                    String title = (String) document.getData().get("Title");
//
//
//                    movie = new Movie(image, title, id);
//                    listOfMovies.add(movie);
//                }
//
//                PlanToWatchAdapter adapter = new PlanToWatchAdapter(getApplicationContext(), listOfMovies);
//                lvPlanToWatch.setAdapter(adapter);
//            }
//        });


//        moviesRef = db.collection("Movies");
//        //Query queryMovies = moviesRef.whereIn("movieId", listOfMovieIds);
//        Query queryMovies = moviesRef.whereArrayContainsAny(com.google.firebase.firestore.FieldPath.documentId(), listOfMovieIds);
//
//        queryMovies.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
//                    Movie movie;
//                    String id = document.getId();
//                    String image = (String) document.getData().get("Image");
//                    String title = (String) document.getData().get("Title");
//
//
//                    movie = new Movie(image, title, id);
//                    listOfMovies.add(movie);
//                }
//
//                PlanToWatchAdapter adapter = new PlanToWatchAdapter(getApplicationContext(), listOfMovies);
//                lvPlanToWatch.setAdapter(adapter);
//            }
//        });

        ratingRef = db.collection("Ratings");
        Query queryRatings = ratingRef.whereEqualTo("User_ID", uid);

        queryRatings.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    String movieId = (String) document.getData().get("Movie_ID");
                    Double rating = (Double) document.getData().get("Rating");


                    listOfRatings.add(movieId);
                    listOfRatings.add(rating.toString());

                }

                

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