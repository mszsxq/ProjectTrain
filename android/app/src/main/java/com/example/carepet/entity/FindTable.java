package com.example.carepet.entity;

public class FindTable {
    private int id;
    private int userid;
    private double longitude;
    private double latitude;
    private String imgjson;
    private String title;
    private String content;
    private String time;
    private String city;
    private String pettype;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public String getImgjson() {
        return imgjson;
    }
    public void setImgjson(String imgjson) {
        this.imgjson = imgjson;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getPettype() {
        return pettype;
    }
    public void setPettype(String pettype) {
        this.pettype = pettype;
    }
    @Override
    public String toString() {
        return "FindTable [id=" + id + ", userid=" + userid + ", longitude=" + longitude + ", latitude=" + latitude
                + ", imgjson=" + imgjson + ", title=" + title + ", content=" + content + ", time=" + time + ", city="
                + city + ", pettype=" + pettype + "]";
    }


}
