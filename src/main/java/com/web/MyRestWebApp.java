package com.web;

/**
 * Created by khalid on 6/2/16.
 */
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

class MyRestWebApp{
    //Simple servlet class using Jetty and Jersey
    public static void main(String arg[]) throws Exception {

        ResourceConfig config = new ResourceConfig();
        config.packages("com.web");
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        Server server = new Server  (2222);
        ServletContextHandler context = new ServletContextHandler(server, "/*");
        context.addServlet(servlet, "/*");

        server.start();
        server.join();
        server.destroy();

    }
}
