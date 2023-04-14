package com.example.mymovie;

public class ModelClass1 {
    private String mImage,mTitle,mReleasedDate,overView;

    public ModelClass1(String mImage, String mTitle, String mReleasedDate,String overView) {
        this.mImage = mImage;
        this.mTitle = mTitle;
        this.mReleasedDate = mReleasedDate;
        this.overView= overView;
    }

    public ModelClass1() {

    }

    public String getmImage() {
        return mImage;
    }
    public String getOverView() {
        return overView;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmReleasedDate() {
        return mReleasedDate;
    }

    public void setmReleasedDate(String mReleasedDate) {
        this.mReleasedDate = mReleasedDate;
    }
}
