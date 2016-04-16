package com.wordpress.ilyaps.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.wordpress.ilyaps.db.CoorDAO;

import java.util.Date;

import com.wordpress.ilyaps.message.*;
import com.wordpress.ilyaps.model.Coor;
import com.wordpress.ilyaps.model.CoorMysql;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ReceivingDataServlet extends HttpServlet {
    static final Logger LOGGER = LogManager.getLogger(ReceivingDataServlet.class);

    private CoorDAO coorDAO;

    public ReceivingDataServlet(CoorDAO coorDAO) {
        this.coorDAO = coorDAO;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();

        String jsonResponseMsg;

        try {
            DataMsg jsonDataMsg = gson.fromJson(req.getReader(), DataMsg.class);
            LOGGER.info("(doPost) jsonDataMsg = " + jsonDataMsg.getId());
            LOGGER.info("(doPost) jsonDataMsg = " + jsonDataMsg.getCoors());

            List<CoorMysql> coors =  jsonDataMsg.getCoors();
            int count = putDataToDB(coors);

            jsonResponseMsg = new Gson().toJson(new StatusMsg(Status.OK, count));
        } catch (JsonSyntaxException e) {
            LOGGER.warn("JsonSyntaxException");
            jsonResponseMsg = new Gson().toJson(new StatusMsg(Status.ERROR));
        }

        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().write(jsonResponseMsg);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String jsonResponseMsg;

        try {
            CoorMysql coor = new CoorMysql();

            coor.setId(new Integer(req.getParameter("id")));
            coor.setLongitude(new Integer(req.getParameter("longitude")));
            coor.setLatitude(new Integer(req.getParameter("latitude")));
            coor.setDate(new Date());

            LOGGER.info("(doGet) get query with id = " + coor);

            int count = putCoorToDB(coor);

            jsonResponseMsg = new Gson().toJson(new StatusMsg(Status.OK, count));
        } catch (NumberFormatException e) {
            LOGGER.warn("NumberFormatException");
            jsonResponseMsg = new Gson().toJson(new StatusMsg(Status.ERROR, 0));
        }

        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().write(jsonResponseMsg);
    }

    private int putDataToDB(List<CoorMysql> coors) {
        int count = 0;
        for (Coor coor : coors) {
            count += putCoorToDB(coor);
        }
        return count;
    }

    private int putCoorToDB(Coor coor) {
        try {
            return coorDAO.insert(coor);
        } catch (SQLException e) {
            LOGGER.warn("putCoorToDB", e);
            e.printStackTrace();
            return 0;
        }
    }
}
