package de.ur.mi.android.picturediary.photos;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
public class Picture {

    @PrimaryKey(autoGenerate = true)
    public Integer uid;
    public final Date date;
    public final String filePath;

    public Picture(Date date, String filePath) {
        this.date = date;
        this.filePath = filePath;
    }

    public int getAgeInDays() {
        Date now = new Date();
        long deltaTimeInMS = Math.abs(now.getTime() - date.getTime());
        int deltaTimeInDays = (int) TimeUnit.DAYS.convert(deltaTimeInMS, TimeUnit.MILLISECONDS);
        return deltaTimeInDays;
    }

    public static Picture createFromFile(File file) {
        try {
            FileTime creationTime = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
            Date creationDate = new Date( creationTime.toMillis() );
            return new Picture(creationDate, file.getAbsolutePath());
        } catch (IOException e) {
            return null;
        }
    }
}
