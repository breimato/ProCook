package com.example.nuevotfg.Model;

public class Result {

    public String number;
    public int id;
    public String title;
    public String image;
    public String imageType;

    public Result(int id, String title, String image, String imageType) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.imageType = imageType;
    }
    public String getNumber() {
        String[] urlPartes = image.split("/");
        return urlPartes[urlPartes.length -1];
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
