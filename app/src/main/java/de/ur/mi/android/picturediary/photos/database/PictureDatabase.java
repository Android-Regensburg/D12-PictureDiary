package de.ur.mi.android.picturediary.photos.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import de.ur.mi.android.picturediary.photos.Picture;

@Database(entities = {Picture.class}, version = 1)
@TypeConverters(PictureConverters.class)
public abstract class PictureDatabase extends RoomDatabase {

    public abstract PictureDao pictures();

}
