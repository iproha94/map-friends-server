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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilyaps on 11.04.16.
 */
public class GetCoorServlet extends HttpServlet {
    private CoorDAO coorDAO;

    public GetCoorServlet(CoorDAO coorDAO) {
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

        int id = new Integer(req.getParameter("id"));

        List<Coor> coors = new ArrayList<>();
        try {
            coors = coorDAO.read(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Coor coor : coors) {
            pw.println(coor);
        }

    }
}
