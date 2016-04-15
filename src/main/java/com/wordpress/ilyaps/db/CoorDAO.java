package com.wordpress.ilyaps.db;

import com.wordpress.ilyaps.model.Coor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilyaps on 13.04.16.
 */
public class CoorDAO {
    static final Logger LOGGER = LogManager.getLogger(CoorDAO.class);
    private final DBService service;

    public CoorDAO(DBService service) {
        this.service = service;
    }

    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS coor ( " +
                "  id INT NOT NULL, " +
                "  longitude FLOAT(10) NOT NULL, " +
                "  latitude FLOAT(10) NOT NULL, " +
                "  date DATETIME NOT NULL " +
                ") " +
                "ENGINE = InnoDB;";

        LOGGER.info("Run query: " + query);

        Connection connection = service.openConnection();
        DBExecutor.execUpdate(connection, query);
        connection.close();
    }

    public int insert(Coor coor) throws SQLException {
        String query = "insert into coor (id, longitude, latitude, date) values ( " +
                + coor.getId() + " ,  "
                + coor.getLatitude() + " ,  "
                + coor.getLongitude() + " ,  "
                + " '" + coor.getDate() + "' " +
                " )";

        LOGGER.info("Run query: " + query);

        Connection connection = service.openConnection();
        int num = DBExecutor.execUpdate(connection, query);
        connection.close();

        return num;
    }

    public List<Coor> read(int id) throws SQLException {
        List<Coor> list = new ArrayList<>();
        String query = "select * from coor where id = " + id + " ;";

        LOGGER.info("Run query: " + query);

        Connection connection = service.openConnection();

        DBExecutor.execQuery(connection, query,
                result -> {
                    while (result.next()) {
                        Coor coor = new Coor();
                        coor.setDate(result.getString("date"));
                        coor.setId(result.getInt("id"));
                        coor.setLatitude(result.getDouble("latitude"));
                        coor.setLongitude(result.getDouble("longitude"));
                        list.add(coor);
                    }
                    return null;
                });

        connection.close();
        return list;
    }
}
