package com.example.casa_jip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private GalleryAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ImageData> ImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        ImageList = new ArrayList<>();
        ImageList.add(new ImageData(R.drawable.alps));
        ImageList.add(new ImageData(R.drawable.avocados));
        ImageList.add(new ImageData(R.drawable.bentley));
        ImageList.add(new ImageData(R.drawable.bibimbop));
        ImageList.add(new ImageData(R.drawable.camerajpg));
        ImageList.add(new ImageData(R.drawable.iphone11));
        ImageList.add(new ImageData(R.drawable.kimchi));
        ImageList.add(new ImageData(R.drawable.orange));
        ImageList.add(new ImageData(R.drawable.programmer));
        ImageList.add(new ImageData(R.drawable.travel));
        ImageList.add(new ImageData(R.drawable.vietnam));


        mRecyclerView = findViewById(R.id.gallery_recycler_view);
        mAdapter = new GalleryAdapter(this,ImageList);
        mLayoutManager = new GridLayoutManager(this,3);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

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
                        startActivity(new Intent(getApplicationContext(),TaskActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext(),ChatActivity.class));
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