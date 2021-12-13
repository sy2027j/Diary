package com.example.diary;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Diary {
    @NonNull
    @PrimaryKey
    public String date;

    public String title;

    public String content;

    public String mood;

    public Diary(String date, String title, String content, String mood){
        this.date=date;
        this.title=title;
        this.content=content;
        this.mood=mood;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String emotion) {
        this.mood = emotion;
    }
}
