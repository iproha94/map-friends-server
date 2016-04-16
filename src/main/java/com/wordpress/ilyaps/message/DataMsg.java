package com.wordpress.ilyaps.message;

import com.wordpress.ilyaps.model.Coor;

import java.util.ArrayList;
import java.util.List;

public class  DataMsg {
    long id;
    List<Coor> coors;

    public DataMsg(long id, List<Coor> coors) {
        this.id = id;
        this.coors = coors;
    }

    public DataMsg() {
        id = 0;
        coors = new ArrayList<>();
    }

    public List<Coor> getCoors() {
        return coors;
    }

    public void setCoors(List<Coor> coors) {
        this.coors = coors;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
