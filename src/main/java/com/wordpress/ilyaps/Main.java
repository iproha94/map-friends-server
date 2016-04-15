package com.wordpress.ilyaps;

import com.wordpress.ilyaps.db.CoorDAO;
import com.wordpress.ilyaps.db.DBService;
import com.wordpress.ilyaps.helpers.PropertiesHelper;
import com.wordpress.ilyaps.servlets.DropDBServlet;
import com.wordpress.ilyaps.servlets.SendingDataServlet;
import com.wordpress.ilyaps.servlets.ReceivingDataServlet;
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

        Servlet receivingDataServlet = new ReceivingDataServlet(coorDAO);
        Servlet sendingDataServlet = new SendingDataServlet(coorDAO);
        Servlet dropDBServlet = new DropDBServlet(coorDAO);

        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(receivingDataServlet), propertiesServer.getProperty("data_to_server"));
        context.addServlet(new ServletHolder(sendingDataServlet), propertiesServer.getProperty("data_from_server"));
        context.addServlet(new ServletHolder(dropDBServlet), propertiesServer.getProperty("drop_db"));

        final ResourceHandler resourceHandler = new ResourceHandler();

        final HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});

        final Server server = new Server(new Integer(propertiesServer.getProperty("port")));
        server.setHandler(handlers);

        try {
            server.start();
            LOGGER.info("Server is started");
        } catch (Exception e) {
            LOGGER.error("Server isn't started", e);
        }
    }
}
