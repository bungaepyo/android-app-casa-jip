package com.example.casa_jip;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    private Context mContext;
    private List<ImageData> mImageList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ImageView_image_thumbnail;
        CardView cardView;

        public MyViewHolder(View view){
            super(view);

            ImageView_image_thumbnail = view.findViewById(R.id.image_thumbnail);
            cardView = view.findViewById(R.id.image_id);
        }

    }

    public GalleryAdapter(Context mContext, List<ImageData> mImageList) {
        this.mContext = mContext;
        this.mImageList = mImageList;
    }

    @NonNull
    @Override
    public GalleryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_gallery, parent, false);

        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.ImageView_image_thumbnail.setImageResource(mImageList.get(position).getThumbnail());

        //Click Listener
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, ImageActivity.class);

                //Passing data to ImageActivity
                intent.putExtra("Thumbnail", mImageList.get(position).getThumbnail());
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }




}
