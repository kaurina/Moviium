package com.example.moviium.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviium.CustomAdapters.HomePageAdapter;
import com.example.moviium.CustomAdapters.PlanToWatchAdapter;
import com.example.moviium.CustomAdapters.RatedAdapter;
import com.example.moviium.HelperClass;
import com.example.moviium.Models.Movie;
import com.example.moviium.Models.PlanToWatch;
import com.example.moviium.Models.Rating;
import com.example.moviium.R;
import com.example.moviium.SwipeToDelete;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class MovieList extends BaseActivity {
    ImageButton btnHome, btnFav;

    FirebaseFirestore db = FirebaseFirestore.getInstance(); // database
    CollectionReference watchListRef, moviesRef, ratingRef; // tables

    RecyclerView lvPlanToWatch;
    ListView lvRatedMovies;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

    ArrayList<String> listOfMovieIds = new ArrayList<>();
    ArrayList<Rating> listOfRatedMovies = new ArrayList<>();

    ArrayList<PlanToWatch> listOfToWatchMoviesIds = new ArrayList<>();
    RatedAdapter ratedAdapter;
    PlanToWatchAdapter planToWatchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        btnHome = findViewById(R.id.imgBtnHomeML);
        btnFav = findViewById(R.id.imgBtnStarML);
        lvPlanToWatch = findViewById(R.id.lvPlantowatch);
        lvRatedMovies = findViewById(R.id.lvCompleted);




        ratingRef = db.collection("Ratings");
        Query queryRatings = ratingRef.whereEqualTo("User_ID", uid);

        queryRatings.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    String movieId = (String) document.getData().get("Movie_ID");
                    Double rate = (Double) document.getData().get("Rating");

                    Rating rating = new Rating(movieId, rate);
                    listOfRatedMovies.add(rating);
                }
                getTitleOfMovies(listOfRatedMovies);
            }
        });


        watchListRef = db.collection("Watchlist");
        Query queryWatchlist = watchListRef.whereEqualTo("User_ID", uid);

        queryWatchlist.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryListOfMovies) {
                for (QueryDocumentSnapshot document : queryListOfMovies) {
                    String movieId = (String) document.getData().get("Movie_ID"); // fetch ids from the table
                    PlanToWatch planToWatch = new PlanToWatch(document.getId(),movieId);
                    listOfToWatchMoviesIds.add(planToWatch); // add ids to list
                }
                getTitleOfToWatchMovies(listOfToWatchMoviesIds);
            }
        });



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
    }

    public void getTitleOfMovies(ArrayList<Rating> movies){
        moviesRef = db.collection("Movies");
        List<Integer> counter = new ArrayList<>();
        displayRatedMovies(movies);
        for(Rating m : movies){
            DocumentReference query = moviesRef.document(m.getMovieId());
            query.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot document) {
                    String title = (String) document.getData().get("Title");
                    m.setMovieTitle(title);
                    if(ratedAdapter != null){
                        ratedAdapter.reload();
                    }
                }
            });

        }
    }

    public void displayRatedMovies(ArrayList<Rating> movies){
        ratedAdapter = new RatedAdapter(getApplicationContext(), movies);
        lvRatedMovies.setAdapter(ratedAdapter);
    }

    public void getTitleOfToWatchMovies(ArrayList<PlanToWatch> movies){
        moviesRef = db.collection("Movies");
        displayToWatchMovies(movies);
        for(PlanToWatch m : movies){
            DocumentReference query = moviesRef.document(m.getMovieId());
            query.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot document) {
                    String title = (String) document.getData().get("Title");
                    m.setMovieTitle(title);
                    if(planToWatchAdapter != null){
                        planToWatchAdapter.reload();
                    }
                }
            });

        }
    }

    public void displayToWatchMovies(ArrayList<PlanToWatch> movies){
        planToWatchAdapter = new PlanToWatchAdapter(movies);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lvPlanToWatch.setLayoutManager(layoutManager);
        lvPlanToWatch.setAdapter(planToWatchAdapter);

        SwipeToDelete swipeToDelete = new SwipeToDelete(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getLayoutPosition();
                PlanToWatch planToWatch = planToWatchAdapter.getData().get(position);
                DocumentReference docRef = db.collection("Watchlist").document(planToWatch.getId());
                docRef.delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                planToWatchAdapter.removeItem(position);
                                planToWatchAdapter.notifyItemRemoved(position);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Oops! something went wrong", Toast.LENGTH_LONG).show();
                            }
                        });


            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDelete);
        itemTouchHelper.attachToRecyclerView(lvPlanToWatch);
    }
}