package com.example.moviium.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;

import android.widget.PopupWindow;

import com.example.moviium.CustomAdapters.HomePageAdapter;
import com.example.moviium.Models.Movie;
import com.example.moviium.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BaseActivity implements HomePageAdapter.OnItemClickListener {

    ImageButton btnHome, btnStar;
    SearchView searchView;
    FirebaseFirestore db = FirebaseFirestore.getInstance(); // database
    CollectionReference moviesRef; // tables

    ArrayList<Movie> listOfMovies = new ArrayList<>();
    ListView lvTopMovies;
    PopupWindow popupWindowSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        lvTopMovies = findViewById(R.id.movieListView);
        searchView = findViewById(R.id.searchView);
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
                    String director = (String) document.getData().get("Director");

                    //using constructor from movie class
                    movie = new Movie(image, title, id, director);

                    listOfMovies.add(movie);
                }

                // customAdapter
                HomePageAdapter adapter = new HomePageAdapter(getApplicationContext(), listOfMovies, HomePage.this);
                lvTopMovies.setAdapter(adapter);
            }
        });



        btnHome = findViewById(R.id.imgBtnHomeHM);
        btnStar = findViewById(R.id.imgBtnStarHM);

        btnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToFav = new Intent(getApplicationContext(), MovieList.class);
                startActivity(intentToFav);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(popupWindowSearch != null){
                    popupWindowSearch.dismiss();
                }
                if(!newText.isEmpty()){
                    displaySearchResult(newText);
                }

                return false;
            }
        });

    }

    public void displaySearchResult(String q){
        List<String> result = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        Query query = db.collection("Movies");
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    String title = (String) document.getData().get("Title");
                    if(title.contains(q)){
                        result.add(title);
                        ids.add(document.getId());
                    }
                }

                if (!result.isEmpty()) {
                    ListView listView = new ListView(getApplicationContext());

                    ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,result);
                    listView.setAdapter(adapter);
                    listView.setBackgroundColor(Color.rgb( 240, 240, 240));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            clickedOnSearchedMovie(ids.get(position));
                        }
                    });

                    popupWindowSearch = new PopupWindow(listView, searchView.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindowSearch.showAsDropDown(searchView);
                }
            }
        });
    }


    //comes from interface created in adapter
    @Override
    public void onMovieClick(String id) {
        Intent intent = new Intent(this, RatingPage.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void clickedOnSearchedMovie(String id){
        onMovieClick(id);
    }

}