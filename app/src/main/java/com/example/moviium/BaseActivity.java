package com.example.moviium;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

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

//        logout = findViewById(R.id.imgBtnLogout);
//
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAuth.signOut();
//                startActivity(new Intent(BaseActivity.this, login.class));
//            }
//        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar != null){


        setSupportActionBar(toolbar);
        toolbar.setBackground(new ColorDrawable(getResources().getColor(R.color.purple_700)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            mAuth.signOut();
            startActivity(new Intent(BaseActivity.this, login.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
