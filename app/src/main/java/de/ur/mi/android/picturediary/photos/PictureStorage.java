package de.ur.mi.android.picturediary.photos;

import android.app.Activity;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.concurrent.Executors;

import de.ur.mi.android.picturediary.photos.database.PictureDatabase;

public class PictureStorage {

    private static final String PHOTO_DATABASE = "hair-diary-database";


    private final PictureDatabase db;
    private final Activity context;

    public PictureStorage(Activity context) {
        this.context = context;
        db = Room.databaseBuilder(context, PictureDatabase.class, PHOTO_DATABASE).build();

    }

    public void store(Picture picture, OnPictureStoredListener listener) {
        StorePictureTask task = new StorePictureTask(db, picture, listener);
        Executors.newSingleThreadExecutor().submit(task);
    }

    public void retrieveAll(OnPictureRetrievedListener listener) {
        RetrieveAllPicturesTask task = new RetrieveAllPicturesTask(db, listener);
        Executors.newSingleThreadExecutor().submit(task);
    }

    private class StorePictureTask implements Runnable {

        private final PictureDatabase db;
        private final Picture picture;
        private final OnPictureStoredListener listener;

        public StorePictureTask(PictureDatabase db, Picture picture, OnPictureStoredListener listener) {
            this.db = db;
            this.picture = picture;
            this.listener = listener;
        }

        @Override
        public void run() {
            db.pictures().insertAll(picture);
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listener.onResult(picture);
                }
            });
        }
    }

    private class RetrieveAllPicturesTask implements Runnable {

        private final PictureDatabase db;
        private final OnPictureRetrievedListener listener;

        public RetrieveAllPicturesTask(PictureDatabase db, OnPictureRetrievedListener listener) {
            this.db = db;
            this.listener = listener;
        }

        @Override
        public void run() {
            final ArrayList<Picture> pictures = (ArrayList<Picture>) db.pictures().getAll();
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listener.onResult(pictures);
                }
            });
        }
    }
}
