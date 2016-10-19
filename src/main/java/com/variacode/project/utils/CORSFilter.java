package com.variacode.project.utils;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author miguel@variacode.com
 */
public class CORSFilter implements Filter {

    public CORSFilter() {
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        ((HttpServletResponse) response).addHeader(
                "Access-Control-Allow-Origin", "*"
        );
        ((HttpServletResponse) response).addHeader(
                "Access-Control-Allow-Credentials", "true"
        );
        ((HttpServletResponse) response).addHeader(
                "Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD"
        );
        ((HttpServletResponse) response).addHeader(
                "Access-Control-Allow-Headers", "key,app,token,password,rut,email,emailpro,adminemail,body, X-Requested-With, origin, content-type, accept"
        );
        chain.doFilter(request, response);
    }
}
