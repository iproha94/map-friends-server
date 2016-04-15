package com.wordpress.ilyaps.message;

import com.wordpress.ilyaps.model.Coor;

import java.util.List;

public class DataAndStatusMsg extends DataMsg {
    private int status = Status.ERROR;

    public DataAndStatusMsg(int id, List<Coor> coors, int status) {
        super(id, coors);
        this.status = status;
    }

    public DataAndStatusMsg(int status) {
        super();
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
