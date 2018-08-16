package com.example.student.homework1images;

public class ImageModel {

    private String http;
    private boolean isSaved = false;
    private String name;

    public ImageModel(String http, boolean isSaved,String name) {
        this.http = http;
        this.isSaved = isSaved;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHttp() {
        return http;
    }

    public void setHttp(String http) {
        this.http = http;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
