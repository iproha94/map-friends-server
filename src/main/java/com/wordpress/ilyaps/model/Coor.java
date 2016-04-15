package com.wordpress.ilyaps.model;

import java.sql.Timestamp;
import java.util.Date;

public class Coor {
    private long id;
    private double  longitude;
    private double  latitude;
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public Timestamp getMysqlDate() {
        return new Timestamp(date.getTime());
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMysqlDate(Timestamp date) {
        this.date = new Date(date.getTime());
    }

    @Override
    public String toString() {
        return "Coor{" +
                "id=" + id +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", date='" + date + '\'' +
                '}';
    }

}
