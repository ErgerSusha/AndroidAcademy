package android_academy.erger.task6.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import java.util.List;
import android_academy.erger.task6.entities.Note;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM Note")
    List<Note> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Delete
    void delete(Note note);

/**
 * These methods were used for testing
 *
    @Query("SELECT * FROM Note WHERE id = :id")
    Note getNoteById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Note... notes);
*/
}