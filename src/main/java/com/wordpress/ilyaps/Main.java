package com.wordpress.ilyaps;

import com.wordpress.ilyaps.serverHelpers.Configuration;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        final Configuration confServer = new Configuration(LIST_PROPERIES_SERVER, PROPERTIES_FILE_SERVER);
        final Configuration confDB = new Configuration(LIST_PROPERIES_DB, PROPERTIES_FILE_DB);

        Servlet pushCoorServlet = new PushCoorServlet();
        Servlet getCoorServlet = new GetCoorServlet();

        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(pushCoorServlet), confServer.getValueOfProperty("push_coor"));
        context.addServlet(new ServletHolder(getCoorServlet), confServer.getValueOfProperty("get_coor"));

        final ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase("static");

        final Server server = new Server(new Integer(confServer.getValueOfProperty("port")));
        final HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});
        server.setHandler(handlers);

        try {
            server.start();
        } catch (Exception e) {
            LOGGER.error("Server isn't started");
            LOGGER.error(e);
        }
    }
}
