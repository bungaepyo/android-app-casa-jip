package com.example.casa_jip.gallery;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.casa_jip.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private GalleryAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ImageData> ImageList;
    private StorageReference storageRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ){
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstancesState){
        //Upload Button OnClickListener
        FloatingActionButton fab = getView().findViewById(R.id.button_upload);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Button for uploading images", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ImageList = new ArrayList<>();

        mRecyclerView = getView().findViewById(R.id.gallery_recycler_view);
        mAdapter = new GalleryAdapter(ImageList, getActivity());
        mLayoutManager = new GridLayoutManager(getActivity(),3);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        //Firebase Cloud Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://casajip-ddbc9.appspot.com/");

        storageRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference prefix : listResult.getPrefixes()) {
                            // All the prefixes under listRef.
                            // You may call listAll() recursively on them.
                            Log.d("Prefix", prefix.toString());
                        }

                        for (StorageReference item : listResult.getItems()) {
                            // All the items under listRef.
                            Log.d("Item", item.toString());
                            Task<Uri> itemUrl = item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String stringUrl = uri.toString();
                                    ImageData itemImage = new ImageData(stringUrl);
                                    mAdapter.addImage(itemImage);
                                }
                            });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });

    }

}