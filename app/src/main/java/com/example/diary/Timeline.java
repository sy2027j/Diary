package com.example.diary;

public class Timeline {

    private String year;
    private String month;
    private String day;
    private String title;
    private String content;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public Timeline(String year, String month, String day, String title, String content) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.title = title;
        this.content = content;
    }
}
