package com.wordpress.ilyaps.model;

import java.sql.Timestamp;
import java.util.Date;

public class CoorMysql extends Coor {
    public <T> T getDataBaseDate() {
        return (T) new Timestamp(getDate().getTime());
    }

    public <T> void setDataBaseDate(T date) {
        setDate(new Date(((Timestamp)date).getTime()));
    }

}
