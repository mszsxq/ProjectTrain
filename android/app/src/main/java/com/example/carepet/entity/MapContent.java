package com.example.carepet.entity;

import java.io.Serializable;

public class MapContent implements Serializable {
    private String name;
    private String  touxiang;
    private String  bimg;
    private FindTable findTable;

    public String getBimg() {
        return bimg;
    }

    public void setBimg(String bimg) {
        this.bimg = bimg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public FindTable getFindTable() {
        return findTable;
    }

    public void setFindTable(FindTable findTable) {
        this.findTable = findTable;
    }

    @Override
    public String toString() {
        return "MapContent{" +
                "name='" + name + '\'' +
                ", touxiang='" + touxiang + '\'' +
                ", bimg='" + bimg + '\'' +
                ", findTable=" + findTable +
                '}';
    }
}
