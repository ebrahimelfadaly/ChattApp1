package com.example.chatapp;

public class Users {
   private   String Uid,name,about,imageprofile,phonenumber,status;
   long time;

    public Users() {
    }

    public Users(String uid, String name, String about, String imageprofile, String phonenumber) {
        Uid = uid;
        this.name = name;
        this.about = about;
        this.imageprofile = imageprofile;
        this.phonenumber = phonenumber;

    }


    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImageprofile() {
        return imageprofile;
    }

    public void setImageprofile(String imageprofile) {
        this.imageprofile = imageprofile;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
