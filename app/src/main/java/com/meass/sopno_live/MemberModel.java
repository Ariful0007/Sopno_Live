package com.meass.sopno_live;

public class MemberModel {
    String name,location,whatsapp,image,number,email,pass,
            username,transcationpin,dob,profession,sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTranscationpin() {
        return transcationpin;
    }

    public void setTranscationpin(String transcationpin) {
        this.transcationpin = transcationpin;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public MemberModel(String name, String location, String whatsapp, String image, String number,
                       String email, String pass, String username, String transcationpin, String dob, String profession, String sex) {
        this.name = name;
        this.location = location;
        this.whatsapp = whatsapp;
        this.image = image;
        this.number = number;
        this.email = email;
        this.pass = pass;
        this.username = username;
        this.transcationpin = transcationpin;
        this.dob = dob;
        this.profession = profession;
        this.sex = sex;
    }

    public MemberModel() {
    }
}
