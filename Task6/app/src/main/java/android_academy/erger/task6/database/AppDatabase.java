package android_academy.erger.task6.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android_academy.erger.task6.entities.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase singleton;
    private static final String DATABASE_NAME = "NotesDb.db";

    public abstract NoteDao noteDao();

    public static AppDatabase getInstance(Context context) {
        if (singleton == null) {
            singleton = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        }
        return singleton;
    }

    public static void destroyInstance() {
        singleton = null;
    }
}
