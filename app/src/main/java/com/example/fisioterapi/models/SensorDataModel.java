package com.example.fisioterapi.models;

public class SensorDataModel {
    private String id;
    private String heartBeat;
    private String sp02;
    private String sudut;
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeartBeat() {
        return heartBeat;
    }

    public void setHeartBeat(String heartBeat) {
        this.heartBeat = heartBeat;
    }

    public String getSp02() {
        return sp02;
    }

    public void setSp02(String sp02) {
        this.sp02 = sp02;
    }

    public String getSudut() {
        return sudut;
    }

    public void setSudut(String sudut) {
        this.sudut = sudut;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getOutflex1() {
        return outflex1;
    }

    public void setOutflex1(String outflex1) {
        this.outflex1 = outflex1;
    }

    public String getOutflex2() {
        return outflex2;
    }

    public void setOutflex2(String outflex2) {
        this.outflex2 = outflex2;
    }

    public String getOutflex3() {
        return outflex3;
    }

    public void setOutflex3(String outflex3) {
        this.outflex3 = outflex3;
    }

    public String getOutflex4() {
        return outflex4;
    }

    public void setOutflex4(String outflex4) {
        this.outflex4 = outflex4;
    }

    public String getOutflex5() {
        return outflex5;
    }

    public void setOutflex5(String outflex5) {
        this.outflex5 = outflex5;
    }

    private String date;
    private String patientId;
    private String outflex1;
    private String outflex2;
    private String outflex3;
    private String outflex4;
    private String outflex5;

    public SensorDataModel(String id, String heartBeat, String sp02, String sudut, String time, String date, String patientId, String outflex1, String outflex2, String outflex3, String outflex4, String outflex5) {
        this.id = id;
        this.heartBeat = heartBeat;
        this.sp02 = sp02;
        this.sudut = sudut;
        this.time = time;
        this.date = date;
        this.patientId = patientId;
        this.outflex1 = outflex1;
        this.outflex2 = outflex2;
        this.outflex3 = outflex3;
        this.outflex4 = outflex4;
        this.outflex5 = outflex5;
    }

}
