package com.example.casa_jip.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.casa_jip.R;
import com.example.casa_jip.chat.ChatMainActivity;
import com.example.casa_jip.task.TaskMainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GalleryMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_main);

        //Initialize
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Chat Selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_gallery);

        //ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_task:
                        startActivity(new Intent(getApplicationContext(), TaskMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_chat:
                        startActivity(new Intent(getApplicationContext(), ChatMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_gallery:
                        return true;

                }
                return false;
            }
        });
    }
}