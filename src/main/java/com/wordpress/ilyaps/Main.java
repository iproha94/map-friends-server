package com.wordpress.ilyaps;

import com.wordpress.ilyaps.db.CoorDAO;
import com.wordpress.ilyaps.db.DBService;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by ilyaps on 11.04.16.
 */
public class Main {
    static final Logger LOGGER = LogManager.getLogger(Main.class);

    private static final String PROPERTIES_FILE_DB = "cfg/db.properties";
    private static final String PROPERTIES_FILE_SERVER = "cfg/server.properties";

    private static final List<String> LIST_PROPERIES_SERVER = new ArrayList<>(Arrays.asList(
            "host",
            "port",
            "get_coor",
            "push_coor")
    );

    private static final List<String> LIST_PROPERIES_DB = new ArrayList<>(Arrays.asList(
            "name_driver",
            "jdbc_url",
            "user_name",
            "user_password")
    );

    public static void main(String[] args) {
        LOGGER.info("start of activation of server");

        FileInputStream fisDB = null;
        FileInputStream fisServer = null;
        Properties propertiesDB = null;
        Properties propertiesServer = null;
        try {
            fisDB = new FileInputStream(PROPERTIES_FILE_DB);
            propertiesDB = new Properties();
            propertiesDB.load(fisDB);

            fisServer = new FileInputStream(PROPERTIES_FILE_SERVER);
            propertiesServer = new Properties();
            propertiesServer.load(fisServer);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.close();
        }

        DBService dbService = new DBService(propertiesDB);
        CoorDAO coorDAO = new CoorDAO(dbService.openConnection());
        try {
            coorDAO.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
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
            fisDB.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fisServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            server.start();
            LOGGER.info("Server is started");
        } catch (Exception e) {
            LOGGER.error("Server isn't started");
            LOGGER.error(e);
        }
    }
}
