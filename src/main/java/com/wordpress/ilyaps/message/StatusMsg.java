package com.wordpress.ilyaps.message;

public class StatusMsg {
    private int status = Status.NOT_IMPLEMENTED;
    private int count;

    public StatusMsg(int status) {
        this.status = status;
        this.count = 0;
    }

    public int getCount() {
        return count;
    }

    public void setCountAdd(int countAdd) {
        this.count = countAdd;
    }

    public StatusMsg(int status, int count) {
        this.status = status;
        this.count = count;
    }

    public int getStatus() {
        return status;
    }
}
