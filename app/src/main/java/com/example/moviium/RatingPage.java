package com.example.moviium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviium.CustomAdapters.HomePageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatingPage extends BaseActivity {

    ImageButton btnHome, btnFav, btnProfile, btnToWatch;
    TextView title, storyLine, genres, actors, dbRating, myRating;
    ImageView movieImg;
    FirebaseFirestore db = FirebaseFirestore.getInstance(); // database
    CollectionReference moviesRef; // tables
    CollectionReference watchlistRef; // tables
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    String watchlistDocumentId = "";
    String movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_page);

        btnHome = findViewById(R.id.imgBtnHomeRP);
        btnFav = findViewById(R.id.imgBtnStarRP);
        btnProfile = findViewById(R.id.imgBtnProfileRP);
        title = findViewById(R.id.txtRatingRP);
        movieImg = findViewById(R.id.imgMovieRP);
        storyLine = findViewById(R.id.txtStoryline);
        genres = findViewById(R.id.txtGenreRP);
        actors = findViewById(R.id.txtActorsRP);
        dbRating = findViewById(R.id.txtRating);
        myRating = findViewById(R.id.myRating);
        btnToWatch = findViewById(R.id.imgBtnWatchList);

        Intent intent = getIntent();
        movieId = intent.getStringExtra("id");

        moviesRef = db.collection("Movies");
        DocumentReference query = moviesRef.document(movieId);

        //title.setText(user.getUid());

        query.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot document) {
                //Movie movie = new Movie(); // Save the fields

                String imageUrl = (String) document.getData().get("Image");
                String titleFromDb = (String) document.getData().get("Title");
                List<String> actorsDb = (List<String>) document.getData().get("Actors");
                List<String> genresDb = (List<String>) document.getData().get("Genres");
                String storyLineDb = (String) document.getData().get("Storyline");
                String dbRatingFl = document.getData().get("Rating").toString();

                title.setText(titleFromDb);
                dbRating.setText(dbRatingFl);
                storyLine.setText(storyLineDb);

                String actorList = "";
                for(String string : actorsDb){
                    actorList += string + ", ";
                }
                actors.setText(actorList);

                String genreList = "";
                for(String string : genresDb){
                    genreList += string + ", ";
                }
                genres.setText(genreList);

                try{
                    movieImg.setImageDrawable(drawableFromUrl(imageUrl));
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
        });

        watchlistRef = db.collection("WatchList");

        btnToWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!watchlistDocumentId.isEmpty()){
                    DocumentReference docRef = watchlistRef.document(watchlistDocumentId);
                    docRef.delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    watchlistDocumentId = "";
                                    btnToWatch.setBackgroundResource(R.drawable.addimage);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle errors
                                }
                            });
                } else {
                    Map<String,Object> data = new HashMap<>();
                    data.put("Movie_ID",movieId);
                    data.put("User_ID",uid);

                    db.collection("WatchList").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(RatingPage.this, "Movie successfully added to Watch List", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RatingPage.this, MovieList.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RatingPage.this, "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    });
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

    @Override
    protected void onResume() {
        super.onResume();
        Query queryWatchlist = watchlistRef.whereEqualTo("User_ID", uid).whereEqualTo("Movie_ID", movieId);

        queryWatchlist.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            int count = querySnapshot.size();
                            if(count > 0){
                                btnToWatch.setBackgroundResource(R.drawable.removeimage);
                                for (QueryDocumentSnapshot document : querySnapshot) {
                                    watchlistDocumentId = document.getId();
                                }
                            } else {
                                watchlistDocumentId = "";
                                btnToWatch.setBackgroundResource(R.drawable.addimage);
                            }
                        } else {
                            // Handle errors
                        }
                    }
                });
    }

    public Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(Resources.getSystem(), x);
    }
}