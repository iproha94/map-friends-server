package com.wordpress.ilyaps.message;

import com.wordpress.ilyaps.model.CoorMysql;

import java.util.ArrayList;
import java.util.List;

public class  DataMsg {
    long id;
    List<CoorMysql> coors;

    public DataMsg(long id, List<CoorMysql> coors) {
        this.id = id;
        this.coors = coors;
    }

    public DataMsg() {
        id = 0;
        coors = new ArrayList<>();
    }

    public List<CoorMysql> getCoors() {
        return coors;
    }

    public void setCoors(List<CoorMysql> coors) {
        this.coors = coors;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
