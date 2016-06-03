package com.web;

/**
 * Created by khalid on 6/2/16.
 */

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

final class MyWrapperClass extends HttpServletRequestWrapper {
        // holds custom header and value mapping
        private final Map<String, String> customHeaders;

        public MyWrapperClass(HttpServletRequest request){
            super(request);
            this.customHeaders = new HashMap<String, String>();
        }

        public void putHeader(String name, String value){
            this.customHeaders.put(name, value);
        }

        public String getHeader(String name) {
            // check the custom headers based on key search
            if(customHeaders.containsKey(name))
            return(customHeaders.get(name) );
            else
            return( super.getHeader( name ) );
        }

        public Enumeration<String> getHeaderNames() {
            //create a set containing all custom headers and add request Headers also.
            Set<String> set = new HashSet<String>(customHeaders.keySet());
            Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
            while (e.hasMoreElements()) {
                // add the names of the request headers into the list
                String n = e.nextElement();
                set.add(n);
            }

            // create an enumeration from the set and return
            return Collections.enumeration(set);
        }

}


