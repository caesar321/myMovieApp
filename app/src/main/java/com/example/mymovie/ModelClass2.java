package com.example.mymovie;

public class ModelClass2 {
    private String image_url,title;

    public ModelClass2() {

    }

    public ModelClass2(String image_url, String title, String mReleasedDate) {
        this.image_url = image_url;
        this.title = title;
        //  this.mReleasedDate = mReleasedDate;

    }


    public String getImage_url() {
        return image_url;
    }


    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitlee(String title) {
        this.title = title;
    }

}
