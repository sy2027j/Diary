package com.example.diary;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DiaryDao {
    @Query("SELECT * FROM Diary ORDER BY date ASC")
    List<Diary> getAll();

    @Query("SELECT * FROM diary WHERE date IN (:date)")
    Diary loadAllByDate(String date);

    @Query("UPDATE diary SET title=:title, content=:content, mood=:mood WHERE date=:date")
    void diaryUpdate(String title, String content, String mood, String date);

    @Query("DELETE FROM diary WHERE date=:date")
    void diaryDelete(String date);

    @Insert
    void insertAll(Diary diary);

    @Delete
    void delete(Diary diary);

    @Insert
    void insert(Diary diary);

    @Update
    void update(Diary diary);

    @Query("SELECT date FROM diary")
    List<Diary> datelist();
}