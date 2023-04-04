package com.example.moviium;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

public class BaseActivity extends AppCompatActivity {

    ImageButton logout;
    public FirebaseAuth mAuth;


    @Override
    protected void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
            finish();
        }

        logout = findViewById(R.id.imgBtnLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(BaseActivity.this, login.class));
            }
        });


        String url = "";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }
}
