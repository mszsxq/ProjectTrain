package com.example.carepet.entity;

public class Community {
    private int id;
    private String title;
    //imgjson存储图片的连接 多个图片时 采用++进行间隔
    private String imgjson;
    private String pic;
    private String content;
    private int userId;
    private String time;
    private String city;
    private String tag;
    private int flag;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Community{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imgjson='" + imgjson + '\'' +
                ", pic='" + pic + '\'' +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", time='" + time + '\'' +
                ", city='" + city + '\'' +
                ", tag='" + tag + '\'' +
                ", flag=" + flag +
                '}';
    }
}
