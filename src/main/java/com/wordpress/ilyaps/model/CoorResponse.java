package com.wordpress.ilyaps.model;

import java.util.List;

/**
 * Created by ilyaps on 15.04.16.
 */
public class CoorResponse {
    int status;
    List<Coor> coors;
    int id;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Coor> getCoors() {
        return coors;
    }

    public void setCoors(List<Coor> coors) {
        this.coors = coors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
