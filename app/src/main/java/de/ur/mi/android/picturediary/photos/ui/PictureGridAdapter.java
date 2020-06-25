package de.ur.mi.android.picturediary.photos.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import de.ur.mi.android.picturediary.R;
import de.ur.mi.android.picturediary.photos.Picture;

public class PictureGridAdapter extends RecyclerView.Adapter<PictureGridAdapter.PictureViewHolder> {

    private ArrayList<Picture> currentPictures;

    public PictureGridAdapter() {
        currentPictures = new ArrayList<>();
    }

    public void add(Picture picture) {
        currentPictures.add(picture);
        this.notifyDataSetChanged();
    }

    public void add(ArrayList<Picture> pictures) {
        currentPictures.addAll(pictures);
        Log.d("HairDiary", "Number of pictures: " + currentPictures.size());
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_item, parent, false);
        PictureViewHolder vh = new PictureViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
        Picture picture = currentPictures.get(position);
        File pictureFile = new File(picture.filePath);
        if(pictureFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath());
            holder.imageView.setImageBitmap(myBitmap);
            holder.ageView.setText(String.format("%s days", picture.getAgeInDays()));
        }
    }

    @Override
    public int getItemCount() {
        return currentPictures.size();
    }

    class PictureViewHolder extends RecyclerView.ViewHolder {

        public final ImageView imageView;
        public final TextView ageView;

        public PictureViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.image_view);
            ageView = v.findViewById(R.id.age_in_days_view);
        }


    }
}
