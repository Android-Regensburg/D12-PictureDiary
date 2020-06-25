package de.ur.mi.android.picturediary.camera;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.ur.mi.android.picturediary.photos.Picture;

public class CameraHelper {

    public static final int REQUEST_TAKE_PHOTO = 1;

    private Activity context;
    private CameraHelperListener listener;
    private String currentPhotoPath;

    public CameraHelper(Activity context) {
        this.context = context;
    }

    public void setListener(CameraHelperListener listener) {
        this.listener = listener;
    }

    // From: https://developer.android.com/training/camera/photobasics
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context, "de.ur.mi.android.picturediary", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                context.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void handleTakePictureIntentResult(Intent intent) {
        File imageFile = new File(currentPhotoPath);
        if(listener != null) {
            Picture picture = Picture.createFromFile(imageFile);
            listener.onPictureTaken(picture);
        }
    }

    // From: https://developer.android.com/training/camera/photobasics
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PictureDiary_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
