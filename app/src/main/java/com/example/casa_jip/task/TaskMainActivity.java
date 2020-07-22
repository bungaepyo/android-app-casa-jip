package com.example.casa_jip.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.casa_jip.R;
import com.example.casa_jip.chat.ChatMainActivity;
import com.example.casa_jip.gallery.GalleryMainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class TaskMainActivity extends AppCompatActivity {

    //Calendar View
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_main);

        //Initialize
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Chat Selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_task);

        //ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_task:
                        return true;
                    case R.id.navigation_chat:
                        startActivity(new Intent(getApplicationContext(), ChatMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_gallery:
                        startActivity(new Intent(getApplicationContext(), GalleryMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}