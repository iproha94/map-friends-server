package com.wordpress.ilyaps.servlets;

import com.wordpress.ilyaps.db.CoorDAO;
import com.wordpress.ilyaps.model.Coor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class PushCoorServlet extends HttpServlet {
    private CoorDAO coorDAO;

    public PushCoorServlet(CoorDAO coorDAO) {
        this.coorDAO = coorDAO;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        Coor coor = new Coor();

        coor.setId(new Integer(req.getParameter("id")));
        coor.setLongitude(new Integer(req.getParameter("longitude")));
        coor.setLatitude(new Integer(req.getParameter("latitude")));
        coor.setDate(req.getParameter("date"));

//        java.text.SimpleDateFormat sdf =
//                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String currentTime = sdf.format(new Date());

        try {
            coorDAO.insert(coor);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        pw.println("you push coor " + req.getParameter("id"));
    }
}
