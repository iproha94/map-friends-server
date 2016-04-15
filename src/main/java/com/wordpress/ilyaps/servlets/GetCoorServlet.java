package com.wordpress.ilyaps.servlets;

import com.wordpress.ilyaps.db.CoorDAO;
import com.wordpress.ilyaps.model.Coor;
import com.wordpress.ilyaps.model.CoorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetCoorServlet extends HttpServlet {
    static final Logger LOGGER = LogManager.getLogger(GetCoorServlet.class);
    private static final int OK = 200;
    private static final int ERROR = 404;

    private final CoorDAO coorDAO;

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
        resp.setContentType("application/json; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        String idStr = req.getParameter("id");
        LOGGER.info("get query with id = " + idStr);

        ObjectMapper mapper = new ObjectMapper();
        CoorResponse cr = new CoorResponse();

        Integer id;
        try {
            id = new Integer(idStr);
        } catch (NumberFormatException e) {
            cr.setStatus(ERROR);
            pw.print(mapper.writeValueAsString(cr));
            return;
        }

        List<Coor> coors;
        try {
            coors = coorDAO.read(id);
        } catch (SQLException e) {
            LOGGER.warn("SQLException", e);
            coors = new ArrayList<>();
        }

        cr.setCoors(coors);
        cr.setId(id);
        cr.setStatus(OK);

        pw.print(mapper.writeValueAsString(cr));
    }
}
