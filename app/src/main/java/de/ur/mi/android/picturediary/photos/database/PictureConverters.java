package de.ur.mi.android.picturediary.photos.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class PictureConverters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date.getTime();
    }

}
