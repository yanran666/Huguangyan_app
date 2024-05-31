package com.adnroidstudy.huguangyan_app.entity;

public class CarouselItem {
    private int imageResId;
    private String description;

    public CarouselItem(int imageResId, String description) {
        this.imageResId = imageResId;
        this.description = description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getDescription() {
        return description;
    }
}
