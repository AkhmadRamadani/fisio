package com.example.fisioterapi.models;

public class HospitalModel {
    private String name, address, phone, email, id, longitude, latitude, gmapsUrl, gformUrl;

    public HospitalModel(String name, String address, String phone, String email, String id, String longitude, String latitude, String gmapsUrl, String gformUrl) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.gmapsUrl = gmapsUrl;
        this.gformUrl = gformUrl;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getGmapsUrl() {
        return gmapsUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setGmapsUrl(String gmapsUrl) {
        this.gmapsUrl = gmapsUrl;
    }

    public String getGformUrl() {
        return gformUrl;
    }

    public void setGformUrl(String gformUrl) {
        this.gformUrl = gformUrl;
    }
}
