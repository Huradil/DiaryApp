package com.example.diaryapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM Note")
    List<Note> getAll();

    @Query("SELECT * FROM Note")
    LiveData<List<Note>> getAllLiveData();

    @Query("SELECT * FROM Note WHERE id IN (:noteIds)")
    List<Note> loadAllByIds(int[] noteIds);

    @Query("SELECT * FROM Note WHERE id = :id LIMIT 1")
    Note findById(int id);

    @Query("SELECT * FROM Note WHERE text LIKE '%' || :keyword || '%' OR title LIKE '%' || :keyword || '%'")
    LiveData<List<Note>> searchNotesByKeyword(String keyword);

    @Query("SELECT * FROM Note WHERE timestamp = :fromTimestamp")
    LiveData<List<Note>> filterNotesByTimestamp(long fromTimestamp);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

}
