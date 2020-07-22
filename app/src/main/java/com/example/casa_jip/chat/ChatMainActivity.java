package com.example.casa_jip.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.casa_jip.R;
import com.example.casa_jip.gallery.GalleryMainActivity;
import com.example.casa_jip.task.TaskMainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChatMainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    //MyPage Drawer Toggle
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        //MyPage Drawer Toggle
        mDrawerLayout = (DrawerLayout) findViewById(R.id.chat_drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initialize
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Chat Selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_chat);

        //ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_task:
                        startActivity(new Intent(getApplicationContext(), TaskMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_chat:
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