package de.ur.mi.android.picturediary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import de.ur.mi.android.picturediary.camera.CameraHelper;
import de.ur.mi.android.picturediary.camera.CameraHelperListener;
import de.ur.mi.android.picturediary.photos.OnPictureRetrievedListener;
import de.ur.mi.android.picturediary.photos.OnPictureStoredListener;
import de.ur.mi.android.picturediary.photos.Picture;
import de.ur.mi.android.picturediary.photos.PictureStorage;
import de.ur.mi.android.picturediary.photos.ui.PictureGridAdapter;

public class MainActivity extends AppCompatActivity implements CameraHelperListener {

    private CameraHelper camera;
    private PictureStorage storage;
    private PictureGridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHelper();
        initUI();
    }

    private void initHelper() {
        camera = new CameraHelper(this);
        camera.setListener(this);
        storage = new PictureStorage(this);
    }

    private void initUI() {
        setContentView(R.layout.activity_main);
        ImageButton takeImageButton = findViewById(R.id.take_photo_button);
        takeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTakeImageButtonClicked();
            }
        });
        RecyclerView imageGridView = findViewById(R.id.image_grid);
        gridAdapter = new PictureGridAdapter();
        imageGridView.setAdapter(gridAdapter);
        //imageGridView.setLayoutManager(new GridLayoutManager(this, 4));
        storage.retrieveAll(new OnPictureRetrievedListener() {
            @Override
            public void onResult(ArrayList<Picture> pictures) {
                gridAdapter.add(pictures);
            }
        });
    }

    private void onTakeImageButtonClicked() {
        Log.d("HairDiary", "Click on Camera Button");
        camera.dispatchTakePictureIntent();
    }

    private void onPlayVideoButtonClicked() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CameraHelper.REQUEST_TAKE_PHOTO) {
            if(resultCode == Activity.RESULT_OK) {
                camera.handleTakePictureIntentResult(data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPictureTaken(Picture picture) {
        Log.d("HairDiary", "On Picture Taken");
        storage.store(picture, new OnPictureStoredListener() {
            @Override
            public void onResult(Picture picture) {
                gridAdapter.add(picture);
                Log.d("HairDiary", "Image Stored in Database");
            }
        });
    }
}
