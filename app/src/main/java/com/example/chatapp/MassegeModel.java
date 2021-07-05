package com.example.chatapp;

public class MassegeModel {
   private String uId,massege,sendrid;
   private long timestamp;
   private int feel=-1;

    public MassegeModel(String uId, String massege, long timestamp) {
        this.uId = uId;
        this.massege = massege;
        this.timestamp = timestamp;
    }

    public MassegeModel(String uId, String massege, String sendrid, long timestamp,int feel) {
        this.uId = uId;
        this.massege = massege;
        this.sendrid = sendrid;
        this.timestamp = timestamp;
        this.feel=feel;
    }


    public MassegeModel() {
    }

    public MassegeModel(String massege, long timestamp) {
        this.massege = massege;
        this.timestamp = timestamp;
    }

    public MassegeModel(String uId, String massege) {
        this.uId = uId;
        this.massege = massege;
    }

    public String getSendrid() {
        return sendrid;
    }

    public void setSendrid(String sendrid) {
        this.sendrid = sendrid;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMassege() {
        return massege;
    }

    public void setMassege(String massege) {
        this.massege = massege;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getFeel() {
        return feel;
    }

    public void setFeel(int feel) {
        this.feel = feel;
    }
}
