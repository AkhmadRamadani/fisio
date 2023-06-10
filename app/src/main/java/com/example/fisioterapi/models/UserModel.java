package com.example.fisioterapi.models;

public class UserModel {

    String uid;
    String password;
    String umur;
    String role;
    String gender;
    String phone;
    String name;
    String email;
    String alamat;
    String hospitalId;


    String spreadSheetUrl;

    public UserModel(String uid, String password, String umur, String role, String gender, String phone, String name, String email, String alamat, String hospitalId) {
        this.uid = uid;
        this.password = password;
        this.umur = umur;
        this.role = role;
        this.gender = gender;
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.alamat = alamat;
        this.hospitalId = hospitalId;
    }
    public UserModel(String uid, String password, String umur, String role, String gender, String phone, String name, String email, String alamat, String hospitalId, String spreadSheetUrl) {
        this.uid = uid;
        this.password = password;
        this.umur = umur;
        this.role = role;
        this.gender = gender;
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.alamat = alamat;
        this.hospitalId = hospitalId;
        this.spreadSheetUrl = spreadSheetUrl;
    }

    public String getSpreadSheetUrl() {
        return spreadSheetUrl;
    }

    public void setSpreadSheetUrl(String spreadSheetUrl) {
        this.spreadSheetUrl = spreadSheetUrl;
    }

    public String getUid() {
        return uid;
    }

    public String getPassword() {
        return password;
    }

    public String getUmur() {
        return umur;
    }

    public String getRole() {
        return role;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getHospitalId() {
        return hospitalId;
    }

}
