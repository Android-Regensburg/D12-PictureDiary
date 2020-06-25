package de.ur.mi.android.picturediary.photos.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import de.ur.mi.android.picturediary.photos.Picture;

@Dao
public interface PictureDao {

    @Query("SELECT * FROM Picture")
    List<Picture> getAll();

    @Insert
    void insertAll(Picture... bookmarks);

    @Delete
    void deleteAll(Picture... bookmarks);

}
