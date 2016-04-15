package com.wordpress.ilyaps;

import com.wordpress.ilyaps.db.CoorDAO;
import com.wordpress.ilyaps.db.DBService;
import com.wordpress.ilyaps.helpers.PropertiesHelper;
import com.wordpress.ilyaps.servlets.GetCoorServlet;
import com.wordpress.ilyaps.servlets.PushCoorServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    static final Logger LOGGER = LogManager.getLogger(Main.class);

    private static final String PROPERTIES_FILE_DB = "cfg/db.properties";
    private static final String PROPERTIES_FILE_SERVER = "cfg/server.properties";

    public static void main(String[] args) {
        LOGGER.info("start of main of server");

        Properties propertiesDB = PropertiesHelper.getProperties(PROPERTIES_FILE_DB);
        Properties propertiesServer = PropertiesHelper.getProperties(PROPERTIES_FILE_SERVER);

        DBService dbService = new DBService(propertiesDB);
        CoorDAO coorDAO = new CoorDAO(dbService);
        try {
            coorDAO.createTable();
        } catch (SQLException e) {
            LOGGER.error("Error creating table Coor", e);
            System.out.close();
        }

        Servlet pushCoorServlet = new PushCoorServlet(coorDAO);
        Servlet getCoorServlet = new GetCoorServlet(coorDAO);

        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(pushCoorServlet), propertiesServer.getProperty("push_coor"));
        context.addServlet(new ServletHolder(getCoorServlet), propertiesServer.getProperty("get_coor"));

        final ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase("static");

        final Server server = new Server(new Integer(propertiesServer.getProperty("port")));
        final HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});
        server.setHandler(handlers);

        try {
            server.start();
            LOGGER.info("Server is started");
        } catch (Exception e) {
            LOGGER.error("Server isn't started", e);
        }
    }
}
