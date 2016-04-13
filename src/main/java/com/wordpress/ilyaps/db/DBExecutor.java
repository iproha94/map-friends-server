package com.wordpress.ilyaps.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ilyap on 23.11.2015.
 */
public class DBExecutor {

    public static <T> T execQuery( Connection connection,
                            String query,
                            TResultHandler<T> handler)
            throws SQLException
    {
        T value;

        try (Statement stmt = connection.createStatement();
             ResultSet result = stmt.executeQuery(query)) {
                value = handler.handle(result);
            }
        return value;
    }

    public static int execUpdate(Connection connection, String update)
            throws SQLException
    {
        int count;
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(update);
            count = stmt.getUpdateCount();
        }
        return count;
    }

}