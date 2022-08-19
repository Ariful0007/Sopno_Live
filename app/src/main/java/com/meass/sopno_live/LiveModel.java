package com.meass.sopno_live;

public class LiveModel {
    String uderid,name,image,time,meeting_id;

    public LiveModel() {
    }

    public String getUderid() {
        return uderid;
    }

    public void setUderid(String uderid) {
        this.uderid = uderid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(String meeting_id) {
        this.meeting_id = meeting_id;
    }

    public LiveModel(String uderid, String name, String image, String time, String meeting_id) {
        this.uderid = uderid;
        this.name = name;
        this.image = image;
        this.time = time;
        this.meeting_id = meeting_id;
    }
}
