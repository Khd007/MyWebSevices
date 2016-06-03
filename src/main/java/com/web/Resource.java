package com.web;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by khalid on 5/31/16.
 */
    @Path("home")
    //This Resource class serves as a resource to myRestWebApp class.
    public class Resource {
        @GET
        @Path("hello")
        @Produces(MediaType.TEXT_PLAIN)
        //Get method with "Key-Value" pair passed as QueryParam
        public String helloWorld(@QueryParam("name") String name) {

            return "Hello," + name;
        }

        @POST
        @Path("post")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        @Produces(MediaType.TEXT_HTML)
        //Post method gets parameter from html form on site "http://localhost:63342/my-restWebService/target/site/index.html"
        public String postMethod(@FormParam("name") String name) {
            return "<h2>Hello, " + name + "</h2>";
        }
    }

