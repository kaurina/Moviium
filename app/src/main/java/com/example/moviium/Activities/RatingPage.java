package com.example.moviium.Activities;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviium.CustomAdapters.CommentAdapter;
import com.example.moviium.HelperClass;
import com.example.moviium.Helpers.ListViewUpdate;
import com.example.moviium.Models.Comment;
import com.example.moviium.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RatingPage extends BaseActivity {
    Button btnAddRating;
    ImageButton btnHome, btnFav, btnToWatch;
    TextView title, storyLine, genres, actors, dbRating;
    Button btnComment;
    EditText editTextComments;
    ImageView movieImg;
    FirebaseFirestore db = FirebaseFirestore.getInstance(); // database
    CollectionReference moviesRef; // tables
    CollectionReference watchlistRef; // tables
    CollectionReference commentsRef; // tables
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    String email = user.getEmail();
    String watchlistDocumentId = "";
    String movieId;
    ListView lvComments;
    ArrayList<Comment> comments;
    RatingBar ratingBar;
    String ratingDocId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_page);

        btnHome = findViewById(R.id.imgBtnHomeRP);
        btnFav = findViewById(R.id.imgBtnStarRP);
        title = findViewById(R.id.txtRatingRP);
        movieImg = findViewById(R.id.imgMovieRP);
        storyLine = findViewById(R.id.txtStoryline);
        genres = findViewById(R.id.txtGenreRP);
        actors = findViewById(R.id.txtActorsRP);
        dbRating = findViewById(R.id.txtRating);
        ratingBar = findViewById(R.id.ratingBar);
        btnToWatch = findViewById(R.id.imgBtnWatchList);
        lvComments = findViewById(R.id.lvComments);
        btnComment = findViewById(R.id.btnComment);
        editTextComments = findViewById(R.id.editTextComments);
        comments = new ArrayList<>();

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setBounds(0, 0, 50, 50);


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
                    movieImg.setImageDrawable(HelperClass.drawableFromUrl(imageUrl));
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
        });

        watchlistRef = db.collection("Watchlist");

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

                    db.collection("Watchlist").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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

        loadComments();

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = editTextComments.getText().toString();
                if(!comment.isEmpty()){
                    Map<String,Object> data = new HashMap<>();
                    Timestamp timestamp = Timestamp.now();
                    data.put("Movie_ID",movieId);
                    data.put("User_ID",uid);
                    data.put("User_Email",email);
                    data.put("Datetime", timestamp);
                    data.put("Comment", comment);

                    db.collection("Comments").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            editTextComments.setText("");
                            loadComments();
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

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Map<String,Object> data = new HashMap<>();
                data.put("Movie_ID",movieId);
                data.put("User_ID",uid);
                data.put("Rating",v * 2);

                if(ratingDocId.isEmpty()){
                    db.collection("Ratings").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            ratingDocId = documentReference.getId();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RatingPage.this, "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    db.collection("Ratings").document(ratingDocId).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    });

                }


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

        Query queryRating = db.collection("Ratings").whereEqualTo("User_ID", uid).whereEqualTo("Movie_ID", movieId);

        queryRating.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            int count = querySnapshot.size();
                            if(count > 0){
                                for (QueryDocumentSnapshot document : querySnapshot) {
                                    ratingDocId = document.getId();
                                    double rating = ((double) document.getData().get("Rating"))/2;
                                    ratingBar.setRating((float) rating);
                                }
                            } else {
                                ratingDocId = "";
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

    public void loadComments(){
        commentsRef = db.collection("Comments");
        Query queryComments = commentsRef.whereEqualTo("Movie_ID", movieId).orderBy("Datetime", Query.Direction.DESCENDING);

        queryComments.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryListOfMovies) {
                comments = new ArrayList<>();
                //iterate through list
                for (QueryDocumentSnapshot document : queryListOfMovies) {
                    Comment comment; // Save the fields
                    String commentText = (String) document.getData().get("Comment");
                    Timestamp datetime = (Timestamp) document.getTimestamp("Datetime");
                    long milliseconds = datetime.getSeconds() * 1000;
                    Date date = new Date(milliseconds);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                    String formattedDate = sdf.format(date);
                    String userEmail = (String) document.getData().get("User_Email");

                    //using constructor from movie class
                    comment = new Comment(commentText, formattedDate, userEmail);

                    comments.add(comment);
                }

                // customAdapter
                CommentAdapter adapter = new CommentAdapter(getApplicationContext(), comments);
                lvComments.setAdapter(adapter);
                ListViewUpdate.getListViewSize(lvComments);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RatingPage.this, e.toString(), Toast.LENGTH_LONG).show();}
        });
    }

}