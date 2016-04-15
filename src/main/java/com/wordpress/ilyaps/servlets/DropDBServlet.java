package com.wordpress.ilyaps.servlets;

import com.wordpress.ilyaps.db.CoorDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DropDBServlet  extends HttpServlet {
    static final Logger LOGGER = LogManager.getLogger(DropDBServlet.class);

    private CoorDAO coorDAO;

    public DropDBServlet(CoorDAO coorDAO) {
        this.coorDAO = coorDAO;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            coorDAO.dropTable();
        } catch (SQLException e) {
            LOGGER.warn("dropTable", e);
            e.printStackTrace();
        }

        try {
            coorDAO.createTable();
        } catch (SQLException e) {
            LOGGER.warn("dropTable", e);
            e.printStackTrace();
        }
    }
}
