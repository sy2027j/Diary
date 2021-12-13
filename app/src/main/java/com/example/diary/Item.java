package com.example.diary;

import android.widget.ImageView;

public class Item {

    public String imgfile;

    public String getImgfile() {
        return imgfile;
    }

    public void setImgfile(String imgfile) {
        this.imgfile = imgfile;
    }

    public Item(String imgfile){
        this.imgfile=imgfile;
    }

}