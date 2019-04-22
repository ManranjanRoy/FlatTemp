package com.example.flattemp.Model;

public class GalleryCatagory {
    String id,catagory,icon;

    public GalleryCatagory() {
    }

    public GalleryCatagory(String id, String catagory, String icon) {
        this.id = id;
        this.catagory = catagory;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
