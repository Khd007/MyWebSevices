package com.web;

/**
 * Created by khalid on 6/2/16.
 */
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.json.JSONObject;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.EnumSet;
import java.util.Enumeration;

public class ServletWithFilter extends HttpServlet{
    public static void main(final String[] args) throws Exception {
        Server server = new Server(8080);

        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        handler.addServletWithMapping(HelloServlet.class, "/*");
        handler.addFilterWithMapping(HelloPrintingFilter.class, "/*",EnumSet.of(DispatcherType.REQUEST));

        server.start();
        server.join();
        server.destroy();
    }
    //servlet handler class
    public static class HelloServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //if no Filter class then this will print default headers.
            response.setContentType(MediaType.TEXT_HTML);
            PrintWriter out = response.getWriter();
            Enumeration headerNames = request.getHeaderNames();

            while(headerNames.hasMoreElements()) {
                String paramName = (String)headerNames.nextElement();
                out.print("<h3>"+paramName+"<h3>" );
                String paramValue = request.getHeader(paramName);
                out.println("<h3>"+paramValue+"<h3>"+"<br>" );
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("<h1>Hello SimpleServlet</h1>");
        }
    }
    //Custom Filter class
    public static class HelloPrintingFilter implements Filter {
        public void init(FilterConfig arg0) throws ServletException {
        }
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            //This filter method adds custom header "pageRequestedOn" header to header list and then prints all headers
            //in the form of "JSON" object.
            response.setContentType(MediaType.APPLICATION_JSON);
            PrintWriter out = response.getWriter();

            MyWrapperClass obj = new MyWrapperClass((HttpServletRequest) request);
            obj.putHeader("pageRequestedOn", String.valueOf(System.currentTimeMillis()));

            out.println("All Request_Headers with Custom header \"pageRequestedOn\" Custom header.");

            JSONObject jobj = new JSONObject();
            Enumeration headerNames = obj.getHeaderNames();
            while(headerNames.hasMoreElements()) {
                String paramName = (String) headerNames.nextElement();
                String paramValue = obj.getHeader(paramName);
                jobj.put(paramName,paramValue);
            }
            out.println(jobj.toString(4));
        }
        public void destroy() {
        }
    }
}


