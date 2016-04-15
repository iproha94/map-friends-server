package com.wordpress.ilyaps.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.wordpress.ilyaps.db.CoorDAO;
import com.wordpress.ilyaps.message.*;
import com.wordpress.ilyaps.model.Coor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SendingDataServlet extends HttpServlet {
    static final Logger LOGGER = LogManager.getLogger(SendingDataServlet.class);

    private final CoorDAO coorDAO;

    public SendingDataServlet(CoorDAO coorDAO) {
        this.coorDAO = coorDAO;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();

        String jsonResponseMsg;

        try {
            RequestDataMsg jsonRequestDataMsg = gson.fromJson(req.getReader(), RequestDataMsg.class);

            int id = jsonRequestDataMsg.getId();
            LOGGER.info("(doPost)get query with id = " + id);

            List coors = takeDataFromDB(id);
            jsonResponseMsg = new Gson().toJson(new DataAndStatusMsg(id, coors, Status.OK));
        } catch (JsonSyntaxException e) {
            LOGGER.warn("JsonSyntaxException");
            jsonResponseMsg = new Gson().toJson(new DataAndStatusMsg(Status.ERROR));
        }

        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().write(jsonResponseMsg);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String jsonResponseMsg;

        try {
            int id = new Integer(req.getParameter("id"));
            LOGGER.info("(doGet) get query with id = " + id);

            List coors = takeDataFromDB(id);
            jsonResponseMsg = new Gson().toJson(new DataAndStatusMsg(id, coors, Status.OK));
        } catch (NumberFormatException e) {
            LOGGER.warn("NumberFormatException for req.getParameter(\"id\")");
            jsonResponseMsg = new Gson().toJson(new DataAndStatusMsg(Status.ERROR));
        }

        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().write(jsonResponseMsg);
    }

    private List<Coor> takeDataFromDB(int id) {
        try {
            return coorDAO.read(id);
        } catch (SQLException e) {
            LOGGER.warn("takeDataAndStatusMsg SQLException", e);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
